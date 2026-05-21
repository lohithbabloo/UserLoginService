import GithubLogin from "./GithubLogin";

function WelcomePage() {
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
          <GithubLogin />
        </div>
      </div>

      <div className="mt-12 text-gray-500 text-center">
        <p>© 2026 User Login Service. All rights reserved.</p>
      </div>
    </div>
  );
}

export default WelcomePage;
