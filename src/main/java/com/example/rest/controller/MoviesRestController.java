package com.example.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Movie;
import com.example.model.Movies;
import com.example.repository.IMovieCustomRepository;
import com.example.repository.custom.parameters.FilterParameter;
import com.example.rest.controller.utils.RestControllerUtils;
import com.example.service.api.IMovieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/movies")
public class MoviesRestController {

	@Value("${pageable.min-results}")
	private int minResults;

	@Value("${pageable.max-results}")
	private int maxResults;

	@Autowired
	private IMovieService moviesService;
	
	@Autowired
	private IMovieCustomRepository movieCustomRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Movies getAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "0", required = false) int size,
			@RequestParam(value = "sort", defaultValue = "", required = false) String[] sort,
			HttpServletResponse response) {

		page = page >= 0 ? page : page * -1;
		size = size <= 0 ? minResults : (size > maxResults ? maxResults : size);
		sort = sort.length == 2 ? (sort[1].equalsIgnoreCase("asc") || sort[1].equalsIgnoreCase("desc")
				? new String[] { String.join(",", sort) } : sort) : sort;

		Page<Movie> moviePage = moviesService.getAll(page, size, sort);

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

		System.out.println("movie: " + movie);
		return moviesService.modify(id, movie);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Movie delete(@PathVariable Long id) {
		return moviesService.delete(id);
	}

	@RequestMapping(value = "/searchByName/{name}", method = RequestMethod.GET)
	public Movies getByName(@PathVariable String name,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "0", required = false) int size,
			@RequestParam(value = "sort", defaultValue = "", required = false) String[] sort,
			HttpServletResponse response) {

		page = page >= 0 ? page : page * -1;
		size = size <= 0 ? minResults : (size > maxResults ? maxResults : size);
		sort = sort.length == 2 ? (sort[1].equalsIgnoreCase("asc") || sort[1].equalsIgnoreCase("desc")
				? new String[] { String.join(",", sort) } : sort) : sort;

		Page<Movie> moviePage = moviesService.getByName(name, page, size, sort);

		RestControllerUtils.setHeaders.accept(response, moviePage);

		return Movies.builder().movies(moviePage.getContent()).build();

	}

	@RequestMapping(value = "/searchByNameContaining/{name}", method = RequestMethod.GET)
	public Movies getByNameLike(@PathVariable String name,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "0", required = false) int size,
			@RequestParam(value = "sort", defaultValue = "", required = false) String[] sort,
			HttpServletResponse response) {

		page = page >= 0 ? page : page * -1;
		size = size <= 0 ? minResults : (size > maxResults ? maxResults : size);
		sort = sort.length == 2 ? (sort[1].equalsIgnoreCase("asc") || sort[1].equalsIgnoreCase("desc")
				? new String[] { String.join(",", sort) } : sort) : sort;

		Page<Movie> moviePage = moviesService.getByNameLike(name, page, size, sort);

		RestControllerUtils.setHeaders.accept(response, moviePage);

		return Movies.builder().movies(moviePage.getContent()).build();
	}
	
	@RequestMapping(value = "/searchByNameWithSpecialCharacters", method = RequestMethod.GET)
	public Movies getByNameQuery2(
			@RequestParam(value = Movie.NAME_PROPERTY, required = false) List<String> names,
			@PageableDefault(page = 0, size = 25, sort = "id", direction = Direction.ASC) Pageable pageable,
			HttpServletResponse response) throws InterruptedException, ExecutionException {
		
		final List<FilterParameter> parameterList = new ArrayList<>();
		parameterList.add(new FilterParameter(Movie.NAME_PROPERTY, names));
		
		System.out.println(parameterList);

		Future<List<Movie>> movieListFuture = null;

		try {
			movieListFuture = movieCustomRepository.getAllByFilterParameters(parameterList, pageable);
		} catch (RuntimeException re) {
			log.error("Error in getAllByFilterParameters: {}", re.getMessage());
			movieListFuture = CompletableFuture.completedFuture(new ArrayList<>());
		}

		//RestControllerUtils.setHeaders.accept(response, moviePage);

		return Movies.builder().movies(movieListFuture.get()).build();
	}
}
