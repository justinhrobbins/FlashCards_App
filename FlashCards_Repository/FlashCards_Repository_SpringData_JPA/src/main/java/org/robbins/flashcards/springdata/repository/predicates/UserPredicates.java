package org.robbins.flashcards.springdata.repository.predicates;

import com.mysema.query.types.Predicate;
import org.robbins.flashcards.model.QUser;

public class UserPredicates {

    public static Predicate hasOpenId(final String openId) {
        QUser user = QUser.user;
        return user.openid.eq(openId);
    }
}
