console.log(process.env.NODE_ENV)

const apiConfig = {
	baseUrl:
		process.env.NODE_ENV === 'development'
			? 'http://localhost:8080'
			: 'http://localhost:8080',
			// : 'http://192.168.49.2:30001',
	endpoints: {
		departments: '/departments',
		stories: '/stories',
		getStoriesByDepartment: "/stories/department/"
	},
};

export default apiConfig;
