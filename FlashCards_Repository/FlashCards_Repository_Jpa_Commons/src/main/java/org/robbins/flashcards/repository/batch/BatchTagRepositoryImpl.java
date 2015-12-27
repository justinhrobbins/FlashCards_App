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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.robbins.flashcards.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("batchTagRepository")
public class BatchTagRepositoryImpl extends AbstractBatchCustomRepositoryImpl implements BatchSavingRepository<Tag>
{
	private static final String BATCH_INSERT_SQL = "insert into tag (TagName, CreatedUserId, CreatedDate) "
			+ " VALUES (?, ?, ?)";

	// TODO: Replace with injected property value
	private static final Integer BATCH_SIZE = 1000;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void postConstruct() {
		setDataSource(dataSource);
	}

	@Override
	public int batchSave(List<Tag> records) {
		int count = 0;
		for(final List<Tag> batch : Lists.partition(records, BATCH_SIZE)) {
			count += executeBatch(BATCH_INSERT_SQL, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException
				{
					Tag tag = batch.get(i);
					ps.setString(1, tag.getName());
					ps.setLong(2, tag.getCreatedBy());
					ps.setDate(3, new Date(tag.getCreatedDateAsDate().getTime()));
				}

				@Override
				public int getBatchSize() {
					return batch.size();
				}
			});
		}
		return count;
	}
}
