package com.dataox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

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
    private String jobPageUrl;
    private String positionName;
    private String urlToOrganization;
    private String logoUrl;
    private String organizationTitle;
    private String laborFunction;
    private String address;
    private LocalDate postedDate;
    private String description;
    private String tags;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
