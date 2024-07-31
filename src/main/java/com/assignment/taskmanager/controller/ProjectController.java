package com.assignment.taskmanager.controller;

import com.assignment.taskmanager.model.Project;
import com.assignment.taskmanager.model.Task;
import com.assignment.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects") // Base URL for all mappings in this controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        boolean added = projectService.addProject(project);
        if (!added) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.ok("Project added successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateProject(@RequestBody Project project) {
        boolean updated = projectService.updateProject(project);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.ok("Project updated successfully");
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        boolean deleted = projectService.deleteProject(projectId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
        return ResponseEntity.ok("Project deleted successfully");
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<String> addTask(@PathVariable Long projectId, @RequestBody Task task) {
        boolean added = projectService.addTask(task, projectId);
        if (!added) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add task");
        }
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/{projectId}/tasks")
    public ResponseEntity<String> updateTask(@PathVariable Long projectId, @RequestBody Task task) {
        boolean updated = projectService.updateTask(task, projectId);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update task");
        }
        return ResponseEntity.ok("Task updated successfully");
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        boolean deleted = projectService.deleteTask(taskId, projectId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete task");
        }
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        Task task = projectService.getTask(taskId, projectId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
}
