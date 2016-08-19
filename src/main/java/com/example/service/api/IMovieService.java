package com.example.service.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.Movie;

public interface IMovieService {
	List<Movie> getByName(String name);

	List<Movie> getByRatingInterval(Double beginning, Double end);

	Movie addMovie(Movie movie);

	Page<Movie> getAll();

	Movie create(Movie movie);

	Movie getById(Long id);

	Movie modify(Long id, Movie movie);

	Movie delete(Long id);

	// List<Movie> getAllWithWorkstationPosition();
}
