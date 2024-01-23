package com.dataox.controller;

import com.dataox.model.Job;
import com.dataox.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    public String getHomePage(){
        return "jobsearch";
    }

    @GetMapping("/jobs/scrape/{jobFunction}")
    public ResponseEntity<List<Job>> scrapeAndSaveJobs(@PathVariable String jobFunction) {
        System.out.println(jobFunction);
        List<Job> jobs = jobService.scrapeAndSaveJobs(jobFunction);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping("/search")
    public String searchJobs(Model model, @RequestParam String jobFunction) {
        System.out.println(jobFunction);
        List<Job> jobs = jobService.scrapeAndSaveJobs(jobFunction);
        model.addAttribute("jobs", jobs);
        return "searchresults";
    }
}