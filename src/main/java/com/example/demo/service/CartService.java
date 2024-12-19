package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.example.demo.model.CustomerCart;
import com.example.demo.model.CustomerCartEntry;
import com.example.demo.model.Item;
import com.example.demo.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    public CustomerCart getCustomerCart(String customerId) {
        return cartRepository.findByCustomerId(customerId).get();
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
            Item catalogItem = catalogService.getItemById(itemId);
            if (catalogItem == null) {
                continue;
            }
            int newQty = updateEntry.getQty();
            int inStockQty = catalogItem.getQuantity();
            // Check if newQty is valid
            if (newQty < 0 || newQty > inStockQty) {                
                failedItems.add(cartItemToJsonMapping(updateEntry));
                continue;
            }

            // Update qty in items
            boolean found = false;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemId().equals(itemId)) {
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

            System.out.println(found);
            // If not in items, add to items
            if (!found && newQty > 0) {
                CustomerCartEntry newEntry = new CustomerCartEntry();
                newEntry.setItemId(itemId);
                newEntry.setQty(newQty);
                items.add(newEntry);
            }
        }

        // TODO Move this code to a DAO
        Query query = new Query(Criteria.where("customerId").is(customerId));
        Update update = new Update().set("items", items);
        mongoTemplate.updateFirst(query, update, "customerCart");
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
