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
package org.robbins.flashcards.context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class FlashCardsApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardsApplicationContextInitializer.class);

	private final String REPOSITORY_PROFILE_PROPERTY = "repository.profile";
	private final String SECURITY_PROFILE_PROPERTY = "security.profile";
	private final String TESTDATA_PROFILE_PROPERTY= "test.data.profile";

	private final String DEFAULT_REPOSITORY_PROFILE = "spring-data";
	private final String DEFAULT_SECURITY_PROFILE = "security-http";
	private final String DEFAULT_TESTDATA_PROFILE = "test-data";

	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		final ConfigurableEnvironment environment = applicationContext.getEnvironment();

		addPropertiesToEnvironment(environment);
		addSpringProfiles(environment);
	}

	private void addPropertiesToEnvironment(final ConfigurableEnvironment environment)
	{
		addDefaultPropsToEnvironment(environment);
		addOverridePropsToEnvironment(environment);
		addEnvironmentPropsToEnvironment(environment);
	}

	private void addSpringProfiles(final ConfigurableEnvironment environment)
	{
		addRepositoryProfile(environment);
		addSecurityProfile(environment);
		addTestDataProfile(environment);
	}

	private void addDefaultPropsToEnvironment(final ConfigurableEnvironment environment)
	{
		final String DEFAULT_PROPS = "default.properties";
		final String DEFAULT_PROPS_LOCATION = "classpath:" + DEFAULT_PROPS;

		addPropsToEnvironment(DEFAULT_PROPS, DEFAULT_PROPS_LOCATION, environment);
	}

	private void addOverridePropsToEnvironment(final ConfigurableEnvironment environment)
	{
		final String OVERRIDE_PROPS = "override.properties";
		final String LOCATION = "classpath:" + OVERRIDE_PROPS;

		addPropsToEnvironment(OVERRIDE_PROPS, LOCATION, environment);
	}

	private void addEnvironmentPropsToEnvironment(final ConfigurableEnvironment environment)
	{
		final String ENV = environment.getProperty("FLASHCARDS_ENV");
		if (!StringUtils.isEmpty(ENV))
		{
			final String ENV_PROPS =  ENV + "-profile.properties";
			final String LOCATION = "classpath:" + ENV_PROPS;

			addPropsToEnvironment(ENV_PROPS, LOCATION, environment);
		}
	}

	private void addPropsToEnvironment(final String PROPS, final String LOCATION, final ConfigurableEnvironment environment)
	{
		try
		{
			final Resource defaultPropsResource = new DefaultResourceLoader().getResource(LOCATION);
			LOGGER.info(PROPS + " loaded from {}", defaultPropsResource.getURI());
			final PropertiesPropertySource defaultProps = new ResourcePropertySource(defaultPropsResource);
			environment.getPropertySources().addFirst(defaultProps);
		}
		catch (final IOException e)
		{
			LOGGER.info("Unable to find properties file {}", PROPS);
		}
	}

	private void addRepositoryProfile(final ConfigurableEnvironment environment)
	{
		addProfile(REPOSITORY_PROFILE_PROPERTY, DEFAULT_REPOSITORY_PROFILE, environment);
	}

	private void addSecurityProfile(final ConfigurableEnvironment environment)
	{
		addProfile(SECURITY_PROFILE_PROPERTY, DEFAULT_SECURITY_PROFILE, environment);
	}

	private void addTestDataProfile(final ConfigurableEnvironment environment)
	{
		addProfile(TESTDATA_PROFILE_PROPERTY, DEFAULT_TESTDATA_PROFILE, environment);
	}

	private void addProfile(final String profileName, final String defaultProfileValue, final ConfigurableEnvironment environment)
	{
		final String repositoryProfile = environment.getProperty(profileName, defaultProfileValue);
		LOGGER.info("Setting Spring Profile: {}={}", profileName, repositoryProfile);
		environment.addActiveProfile(repositoryProfile);
	}
}
