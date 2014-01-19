
package org.robbins.flashcards.service.jpa.base;

import org.robbins.flashcards.repository.jpa.base.CrudRepository;

public interface CrudService<T> {

    CrudRepository<T> getRepository();
}
