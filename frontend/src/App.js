import apiConfig from './apiConfig';
import axios from 'axios';
import './styles/App.css';
import { useEffect, useState } from 'react';
import { PuffLoader } from 'react-spinners';
import DepartmentsPage from './components/DepartmentsPage';
import { Routes, Route, useNavigate } from 'react-router-dom';
import StoriesPage from './components/StoriesPage';

function App() {
    const [departments, setDepartments] = useState([]);
    const [isLoading, setLoading] = useState(false);
    const navigate = useNavigate();

    function loadStories(department) {
        console.log(department);
        navigate('/stories', { state: { department: department} });
    }

    useEffect(() => {
        setLoading(true);
        axios
            .get(`${apiConfig.baseUrl}${apiConfig.endpoints.departments}`)
            .then((response) => {
                console.log('Departments fetched:', response.data);
                setDepartments(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error:', error);
                alert("Couldn't fetch departments");
                setLoading(false); // Ensure loading state is reset even on error
            });
    }, []);

    if (isLoading) {
        return (
            <div className="App">
                <PuffLoader />
            </div>
        );
    }

    return (
        <div className="App">
            <Routes>
                <Route
                    path="/"
                    element={
                        <DepartmentsPage
                            departments={departments}
                            departmentClick={loadStories}
                        />
                    }
                />
                <Route
                    path="/stories"
                    element={<StoriesPage />}
                />
            </Routes>
        </div>
    );
}

export default App;
