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
package org.robbins.flashcards.cassandra.repository.executionlisteners;

import org.cassandraunit.spring.AbstractCassandraUnitTestExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class CassandraUnitDependencyInjectionTestExecutionListener extends AbstractCassandraUnitTestExecutionListener
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraUnitDependencyInjectionTestExecutionListener.class);

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		startServer(testContext);
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		if (Boolean.TRUE.equals(testContext.getAttribute(DependencyInjectionTestExecutionListener.REINJECT_DEPENDENCIES_ATTRIBUTE))) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Cleaning and reloading server for test context [" + testContext + "].");
			}
			cleanServer();
			startServer(testContext);
		}
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		cleanServer();
	}
}
