import { useState } from "react";
import axios from "axios";

const baseurl = axios.create({
  baseURL: "http://localhost:8080/auth/api/v1",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
    "X-XSRF-TOKEN": document.cookie
      .split("; ")
      .find((row) => row.startsWith("XSRF-TOKEN="))
      ?.split("=")[1],
  },
});
function SignIn() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const handleSignIn = async (e) => {
    const payload = {
      username: userName,
      password: password,
    };
    try {
      const response = await baseurl.post("/login", payload);
      console.log(baseurl);
      console.log(response);
      setUserName("");
      setPassword("");
      if (response.status === 200) {
        alert("Login successful!");
      }
    } catch (e) {
      alert("login failed man!!!!");
    }
  };
  return (
    <div>
      <input
        type="text"
        placeholder="Username"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
        required
      ></input>
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      ></input>
      <button onClick={handleSignIn}>Log In</button>
    </div>
  );
}

export default SignIn;
