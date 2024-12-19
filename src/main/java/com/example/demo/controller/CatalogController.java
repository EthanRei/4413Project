package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.CatalogService;

@RestController
@RequestMapping("/api")
public class CatalogController {
	
	
	@Autowired
	CatalogService catalogService;
	@Autowired
	ItemRepository itemRepository;
	
	
	@GetMapping("/catalog")
	public ResponseEntity<?> getAllItems(@RequestParam(name="brand", required=false) String brand, @RequestParam(name="category", required=false) String category) {
        Map<String, String> filters = new HashMap<>();
		System.out.println(brand);
		if (brand != null) { filters.put("brand", brand); }
		if (category != null) { filters.put("category", category); }
		List<Item> catalogItems = catalogService.getCatalogItems(filters);

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "success");
		responseBody.put("items", catalogItems);
		return ResponseEntity.ok(responseBody);		
	}
	
	@GetMapping("/catalog/{itemId}")
	public ResponseEntity<?> getProduct(@PathVariable("itemId") String itemId){

		Item item = catalogService.getItemById(itemId);
		if (item != null) {
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("message", "success");
			responseBody.put("item", item);
			return ResponseEntity.ok(responseBody);
		}
		return ResponseEntity.status(404).body("{\"message\": \"no product found\"}");
		
		
	}

	@PutMapping("/catalog/{itemId}/quantity")
	public ResponseEntity<?> updateProductInventory(@PathVariable("itemId") String itemId, @RequestParam(name = "qty", required = true) int qty){
		
		Item item = catalogService.updateItemQuantity(itemId, qty);

		if (item != null) {
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("message", "success");
			return ResponseEntity.ok(responseBody);
		}
		return ResponseEntity.status(404).body("{\"message\": \"no product found\"}");
	}

	// TODO Remove this, temporarily use it to populate db with items
	@PostMapping("/catalog")
	public ResponseEntity<?> addItem(@RequestBody Item item){
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "success");
		responseBody.put("item", itemRepository.save(item));
		return ResponseEntity.ok(responseBody);
	}

}