package com.example.backend;

import com.example.backend.model.Department;
import com.example.backend.model.Story;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {

    @Test
    public void testStoryConstructor() {
        Department department = new Department("Engineering");
        String headline = "New Tech Innovations";
        String content = "Details about the latest tech innovations.";

        Story story = new Story(headline, content, department);

        assertNotNull(story);
        assertEquals(headline, story.getHeadline());
        assertEquals(content, story.getContent());
        assertEquals(department, story.getDepartment());
        assertNotNull(story.getPostingTime());
        assertEquals(0, story.getLikes());
    }

    @Test
    public void testDefaultConstructor() {
        Story story = new Story();

        assertNotNull(story);
        assertNull(story.getHeadline());
        assertNull(story.getContent());
        assertNull(story.getDepartment());
        assertNull(story.getPostingTime());
        assertEquals(0, story.getLikes());
    }

    @Test
    public void testSetHeadline() {
        Story story = new Story();
        String headline = "Updated Headline";

        story.setHeadline(headline);

        assertEquals(headline, story.getHeadline());
    }

    @Test
    public void testSetContent() {
        Story story = new Story();
        String content = "Updated content for the story.";

        story.setContent(content);

        assertEquals(content, story.getContent());
    }

    @Test
    public void testSetDepartment() {
        Story story = new Story();
        Department department = new Department("HR");

        story.setDepartment(department);

        assertEquals(department, story.getDepartment());
    }

    @Test
    public void testGetPostingTime() {
        Department department = new Department("Marketing");
        String headline = "Marketing Strategies";
        String content = "Discussion about new marketing strategies.";

        Story story = new Story(headline, content, department);
        LocalDateTime postingTime = story.getPostingTime();

        assertNotNull(postingTime);
        assertTrue(postingTime.isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    public void testLike() {
        Department department = new Department("Finance");
        Story story = new Story("Financial News", "Latest updates in finance.", department);

        story.like();
        assertEquals(1, story.getLikes());

        story.like();
        assertEquals(2, story.getLikes());
    }

    @Test
    public void testGetId() {
        Department department = new Department("IT");
        Story story = new Story("IT Trends", "Trends in the IT industry.", department);

        assertEquals(0, story.getId());
    }
}
