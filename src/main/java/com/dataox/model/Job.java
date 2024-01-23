package com.dataox.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * Job entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Job {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 1000)
    private String jobPageUrl;
    private String positionName;
    @Column(length = 1000)
    private String urlToOrganization;
    private String logoUrl;
    private String organizationTitle;
    private String laborFunction;
    private String address;
    private LocalDate postedDate;
    private String description;
    private String tags;
}
