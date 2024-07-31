package com.assignment.taskmanager.service;

import com.assignment.taskmanager.model.Project;
import com.assignment.taskmanager.model.Task;
import com.assignment.taskmanager.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public boolean addProject(Project project) {
        return projectRepository.save(project) != null;
    }

    public boolean updateProject(Project project) {
        if (projectRepository.existsById(project.getId())) {
            projectRepository.save(project);
            return true;
        }
        return false;
    }

    public boolean deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
            return true;
        }
        return false;
    }

    public Task getTask(Long taskId, Long projectId) {
        Project project = getProject(projectId);
        if (project != null) {
            return project.getTasks().stream()
                    .filter(task -> task.getId().equals(taskId))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean addTask(Task task, Long projectId) {
        Project project = getProject(projectId);
        if (project != null) {
            task.setProject(project);
            project.getTasks().add(task);
            projectRepository.save(project); // Save the project to persist the new task
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task, Long projectId) {
        Project project = getProject(projectId);
        if (project != null) {
            for (int i = 0; i < project.getTasks().size(); i++) {
                if (project.getTasks().get(i).getId().equals(task.getId())) {
                    project.getTasks().set(i, task);
                    projectRepository.save(project); // Save the project to persist the updated task
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteTask(Long taskId, Long projectId) {
        Project project = getProject(projectId);
        if (project != null) {
            boolean removed = project.getTasks().removeIf(task -> task.getId().equals(taskId));
            if (removed) {
                projectRepository.save(project); // Save the project to persist the task removal
                return true;
            }
        }
        return false;
    }
}
