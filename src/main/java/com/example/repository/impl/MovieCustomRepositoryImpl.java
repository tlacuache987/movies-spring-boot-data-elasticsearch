package com.example.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.example.model.Movie;
import com.example.repository.IMovieCustomRepository;
import com.example.repository.custom.parameters.FilterParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MovieCustomRepositoryImpl implements IMovieCustomRepository {

	@Value("${venue.search.scroll.page.size: 1000}")
	private int scrollPageSize;

	@Value("${venue.search.scroll.searchcontext.time.millis: 1000}")
	private int scrollTime;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public Future<List<Movie>> getAllByFilterParameters(List<FilterParameter> parameterList, Pageable page) {
		Sort userRequestedSort = page.getSort();
		List<Movie> outcome = new ArrayList<Movie>();
		Pageable scrollPage;

		boolean hasRecords = true;
		int i = 0;
		while (hasRecords) {
			scrollPage = new PageRequest(i++, scrollPageSize, userRequestedSort);
			log.debug("Pagination : {} ", scrollPage.toString());

			final SearchQuery query = composeQuery2(parameterList, scrollPage, true);
			query.addIndices("blockbuster");
			query.addTypes("movie");
			
			Optional.ofNullable(query.getQuery()).ifPresent(e -> {
				log.info(e.toString());
			});

			Optional.ofNullable(query.getFilter()).ifPresent(e -> {
				log.info(e.toString());
			});

			Optional.ofNullable(query.getElasticsearchSorts()).ifPresent(e -> {
				e.forEach(s -> {
					log.info(s.toString());
				});
			});

			Page<Movie> pagedResults = elasticsearchTemplate.queryForPage(query, Movie.class);
			if (pagedResults.hasContent()) {
				outcome.addAll(pagedResults.getContent());
			}
			if (!pagedResults.hasNext()) {
				hasRecords = false;
			}
		}

		log.info("Total number of records obtained --" + outcome.size());

		return new AsyncResult<List<Movie>>(outcome);
	}

	public SearchQuery composeQuery2(List<FilterParameter> parameterList, Pageable pageable, boolean isNvs) {

		final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		final NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

		if (parameterList != null && parameterList.size() > 0) {

			for (FilterParameter filterParameter : parameterList) {

				if (filterParameter.getKey() != null && filterParameter.getValue() != null)
					switch (filterParameter.getKey()) {
					case Movie.NAME_PROPERTY: {

						@SuppressWarnings("unchecked")
						List<String> values = (List<String>) filterParameter.getValue();

						boolQueryBuilder
								.must(buildShouldBoolQueryBuilderFromListOfValues(values, filterParameter.getKey()));
						break;
					}
					}
			}

		}

		nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

		final SearchQuery query = nativeSearchQueryBuilder.withPageable(pageable).build();

		return query;
	}

	private BoolQueryBuilder buildShouldBoolQueryBuilderFromListOfValues(List<String> filterValues, String fieldName) {
		BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();
		for (String value : filterValues) {
			QueryStringQueryBuilder query = QueryBuilders.queryString(this.preUpTextSearch(value)).field(fieldName);
			subBoolQueryBuilder.should(query);
		}
		return subBoolQueryBuilder;
	}

	public String preUpTextSearch(String text) {
		final StringBuffer result = new StringBuffer();

		String tmp = text;//text.replaceAll("[^a-zA-Z0-9\\s]", "");

		final String words[] = tmp.split(" ");

		for (int i = 0; i < words.length; i++) {
			tmp = words[i].trim();
			if (tmp.length() > 0) {
				result.append(String.format("*%s* ", words[i].trim()));
			}
		}

		return result.toString().trim();
	}

}
