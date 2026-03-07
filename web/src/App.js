import SignIn from "./components/signIn";
import Login from "./components/login";
import BasePage from "./components/basePage";
import { BrowserRouter, Routes, Route } from "react-router";
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/signup" element={<SignIn />} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<BasePage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
