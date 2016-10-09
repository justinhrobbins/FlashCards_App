/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package org.robbins.flashcards.caching;

import java.lang.annotation.Annotation;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;

import org.robbins.flashcards.model.common.AbstractPersistable;
import org.springframework.cache.interceptor.SimpleKey;

public class PersistableCacheKeyGenerator implements CacheKeyGenerator
{
	@Override
	public GeneratedCacheKey generateCacheKey(final CacheKeyInvocationContext<? extends Annotation> context)
	{
		final Object keyValue = context.getKeyParameters()[0].getValue();
		if (keyValue instanceof Long)
		{
			return new SimpleGeneratedCacheKey(keyValue);
		}
		if (keyValue instanceof String)
		{
			return new SimpleGeneratedCacheKey(keyValue);
		}
		else if (keyValue instanceof AbstractPersistable)
		{
			final AbstractPersistable persistable = (AbstractPersistable) keyValue;
			return new SimpleGeneratedCacheKey(persistable.getId());
		}
		else
		{
			throw new IllegalArgumentException("Unable to determine key value from CacheKeyInvocationContext");
		}
	}

	@SuppressWarnings("serial")
	private static class SimpleGeneratedCacheKey extends SimpleKey implements GeneratedCacheKey
	{

		SimpleGeneratedCacheKey(final Object... elements)
		{
			super(elements);
		}
	}
}
