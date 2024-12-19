package com.example.demo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;


@Service
public class CatalogService {
	
    @Autowired
    private ItemRepository itemRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Item> getCatalogItems(Map<String, String> filters) {
		
		// TODO Move to a DAO
		if (filters.size() == 0) { return itemRepository.findAll(); }

		Query query = new Query();

		Iterator<String> filterKeys = filters.keySet().iterator();
		while(filterKeys.hasNext()) {
			String currFilter = filterKeys.next();
			query.addCriteria(Criteria.where(currFilter).is(filters.get(currFilter)));
		}
		
		List<Item> items = mongoTemplate.find(query, Item.class);

        return items;
	}

	public Item getItemById(String itemID) {
        return itemRepository.findById(itemID).orElse(null);
	}

	public Item updateItemQuantity(String itemID, int newQuantity) {
		
		Item existingItem = getItemById(itemID);
		
		//item not found
		if (existingItem == null) { return null; }
		
		existingItem.setQuantity(newQuantity);
		
		return itemRepository.save(existingItem);
	}

}