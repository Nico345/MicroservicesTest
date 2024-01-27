package com.Image2.service;

public interface IBuildService {

	public boolean createBuild(String name, String pathRepo, String version );
	
	public void initialize(String apiUrl, String apiToken);
}