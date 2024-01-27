package com.Image2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.Image2.model.Build;
import com.Image2.repository.BuildsRepository;

import reactor.core.publisher.Mono;

@Service
public class BuildServiceImpl implements IBuildService {

	private WebClient jenkinsWebClient;

	@Autowired
	BuildsRepository repository;

	public void initialize(String url, String apiToken) {

		this.jenkinsWebClient = WebClient.builder().baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+apiToken)
				.build();
	}

	public void callApi(Build build) {
		jenkinsWebClient.post()
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromValue(build))
		.exchangeToMono(response -> {
			if (response.statusCode().is2xxSuccessful()) {
				System.out.println("Request successful");
				return Mono.empty();
			} else {
				System.out.println("Request failed. Status code: " + response.statusCode());
				return Mono.empty();
			}
		})
		.block();
	}

	@Override
	public boolean createBuild(String name, String pathRepo, String version) {

		if (isValidName(name) && isValidPathRepo(pathRepo) && isValidVersion(version)) {
			Build build = new Build(name,pathRepo,version);
			repository.save(build);
			callApi(build);
			return true;
		}else {
			return false;
		}


	}

	private boolean isValidName(String name) {
		return name.matches("[a-zA-Z]+");
	}

	private boolean isValidPathRepo(String pathRepo) {
		return pathRepo.matches(".*\\\\.*");
	}

	private boolean isValidVersion(String version) {
		return version.matches("\\d+\\.\\d+\\.\\d+");
	}

}
