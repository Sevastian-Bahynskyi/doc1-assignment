package com.example.backend;

import com.example.backend.API.StoryController;
import com.example.backend.API.StoryRequest;
import com.example.backend.application.dao.StoryDao;
import com.example.backend.model.Department;
import com.example.backend.model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StoryControllerTest {

    @Mock
    private StoryDao storyDao;

    @InjectMocks
    private StoryController storyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStory() throws ExecutionException, InterruptedException {
        // Mock request
        StoryRequest request = new StoryRequest();
        request.setHeadline("New Story Headline");
        request.setContent("New Story Content");
        request.setDepartmentId(1);

        // Mock data
        Department department = new Department("IT");
        Story createdStory = new Story(request.getHeadline(), request.getContent(), department);

        // Mock behavior of storyDao.createStory()
        when(storyDao.createStory(anyString(), anyString(), anyInt()))
                .thenReturn(CompletableFuture.completedFuture(createdStory));

        // Call controller method
        CompletableFuture<ResponseEntity<Story>> response = storyController.CreateAsync(request);

        // Verify
        assertEquals(ResponseEntity.created(URI.create("/stories/" + createdStory.getId())).body(createdStory).getBody(),
                response.get().getBody());
        verify(storyDao, times(1)).createStory(eq(request.getHeadline()), eq(request.getContent()), eq(request.getDepartmentId()));
    }


    @Test
    public void testGetAllStories() throws ExecutionException, InterruptedException {
        // Mock data
        List<Story> stories = new ArrayList<>();
        Department department = new Department("IT");
        stories.add(new Story("Headline 1", "Content 1", department));
        stories.add(new Story("Headline 2", "Content 2", department));

        // Mock behavior of storyDao.getAllStories()
        when(storyDao.getAllStories()).thenReturn(CompletableFuture.completedFuture(stories));

        // Call controller method
        CompletableFuture<ResponseEntity<List<Story>>> response = storyController.GetAllAsync();

        // Verify
        assertEquals(ResponseEntity.ok(stories).getBody(), response.get().getBody());
        verify(storyDao, times(1)).getAllStories();
    }

    @Test
    public void testGetAllStories_emptyList() throws ExecutionException, InterruptedException {
        // Mock behavior of storyDao.getAllStories() returning an empty list
        when(storyDao.getAllStories()).thenReturn(CompletableFuture.completedFuture(new ArrayList<>()));

        // Call controller method
        CompletableFuture<ResponseEntity<List<Story>>> response = storyController.GetAllAsync();

        // Verify
        assertEquals(ResponseEntity.ok(new ArrayList<>()).getBody(), response.get().getBody());
        verify(storyDao, times(1)).getAllStories();
    }


    @Test
    public void testGetStoryById_existingStory() throws ExecutionException, InterruptedException {
        int storyId = 1;
        Department department = new Department("HR");
        Story story = new Story("Test Headline", "Test Content", department);

        // Mock behavior of storyDao.getStoryById()
        when(storyDao.getStoryById(anyInt())).thenReturn(CompletableFuture.completedFuture(story));

        // Call controller method
        CompletableFuture<ResponseEntity<Story>> response = storyController.GetStoryAsync(storyId);

        // Verify
        assertEquals(ResponseEntity.ok(story).getBody(), response.get().getBody());
        verify(storyDao, times(1)).getStoryById(storyId);
    }

    @Test
    public void testGetStoryById_invalidId() throws Exception {
        // Mock behavior of storyDao.getStoryById() with an invalid id
        when(storyDao.getStoryById(-1))
                .thenReturn(CompletableFuture.completedFuture(null)); // Assuming getStoryById returns null for non-existing story

        // Call controller method
        CompletableFuture<ResponseEntity<Story>> response = storyController.GetStoryAsync(-1);

        // Verify
        assertEquals(ResponseEntity.notFound().build().getBody(), response.get().getBody());
    }


    @Test
    public void testGetStoryById_nonExistingStory() throws ExecutionException, InterruptedException {
        int nonExistingStoryId = 100;

        // Mock behavior of storyDao.getStoryById() returning null
        when(storyDao.getStoryById(anyInt())).thenReturn(CompletableFuture.completedFuture(null));

        // Call controller method
        CompletableFuture<ResponseEntity<Story>> response = storyController.GetStoryAsync(nonExistingStoryId);

        // Verify
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null).getBody(), response.get().getBody());
    }

    @Test
    public void testGetStoriesByDepartmentId() throws ExecutionException, InterruptedException {
        int departmentId = 1;
        // Mock data
        List<Story> stories = new ArrayList<>();
        Department department = new Department("HR");
        stories.add(new Story("Headline 1", "Content 1", department));
        stories.add(new Story("Headline 2", "Content 2", department));

        // Mock behavior of storyDao.getStoriesByDepartmentId()
        when(storyDao.getStoriesByDepartmentId(anyInt())).thenReturn(CompletableFuture.completedFuture(stories));

        // Call controller method
        CompletableFuture<ResponseEntity<List<Story>>> response = storyController.GetStoriesOfAsync(departmentId);

        // Verify
        assertEquals(ResponseEntity.ok(stories).getBody(), response.get().getBody());
        verify(storyDao, times(1)).getStoriesByDepartmentId(departmentId);
    }

    @Test
    public void testGetStoriesByDepartmentId_noStoriesFound() throws ExecutionException, InterruptedException {
        int departmentId = 2; // Assuming department with ID 2 has no stories

        // Mock behavior of storyDao.getStoriesByDepartmentId() returning an empty list
        when(storyDao.getStoriesByDepartmentId(anyInt())).thenReturn(CompletableFuture.completedFuture(new ArrayList<>()));

        // Call controller method
        CompletableFuture<ResponseEntity<List<Story>>> response = storyController.GetStoriesOfAsync(departmentId);

        // Verify
        assertEquals(ResponseEntity.ok(new ArrayList<>()).getBody(), response.get().getBody());
        verify(storyDao, times(1)).getStoriesByDepartmentId(departmentId);
    }

}
