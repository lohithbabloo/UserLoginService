import SignUp from "./components/signIn";
import Login from "./components/login";
import BasePage from "./components/basePage";
import { BrowserRouter, Routes, Route } from "react-router";
import "./index.css";
function App() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900">
      <BrowserRouter>
        <Routes>
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<BasePage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
