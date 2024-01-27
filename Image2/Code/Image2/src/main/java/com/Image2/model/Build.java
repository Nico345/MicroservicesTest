package com.Image2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Builds")
public class Build {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "buildId")
	@JsonProperty("buildId")
	private Integer buildId;
	
	@Column(name = "name")
	@JsonProperty("name")
	private String name;
	
	@Column(name = "pathRepo")
	@JsonProperty("pathRepo")
	private String pathRepo;
	
	@Column(name = "version")
	@JsonProperty("version")
	private String version;
	
	//////////////Constructor/////////////////////////////
	
	public Build(String name, String pathRepo, String version) {
		this.name = name;
		this.pathRepo = pathRepo;
		this.version = version;
	}
	//////////////Getters - Setters/////////////////////////////

	public Integer getBuildId() {
		return buildId;
	}
	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPathRepo() {
		return pathRepo;
	}
	public void setPathRepo(String pathRepo) {
		this.pathRepo = pathRepo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}