package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.model.Movie;

public interface IMovieRepository extends ElasticsearchRepository<Movie, Long> {

	public Page<Movie> findByName(String name, Pageable pageable);

	public Page<Movie> findByNameContaining(String name, Pageable pageable);

	public Page<Movie> findByRatingBetween(Double beginning, Double end, Pageable pageable);
}
