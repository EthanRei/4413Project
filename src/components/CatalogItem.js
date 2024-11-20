import React, { useContext } from 'react';
import { CartContext } from '../CartContext';

const CatalogItem = ({ product }) => {
  const { addToCart } = useContext(CartContext);

  return (
    <div className="catalog-item">
        <div className="image-container">
            <img src={product.imageUrl} alt={product.name} />
        </div>
      
      <div className="desc">
        <p>{product.brand}</p>
        <h3>{product.name}</h3>
        <p>{product.description}</p>
        <p>Price: ${product.price}</p>
        <button onClick={() => addToCart(product)}>Add to Cart</button>
      </div>
     
      
    </div>
  );
};

export default CatalogItem;