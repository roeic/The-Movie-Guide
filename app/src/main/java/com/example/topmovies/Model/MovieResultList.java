package com.example.topmovies.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class MovieResultList{

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private List<ResultsMovie> results;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setResults(List<ResultsMovie> results){
		this.results = results;
	}

	public List<ResultsMovie> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"MovieResultList{" + 
			"page = '" + page + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}