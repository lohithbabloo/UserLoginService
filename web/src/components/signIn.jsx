import { useState } from "react";
import axios from "axios";

const baseurl = axios.create({
  baseURL: "http://localhost:8080/api/v1/user",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});
function SignIn() {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rePassword, setRePassword] = useState("");
  const handlePasswordCheck = (e) => {
    if (password === "" || rePassword === "") {
      alert("Please fill in all fields");
      return;
    }
    if (password !== rePassword) {
      alert("Passwords do not match");
      return;
    }
  };

  const handleSignIn = async (e) => {
    handlePasswordCheck();
    const payload = {
      username: userName,
      password: password,
      email: email,
    };
    try {
      const response = await baseurl.post("/login", payload);
      console.log(response);
      setUserName("");
      setEmail("");
      setRePassword("");
      setPassword("");
    } catch (e) {
      alert("signup failed man!!!!");
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
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      ></input>
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      ></input>
      <input
        type="password"
        placeholder="Re-Confirm Password"
        value={rePassword}
        onChange={(e) => setRePassword(e.target.value)}
        required
      ></input>
      <button onClick={handleSignIn}>Sign In</button>
    </div>
  );
}

export default SignIn;
