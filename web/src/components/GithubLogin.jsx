function GithubLogin() {
  const githubcall = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/github";
  };
  return (
    <div>
      <button onClick={githubcall}>Login to github</button>
    </div>
  );
}

export default GithubLogin;
