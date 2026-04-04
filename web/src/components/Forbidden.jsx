import { useNavigate } from "react-router";

export default function Forbidden() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900 flex items-center justify-center px-4">
      <div className="text-center">
        <h1 className="text-6xl font-bold text-red-500 mb-4">403</h1>
        <h2 className="text-3xl font-semibold text-white mb-4">
          Access Denied
        </h2>
        <p className="text-gray-300 mb-8 text-lg">
          You are not authorized to access this resource.
        </p>
        <div className="flex gap-4 justify-center">
          <button
            onClick={() => navigate("/")}
            className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded transition"
          >
            Go Home
          </button>
          <button
            onClick={() => navigate(-1)}
            className="bg-gray-600 hover:bg-gray-700 text-white font-bold py-2 px-6 rounded transition"
          >
            Go Back
          </button>
        </div>
      </div>
    </div>
  );
}
