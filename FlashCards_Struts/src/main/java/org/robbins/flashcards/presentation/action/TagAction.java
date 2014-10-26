
package org.robbins.flashcards.presentation.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.robbins.flashcards.dto.TagDto;
import org.robbins.flashcards.exceptions.FlashcardsException;
import org.robbins.flashcards.facade.TagFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class TagAction extends FlashCardsAppBaseAction implements ModelDriven<TagDto>,
        Preparable, SessionAware {

    private static final long serialVersionUID = 2900181619806808497L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TagAction.class);

    private TagDto tag = new TagDto();

    @Inject
	@Qualifier("presentationTagFacade")
    private transient TagFacade tagFacade;

    private Map<String, Object> httpSession;

    private List<TagDto> tagList = new ArrayList<>();

    @SkipValidation
    public String list() {
        try {
            tagList = tagFacade.list();

            LOGGER.debug("listTags found '" + tagList.size() + "' tags");

            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception in listTags():", e);

            return "error";
        }
    }

    public String saveOrUpdate() {
        try {
            if ((this.tag.getId() != null) && (this.tag.getId() != 0)) {
                TagDto existingTag = tagFacade.findOne(this.tag.getId());
                existingTag.setName(this.tag.getName());
                tagFacade.save(existingTag);

                LOGGER.debug("Tag updated successfully");
                this.addActionMessage(getText("actionmessage.tag.updated"));
            } else {
                tagFacade.save(this.tag);

                LOGGER.debug("Tag created successfully");
                this.addActionMessage(getText("actionmessage.tag.created"));
            }
            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception while saving Tag:", e);
            return "error";
        }
    }

    @SkipValidation
    public String delete() {
        try {
            tagFacade.delete(this.tag.getId());

            LOGGER.debug("Tag deleted successfully");

            this.addActionMessage(getText("actionmessage.tag.deleted"));

            return "success";
        } catch (Exception e) {
            LOGGER.error("unable to delete Tag");

            this.addActionMessage(getText("error.tag.delete.failed"));

            // we'll assume the Tag was not deleted because it is already assigned to one
            // or more Flashcards
            // maybe need to add code later to prevent a delete attempt if the Tag has
            // FlashCards
            this.addActionMessage(getText("error.tag.delete.failed.extra.info"));

            return "input";
        }
    }

    @SkipValidation
    public String display() {
        try {
            if ((this.tag.getId() != null) && (this.tag.getId() != 0)) {
                this.tag = tagFacade.findOne(this.tag.getId());
            } else if (this.tag.getName() != null) {
                this.tag = tagFacade.findByName(this.tag.getName());
            }
            return "success";
        } catch (Exception e) {
            LOGGER.error("Exception in display():", e);
            return "error";
        }
    }

    @SkipValidation
    public String form() {
        return display();
    }

    @Override
    public void validate() {
        // Tag name cannot be empty
        if (tag.getName().length() == 0) {
            LOGGER.debug("Tag name is empty. Adding Field Error for 'Name'");
            addFieldError("tag.name", getText("error.tag.name.required"));
        } // Prevent duplicate Tag
        else {
            LOGGER.debug("Confirming Tag does not already exist") ;
			try
			{
				TagDto tempTag = tagFacade.findByName(tag.getName());
				if (tempTag != null) {
					LOGGER.debug("Tag already exists.  Adding Field Error for 'Name'");
					addFieldError("tag.name", getText("error.tag.exists"));
				}
			}
            catch (FlashcardsException e)
			{
				LOGGER.error("Exception in display():", e);
				addFieldError("tag.name", getText("error.tag.validation.failure"));
			}
        }
    }

    @Override
    public TagDto getModel() {
        return tag;
    }

    @Override
    public void prepare() throws Exception {
    }

    public Map<String, Object> getHttpSession() {
        return httpSession;
    }

    @Override
    public void setSession(final Map<String, Object> httpSession) {
        this.httpSession = httpSession;
    }

    public TagDto getTag() {
        return tag;
    }

    public void setTag(final TagDto tag) {
        this.tag = tag;
    }

    public List<TagDto> getTagList() {
        return tagList;
    }

    public void setTagList(final List<TagDto> tagList) {
        this.tagList = tagList;
    }
}
