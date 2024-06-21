import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { TextField, TextareaAutosize } from '@mui/material';

const AddStoryModal = ({ isOpen, onClose, onConfirm }) => {
  const [headline, setHeadline] = useState('');
  const [content, setContent] = useState('');

  const handleSave = () => {
    // Perform validation
    if (!headline.trim() || !content.trim()) {
      alert('Please fill in both headline and content.');
      return;
    }

    onConfirm({ headline, content });
    setHeadline('');
    setContent('');
    onClose();
  };

  return (
    <AnimatePresence>
      {isOpen && (
        <motion.div
          className="modal-overlay"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ duration: 0.3 }}
        >
          <motion.div
            className="modal"
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            exit={{ scale: 0 }}
            transition={{ duration: 0.3 }}
          >
            <div className="modal-content">
              <h2 className="text-center mb-4">Add New Story</h2>
              <TextField
                size="small"
                label="Headline"
                variant="outlined"
                value={headline}
                onChange={(e) => setHeadline(e.target.value)}
              />
              <div className="mt-3">Content</div>
              <TextareaAutosize
                value={content}
                onChange={(e) => setContent(e.target.value)}
                minRows={4} // Adjust as needed
                style={{ width: '100%', resize: 'vertical' }}
              />
              <div className="modal-actions">
                <button onClick={handleSave}>Confirm</button>
                <button onClick={onClose}>Cancel</button>
              </div>
            </div>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default AddStoryModal;
