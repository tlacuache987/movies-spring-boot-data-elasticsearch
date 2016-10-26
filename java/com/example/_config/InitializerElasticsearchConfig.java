package com.example._config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;

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
					movieService.create(m);
				}

			movieService.create(theHobbit());
			movieService.create(theLittleHobbit());
			movieService.create(littleBunny());
			movieService.create(hotBunnyMachine());
		};
	}

	private Movie hotBunnyMachine() {
		Movie movie = new Movie();
		movie.setId(602L);
		movie.setRating(5.4d);
		movie.setName("Hot Bunny Machine");

		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("ADULTS"));
		genres.add(new Genre("RATED"));
		genres.add(new Genre("FANTASY"));
		movie.setGenre(genres);

		return movie;
	}

	private Movie littleBunny() {
		Movie movie = new Movie();
		movie.setId(603L);
		movie.setRating(5.4d);
		movie.setName("Little Bunny");

		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("BABIES"));
		movie.setGenre(genres);

		return movie;
	}

	private Movie theLittleHobbit() {
		Movie movie = new Movie();
		movie.setId(604L);
		movie.setRating(6.4d);
		movie.setName("The Little Hobbit");

		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("FANTASY"));
		genres.add(new Genre("BABIES"));
		movie.setGenre(genres);

		return movie;
	}

	private Movie theHobbit() {
		Movie movie = new Movie();
		movie.setId(605L);
		movie.setRating(3.4d);
		movie.setName("The Hobbit");

		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("FANTASY"));
		movie.setGenre(genres);

		return movie;
	}

	@Bean
	@DependsOn(value = { "initializeElasticSearch" })
	public CommandLineRunner testElasticSearch() {
		return (args) -> {
			Page<Movie> starWarsNameQuery = movieService.getByName("Star Wars", 0, 10, new String[] {});
			log.info("Content of star wars name query is {}", starWarsNameQuery);

			Page<Movie> brideQuery = movieService.getByName("The Princess Bride", 0, 10, new String[] {});
			log.info("Content of princess bride name query is {}", brideQuery);

			Page<Movie> byRatingInterval = movieService.getByRatingInterval(6d, 9d, 0, 10, new String[] {});
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
