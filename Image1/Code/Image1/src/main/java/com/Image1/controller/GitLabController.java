package com.Image1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Image1.service.IGitLabService;

@RestController
public class GitLabController {

	@Value("${gitlab.api.url}")
	private String gitLabApiUrl;

	@Value("${gitlab.api.user}")
	private String gitLabApiUser;

	@Value("${gitlab.api.token}")
	private String gitLabApiToken;

	@Autowired
	private IGitLabService gitLabService;

	@PostMapping("/repo/create")
	public void createRepo(@RequestParam String projectNamespace, @RequestParam String repoName) {
		
		gitLabService.initialize(gitLabApiUrl, gitLabApiUser, gitLabApiToken);
		
		gitLabService.createRepo(gitLabApiUrl, gitLabApiUser, gitLabApiToken, projectNamespace, repoName);
		
		gitLabService.createBranch(gitLabApiUrl, gitLabApiUser, gitLabApiToken, projectNamespace, repoName, "master", "dev");
		
		gitLabService.createTag(gitLabApiUrl, gitLabApiUser, gitLabApiToken, projectNamespace, repoName, "master", "0.0.1");

	}
}
