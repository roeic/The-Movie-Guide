package com.example.topmovies.Model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class VideolistResult{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsVideo> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<ResultsVideo> results){
		this.results = results;
	}

	public List<ResultsVideo> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"VideolistResult{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}