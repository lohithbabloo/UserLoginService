import WelcomePage from "./components/WelcomePage";
import { BrowserRouter, Routes, Route } from "react-router";
import "./index.css";
import GithubSuccess from "./components/GithubSucces";
import Forbidden from "./components/Forbidden";
function App() {
  return (
    <div className="">
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
