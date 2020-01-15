package net.zoostar.md.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DashboardController {

	@GetMapping("/")
	public String load() {
		log.info("{}", "Loading index page...");
		return "index";
	}
}
