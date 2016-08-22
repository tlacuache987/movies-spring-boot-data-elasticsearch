package com.example.service.api.impl;

import java.util.List;

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
		return movieRepository.findByName(name);
	}

	public List<Movie> getByRatingInterval(Double beginning, Double end) {
		return movieRepository.findByRatingBetween(beginning, end);
	}

	public Movie addMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public Page<Movie> getAll(int page, int limit) {

		Pageable pageable = new PageRequest(page, limit, Sort.Direction.ASC, "id");

		Page<Movie> pageResult = (Page<Movie>) movieRepository.findAll(pageable);

		// page.get
		return pageResult;
	}

	@Override
	public Movie create(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie modify(Long id, Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}