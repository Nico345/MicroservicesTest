package com.Image1.service;

public interface IGitLabService {

	public void createRepo(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName);

	public void createBranch(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName, String refBranchName, String newBranchName);

	public void createTag(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken, String projectNamespace, String repoName, String branchName, String tagName);

	public void initialize(String gitLabApiUrl, String gitLabApiUser, String gitLabApiToken);
}