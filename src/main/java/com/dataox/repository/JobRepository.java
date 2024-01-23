package com.dataox.repository;

import com.dataox.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for the Job entity, extending JpaRepository for basic CRUD operations.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    /**
     * Custom query method to find a Job by its unique Job_page_url field.
     *
     * @param jobPageUrl The Job_page_url to search for.
     * @return An Optional containing the found Job or an empty Optional if not found.
     */
    Optional<Job> findByJobPageUrl(String jobPageUrl);
}
