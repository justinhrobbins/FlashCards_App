
package org.robbins.flashcards.repository;

import java.io.Serializable;

public interface UserRepository<E, ID extends Serializable> extends FlashCardsAppRepository<E, ID> {

    E findUserByOpenid(final String openid);
}
