import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import "../Login.css"; //
import kakaoLoginImage from "../assets/images/kakao_login_large_narrow.png";

function Login({ setIsLoggedIn }) {
  const [username, setUsername] = useState("kid4211@yahoo.co.kr");
  const [password, setPassword] = useState("1234");

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate 훅 사용

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // /login API에 POST 요청
      const response = await axios.post(
        "http://localhost:8080/api/v1/user/login",
        {
          email: username,
          password,
        }
      );

      if (!response.data.success) {
        alert(`${response.data.message}\n(Error Code : ${response.data.code})`);
      } else {
        // 응답이 성공하면 로그인 처리 (토큰 저장 등)
        // 예: localStorage에 토큰 저장
        // localStorage.setItem("token", response.data.data.accessToken);
        // localStorage.setItem("refresh_token", response.data.data.refreshToken);
        localStorage.setItem("token", response.data.data);
        localStorage.setItem("refresh_token", response.data.data);
        // 이후 페이지 이동 또는 로그인 처리 로직 추가
        setIsLoggedIn(true);
        // 예: 대시보드로 이동
        navigate("/dashboard");
      }
    } catch (error) {
      // 오류 처리
      console.error("Login failed:", error);
      alert("로그인 실패: 사용자 정보가 일치하지 않습니다.");
    }
  };

  const handleKakaoLogin = () => {
    window.location.href = `http://localhost:8080/oauth2/authorization/kakao`; // 카카오 로그인 페이지로 리디렉션
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h3 className="login-title">로그인</h3>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username" className="form-label">
              Username
            </label>
            <input
              type="text"
              className="form-control"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="form-group">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <button type="submit" className="btn login-btn">
            로그인
          </button>
        </form>
        <button onClick={handleKakaoLogin} className="btn kakao-btn">
          <img
            src={kakaoLoginImage}
            alt="카카오 로그인"
            className="kakao-icon"
          />
        </button>
      </div>
    </div>
  );
}

export default Login;
