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
package org.robbins.flashcards.presentation.facade;

import org.robbins.flashcards.service.base.GenericPagingAndSortingService;

import java.io.Serializable;

public interface PagingAndSortingFacade<D, ID extends Serializable>
{
	GenericPagingAndSortingService<D, ID> getService();
}
