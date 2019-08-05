package com.stone.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class SimpleController {

	@GetMapping("/check")
	public String status() {
		return "working...";
	}
}
