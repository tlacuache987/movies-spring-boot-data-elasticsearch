package com.example.repositoryrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Movie;
import com.example.service.api.IMovieService;

@RestController
@RequestMapping("/movies")
public class MoviesRestController {

	@Autowired
	private IMovieService moviesService;

	@RequestMapping(method = RequestMethod.GET)
	// public List<Movie> getAll() {
	public Object getAll() {
		return moviesService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Movie create(@RequestBody Movie movie) {
		return moviesService.create(movie);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Movie get(@PathVariable Long id) {
		return moviesService.getById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Movie update(@PathVariable Long id, @RequestBody Movie movie) {

		return moviesService.modify(id, movie);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Movie delete(@PathVariable Long id) {

		return moviesService.delete(id);
	}
}
