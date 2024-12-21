import React, { createContext, useState, useEffect } from "react";
import { fetchCart, updateCartItems } from "./services/api"; // Import API functions

// Create the CartContext
export const CartContext = createContext();

// CartContext Provider
export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const initializeCart = async () => {
      const customerId = localStorage.getItem("customerId"); // Retrieve customerId from local storage
      if (!customerId) {
        console.error("Customer ID not found");
        setCart([]);
        setIsLoading(false);
        return;
      }
  
      try {
        const backendCartResponse = await fetchCart(customerId);
        const backendCart = backendCartResponse.cart.items;
        if (backendCart.length > 0) {
          setCart(backendCart); // Use backend cart if it exists
          localStorage.setItem("cart", JSON.stringify(backendCart)); // Sync backend cart to local storage
        } else {
          const localCart = JSON.parse(localStorage.getItem("cart")) || [];
          setCart(localCart); // Use local cart if backend cart is empty
          if (localCart.length > 0) {
            await updateCartItems(customerId, localCart); // Sync local cart to backend
          }
        }
      } catch (error) {
        console.error("Error fetching or initializing cart:", error);
      } finally {
        setIsLoading(false); // Loading complete
      }
    };
  
    initializeCart();
  }, []);

  useEffect(() => {
    if (!isLoading) {
      localStorage.setItem("cart", JSON.stringify(cart)); 
    }
  }, [cart, isLoading]);

  const syncCartWithBackend = async (updatedCart) => {
    const customerId = localStorage.getItem("customerId");
    if (!customerId) {
      console.error("Customer ID not found");
      return;
    }
  
    const backendCart = {
      items: updatedCart.map((item) => ({
        itemId: item.itemId,
        qty: item.quantity, // Map "quantity" to "qty"
      })),
    };
  
    try {
      await updateCartItems(customerId, backendCart.items);
    } catch (error) {
      console.error("Error updating cart in backend:", error);
    }
  };

  // Add item to cart
  const addToCart = (product) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find((item) => item.itemId === product.itemId);
      let updatedCart;
  
      if (existingItem) {
        updatedCart = prevCart.map((item) =>
          item.itemId === product.itemId
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      } else {
        updatedCart = [...prevCart, { ...product, quantity: 1 }];
      }
  
      syncCartWithBackend(updatedCart); // Sync updated cart to backend
      return updatedCart;
    });
  };

  // Remove item from cart
  const removeFromCart = (productId) => {
    setCart((prevCart) => {
      const updatedCart = prevCart.filter((item) => item.itemId !== productId);
      syncCartWithBackend(updatedCart); // Sync to backend
      return updatedCart;
    });
  };
  const clearCart = () => {
    setCart([]);
  }
  // Update item quantity
  const updateQuantity = (productId, quantity) => {
    const qty = Math.max(1, parseInt(quantity) || 1);
    setCart((prevCart) => {
      const updatedCart = prevCart.map((item) =>
        item.itemId === productId ? { ...item, quantity: qty } : item
      );
      syncCartWithBackend(updatedCart); // Sync to backend
      return updatedCart;
    });
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <CartContext.Provider
      value={{ cart, clearCart, addToCart, removeFromCart, updateQuantity }}
    >
      {children}
    </CartContext.Provider>
  );
};
