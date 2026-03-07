import { useNavigate } from "react-router";

function BasePage() {
  const navigate = useNavigate();
  function navigatePath(path) {
    navigate(path);
  }
  return (
    <div>
      <h1>Welcome</h1>
      <button onClick={() => navigatePath("/signup")}>Signin</button>
      <button onClick={() => navigatePath("/login")}>Login</button>
    </div>
  );
}

export default BasePage;
