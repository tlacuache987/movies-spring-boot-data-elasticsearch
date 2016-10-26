package com.example.rest.controller.utils;

import java.util.function.BiConsumer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;

public class RestControllerUtils {

	public static BiConsumer<HttpServletResponse, Page<?>> setHeaders = (response, page) -> {
		response.setHeader("page.number", String.valueOf(page.getNumber()));
		response.setHeader("page.numberOfElements", String.valueOf(page.getNumberOfElements()));
		response.setHeader("page.size", String.valueOf(page.getSize()));
		response.setHeader("page.totalElements", String.valueOf(page.getTotalElements()));
		response.setHeader("page.totalPages", String.valueOf(page.getTotalPages()));
		response.setHeader("page.first", String.valueOf(page.isFirst()));
		response.setHeader("page.last", String.valueOf(page.isLast()));
	};
}
