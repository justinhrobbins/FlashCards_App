package org.robbins.flashcards.service;

import javax.inject.Inject;

import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.repository.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends GenericJpaServiceImpl<Tag, Long> implements TagService {
	
	@Inject
	private TagRepository repository;
	
	@Override
	protected JpaRepository <Tag, Long> getRepository() {
		return repository;
	}
	
	@Override
	public Tag findByName(String name) {
		return repository.findByName(name);
	}
}
