package com.dataox.service;

import com.dataox.model.Job;
import com.dataox.model.LaborFunction;
import com.dataox.repository.JobRepository;
import com.dataox.util.JobFunctionConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> scrapeAndSaveJobs(String jobFunction) {
        List<Job> jobs = new ArrayList<>();

        try {
            String encodedJobFunction = JobFunctionConverter.convertToEncodedValue(jobFunction);

            String url = "https://jobs.techstars.com/jobs?filter=" + encodedJobFunction;

            Document document = Jsoup.connect(url).get();

         //   Elements jobElements = document.select("sc-beqWaB sc-gueYoa diHipZ MYFxR");
            Elements jobElements = document.select(".job-info");

            for (Element jobElement : jobElements) {
                Job job = new Job();

                job.setPositionName(((TextNode) jobElement.getElementsByAttributeValue("itemprop", "title").get(0).childNode(0)).text());
                job.setUrlToOrganization(jobElement.getElementsByClass("sc-beqWaB iljPHq theme_only").get(0).attr("href"));
                job.setLogoUrl(jobElement.getElementsByAttributeValue("itemprop", "logo").get(0).attr("content"));
                job.setOrganizationTitle(jobElement.getElementsByAttributeValue("itemprop", "name").get(0).attr("content"));
                job.setPostedDate(LocalDate.parse(jobElement.getElementsByAttributeValue("itemprop", "datePosted").get(0).attr("content")));
                job.setDescription(jobElement.select(".job-description").html());
                job.setTags(getTagsByElements(jobElement.getElementsByClass("sc-dmqHEX dncTlc")));

                LaborFunction laborFunction = LaborFunction.fromString(jobFunction);
                if (laborFunction != null) {
                    String enumValue = laborFunction.getValue();
                    job.setLaborFunction(enumValue);
                } else {
                    System.out.println("LaborFunction not found for value: " + jobFunction);
                }

                Elements addressElements = jobElement.getElementsByAttributeValue("itemprop", "address");
                String address = addressElements.isEmpty() ? "NOT_FOUND" : addressElements.get(0).attr("content");
                job.setAddress(address);

                jobRepository.save(job);

                jobs.add(job);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    private String getTagsByElements(Elements tags) {
        return tags.stream()
                .map(element -> element.childNode(0))
                .map(node -> node.toString())
                .map(tag -> tag.substring(1))
                .collect(Collectors.joining(", "));
    }
}
