package com.example.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Movie;
import com.example.model.Movies;
import com.example.rest.controller.utils.RestControllerUtils;
import com.example.service.api.IMovieService;

@RestController
@RequestMapping("/api/movies")
public class MoviesRestController {

	@Value("${pageable.min-results}")
	private int minResults;

	@Value("${pageable.max-results}")
	private int maxResults;

	@Autowired
	private IMovieService moviesService;

	@RequestMapping(method = RequestMethod.GET)
	public Movies getAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "limit", defaultValue = "0", required = false) int limit,
			HttpServletResponse response) {

		page = page >= 0 ? page : page * -1;
		limit = limit <= 0 ? minResults : (limit > maxResults ? maxResults : limit);

		Page<Movie> moviePage = moviesService.getAll(page, limit);

		RestControllerUtils.setHeaders.accept(response, moviePage);

		return Movies.builder().movies(moviePage.getContent()).build();
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
