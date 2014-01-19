
package org.robbins.flashcards.presentation.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.robbins.flashcards.model.FlashCard;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.FlashCardService;
import org.robbins.flashcards.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FlashCardAction extends FlashCardsAppBaseAction implements
        ModelDriven<FlashCard>, Preparable, SessionAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6246981237373738037L;

    static final Logger logger = LoggerFactory.getLogger(FlashCardAction.class);

    @Inject
    private transient FlashCardService flashcardService;

    @Inject
    private transient TagService tagService;

    private FlashCard flashCard = new FlashCard();

    // the exploded string (tag1, tag2, etc.) of the current Flashcard's Tags
    private String explodedTags;

    // used in Browse mode to switch between List View and Slideshow
    private String viewType;

    private Map<String, Object> httpSession;

    // a list of Flashcards used in List and Browse mode
    private List<FlashCard> flashCardList = new ArrayList<FlashCard>();

    // a separate list of Tags not belonging directly to the Flashcard
    // usually a list of All the Tags in the DB
    private List<Tag> tagList = new ArrayList<Tag>();

    // the exploded string (tag1, tag2, ect) of the Tags in the tagList
    // this is not the same as the "explodedTags" which is an exploded list of the
    // FlashCard's tags
    private String allExplodedTags;

    @SkipValidation
    public String list() {
        // reset the FlashCard list
        this.flashCardList = null;

        try {
            this.flashCardList = flashcardService.findAll();

            return "success";
        } catch (Exception e) {
            logger.error("Exception in list():", e);
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
        // workaround that converts that Tags passed by the form to Tag objects on the
        // FlashCard
        this.flashCard.setTags(convertToTags(getExplodedTags()));

        try {
            if ((this.flashCard.getId() != null) && (this.flashCard.getId() != 0)) {
                FlashCard existingFlashCard = flashcardService.findOne(this.flashCard.getId());
                existingFlashCard.setQuestion(this.flashCard.getQuestion());
                existingFlashCard.setAnswer(this.flashCard.getAnswer());
                existingFlashCard.setTags(this.flashCard.getTags());
                existingFlashCard.setLinks(this.flashCard.getLinks());

                flashcardService.save(existingFlashCard);

                logger.debug("Flash Card updated successfully");
                this.addActionMessage(getText("actionmessage.flashcard.updated"));
            } else {
                flashcardService.save(this.flashCard);

                logger.debug("Flash Card created successfully");
                this.addActionMessage(getText("actionmessage.flashcard.created"));
            }
            return "success";
        } catch (Exception e) {
            logger.error("unable to create or update FlashCard", e);
            return "error";
        }
    }

    private Set<Tag> convertToTags(String explodedTags) {
        Set<Tag> tags = new HashSet<Tag>();

        if (getExplodedTags() == null) {
            return null;
        }

        // create an array of strings with a space (",") as their delimiter
        String tokens[] = explodedTags.split(",");
        for (String token : tokens) {
            if (token.length() > 0) {
                Tag tag = tagService.findByName(token);
                tags.add(tag);
            }
        }
        return tags;
    }

    private String convertFromTags(Collection<Tag> tags, boolean addQuotes) {

        StringBuilder sb = new StringBuilder();
        String prefix = "";

        for (Tag tempTag : tags) {
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
            flashcardService.delete(this.flashCard.getId());

            logger.debug("Flash Card deleted successfully");

            this.addActionMessage(getText("actionmessage.flashcard.deleted"));

            return "success";
        } catch (Exception e) {
            logger.error("unable to delete Flash Card");
            return "error";
        }
    }

    @SkipValidation
    public String display() {
        try {
            if ((this.flashCard.getId() != null) && (this.flashCard.getId() != 0)) {
                this.flashCard = flashcardService.findOne(this.flashCard.getId());
            } else if (this.flashCard.getQuestion() != null) {
                this.flashCard = flashcardService.findByQuestion(this.flashCard.getQuestion());
            }
            this.setExplodedTags(convertFromTags(this.flashCard.getTags(), false));

            // get a list of Tags from the data tier
            this.tagList = null;

            tagList = tagService.findAll();

            this.setAllExplodedTags(convertFromTags(tagList, true));

            return "success";
        } catch (Exception e) {
            logger.error("Exception in display():", e);

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
                logger.debug("viewType found in Request: '" + this.getViewType() + "'");
                httpSession.put("viewType", this.getViewType());
            }
            // otherwise, try to get the ViewType from the HTTP Session
            else if (httpSession.containsKey("viewType")) {
                logger.debug("viewType found in Session: '"
                        + (String) httpSession.get("viewType") + "'");
                this.setViewType((String) httpSession.get("viewType"));
            }

            // if we have specified Tags to limit the FlashCard results
            if ((this.getExplodedTags() != null)
                    && (!this.getExplodedTags().equals("All"))) {

                logger.debug("ExplodedTags found in Request");

                // add the Exploded Tags to the HTTP Session
                httpSession.put("explodedTags", this.getExplodedTags());

                // workaround that converts that Tags passed by the form to Tag objects on
                // the FlashCard
                this.flashCard.setTags(convertToTags(getExplodedTags()));

                // get a list of Flash Cards from the data tier
                this.flashCardList = flashcardService.findByTagsIn(this.flashCard.getTags());
            }
            // if we have the Tag filter saved in the HTTP Session
            else if ((httpSession.containsKey("explodedTags"))
                    && (this.getExplodedTags() == null)) {

                logger.debug("ExplodedTags found in HTTP Session");

                // do we have Tags saved in the session?
                this.setExplodedTags((String) httpSession.get("explodedTags"));

                // workaround that converts that Tags passed by the form to Tag objects on
                // the FlashCard
                this.flashCard.setTags(convertToTags(getExplodedTags()));

                // get a list of Flash Cards from the data tier
                this.flashCardList = flashcardService.findByTagsIn(this.flashCard.getTags());
            }
            // otherwise, let's grab all FlashCards for all Tags
            else {
                logger.debug("Retrieving FlashCards for all Tags");

                // retrieve all the FlashCards
                this.flashCardList = flashcardService.findAll();
            }

            // get a list of Tags from the data tier
            this.tagList = null;
            tagList = tagService.findAll();

            return "success";
        } catch (Exception e) {
            logger.error("Exception in browse():", e);

            return "error";
        }
    }

    @SkipValidation
    public String form() {
        return display();
    }

    @Override
    public FlashCard getModel() {
        return this.flashCard;
    }

    @Override
    public void setSession(Map<String, Object> httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public void prepare() throws Exception {
    }

    public String getAllExplodedTags() {
        return allExplodedTags;
    }

    public void setAllExplodedTags(String allExplodedTags) {
        this.allExplodedTags = allExplodedTags;
    }

    public Map<String, Object> getHttpSession() {
        return httpSession;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getExplodedTags() {
        return explodedTags;
    }

    public void setExplodedTags(String explodedTags) {
        this.explodedTags = explodedTags;
    }

    public FlashCard getFlashCard() {
        return this.flashCard;
    }

    public void setFlashCard(FlashCard flashCard) {
        this.flashCard = flashCard;
    }

    public List<FlashCard> getFlashCardList() {
        return this.flashCardList;
    }

    public void setFlashCardList(List<FlashCard> flashCardList) {
        this.flashCardList = flashCardList;
    }
}
