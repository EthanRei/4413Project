import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import CatalogView from './pages/CatalogView';
import CartView from './pages/CartView';
import { CartProvider } from './CartContext';
import LoginView from './pages/LoginView';
import { useNavigate } from 'react-router-dom';

// Wrapper for LoginView to handle navigation
const LoginViewWrapper = ({ onLogin }) => {
  const navigate = useNavigate(); // useNavigate is valid here because this component is inside Router

  const handleLogin = (username) => {
    onLogin(username); // Update logged-in user in App state
    navigate('/'); // Redirect to home page after login
  };

  return <LoginView onLogin={handleLogin} />;
};

const App = () => {
  const [loggedInUser, setLoggedInUser] = useState(''); // State to track logged-in user

  return (
    <CartProvider>
      <Router>
        <Navbar loggedInUser={loggedInUser} />
        <Routes>
          <Route path="/" element={<CatalogView />} />
          <Route path="/cart" element={<CartView />} />
          <Route
            path="/login"
            element={<LoginViewWrapper onLogin={setLoggedInUser} />} // Pass setLoggedInUser to LoginViewWrapper
          />
        </Routes>
      </Router>
    </CartProvider>
  );
};

export default App;
