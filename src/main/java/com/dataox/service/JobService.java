package com.dataox.service;

import com.dataox.model.Job;
import com.dataox.model.LaborFunction;
import com.dataox.repository.JobRepository;
import com.dataox.util.JobFunctionConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling job-related operations.
 */
@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * Scrapes job information from a website, saves it to the database, and returns the list of jobs.
     *
     * @param jobFunction The job function to filter the job search.
     * @return List of Job objects containing scraped job information.
     * @throws RuntimeException if an IOException occurs during the scraping process.
     */
    public List<Job> scrapeAndSaveJobs(String jobFunction) {
        List<Job> jobs = new ArrayList<>();

        try {
            String encodedJobFunction = JobFunctionConverter.convertToEncodedValue(jobFunction);

            String url = "https://jobs.techstars.com/jobs?filter=" + encodedJobFunction;

            Document document = Jsoup.connect(url).get();

            Elements jobElements = document.select(".job-info");

            for (Element jobElement : jobElements) {

                String jobPageUrl = jobElement.select("a.sc-beqWaB").attr("href");
                if ((jobPageUrl.contains("companies"))) {
                     jobPageUrl = "https://jobs.techstars.com" + jobPageUrl;
                }

                Optional<Job> existingJob = jobRepository.findByJobPageUrl(jobPageUrl);

                Job job = createJobFromElement(jobElement, jobFunction, jobPageUrl);

                if (existingJob.isEmpty()) {
                    jobRepository.save(job);
                }
                jobs.add(job);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jobs;
    }

    /**
     * Creates a Job object from the provided HTML element and additional information.
     *
     * @param jobElement   The HTML element containing job information.
     * @param jobFunction  The job function used for categorization.
     * @param jobPageUrl   The URL of the job page.
     * @return A Job object created from the HTML element.
     */
    private Job createJobFromElement(Element jobElement, String jobFunction, String jobPageUrl) {
        Job job = new Job();

        job.setPositionName(((TextNode) jobElement.getElementsByAttributeValue("itemprop", "title").get(0).childNode(0)).text());
        job.setUrlToOrganization(jobElement.getElementsByClass("sc-beqWaB iljPHq theme_only").get(0).attr("href"));
        job.setLogoUrl(jobElement.getElementsByAttributeValue("itemprop", "logo").get(0).attr("content"));
        job.setOrganizationTitle(jobElement.getElementsByAttributeValue("itemprop", "name").get(0).attr("content"));
        job.setPostedDate(LocalDate.parse(jobElement.getElementsByAttributeValue("itemprop", "datePosted").get(0).attr("content")));
        job.setDescription(String.valueOf(jobElement.getElementsByClass("sc-beqWaB fmCCHr")));
        job.setTags(getTagsByElements(jobElement.getElementsByClass("sc-dmqHEX dncTlc")));
        job.setLaborFunction(LaborFunction.fromString(jobFunction));
        job.setJobPageUrl(jobPageUrl);

        Elements addressElements = jobElement.getElementsByAttributeValue("itemprop", "address");
        String address = addressElements.isEmpty() ? "NOT_FOUND" : addressElements.get(0).attr("content");
        job.setAddress(address);

        return job;
    }

    /**
     * Retrieves tags from HTML elements and concatenates them into a comma-separated string.
     *
     * @param tags The HTML elements containing tags.
     * @return A comma-separated string of tags.
     */
    private String getTagsByElements(Elements tags) {
        return tags.stream()
                .map(element -> element.childNode(0))
                .map(Node::toString)
                .map(tag -> tag.substring(1))
                .collect(Collectors.joining(", "));
    }
}
