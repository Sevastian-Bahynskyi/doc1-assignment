package com.example.backend.application.dao_impl;

import com.example.backend.application.dao.StoryDao;
import com.example.backend.model.Department;
import com.example.backend.model.Story;
import com.example.backend.repository.DepartmentRepository;
import com.example.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StoryDaoImpl implements StoryDao {

    private final StoryRepository storyRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public StoryDaoImpl(StoryRepository storyRepository, DepartmentRepository departmentRepository) {
        this.storyRepository = storyRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public CompletableFuture<Story> createStory(String headline, String content, int departmentId) {
        return CompletableFuture.supplyAsync(() -> {
            Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("Department with id " + departmentId + " not found"));
            Story story = new Story(headline, content, department);
            return storyRepository.save(story);
        });
    }


    @Override
    public CompletableFuture<List<Story>> getAllStories() {
        return CompletableFuture.supplyAsync(() -> {
            List<Story> stories = storyRepository.findAll();
            stories.forEach(story -> story.getDepartment()); // Eager loading
            return stories;
        });
    }

    @Override
    public CompletableFuture<List<Story>> getStoriesByDepartmentId(int id) {
        return CompletableFuture.supplyAsync(() -> {
            List<Story> stories = storyRepository.findByDepartmentId(id);
            stories.forEach(story -> story.getDepartment()); // Eager loading
            return stories;
        });
    }

    @Override
    public CompletableFuture<Story> getStoryById(int id) {
        return CompletableFuture.supplyAsync(() -> {
            Story story = storyRepository.findById(id).orElse(null);
            if (story != null) {
                story.getDepartment(); // Eager loading
            }
            return story;
        });
    }
}
