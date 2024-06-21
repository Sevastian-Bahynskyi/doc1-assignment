import React from 'react';
import '../styles/DepartmentsPage.css';

function DepartmentsPage({ departments, departmentClick }) {
    return (
        <div className="text-center">
            <h1>Departments</h1>
            <div className="departments-grid-container">
                {departments.map((department, index) => (
                    <div
                        key={index}
                        className="departments-grid-item"
                        onClick={() => departmentClick(department)}
                    >
                        {department.name}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default DepartmentsPage;
