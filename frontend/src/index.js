import React from 'react';
import ReactDOM from 'react-dom/client';
import '../src/styles/index.css';
import App from './App'; // Replace with your main component path
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router } from 'react-router-dom'; // Import BrowserRouter


ReactDOM.createRoot(document.getElementById("root")).render(
	<Router>
	  <React.StrictMode>
		<App />
	  </React.StrictMode>
	</Router>
  );

reportWebVitals();
