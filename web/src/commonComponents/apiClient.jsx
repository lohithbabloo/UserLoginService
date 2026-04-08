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
  console.log("intercepted error:"),
  (error) => {
    console.log("intercepted error:", error.response);
    if (error.response?.status === 403) {
      window.location.href = "/forbidden";
    }
    return Promise.reject(error);
  },
);

export default baseurl;
