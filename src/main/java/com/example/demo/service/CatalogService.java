package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Item;
import com.example.demo.repository.CatalogRepository;


@Service
public class CatalogService {
	
    @Autowired
    private CatalogRepository catalogRepository;

	public List<Item> getAllCatalogItems() {
		// TODO Auto-generated method stub
		
        return catalogRepository.findAll();
	}

	public Item getItemById(String itemID) {
		// TODO Auto-generated method stub
        return catalogRepository.findById(itemID).orElse(null);
	}

	public Item updateItemQuantity(String itemID, int newQuantity) {
		// TODO Auto-generated method stub
		
		Item existingItem = catalogRepository.findById(itemID).orElse(null);
		
		if (existingItem == null) {
			//item not found
			return null;
		}
		
		existingItem.setQty(newQuantity);
		
		return catalogRepository.save(existingItem);
	}

}
