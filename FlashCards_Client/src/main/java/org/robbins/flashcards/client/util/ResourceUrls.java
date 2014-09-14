
package org.robbins.flashcards.client.util;

public final class ResourceUrls {

    private ResourceUrls() {
    };

    public static final String tag = "v1/tags/{id}";

    public static final String tags = "v1/tags/";

    public static final String tagsSearch = "v1/tags/search/?name={name}";

    public static final String tagUpdate = "v1/tags/{id}/update";

    public static final String flashCard = "v1/flashcards/{id}";

    public static final String flashCards = "v1/flashcards/";

    public static final String flashCardsSearch = "v1/flashcards/search/?question={question}&tags={tags}";

    public static final String flashCardUpdate = "v1/flashcards/{id}/update";

    public static final String user = "v1/users/{id}";

    public static final String users = "v1/users/";

    public static final String usersSearch = "v1/users/search/?openid={openid}";

    public static final String userUpdate = "v1/users/{id}/update";

    public static final String status = "status/";
}
