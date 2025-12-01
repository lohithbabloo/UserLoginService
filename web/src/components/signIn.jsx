import { useState } from "react";
function SignIn() {
  const [password, setPassword] = useState("");
  const [rePassword, setRePassword] = useState("");
  const handlePasswordCheck = (e) => {
    e.preventDefault();
    if (password === "" || rePassword === "") {
      alert("Please fill in all fields");
      return;
    }
    if (password !== rePassword) {
      alert("Passwords do not match");
      return;
    }
  };
  return (
    <div>
      <input type="text" placeholder="Username" required></input>
      <input type="email" placeholder="Email" required></input>
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
      <button onClick={handlePasswordCheck}>Sign In</button>
    </div>
  );
}

export default SignIn;
