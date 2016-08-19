package com.example.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "blockbuster", type = "movie")
public class Movie {
	@Id
	private Long id;
	private String name;
	private Double rating;

	@Field(type = FieldType.Nested)
	private List<Genre> genre;

}
