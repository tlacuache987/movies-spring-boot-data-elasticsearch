package com.example.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

	public static Pageable createPageable(int page, int size, String... sortAttributes) {
		List<Sort.Order> sortOrderList = new ArrayList<>();

		if (sortAttributes != null && sortAttributes.length > 0) {
			for (String s : sortAttributes) {
				if (s.contains(",")) {
					String[] parts = s.split(",");
					sortOrderList
							.add(new Sort.Order(Sort.Direction.fromStringOrNull(parts[1].toUpperCase()), parts[0]));
				} else {
					sortOrderList.add(new Sort.Order(Sort.Direction.ASC, s));
				}
			}
		}

		// Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));

		Pageable pageable = null;

		if (sortOrderList.size() > 0) {
			Sort sort = new Sort(sortOrderList);
			pageable = new PageRequest(page, size, sort);
		} else {
			pageable = new PageRequest(page, size);
		}

		return pageable;
	}
}
