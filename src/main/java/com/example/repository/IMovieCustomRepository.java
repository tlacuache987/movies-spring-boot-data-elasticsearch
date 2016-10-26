package com.example.repository;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.domain.Pageable;

import com.example.model.Movie;
import com.example.repository.custom.parameters.FilterParameter;

public interface IMovieCustomRepository {
	Future<List<Movie>> getAllByFilterParameters(List<FilterParameter> parameterList, Pageable page);
}
