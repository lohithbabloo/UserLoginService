import { useNavigate } from "react-router";

function WelcomePage() {
  const navigate = useNavigate();
  function navigatePath(path) {
    navigate(path);
  }
  return (
    <div className="min-h-screen flex flex-col justify-center items-center px-4">
      <div className="text-center mb-12">
        <h1 className="text-6xl font-bold text-gray-300 mb-4 drop-shadow-lg">
          Welcome
        </h1>
        <p className="text-2xl text-gray-400 mb-8">User Login Service</p>
      </div>

      <div className="bg-white rounded-2xl shadow-2xl p-12 max-w-md w-full">
        <p className="text-gray-600 text-center mb-8 text-lg">
          Get started with our secure authentication platform
        </p>

        <div className="space-y-4">
          <button
            onClick={() => navigatePath("/signup")}
            className="w-full bg-gray-700 text-white font-bold py-3 px-6 rounded-lg hover:shadow-lg hover:scale-105 transition-all duration-200 text-lg hover:bg-gray-600"
          >
            Create Account
          </button>
          <button
            onClick={() => navigatePath("/login")}
            className="w-full bg-white border-2 border-gray-700 text-gray-800 font-bold py-3 px-6 rounded-lg hover:bg-gray-100 hover:shadow-lg transition-all duration-200 text-lg"
          >
            Sign In
          </button>
        </div>
      </div>

      <div className="mt-12 text-gray-500 text-center">
        <p>© 2026 User Login Service. All rights reserved.</p>
      </div>
    </div>
  );
}

export default WelcomePage;
