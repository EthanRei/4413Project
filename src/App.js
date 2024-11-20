import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import CatalogView from './pages/CatalogView';
import CartView from './pages/CartView';
import { CartProvider } from './CartContext';

const App = () => {
  return (
    <CartProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<CatalogView />} />
          <Route path="/cart" element={<CartView />} />
        </Routes>
      </Router>
    </CartProvider>
  );
};

export default App;