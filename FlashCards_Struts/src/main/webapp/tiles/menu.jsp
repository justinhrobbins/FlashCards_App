<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="browseflashcards" namespace="/flashcard" action="browse.action" >
	<s:param name="explodedTags">All</s:param>
</s:url>
<s:url id="flashcardform" namespace="/flashcard" action="form.action"/>
<s:url id="listflashcards" namespace="/flashcard" action="list.action"/>

<s:url id="tagform" namespace="/tag" action="form.action"/>
<s:url id="listtags" namespace="/tag" action="list.action"/>

<h4><s:text name="menu.flascard.links" /></h4>
<ul>
	<li><s:a errorText="Sorry your request had an error." href="%{browseflashcards}"><s:text name="menu.browse.flashcards" /></s:a></li>
	<li><s:a errorText="Sorry your request had an error." href="%{flashcardform}"><s:text name="menu.new.flashcard" /></s:a></li>
	<li><s:a errorText="Sorry your request had an error." href="%{listflashcards}"><s:text name="menu.list.flashcards" /></s:a></li>
</ul>
<h4><s:text name="menu.tag.links" /></h4>
<ul>
	<li><s:a errorText="Sorry your request had an error." href="%{tagform}"><s:text name="menu.new.tag" /></s:a></li>
	<li><s:a errorText="Sorry your request had an error." href="%{listtags}"><s:text name="menu.list.tags" /></s:a></li>
</ul>