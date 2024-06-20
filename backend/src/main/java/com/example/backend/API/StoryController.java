package com.example.backend.API;

import com.example.backend.application.dao.StoryDao;
import com.example.backend.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryDao storyDao;

    @Autowired
    public StoryController(StoryDao storyDao) {
        this.storyDao = storyDao;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Story>> CreateAsync(@RequestBody StoryRequest request) {
        try {
            var story = storyDao.createStory(request.getHeadline(), request.getContent(), request.getDepartmentId());
            return story.thenApply(createdStory -> ResponseEntity.created(URI.create("/stories/" + createdStory.getId())).body(createdStory));
        } catch (Exception ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(500).body(null));
        }
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Story>>> GetAllAsync() {
        try {
            var stories = storyDao.getAllStories();
            return stories.thenApply(ResponseEntity::ok);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(500).body(null));
        }
    }

    @GetMapping("/department/{departmentId}")
    public CompletableFuture<ResponseEntity<List<Story>>> GetStoriesOfAsync(@PathVariable int departmentId) {
        try {
            var stories = storyDao.getStoriesByDepartmentId(departmentId);
            return stories.thenApply(ResponseEntity::ok);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(500).body(null));
        }
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Story>> GetStoryAsync(@PathVariable int id) {
        try {
            var story = storyDao.getStoryById(id);
            return story.thenApply(s -> {
                if (s == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.ok(s);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            return CompletableFuture.completedFuture(ResponseEntity.status(500).body(null));
        }
    }
}

class StoryRequest {
    private String headline;
    private String content;
    private int departmentId;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}