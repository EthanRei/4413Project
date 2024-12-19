package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BillInfo;
import com.example.demo.model.OrderDetails;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/history")
	public ResponseEntity<?> getOrders() {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "success");
        responseBody.put("orders", orderService.getOrders(null));
        return ResponseEntity.ok(responseBody);
	}

    @GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable("orderId") String orderId){
        OrderDetails order = orderService.getOrder(orderId);
		Map<String, Object> responseBody = new HashMap<>();
        if (order != null) {
			responseBody.put("message", "success");
			responseBody.put("order", order);
			return ResponseEntity.ok(responseBody);		      
        }
		responseBody.put("message", "order not found");
		return ResponseEntity.status(404).body(responseBody);
        
	}

	@PostMapping("/{customerId}")
	public ResponseEntity<?> createOrder(@PathVariable("customerId") String customerId, @RequestBody(required = true) BillInfo billInfo){
		OrderDetails order = orderService.createOrderForCustomer(customerId, billInfo);
		Map<String, Object> responseBody = new HashMap<>();
        if (order != null) {
			responseBody.put("message", "success");
			responseBody.put("newOrder", order);
			return ResponseEntity.ok(responseBody);	      
        }
		responseBody.put("message", "there was an error in customer order");
		return ResponseEntity.status(400).body(responseBody);
		


	}

}
