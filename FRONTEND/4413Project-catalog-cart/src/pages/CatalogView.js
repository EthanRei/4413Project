import React, { useState, useEffect } from "react";
import CatalogItem from "../components/CatalogItem";
import { fetchCatalog } from "../services/api"; // Import API service for fetching catalog

const CatalogView = () => {
  const [products, setProducts] = useState([]); // State for storing product data
  const [filterType, setFilterType] = useState("All");
  const [filterValue, setFilterValue] = useState("");
  const [loading, setLoading] = useState(true); // Loading state

  // Fetch product catalog from the API
  useEffect(() => {
    const loadCatalog = async () => {
      try {
        setLoading(true);
        const data = await fetchCatalog();
        console.log("Loaded products:", data); // Debugging
        setProducts(Array.isArray(data) ? data : []); // Safe fallback
      } catch (error) {
        console.error("Failed to load products:", error);
        setProducts([]); // Ensure products is always an array
      } finally {
        setLoading(false);
      }
    };

    loadCatalog();
  }, []);

  // Extract unique options for brand and category
  const uniqueBrands = [...new Set(products.map((product) => product.brand))];
  const uniqueCategories = [...new Set(products.map((product) => product.category))];

  // Filtered products based on filter type and value
  const filteredProducts = products.filter((product) => {
    if (filterType === "All") return true;
    return product[filterType.toLowerCase()] === filterValue;
  });

  return (
    <div className="catalog-view">
      <h2>Products</h2>

      {/* Loading Indicator */}
      {loading ? (
        <p>Loading products...</p>
      ) : (
        <>
          {/* Filter Controls */}
          <div className="filter-controls">
            <select
              value={filterType}
              onChange={(e) => {
                setFilterType(e.target.value);
                setFilterValue(""); // Reset filter value when type changes
              }}
            >
              <option value="All">All</option>
              <option value="Brand">Brand</option>
              <option value="Category">Category</option>
            </select>

            {filterType !== "All" && (
              <select
                value={filterValue}
                onChange={(e) => setFilterValue(e.target.value)}
              >
                <option value="">Select {filterType}</option>
                {(filterType === "Brand" ? uniqueBrands : uniqueCategories).map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </select>
            )}
          </div>

          {/* Product Grid */}
          <div className="product-grid">
            {filteredProducts.length > 0 ? (
              filteredProducts.map((product) => (
                <CatalogItem key={product.id} product={product} />
              ))
            ) : (
              <p>No products match your criteria.</p>
            )}
          </div>
        </>
      )}
    </div>
  );
};

export default CatalogView;