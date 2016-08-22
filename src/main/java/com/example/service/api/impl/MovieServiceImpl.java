package com.example.service.api.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.model.Movie;
import com.example.repository.IMovieRepository;
import com.example.service.api.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieRepository movieRepository;

	public List<Movie> getByName(String name) {
		return this.movieRepository.findByName(name);
	}

	public List<Movie> getByRatingInterval(Double beginning, Double end) {
		return this.movieRepository.findByRatingBetween(beginning, end);
	}

	public Movie addMovie(Movie movie) {
		return this.movieRepository.save(movie);
	}

	@Override
	public Page<Movie> getAll(int page, int limit) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));

		Pageable pageable = new PageRequest(page, limit, sort);

		Page<Movie> pageResult = (Page<Movie>) this.movieRepository.findAll(pageable);

		return pageResult;
	}

	@Override
	public Movie create(Movie movie) {
		return this.movieRepository.save(movie);
	}

	@Override
	public Movie getById(Long id) {
		return this.movieRepository.findOne(id);
	}

	@Override
	public Movie modify(Long id, Movie movie) {
		Movie existingMovie = this.movieRepository.findOne(id);

		Long existingId = existingMovie.getId();

		BeanUtils.copyProperties(movie, existingMovie);

		existingMovie.setId(existingId);

		return this.movieRepository.save(existingMovie);
	}

	@Override
	public Movie delete(Long id) {
		Movie movie = this.movieRepository.findOne(id);

		movieRepository.delete(movie);

		return movie;
	}
}