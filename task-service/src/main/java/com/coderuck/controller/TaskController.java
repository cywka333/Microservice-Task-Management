package com.coderuck.controller;

import com.coderuck.modal.Task;
import com.coderuck.modal.TaskStatus;
import com.coderuck.modal.UserDTO;
import com.coderuck.service.TaskServiceImplementation;
import com.coderuck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImplementation taskServiceImplementation;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        Task createdTask = taskServiceImplementation.createTask(task, user.getRole());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        Task task = taskServiceImplementation.getTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        List<Task> tasks = taskServiceImplementation.assignedUsersTask(user.getId(), status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        List<Task> tasks = taskServiceImplementation.getAllTask(status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        Task task = taskServiceImplementation.assignedToUser(userId, id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);

        Task task = taskServiceImplementation.updateTask(id, req, user.getId());

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception {

        Task task = taskServiceImplementation.completeTask(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {

        taskServiceImplementation.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
