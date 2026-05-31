export default function Forbidden() {
  return (
    <div className="bg-black min-h-screen flex justify-center">
      <div className="align-bottom">
        <h1 className="text-4xl font-bold text-center text-white">
          403 Forbidden
        </h1>
        <p className="text-center text-gray-500 mt-4">
          You do not have permission to access this resource.
        </p>
      </div>
    </div>
  );
}
