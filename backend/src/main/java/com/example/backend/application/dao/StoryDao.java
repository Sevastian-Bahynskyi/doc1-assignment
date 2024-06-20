package com.example.backend.application.dao;

import com.example.backend.model.Department;
import com.example.backend.model.Story;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StoryDao {
    CompletableFuture<Story> createStory(String headline, String content, int departmentId);
    CompletableFuture<List<Story>> getAllStories();
    CompletableFuture<List<Story>> getStoriesByDepartmentId(int id);
    CompletableFuture<Story> getStoryById(int id);
}
