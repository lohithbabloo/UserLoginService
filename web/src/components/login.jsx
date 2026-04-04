import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router";

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
function Login() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSignIn = async (e) => {
    e.preventDefault();
    setError("");
    
    if (!userName || !password) {
      setError("Please fill in all fields");
      return;
    }
    
    setLoading(true);
    const payload = {
      username: userName,
      password: password,
    };
    try {
      const response = await baseurl.post("/login", payload);
      setUserName("");
      setPassword("");
      if (response.status === 200) {
        alert("Login successful!");
        navigate("/");
      }
    } catch (e) {
      setError("Login failed. Please check your credentials.");
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white rounded-2xl shadow-2xl p-8 md:p-10">
          <h1 className="text-4xl font-bold text-center text-gray-900 mb-2">Welcome Back</h1>
          <p className="text-center text-gray-700 mb-8">Sign in to your account</p>
          
          <form onSubmit={handleSignIn} className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Username</label>
              <input
                type="text"
                placeholder="Enter your username"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Password</label>
              <input
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>
            
            {error && (
              <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded">
                <p className="text-red-700 text-sm">{error}</p>
              </div>
            )}
            
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-gray-700 text-white font-bold py-3 rounded-lg hover:shadow-lg hover:scale-105 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed disabled:scale-100 hover:bg-gray-600"
            >
              {loading ? "Signing in..." : "Sign In"}
            </button>
          </form>
          
          <div className="mt-6 text-center">
            <p className="text-gray-600">Don't have an account? 
              <button 
                onClick={() => navigate("/signup")}
                className="text-gray-800 font-semibold hover:underline ml-1"
              >
                Sign up here
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
