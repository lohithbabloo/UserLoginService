import { useState } from "react";
import { useNavigate } from "react-router";
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
function SignUp() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rePassword, setRePassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handlePasswordCheck = () => {
    if (!userName || !email || !password || !rePassword) {
      setError("Please fill in all fields");
      return false;
    }
    if (password !== rePassword) {
      setError("Passwords do not match");
      return false;
    }
    if (password.length < 6) {
      setError("Password must be at least 6 characters");
      return false;
    }
    return true;
  };

  const handleSignUp = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!handlePasswordCheck()) {
      return;
    }

    setLoading(true);
    const payload = {
      username: userName,
      password: password,
      email: email,
    };
    try {
      const response = await baseurl.post("/signup", payload);
      if (response.status === 200) {
        setSuccess("Signup successful! Redirecting to login...");
        setUserName("");
        setEmail("");
        setRePassword("");
        setPassword("");
        setTimeout(() => navigate("/login"), 2000);
      }
    } catch (e) {
      setError(e.response?.data?.message || "Signup failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white rounded-2xl shadow-2xl p-8 md:p-10">
          <h1 className="text-4xl font-bold text-center text-gray-900 mb-2">
            Create Account
          </h1>
          <p className="text-center text-gray-700 mb-8">
            Join our platform today
          </p>

          <form onSubmit={handleSignUp} className="space-y-5">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Username
              </label>
              <input
                type="text"
                placeholder="Choose a username"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Email
              </label>
              <input
                type="email"
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Password
              </label>
              <input
                type="password"
                placeholder="Create a password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Confirm Password
              </label>
              <input
                type="password"
                placeholder="Confirm your password"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
                required
                className="w-full px-4 py-3 border-2 border-gray-400 rounded-lg focus:outline-none focus:border-gray-700 transition-colors"
              />
            </div>

            {error && (
              <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded">
                <p className="text-red-700 text-sm">{error}</p>
              </div>
            )}

            {success && (
              <div className="bg-green-50 border-l-4 border-green-500 p-4 rounded">
                <p className="text-green-700 text-sm">{success}</p>
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-gray-700 text-white font-bold py-3 rounded-lg hover:shadow-lg hover:scale-105 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed disabled:scale-100 hover:bg-gray-600"
            >
              {loading ? "Creating account..." : "Sign Up"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-gray-600">
              Already have an account?
              <button
                onClick={() => navigate("/login")}
                className="text-gray-800 font-semibold hover:underline ml-1"
              >
                Sign in here
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
