package com.sf.util;

import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.util.CollectionUtils;

public class CollectionUtil {

	// TODO : Validate for max limit as well

	/**
	 * returns a view (not a new list) of the sourceList for the range based on
	 * page and pageSize
	 * 
	 * @param sourceList
	 * @param page,
	 *            page number should start from 1
	 * @param pageSize
	 * @return
	 */
	public static <T> List<T> getPaginatedList(List<T> sourceList, int pageNo, int pageSize) {
		if (pageSize <= 0) {
			throw new IllegalArgumentException("Invalid Page Size: " + pageSize);
		}
		if (pageNo <= 0) {
			throw new IllegalArgumentException("Invalid Page No: " + pageNo);
		}
		int fromIndex = (pageNo - 1) * pageSize;
		if (CollectionUtils.isEmpty(sourceList) || sourceList.size() < fromIndex) {
			return Collections.emptyList();
		}
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}

	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> combineFiltersUsingOperator(BinaryOperator<Predicate<T>> operator,
	                Predicate<T>... predicates) {
		return Stream.of(predicates).reduce(x -> true, operator);
	}

}
