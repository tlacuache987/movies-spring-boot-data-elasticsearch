package com.example._config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.example.model.Genre;
import com.example.model.Movie;
import com.example.service.api.IMovieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class InitializerElasticsearchConfig {

	@Autowired
	private IMovieService movieService;

	@Bean
	public CommandLineRunner initializeElasticSearch() {
		return (args) -> {
			List<Movie> movies = MovieStub.createMovies();
			Long id = 1L;
			for (int i = 0; i < 300; i++)
				for (Movie m : movies) {
					m.setId(id++);
					movieService.addMovie(m);
				}
		};
	}

	@Bean
	@DependsOn(value = { "initializeElasticSearch" })
	public CommandLineRunner testElasticSearch() {
		return (args) -> {
			List<Movie> starWarsNameQuery = movieService.getByName("Star Wars");
			log.info("Content of star wars name query is {}", starWarsNameQuery);

			List<Movie> brideQuery = movieService.getByName("The Princess Bride");
			log.info("Content of princess bride name query is {}", brideQuery);

			List<Movie> byRatingInterval = movieService.getByRatingInterval(6d, 9d);
			log.info("Content of Rating Interval query is {}", byRatingInterval);
		};
	}

	static class MovieStub {

		public static List<Movie> createMovies() {

			Movie princessPrideMovie = new Movie();
			princessPrideMovie.setId(2L);
			princessPrideMovie.setRating(8.4d);
			princessPrideMovie.setName("The Princess Bride");

			List<Genre> princessPrideGenre = new ArrayList<Genre>();
			princessPrideGenre.add(new Genre("ACTION"));
			princessPrideGenre.add(new Genre("ROMANCE"));
			princessPrideMovie.setGenre(princessPrideGenre);

			Movie starWarsMovie = new Movie();
			starWarsMovie.setId(1L);
			starWarsMovie.setRating(9.6d);
			starWarsMovie.setName("Star Wars");

			List<Genre> starWarsGenre = new ArrayList<Genre>();
			starWarsGenre.add(new Genre("ACTION"));
			starWarsGenre.add(new Genre("SCI_FI"));
			starWarsMovie.setGenre(starWarsGenre);

			return new ArrayList<Movie>() {
				{
					add(starWarsMovie);
					add(princessPrideMovie);
				}
			};
		}
	}
}
