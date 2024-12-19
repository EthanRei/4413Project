package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.example.demo.model.CustomerCart;
import com.example.demo.model.CustomerCartEntry;
import com.example.demo.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    /**
     * Creates customer cart given cutomer ID.
     * If the customer already has associated cart, a cart is not created
     */
    public void createCartForCustomer(String customerId) {
        if (customerHasCart(customerId)) {return ;}

        CustomerCart cart = new CustomerCart();
        cart.setItems(new ArrayList<CustomerCartEntry>());
        cart.setCustomerId(customerId);
        cartRepository.save(cart);
    }

    /**
     * Updates customer cart given cutomer ID and list of new item quantities.
     * If there are no issues with updating the shopping cart, returns empty list.
     * If there is not enough stock or attempting to set a negative value, returns 
     * list of item ids with problematic quantities (succesful cart entries are still
     * updated).
     */
    public List<Map<String, Object>> updateCustomerCart(String customerId, List<CustomerCartEntry> newValues) {
        CustomerCart cart = cartRepository.findByCustomerId(customerId).get();
        List<CustomerCartEntry> items = cart.getItems();
        List<Map<String, Object>> failedItems = new ArrayList<Map<String, Object>>();

        for (CustomerCartEntry updateEntry: newValues) {
            String itemId = updateEntry.getItemId();
            int newQty = (int) updateEntry.getQty();

            // Check if newQty is valid
            if (newQty < 0 || newQty > 10000) {
                // 10000 is temporary
                //TODO Check if qty is over current stock when catalog is implemented
                failedItems.add(updateEntry);
                continue;
            }

            // Update qty in items
            boolean found = false;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemId() == itemId) {
                    found = true;
                    if (newQty == 0) {
                        items.remove(i);
                    }
                    else {
                        items.get(i).setQty(newQty);
                    }
                    break;
                }
            }

            // If not in items, add to items
            if (!found && newQty > 0) {
                CustomerCartEntry newEntry = new CustomerCartEntry();
                newEntry.setItemId(itemId);
                newEntry.setQty(newQty);
                items.add(newEntry);
            }
        }
        cartRepository.save(cart);
        return failedItems;

    }


    // Utility Methods

    /**
     * Returns true if given customer has a cart, else false.
     */
    private boolean customerHasCart(String customerId) {
        Optional<CustomerCart> findCart = cartRepository.findByCustomerId(customerId);
        return findCart.isPresent();

    }
    
    public Map<String, Object> cartToJsonMapping(CustomerCart cart) {
        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("customerId", cart.getCustomerId());
        List<Map<String, Object>> itemList = new ArrayList<>();
        for (CustomerCartEntry entry: cart.getItems()) {
            itemList.add(cartItemToJsonMapping(entry));
        }
        cartMap.put("items", itemList);
        return cartMap;
    }

    public Map<String, Object> cartItemToJsonMapping(CustomerCartEntry item) {
        
        Map<String, Object> mappedEntry = new HashMap<>();
        mappedEntry.put("itemId", item.getItemId());
        mappedEntry.put("qty", item.getQty());
        return mappedEntry;
    }
    

}
