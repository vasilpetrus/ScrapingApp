package com.dataox.controller;

import com.dataox.model.Job;
import com.dataox.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/scrape/{jobFunction}")
    public ResponseEntity<List<Job>> scrapeAndSaveJobs(@PathVariable String jobFunction) {
        List<Job> jobs = jobService.scrapeAndSaveJobs(jobFunction);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
