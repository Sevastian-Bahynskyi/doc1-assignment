import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { motion, AnimatePresence } from 'framer-motion';
import axios from 'axios';
import { format } from 'date-fns';
import '../styles/StoriesPage.css';
import apiConfig from '../apiConfig';
import AddStoryModal from './AddStoryModal'; // Adjust path as needed

function StoryComponent({ story }) {
  return (
    <div className="story-card">
      <h3 id="headline">{story.headline}</h3>
      <label id="content"><b>Content:</b> {story.content}</label>
      <label id="posting-time">
        <b>Posting time:</b> {format(new Date(story.postingTime), 'HH:mm (dd/MM)')}
      </label>
    </div>
  );
}

function StoriesPage({ storyClick }) {
  const location = useLocation();

  
  const { department } = location.state || {
    department: {},
  };

  const [stories, setStories] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
        axios
            .get(
                `${apiConfig.baseUrl}${apiConfig.endpoints.getStoriesByDepartment}${department.id}`
            )
            .then((response) => {
                console.log('Stories fetched:', response.data);
                setStories(response.data)
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error:', error);
                alert("Couldn't fetch stories");
                setLoading(false); // Ensure loading state is reset even on error
            });
  }, [department.id]);

  const addNewStory = (newStoryData) => {
	newStoryData.departmentId = department.id; // Assuming department.id is accessible here
  
	console.log(JSON.stringify(newStoryData))
	axios
	  .post(
		`${apiConfig.baseUrl}${apiConfig.endpoints.stories}`,
		JSON.stringify(newStoryData),
		{
		  headers: {
			'Content-Type': 'application/json'
		  }
		}
	  )
	  .then((response) => {
		const createdStory = response.data;
		setStories([createdStory, ...stories]);
	  })
	  .catch((error) => {
		console.error('Error adding new story:', error);
	  });
  };
  

  const toggleModal = () => {
    setIsModalOpen(!isModalOpen);
  };

  return (
    <div className="text-center">
      <h1>Stories of {department.name}</h1>
      
      <div className="stories-grid-container">
        <AnimatePresence>
          {stories.map((story, index) => (
            <motion.div
              key={story.id}
              className="stories-grid-item"
              onClick={() => storyClick && storyClick(story.id)}
              initial={{ x: '-100%', opacity: 0 }}
              animate={{ x: 0, opacity: 1 }}
              exit={{ x: '100%', opacity: 0 }}
              transition={{ duration: 0.5, ease: 'easeInOut' }}
              whileTap={{ scale: 0.95 }}
              style={{ zIndex: stories.length - index }}
            >
              <StoryComponent story={story} />
            </motion.div>
          ))}
        </AnimatePresence>
      </div>

      <div className="fixed-bottom">
        <button onClick={toggleModal}>Add New Story</button>
      </div>

      <AddStoryModal
        isOpen={isModalOpen}
        onClose={toggleModal}
        onConfirm={storyData => addNewStory(storyData)}
      />
    </div>
  );
}

export default StoriesPage;
