package org.robbins.flashcards.util;

public class ConstsUtil {

	public static final String EDIT_FLASHCARD = "editFlashCard";
	public static final String FLASHCARD_FORM = "flashCardForm";
	public static final String LIST_FLASHCARDS = "listFlashCards";
	public static final String LIST_TAGS = "listTags";
	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";
	public static final String LOG_IN_URL = "/api/login/loginForm.jsp";
	public static final String OPEN_ID_URL = "/api/login/openIdServlet";
	public static final String SHELL_VIEW = "ShellView";
	public static final String TAG_FORM = "tagForm";
	public static final String USER_ID = "userId";
	public static final String FLASHCARD_REST_URL = "/api/v1/flashcards";
	public static final String TAG_REST_URL = "/api/v1/tags";
	public static final String USER_REST_URL = "/api/v1/users";
	public static final String DEFAULT_FLASHCARDS_LIST_FIELDS = "id,question,answer,tags,name";
	public static final String DEFAULT_FLASHCARDS_FIELDS = "id,question,answer,tags,name,links,createdDate,lastModifiedDate";
	public static final String DEFAULT_TAGS_LIST_FIELDS = "id,name,flashcards,question";
	public static final String DEFAULT_TAGS_FIELDS = "id,name,flashcards,question,createdDate,lastModifiedDate";
	public static final String DEFAULT_AUTH_HEADER = "Basic YXBpdXNlcjphcGl1c2VycGFzc3dvcmQ=";
}
