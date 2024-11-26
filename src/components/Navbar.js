import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { CartContext } from '../CartContext';


const Navbar = ({loggedInUser}) => {
  const { cart } = useContext(CartContext);
  

  const getCartItemCount = () =>
    cart.reduce((total, item) => total + item.quantity, 0);



  return (
    <nav className="navbar">
      <h1>Ecommerce Store</h1>
      <ul>
        <li><Link to="/">Catalog</Link></li>
        <li><Link to="/cart">Cart ({getCartItemCount()})</Link></li>
        {loggedInUser ? (
          <li>Welcome, {loggedInUser}!</li>
        ) : (
          <li><Link to="/login">Login</Link></li>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;