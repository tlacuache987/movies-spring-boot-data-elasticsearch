package com.example.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.NestedField;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "blockbuster", type = "movie")
@Setting(settingPath = "/settings/settings.json")
public class Movie {

	public static final String NAME_PROPERTY = "name";

	@Id
	private Long id;

	@MultiField(mainField = @Field(type = FieldType.String, index = FieldIndex.analyzed, indexAnalyzer = "my_analyzer"), otherFields = {
			@NestedField(dotSuffix = "untouched", type = FieldType.String, store = true, index = FieldIndex.analyzed),
			@NestedField(dotSuffix = "sort", type = FieldType.String, store = true, index = FieldIndex.analyzed, indexAnalyzer = "keyword") })
	private String name;

	private Double rating;

	@Field(type = FieldType.Nested)
	private List<Genre> genre;

}
