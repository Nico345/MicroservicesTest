package com.Image1.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GitLabServiceImpl implements IGitLabService {

	private WebClient gitLabWebClient;

	public void initialize(String gitLabApiUrl, String gitLabUser, String gitLabApiToken) {

		this.gitLabWebClient = WebClient.builder().baseUrl(gitLabApiUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+gitLabApiToken)
				.build();
	}

	@Override
	public void createRepo(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName) {

		gitLabWebClient.post().uri("/projects")
		.body(BodyInserters.fromValue("{\"name\": \"" + repoName + "\"}"))
		.exchangeToMono(response -> {
			if (response.statusCode().is2xxSuccessful()) {
				System.out.println("Repository created");
				return Mono.empty();
			} else {
				System.out.println("Repository creation failed. Status code: " + response.statusCode());
				return Mono.empty();
			}
		})
		.block();

		gitLabWebClient.post()
		.uri(uriBuilder -> uriBuilder
				.path("/projects/{param1}/repository/commits")
				.build(gitLabApiUser+"/"+ repoName))
		.body(BodyInserters.fromValue("{\"branch\": \"master\", \"commit_message\": \"Initial commit\", \"actions\": [{\"action\": \"create\", \"file_path\": \"README.md\", \"content\": \"# Initial Commit\\nThis is the initial commit.\"}]}"))
		.exchangeToMono(response -> {
			if (response.statusCode().is2xxSuccessful()) {
				System.out.println("Initial commit created");
				return Mono.empty();
			} else {
				System.out.println("Initial commit error. Status code:" + response.statusCode());
				return Mono.empty();
			}
		})
		.block();


	}

	@Override
	public void createBranch(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName, String refBranchName, String newBranchName) {

		gitLabWebClient.post()
		.uri(uriBuilder -> uriBuilder
				.path("/projects/{param1}/repository/branches")
				.queryParam("branch", newBranchName)
	            .queryParam("ref", refBranchName)
				.build(gitLabApiUser+"/"+ repoName))
		.exchangeToMono(response -> {
			System.out.println("Request URL: " + response.request().getURI());
			if (response.statusCode().is2xxSuccessful()) {
				System.out.println("Branch created");
				return Mono.empty();
			} else {
				System.out.println("Branch creation failed. Status code: " + response.statusCode());
				return Mono.empty();
			}
		})
		.block();

	}

	@Override
	public void createTag(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName, String branchName, String tagName) {

		gitLabWebClient.post()
		.uri(uriBuilder -> uriBuilder
				.path("/projects/{param1}/repository/tags")
				.queryParam("tag_name", tagName)
	            .queryParam("ref", branchName)
				.build(gitLabApiUser+"/"+ repoName))
		.exchangeToMono(response -> {
			if (response.statusCode().is2xxSuccessful()) {
				System.out.println("Tag created");
				return Mono.empty();
			} else {
				System.out.println("Tag creation failed. Status code: " + response.statusCode());
				return Mono.empty();
			}
		})
		.block();
	}

}
