package com.Image2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Image2.service.IBuildService;

@RestController
public class BuildController {

	@Value("${api.url}")
	private String apiUrl;

	@Value("${api.token}")
	private String apiToken;

	@Autowired
	private IBuildService buildService;

	@PostMapping("/build/create")
	public String createBuild(@RequestParam String name, @RequestParam String pathRepo, @RequestParam String version) {
		
		buildService.initialize(apiUrl, apiToken);
		
		if (buildService.createBuild(name, pathRepo, version)) {
			return "Success";
		}else {
			return "Incorrect parameters";
		}

	}
	
}
