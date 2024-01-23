package com.dataox.controller;

import com.dataox.model.Job;
import com.dataox.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * Handles GET requests for the home page.
     *
     * @return The name of the Thymeleaf template for the home page ("jobsearch").
     */
    @GetMapping("/")
    public String getHomePage(){
        return "jobsearch";
    }

    /**
     * Handles POST requests for job searches.
     *
     * @param model       The Spring MVC model to which attributes can be added for rendering in the view.
     * @param jobFunction The selected job function submitted via the form.
     * @return The name of the Thymeleaf template for displaying search results ("searchresults").
     */
    @PostMapping("/search")
    public String searchJobs(Model model, @RequestParam String jobFunction) {
        System.out.println(jobFunction);
        List<Job> jobs = jobService.scrapeAndSaveJobs(jobFunction);
        model.addAttribute("jobs", jobs);
        return "searchresults";
    }
}