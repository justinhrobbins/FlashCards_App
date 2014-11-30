
package org.robbins.flashcards.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FlashCardsAppRepository<T, ID extends Serializable> extends
        JpaRepository<T, ID> {

        List<T> findByCreatedBy_Id(Long userId);
}
