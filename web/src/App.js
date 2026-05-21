import WelcomePage from "./components/WelcomePage";
import Forbidden from "./components/Forbidden";
import { BrowserRouter, Routes, Route } from "react-router";
import "./index.css";
import GithubSuccess from "./components/GithubSucces";
function App() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-gray-900">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<WelcomePage />} />
          <Route path="/success" element={<GithubSuccess />} />
          <Route path="/forbidden" element={<Forbidden />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
