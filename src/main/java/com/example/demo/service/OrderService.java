package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.example.demo.model.BillInfo;
import com.example.demo.model.CustomerCart;
import com.example.demo.model.ItemEntry;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Item;
import com.example.demo.repository.OrdersRepository;

@Service
public class OrderService {
    
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CatalogService catalogService;

    public OrderDetails createOrderForCustomer(String customerId, BillInfo billInfo) {
        CustomerCart cart = cartService.getCustomerCart(customerId);

        if (cart.getItems().isEmpty()) {
            System.out.println("Attempted to checkout an empty cart");
            return null; 
        }

        List<String> itemIds = new ArrayList<>();
        for (ItemEntry itemEntry: cart.getItems()) {
            itemIds.add(itemEntry.getItemId());
        }
        List<Item> currentStock = catalogService.getItemsByIds(itemIds);

        if (currentStock.isEmpty()) { 
            System.out.println("Item in cart doesn't exist in item list");
            return null; 
        }
        
        List<ItemEntry> orderEntries = new ArrayList<>();

        // Validate customer order to see if there is enough stock for each item in cart
        boolean invalidCart = false;
        for (ItemEntry cartEntry: cart.getItems()) {
            for (Item itemStock: currentStock) {
                if (itemStock.getItemId().equals(cartEntry.getItemId())) {
                    if (itemStock.getQuantity() < cartEntry.getQty()) {
                        // Cart entry has more items than stock, update cart to match inventory
                        invalidCart = true;
                        cartEntry.setQty(itemStock.getQuantity());
                    } else {
                        // Valid cart entry, add to order
                        orderEntries.add(cartEntry);
                    }
                    break;
                }
            }
        }

        if (invalidCart) {
            System.out.println("Customer cart was invalid");
            cartService.updateCustomerCart(customerId, cart.getItems());
            return null;
        }

        // Pseudo payment processor
        if (billInfo.getCreditCardCVV().contains("2")) {
            return null;
        }        
        
        System.out.println("Valid cart and payment process complete");


        // Update catalog
        double total = 0.0;
        for (ItemEntry cartEntry: cart.getItems()) {
            for (Item itemStock: currentStock) {
                if (itemStock.getItemId().equals(cartEntry.getItemId())) {
                    total += cartEntry.getQty() * itemStock.getPrice();
                    int newStockQty = itemStock.getQuantity() - cartEntry.getQty();
                    catalogService.updateItemQuantity(customerId, newStockQty);
                    break;
                }
            }
        }

        cartService.clearCustomerCart(customerId);

        // Create new order
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomerId(customerId);
        orderDetails.setItems(orderEntries);
        orderDetails.setTotal(total);

        return ordersRepository.save(orderDetails);
    }

	public List<OrderDetails> getOrders(Map<String, String> filters) {
		return ordersRepository.findAll(); 
	}

    public OrderDetails getOrder(String orderId) {
        return ordersRepository.findById(orderId).orElse(null);
    }

}
