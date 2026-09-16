import { motion } from "framer-motion";
import baseurl from "../commonComponents/apiClient";

/**
 * Reusable authentication provider button component.
 */
function AuthenticationProviderButton({ providerName, icon, onClick }) {
  return (
    <button
      onClick={onClick}
      className="w-full flex items-center justify-center gap-3 h-10 px-4 rounded-md bg-[#F2F4F7] text-[#0B0D0F] text-sm font-medium hover:bg-[#E5E7EB] transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-dev-accent focus-visible:ring-offset-2 focus-visible:ring-offset-dev-bg"
    >
      {icon}
      <span>Continue with {providerName}</span>
      <span className="opacity-60 ml-1">→</span>
    </button>
  );
}

function GitHubIcon() {
  return (
    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" aria-hidden="true">
      <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.7 7.7 0 0 1 2-.27c.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z" />
    </svg>
  );
}

export default function GithubLogin() {
  /* ── AUTH LOGIC — DO NOT MODIFY ── */
  async function handleLogin() {
    try {
      const response = await baseurl.get("/auth/v1/health");
      console.log(response);
      window.location.href = "http://localhost:8080/oauth2/authorization/github";
    } catch (error) {
      alert("Backend service is failing");
    }
  }
  /* ── END AUTH LOGIC ── */

  return (
    <div className="min-h-screen bg-dev-bg text-dev-text flex flex-col items-center justify-center p-4">
      
      {/* Brand Header */}
      <div className="flex items-center gap-2 mb-8 select-none">
        <div className="w-6 h-6 bg-dev-text text-dev-bg rounded-md flex items-center justify-center text-xs font-bold">
          A
        </div>
        <span className="font-semibold text-sm tracking-tight text-dev-text">Authify</span>
      </div>

      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3, ease: "easeOut" }}
        className="w-full max-w-[360px]"
      >
        <div className="bg-dev-card border border-dev-border rounded-xl shadow-lg p-6 sm:p-8">
          
          <div className="text-center mb-8">
            <h1 className="text-2xl font-semibold tracking-tight text-dev-text mb-2">
              Welcome back
            </h1>
            <p className="text-sm text-dev-secondary">
              Sign in to continue to Authify.
            </p>
          </div>

          <div className="space-y-4">
            <AuthenticationProviderButton
              providerName="GitHub"
              icon={<GitHubIcon />}
              onClick={handleLogin}
            />
          </div>

          <div className="mt-8 text-center space-y-4">
            <p className="text-[13px] text-dev-secondary">
              Secure authentication for your applications.
            </p>
            <p className="text-xs text-dev-muted">
              By continuing, you agree to our{" "}
              <a href="/terms" className="hover:text-dev-text transition-colors outline-none focus-visible:ring-2 focus-visible:ring-dev-accent rounded-sm px-0.5">Terms</a>
              {" "}and{" "}
              <a href="/privacy" className="hover:text-dev-text transition-colors outline-none focus-visible:ring-2 focus-visible:ring-dev-accent rounded-sm px-0.5">Privacy</a>.
            </p>
          </div>

        </div>
      </motion.div>
      
    </div>
  );
}
