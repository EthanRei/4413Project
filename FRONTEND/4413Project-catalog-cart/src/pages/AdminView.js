import React, { useState, useEffect, useCallback } from "react";
import {
  fetchOrderHistory,
  fetchOrderById,
  fetchCustomerInfo,
  updateCustomerInfo,
  fetchCatalog,
  updateProductQuantity,
} from "../services/api";

const AdminPage = () => {
  const [section, setSection] = useState("sales"); // Manage active tab
  const [sales, setSales] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [inventory, setInventory] = useState([]);
  const [filters, setFilters] = useState({}); // Filters for sales history
  const [selectedOrder, setSelectedOrder] = useState(null);

  // Fetch sales history
  const loadSalesHistory = useCallback(async () => {
    const data = await fetchOrderHistory(filters);
    setSales(data);
  }, [filters]);

  // Fetch customers
  const loadCustomers = async () => {
    try {
      const customerIds = [1, 2, 3]; // Example customer IDs
      const customerData = await Promise.all(
        customerIds.map((id) => fetchCustomerInfo(id))
      );
      setCustomers(customerData);
    } catch (error) {
      console.error("Failed to load customers:", error);
    }
  };

  // Fetch inventory
  const loadInventory = async () => {
    const data = await fetchCatalog();
    setInventory(data);
  };

  // Load data on component mount
  useEffect(() => {
    loadSalesHistory();
    loadCustomers();
    loadInventory();
  }, [loadSalesHistory]);

  // Handle customer updates
  const handleUpdateCustomer = async (id, updatedData) => {
    await updateCustomerInfo(id, updatedData);
    loadCustomers();
  };

  // Handle inventory updates
  const handleUpdateInventory = async (id, newQuantity) => {
    await updateProductQuantity(id, newQuantity);
    loadInventory();
  };

  // Fetch order details
  const handleFetchOrderDetails = async (orderId) => {
    const data = await fetchOrderById(orderId);
    setSelectedOrder(data);
  };

  return (
    <div className="admin-page">
      <h1>Admin Dashboard</h1>
      {/* Navigation */}
      <nav>
        <button onClick={() => setSection("sales")}>Sales History</button>
        <button onClick={() => setSection("customers")}>Customer Management</button>
        <button onClick={() => setSection("inventory")}>Inventory Management</button>
      </nav>

      {/* Sales History Section */}
      {section === "sales" && (
        <div>
          <h2>Sales History</h2>
          {/* Filters */}
          <div>
            <input
              placeholder="Customer"
              onChange={(e) => setFilters({ ...filters, customer: e.target.value })}
            />
            <input
              placeholder="Product"
              onChange={(e) => setFilters({ ...filters, product: e.target.value })}
            />
            <input
              type="date"
              onChange={(e) => setFilters({ ...filters, date: e.target.value })}
            />
            <button onClick={loadSalesHistory}>Apply Filters</button>
          </div>
          {/* Sales Table */}
          <ul>
            {sales.map((sale) => (
              <li key={sale.id} onClick={() => handleFetchOrderDetails(sale.id)}>
                {sale.customer} - {sale.product} - ${sale.price}
              </li>
            ))}
          </ul>
          {/* Order Details */}
          {selectedOrder && (
            <div>
              <h3>Order Details</h3>
              <p>User: {selectedOrder.user}</p>
              <p>Product: {selectedOrder.product}</p>
              <p>Price: ${selectedOrder.price}</p>
              <p>Quantity: {selectedOrder.quantity}</p>
            </div>
          )}
        </div>
      )}

      {/* Customer Management Section */}
      {section === "customers" && (
        <div>
          <h2>Customer Management</h2>
          <ul>
            {customers.map((customer) => (
              <li key={customer.id}>
                {customer.name} - {customer.email}
                <button
                  onClick={() =>
                    handleUpdateCustomer(customer.id, {
                      shippingAddress: "Updated Address",
                    })
                  }
                >
                  Update Address
                </button>
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* Inventory Management Section */}
      {section === "inventory" && (
        <div>
          <h2>Inventory Management</h2>
          <ul>
            {inventory.map((product) => (
              <li key={product.id}>
                {product.name} - {product.quantity} in stock
                <button
                  onClick={() =>
                    handleUpdateInventory(product.id, product.quantity + 1)
                  }
                >
                  Add Inventory
                </button>
                <button
                  onClick={() =>
                    handleUpdateInventory(product.id, product.quantity - 1)
                  }
                >
                  Reduce Inventory
                </button>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default AdminPage;