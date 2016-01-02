/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package org.robbins.flashcards.dto.util;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtils
{
	public static Optional<Pageable> getPageRequest(final Integer page, final Integer size,
			final String sortOrder, final String sortDirection)
	{

		if (page == null || size == null)
		{
			return Optional.empty();
		}

		if (!StringUtils.isEmpty(sortOrder))
		{
			Sort sort = getSort(sortOrder, sortDirection);
			return Optional.of(new PageRequest(page, size, sort));
		}
		else
		{
			return Optional.of(new PageRequest(page, size));
		}
	}

	public static Sort getSort(final String sort, final String order)
	{

		if ((StringUtils.isEmpty(order)) || (order.equals("asc")))
		{
			return new Sort(Sort.Direction.ASC, order);
		}
		else if (order.equals("desc"))
		{
			return new Sort(Sort.Direction.DESC, order);
		}
		else
		{
			throw new IllegalArgumentException("Sort order must be 'asc' or 'desc'.  '"
					+ order + "' is not an acceptable sort order");
		}
	}
}
