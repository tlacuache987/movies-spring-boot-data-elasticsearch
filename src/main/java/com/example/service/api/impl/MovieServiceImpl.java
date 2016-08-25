package com.example.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Movie;
import com.example.repository.IMovieRepository;
import com.example.service.api.IMovieService;
import com.example.service.utils.NullAwareBeanUtilsBean;
import com.example.service.utils.PageableUtils;

import lombok.SneakyThrows;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public Page<Movie> getByName(String name, int page, int size, String... sortAttributes) {
		Pageable pageable = PageableUtils.createPageable(page, size, sortAttributes);

		Page<Movie> pageResult = (Page<Movie>) this.movieRepository.findByName(name, pageable);

		return pageResult;
	}

	@Override
	public Page<Movie> getByNameLike(String name, int page, int size, String... sortAttributes) {
		Pageable pageable = PageableUtils.createPageable(page, size, sortAttributes);

		Page<Movie> pageResult = (Page<Movie>) this.movieRepository.findByNameContaining(name, pageable);

		return pageResult;
	}

	@Override
	public Page<Movie> getByRatingInterval(Double beginning, Double end, int page, int size, String... sortAttributes) {
		Pageable pageable = PageableUtils.createPageable(page, size, sortAttributes);

		Page<Movie> pageResult = (Page<Movie>) this.movieRepository.findByRatingBetween(beginning, end, pageable);

		return pageResult;
	}

	@Override
	public Page<Movie> getAll(int page, int size, String... sortAttributes) {

		Pageable pageable = PageableUtils.createPageable(page, size, sortAttributes);

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
	@SneakyThrows
	public Movie modify(Long id, Movie movie) {
		Movie existingMovie = this.movieRepository.findOne(id);

		Long existingId = existingMovie.getId();

		new NullAwareBeanUtilsBean().copyProperties(existingMovie, movie);

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