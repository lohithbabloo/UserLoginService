import axios from "axios";

const baseurl = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
    "X-XSRF-TOKEN": document.cookie
      .split("; ")
      .find((row) => row.startsWith("XSRF-TOKEN="))
      ?.split("=")[1],
  },
});

// Error interceptor to handle 403 responses
baseurl.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 403) {
      // Redirect to forbidden page
      window.location.href = "/forbidden";
    }
    return Promise.reject(error);
  },
);

export default baseurl;
