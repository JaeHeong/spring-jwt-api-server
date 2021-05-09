package com.ratseno.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRestController {
	
	@GetMapping(value = "/hello")
	public String helloWorld() {
		return "hello world!";
	}
}
