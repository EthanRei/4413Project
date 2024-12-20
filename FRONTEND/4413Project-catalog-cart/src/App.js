import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import CatalogView from './pages/CatalogView';
import CartView from './pages/CartView';
import Login from './pages/login';
import Admin from './pages/Admin';
import AdminView from './pages/AdminView';
import CheckoutView from './pages/CheckoutView'
import Register from './pages/register';
import ProductDetails from './pages/ItemView'; // Import ProductDetails component 
import { CartProvider } from './CartContext';

const App = () => {
  return (
      <CartProvider>
        <Router>
        <Navbar />
          <Routes>
            <Route path="/" element={<CatalogView />} />
            <Route path="/cart" element={<CartView />} />
            <Route path="/login" element={<Login />} />
            <Route path="/admin" element={<Admin />} />
            <Route path="/adminView" element={<AdminView />} />
            <Route path="/register" element={<Register />} />
            <Route path="/product/:productId" element={<ProductDetails />} />
            <Route path="/CheckoutView" element={<CheckoutView />} />
          </Routes>
        </Router>
      </CartProvider>
    
  );
};

export default App;