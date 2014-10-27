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
package org.robbins.flashcards.exceptions;

public class RepositoryException extends FlashcardsException
{
	public RepositoryException(final String message) {
		super(message);
	}

	public RepositoryException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
