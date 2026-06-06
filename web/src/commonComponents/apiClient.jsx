import axios from "axios";

const baseurl = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

baseurl.interceptors.request.use((request) => {
  const xsrfToken = document.cookie
    .split("; ")
    .find((row) => row.startsWith("XSRF-TOKEN="))
    ?.split("=")[1];
  request.headers["X-XSRF-TOKEN"] = xsrfToken;
  return request;
});
baseurl.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (
      error.response &&
      (error.response.status === 401 || error.response.status === 403) //need to handle 500 and 204
    ) {
      const originalRequest = error.config;
      console.log("Original Request URL:", originalRequest.url);
      if (originalRequest.url === "/auth/v1/refreshToken") {
        window.location.href = "/";
        return Promise.reject(error);
      }
      if (originalRequest._retry) {
        window.location.href = "/";
        return Promise.reject(error);
      }
      originalRequest._retry = true;
      try {
        await baseurl.get("/auth/v1/refreshToken");
      } catch (refreshError) {
        return Promise.reject(refreshError);
      }
      return baseurl(originalRequest);
    }
    return Promise.reject(error);
  },
);

export default baseurl;
