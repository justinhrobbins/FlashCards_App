
package org.robbins.flashcards.presentation.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.robbins.flashcards.dto.FlashCardDto;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.FlashcardFacade;
import org.robbins.flashcards.facade.TagFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import java.util.*;

public class FlashCardAction extends FlashCardsAppBaseAction implements
        ModelDriven<FlashCardDto>, Preparable, SessionAware {

    private static final long serialVersionUID = -6246981237373738037L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashCardAction.class);

    @Inject
	@Qualifier("presentationFlashcardFacade")
    private transient FlashcardFacade flashcardFacade;

    @Inject
	@Qualifier("presentationTagFacade")
    private transient TagFacade tagFacade;

    private FlashCardDto flashCard = new FlashCardDto();

    // the exploded string (tag1, tag2, etc.) of the current Flashcard's Tags
    private String explodedTags;

    // used in Browse mode to switch between List View and Slideshow
    private String viewType;

    private Map<String, Object> httpSession;

    // a list of Flashcards used in List and Browse mode
    private List<FlashCardDto> flashCardList = new ArrayList<>();

    // a separate list of Tags not belonging directly to the Flashcard
    // usually a list of All the Tags in the DB
    private List<TagDto> tagList = new ArrayList<>();

    // the exploded string (tag1, tag2, ect) of the Tags in the tagList
    // this is not the same as the "explodedTags" which is an exploded list of the
    // FlashCard's tags
    private String allExplodedTags;

    @SkipValidation
    public String list() {
        // reset the FlashCard list
        this.flashCardList = null;

        try {
            this.flashCardList = flashcardFacade.list();

            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception in list():", e);
            return "error";
        }
    }

    @Override
    public void validate() {
        if (flashCard.getQuestion().length() == 0) {
            addFieldError("flashCard.question", getText("error.flashcard.question"));
        }

        if (flashCard.getAnswer().length() == 0) {
            addFieldError("flashCard.answer", getText("error.flashcard.answer"));
        }

        // remove any empty Link fields from the Arry List
        Iterator<String> iterator = this.flashCard.getLinks().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.length() == 0) {
                iterator.remove();
            }
        }
    }

    public String saveOrUpdate() {
        try {
			// workaround that converts that Tags passed by the form to Tag objects on the
			// FlashCard
			this.flashCard.setTags(convertToTags(getExplodedTags()));

            if ((this.flashCard.getId() != null) && (!StringUtils.isEmpty(this.flashCard.getId()))) {
				FlashCardDto existingFlashCard = flashcardFacade.findOne(this.flashCard.getId());
                existingFlashCard.setQuestion(this.flashCard.getQuestion());
                existingFlashCard.setAnswer(this.flashCard.getAnswer());
                existingFlashCard.setTags(this.flashCard.getTags());
                existingFlashCard.setLinks(this.flashCard.getLinks());

                flashcardFacade.save(existingFlashCard);

                LOGGER.debug("Flash Card updated successfully");
                this.addActionMessage(getText("actionmessage.flashcard.updated"));
            } else {
                flashcardFacade.save(this.flashCard);

                LOGGER.debug("Flash Card created successfully");
                this.addActionMessage(getText("actionmessage.flashcard.created"));
            }
            return "success";
        } catch (Exception e) {
            LOGGER.error("unable to create or update FlashCard", e);
            return "error";
        }
    }

    private Set<TagDto> convertToTags(final String explodedTags) throws FlashcardsException
	{
        Set<TagDto> tags = new HashSet<>();

        if (getExplodedTags() == null) {
            return null;
        }

        // create an array of strings with a space (",") as their delimiter
        String[] tokens = explodedTags.split(",");
        for (String token : tokens) {
            if (token.length() > 0) {
                TagDto tag = tagFacade.findByName(token);
                tags.add(tag);
            }
        }
        return tags;
    }

    private String convertFromTags(final Collection<TagDto> tags, final boolean addQuotes) {

        StringBuilder sb = new StringBuilder();
        String prefix = "";

        for (TagDto tempTag : tags) {
            sb.append(prefix);
            prefix = ", ";

            if (addQuotes) {
                sb.append("'" + tempTag.getName() + "'");
            } else {
                sb.append(tempTag.getName());
            }
        }

        return sb.toString();
    }

    @SkipValidation
    public String delete() {
        try {
            flashcardFacade.delete(this.flashCard.getId());

            LOGGER.debug("Flash Card deleted successfully");

            this.addActionMessage(getText("actionmessage.flashcard.deleted"));

            return "success";
        } catch (Exception e) {
            LOGGER.error("unable to delete Flash Card");
            return "error";
        }
    }

    @SkipValidation
    public String display() {
        try {
            if ((this.flashCard.getId() != null) && (!StringUtils.isEmpty(this.flashCard.getId()))) {
                this.flashCard = flashcardFacade.findOne(this.flashCard.getId());
            } else if (this.flashCard.getQuestion() != null) {
                this.flashCard = flashcardFacade.findByQuestion(this.flashCard.getQuestion());
            }
            this.setExplodedTags(convertFromTags(this.flashCard.getTags(), false));

            // get a list of Tags from the data tier
            this.tagList = null;

            tagList = tagFacade.list();

            this.setAllExplodedTags(convertFromTags(tagList, true));

            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception in display():", e);

            return "error";
        }
    }

    @SkipValidation
    public String browse() {
        // reset the FlashCard list
        this.flashCardList = null;

        try {

            // if there is a ViewType add it to the HTTP Session
            if (this.getViewType() != null) {
                LOGGER.debug("viewType found in Request: '" + this.getViewType() + "'");
                httpSession.put("viewType", this.getViewType());
            } // otherwise, try to get the ViewType from the HTTP Session
            else if (httpSession.containsKey("viewType")) {
                LOGGER.debug("viewType found in Session: '"
                        + (String) httpSession.get("viewType") + "'");
                this.setViewType((String) httpSession.get("viewType"));
            }

            // if we have specified Tags to limit the FlashCard results
            if ((this.getExplodedTags() != null)
                    && (!this.getExplodedTags().equals("All"))) {

                LOGGER.debug("ExplodedTags found in Request");

                // add the Exploded Tags to the HTTP Session
                httpSession.put("explodedTags", this.getExplodedTags());

                // workaround that converts that Tags passed by the form to Tag objects on
                // the FlashCard
                this.flashCard.setTags(convertToTags(getExplodedTags()));

                // get a list of Flash Cards from the data tier
                this.flashCardList = flashcardFacade.findByTagsIn(this.flashCard.getTags());
            } // if we have the Tag filter saved in the HTTP Session
            else if ((httpSession.containsKey("explodedTags"))
                    && (this.getExplodedTags() == null)) {

                LOGGER.debug("ExplodedTags found in HTTP Session");

                // do we have Tags saved in the session?
                this.setExplodedTags((String) httpSession.get("explodedTags"));

                // workaround that converts that Tags passed by the form to Tag objects on
                // the FlashCard
                this.flashCard.setTags(convertToTags(getExplodedTags()));

                // get a list of Flash Cards from the data tier
                this.flashCardList = flashcardFacade.findByTagsIn(this.flashCard.getTags());
            }
            // otherwise, let's grab all FlashCards for all Tags
            else {
                LOGGER.debug("Retrieving FlashCards for all Tags");

                // retrieve all the FlashCards
                this.flashCardList = flashcardFacade.list();
            }

            // get a list of Tags from the data tier
            this.tagList = null;
            tagList = tagFacade.list();

            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception in browse():", e);

            return "error";
        }
    }

    @SkipValidation
    public String form() {
        return display();
    }

    @Override
    public FlashCardDto getModel() {
        return this.flashCard;
    }

    @Override
    public void setSession(final Map<String, Object> httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public void prepare() throws Exception {
    }

    public String getAllExplodedTags() {
        return allExplodedTags;
    }

    public void setAllExplodedTags(final String allExplodedTags) {
        this.allExplodedTags = allExplodedTags;
    }

    public Map<String, Object> getHttpSession() {
        return httpSession;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(final String viewType) {
        this.viewType = viewType;
    }

    public List<TagDto> getTagList() {
        return tagList;
    }

    public void setTagList(final List<TagDto> tagList) {
        this.tagList = tagList;
    }

    public String getExplodedTags() {
        return explodedTags;
    }

    public void setExplodedTags(final String explodedTags) {
        this.explodedTags = explodedTags;
    }

    public FlashCardDto getFlashCard() {
        return this.flashCard;
    }

    public void setFlashCard(final FlashCardDto flashCard) {
        this.flashCard = flashCard;
    }

    public List<FlashCardDto> getFlashCardList() {
        return this.flashCardList;
    }

    public void setFlashCardList(final List<FlashCardDto> flashCardList) {
        this.flashCardList = flashCardList;
    }
}
