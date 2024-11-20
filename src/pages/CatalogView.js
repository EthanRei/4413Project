import React from 'react';
import CatalogItem from '../components/CatalogItem';

const CatalogView = () => {
  // Dummy product data with images
  const dummyProducts = [
    {
      id: 1,
      name: 'iPhone',
      description: 'Flagship phone',
      brand: 'Apple',
      price: 1999.99,
      category: 'Electronics',
      quantity: 0,
      imageUrl: 'https://shop.freedommobile.ca/_next/image?url=https%3A%2F%2Fimages.ctfassets.net%2Fszuukvy7owq5%2F6uYsjQ8l0mPTJbCoyaVXuv%2F90f5ff1ccb1fb1faf256f20f88ce9ef7%2Fiphone16-ultramarine-front.png%3Ffm%3Dwebp&w=384&q=100', 
    },
    {
      id: 2,
      name: 'iPad',
      description: 'Flagship tablet',
      brand: 'Apple',
      price: 799.99,
      category: 'Electronics',
      quantity: 0,
      imageUrl: 'https://multimedia.bbycastatic.ca/multimedia/products/1500x1500/165/16567/16567276.jpg',
    },
    {
      id: 3,
      name: 'MacBook',
      description: 'Flagship laptop',
      brand: 'Apple',
      price: 2199.99,
      category: 'Electronics',
      quantity: 0,
      imageUrl: 'https://multimedia.bbycastatic.ca/multimedia/products/500x500/151/15136/15136915.jpg', 
    },
  ];

  return (
    <div className="catalog-view">
      <h2>Products</h2>
      <div className="product-grid">
        {dummyProducts.map((product) => (
          <CatalogItem key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default CatalogView;