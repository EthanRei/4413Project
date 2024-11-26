// src/pages/LoginView.js

import React, { useState } from 'react';
import '../LoginView.css'; // Ensure the path is correct


// Login view.css is showing up scuessfuly

const LoginView = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!username || !password) {
      alert('Please enter both username and password');
      return;
    }
    onLogin(username); // Pass username to App.js
  };
  return (
    <div className="login-container">
    <form onSubmit={handleSubmit}>
      <h1>Login</h1>
      <div>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Enter your username"
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Enter your password"
        />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
);
};

export default LoginView;
