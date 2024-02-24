package com.coderuck.tasksubmissionservice.controller;

import com.coderuck.tasksubmissionservice.modal.Submission;
import com.coderuck.tasksubmissionservice.modal.UserDTO;
import com.coderuck.tasksubmissionservice.service.SubmissionService;
import com.coderuck.tasksubmissionservice.service.TaskService;
import com.coderuck.tasksubmissionservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @PostMapping
    private ResponseEntity<Submission> submitTask(
        @RequestParam Long task_id,
        @RequestParam String github_link,
        @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(task_id, github_link, user.getId(), jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    private ResponseEntity<Submission> getSubmissionById(
            @PathVariable Long id,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Submission>> getAllSubmissions(
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    private ResponseEntity<List<Submission>> getSubmissionsByTaskId(
            @PathVariable Long taskId,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable Long id,
            @RequestParam ("status") String status,
            @RequestHeader ("Authorization") String jwt
    ) throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id, status);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }


}
