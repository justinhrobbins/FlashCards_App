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
package org.robbins.flashcards.repository.batch;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.base.Preconditions;

public abstract class AbstractBatchCustomRepositoryImpl
{
	private JdbcTemplate jdbcTemplate = null;

	protected int executeBatch(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter) {
		Preconditions.checkNotNull(jdbcTemplate, "Datasource not set for batch statement.");
		int[] rows = jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
		return sumRows(rows);
	}

	private int sumRows(int[] rows) {
		int rowCount = 0;
		for(int row : rows) {
			rowCount += row;
		}
		return rowCount;
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
