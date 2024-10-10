import axios from 'axios';

// Create an Axios instance without a baseURL, as it defaults to the current origin
const apiClient = axios.create({
    headers: {
        'Content-Type': 'application/json',
    },
});

// Export the Axios instance for use in components
export default apiClient;
