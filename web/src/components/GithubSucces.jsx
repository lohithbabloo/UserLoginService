import { useEffect } from "react";
import { motion } from "framer-motion";
import axios from "axios";

function GithubSuccess() {
  /* ── LOGIC — DO NOT MODIFY ── */
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
  /* ── END LOGIC ── */

  return (
    <div className="min-h-screen bg-dev-bg text-dev-text flex flex-col items-center justify-center p-4">
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3, ease: "easeOut" }}
        className="text-center"
      >
        <div className="w-12 h-12 rounded-full bg-dev-success/10 border border-dev-success/20 flex items-center justify-center mx-auto mb-6">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="text-dev-success">
            <polyline points="20 6 9 17 4 12" />
          </svg>
        </div>

        <h1 className="text-xl font-semibold tracking-tight text-dev-text mb-2">
          Signed in successfully
        </h1>
        <p className="text-sm text-dev-secondary">
          Welcome back to Authify.
        </p>
      </motion.div>
    </div>
  );
}

export default GithubSuccess;
