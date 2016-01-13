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
package org.robbins.flashcards.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.domain.Persistable;

/**
 * Interface for auditable entities. Allows storing and retrieving creation and modification information. The changing
 * instance (typically some user) is to be defined by a generics definition.
 *
 * @param <U> the auditing type. Typically some kind of user.
 * @param <ID> the type of the audited type's identifier
 * @author Oliver Gierke
 */
public interface Auditable<U, ID extends Serializable> extends Persistable<ID>
{

	/**
	 * Returns the user who created this entity.
	 *
	 * @return the createdBy
	 */
	U getCreatedBy();

	/**
	 * Sets the user who created this entity.
	 *
	 * @param createdBy the creating entity to set
	 */
	void setCreatedBy(final U createdBy);

	/**
	 * Returns the creation date of the entity.
	 *
	 * @return the createdDate
	 */
	LocalDateTime getCreatedDate();

	/**
	 * Sets the creation date of the entity.
	 *
	 * @param creationDate the creation date to set
	 */
	void setCreatedDate(final LocalDateTime creationDate);

	/**
	 * Returns the user who modified the entity lastly.
	 *
	 * @return the lastModifiedBy
	 */
	U getLastModifiedBy();

	/**
	 * Sets the user who modified the entity lastly.
	 *
	 * @param lastModifiedBy the last modifying entity to set
	 */
	void setLastModifiedBy(final U lastModifiedBy);

	/**
	 * Returns the date of the last modification.
	 *
	 * @return the lastModifiedDate
	 */
	LocalDateTime getLastModifiedDate();

	/**
	 * Sets the date of the last modification.
	 *
	 * @param lastModifiedDate the date of the last modification to set
	 */
	void setLastModifiedDate(final LocalDateTime lastModifiedDate);
}
