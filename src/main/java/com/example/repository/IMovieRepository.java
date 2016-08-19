package com.example.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.model.Movie;

public interface IMovieRepository extends ElasticsearchRepository<Movie, Long> {
	public List<Movie> findByName(String name);

	public List<Movie> findByRatingBetween(Double beginning, Double end);
}
