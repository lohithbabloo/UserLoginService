import { useEffect } from "react";
import axios from "axios";
function GithubSuccess() {
  useEffect(() => {
    const response = axios
      .get("http://localhost:8080/api/github/user", { withCredentials: true })
      .then((response) => {
        console.log("User info:", response.data);
      })
      .catch((error) => {
        console.error("Error fetching user info:", error);
      });
    console.log(response);
  }, []);
  return (
    <div>
      <h1>Github Login Successful</h1>
      <p>Welcome to the app!</p>
    </div>
  );
}

export default GithubSuccess;
