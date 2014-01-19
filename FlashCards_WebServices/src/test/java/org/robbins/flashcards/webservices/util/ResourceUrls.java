
package org.robbins.flashcards.webservices.util;

public class ResourceUrls {

    public static final String tag = "tags/{id}";

    public static final String tags = "tags/";

    public static final String tagsSearch = "tags/search/?name={name}";

    public static final String tagUpdate = "tags/{id}/update";

    public static final String flashCard = "flashcards/{id}";

    public static final String flashCards = "flashcards/";

    public static final String flashCardsSearch = "flashcards/search/?question={question}&tags={tags}";

    public static final String flashCardUpdate = "flashcards/{id}/update";

    public static final String user = "users/{id}";

    public static final String users = "users/";

    public static final String usersSearch = "users/search/?openid={openid}";

    public static final String userUpdate = "users/{id}/update";
}
