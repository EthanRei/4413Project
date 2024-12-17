package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.service.CatalogService;

@RestController
@RequestMapping("/api")
public class CatalogController {
	
	
	@Autowired
	CatalogService catalogService;
	
	
	@GetMapping("/catalog")
	public ResponseEntity<?> getAllCatalogitems() {
		
		
        List<Item> catalogItems = catalogService.getAllCatalogItems();
		
        // Check if the list is empty
        if (catalogItems.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"No catalog items found\"}");
        }

        // Return the list of catalog items
        return ResponseEntity.ok(catalogItems);		
	}
	
	@GetMapping("/product/{ItemID}")
	public ResponseEntity<?> getProduct(@PathVariable String ItemID){
		
		
		Item product = catalogService.getItemById(ItemID);
		
		if (product != null) {
			// product exists
			return ResponseEntity.ok(product);
		}
		
		else {
			return ResponseEntity.status(404).body("{\"message\": \"no product found\"}");
		}

	}

}
