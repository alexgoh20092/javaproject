package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	// define a GreetingComponent object without initializing it
	private GreetingComponent g;
	
	// inject/initialize g in the constructor
	@Autowired
	public GreetingController(GreetingComponent g) {
		this.g = g;
	}
	
	// http://localhost:8080/greeting?name=JOHN
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	// generic <>
	// test the greeting component
	@GetMapping("/testgreeting")
	public ResponseEntity<String> getMessage() {
		return ResponseEntity.ok(g.getMessage());
	}
	
	// http://localhost:8080/greeting/{firstname}/{lastname}
	@GetMapping("/greeting/{firstname}/{lastname}")
	public Greeting greet2Parm(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname) {
		String fullname = firstname + " " + lastname;
		return new Greeting(counter.incrementAndGet(), String.format(template, fullname));
	}
	
}
