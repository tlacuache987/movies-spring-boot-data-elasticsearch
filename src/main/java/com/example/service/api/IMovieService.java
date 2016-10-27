package com.example.service.api;

import org.springframework.data.domain.Page;

import com.example.model.Movie;

public interface IMovieService {

	Page<Movie> getByName(String name, int page, int size, String... sortAttributes);

	Page<Movie> getByNameLike(String name, int page, int size, String... sort);

	Page<Movie> getByRatingInterval(Double beginning, Double end, int page, int size, String... sortAttributes);

	// ya
	Movie create(Movie movie);

	// ya
	Movie getById(Long id);

	// ya
	Movie modify(Long id, Movie movie);

	// ya
	Movie delete(Long id);

	// ya
	Page<Movie> getAll(int page, int size, String... sortAttributes);
}
