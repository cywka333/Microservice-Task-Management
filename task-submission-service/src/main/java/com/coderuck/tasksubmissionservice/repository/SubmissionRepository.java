package com.coderuck.tasksubmissionservice.repository;

import com.coderuck.tasksubmissionservice.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByTaskId(Long taskId);
}
