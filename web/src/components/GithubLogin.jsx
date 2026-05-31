import axios from "axios";
import baseurl from "../commonComponents/apiClient";
export default function GithubLogin() {
  async function handleLogin() {
    try {
      const response = await axios.get(baseurl + "/api/v1/health");
      console.log(response);
      window.location.href = baseurl + "/oauth2/authorization/github";
    } catch (error) {
      alert("Backend service is failing");
    }
  }
  return (
    <div className="min-h-screen bg-black text-white flex items-center justify-center px-6 relative overflow-hidden">
      {/* Background Glow */}
      <div className="absolute top-[-120px] left-[-100px] w-[350px] h-[350px] bg-purple-600/30 blur-3xl rounded-full" />
      <div className="absolute bottom-[-120px] right-[-100px] w-[350px] h-[350px] bg-blue-600/30 blur-3xl rounded-full" />

      <div className="relative z-10 max-w-6xl w-full grid lg:grid-cols-2 gap-10 items-center">
        {/* Left Content */}
        <div className="space-y-8">
          <div className="inline-flex items-center gap-2 bg-white/10 border border-white/10 rounded-full px-4 py-2 text-sm text-gray-300">
            <span className="w-2 h-2 bg-green-400 rounded-full" />
            Secure OAuth Authentication Platform
          </div>

          <div className="space-y-5">
            <h1 className="text-5xl lg:text-6xl font-bold leading-tight tracking-tight">
              Authenticate Once.
              <br />
              Access Every App.
            </h1>

            <p className="text-gray-400 text-lg leading-relaxed max-w-xl">
              Authify powers authentication for DevSync and future applications
              using secure GitHub OAuth, JWT sessions, and scalable microservice
              architecture.
            </p>
          </div>

          <div className="grid sm:grid-cols-3 gap-4 pt-4">
            <div className="bg-white/5 border border-white/10 rounded-2xl p-4 backdrop-blur-sm">
              <p className="text-2xl font-bold">OAuth</p>
              <p className="text-gray-400 text-sm mt-1">
                GitHub Authentication
              </p>
            </div>

            <div className="bg-white/5 border border-white/10 rounded-2xl p-4 backdrop-blur-sm">
              <p className="text-2xl font-bold">JWT</p>
              <p className="text-gray-400 text-sm mt-1">Secure Session Flow</p>
            </div>

            <div className="bg-white/5 border border-white/10 rounded-2xl p-4 backdrop-blur-sm">
              <p className="text-2xl font-bold">API</p>
              <p className="text-gray-400 text-sm mt-1">
                Developer First Design
              </p>
            </div>
          </div>
        </div>

        {/* Right Login Card */}
        <div className="flex justify-center lg:justify-end">
          <div className="w-full max-w-md bg-white/5 border border-white/10 backdrop-blur-xl rounded-3xl p-8 shadow-2xl">
            <div className="space-y-2 text-center mb-10">
              <div className="w-16 h-16 rounded-2xl bg-white text-black font-bold text-2xl flex items-center justify-center mx-auto">
                A
              </div>

              <h2 className="text-3xl font-bold pt-4">Welcome Back</h2>
              <p className="text-gray-400">
                Continue securely with your GitHub account.
              </p>
            </div>

            <button
              onClick={() => {
                handleLogin();
              }}
              className="w-full bg-white text-black font-semibold py-4 rounded-2xl hover:scale-[1.02] transition-all duration-200 flex items-center justify-center gap-3"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="22"
                height="22"
                fill="currentColor"
                viewBox="0 0 16 16"
              >
                <path
                  d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2
                .01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01
                1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3
                .64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.7 7.7 0
                0 1 2-.27c.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.
                56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-
                .01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"
                />
              </svg>
              Continue with GitHub
            </button>

            <div className="my-8 border-t border-white/10" />

            <div className="space-y-5 text-sm text-gray-400">
              <div className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-lg bg-green-500/10 text-green-400 flex items-center justify-center text-xs font-bold">
                  ✓
                </div>
                <div>
                  <p className="text-white font-medium">Secure OAuth Flow</p>
                  <p>Your GitHub credentials are never stored by Authify.</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-lg bg-blue-500/10 text-blue-400 flex items-center justify-center text-xs font-bold">
                  JWT
                </div>
                <div>
                  <p className="text-white font-medium">
                    JWT Session Management
                  </p>
                  <p>Stateless authentication powered by secure cookies.</p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="w-8 h-8 rounded-lg bg-purple-500/10 text-purple-400 flex items-center justify-center text-xs font-bold">
                  API
                </div>
                <div>
                  <p className="text-white font-medium">Developer Ecosystem</p>
                  <p>Built for scalable microservice integrations.</p>
                </div>
              </div>
            </div>

            <p className="text-center text-xs text-gray-500 mt-10">
              By continuing, you agree to Authify Terms and Privacy Policy.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
