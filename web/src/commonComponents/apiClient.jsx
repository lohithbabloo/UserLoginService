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

baseurl.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (
      error.response &&
      (error.response.status === 401 || error.response.status === 403)
    ) {
      const originalRequest = error.config;
      if (originalRequest._retry) {
        window.location.href = "/";
        return Promise.reject(error);
      }
      originalRequest._retry = true;
      try {
        await baseurl.get("/api/v1/refreshToken");
      } catch (refreshError) {
        return Promise.reject(refreshError);
      }
      return baseurl(originalRequest);
    }
  },
);

export default baseurl;
