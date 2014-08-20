
package org.robbins.flashcards.presentation.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.robbins.flashcards.model.Tag;
import org.robbins.flashcards.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class TagAction extends FlashCardsAppBaseAction implements ModelDriven<Tag>,
        Preparable, SessionAware {

    private static final long serialVersionUID = 2900181619806808497L;

    private static final Logger LOGGER = LoggerFactory.getLogger(TagAction.class);

    private Tag tag = new Tag();

    @Inject
    private transient TagService tagService;

    private Map<String, Object> httpSession;

    private List<Tag> tagList = new ArrayList<Tag>();

    @SkipValidation
    public String list() {
        try {
            tagList = tagService.findAll();

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
                Tag existingTag = tagService.findOne(this.tag.getId());
                existingTag.setName(this.tag.getName());
                tagService.save(existingTag);

                LOGGER.debug("Tag updated successfully");
                this.addActionMessage(getText("actionmessage.tag.updated"));
            } else {
                tagService.save(this.tag);

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
            tagService.delete(this.tag.getId());

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
                this.tag = tagService.findOne(this.tag.getId());
            } else if (this.tag.getName() != null) {
                this.tag = tagService.findByName(this.tag.getName());
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
            addFieldError("tag.name", getText("error.tag.name"));
        } // Prevent duplicate Tag
        else {
            LOGGER.debug("Confirming Tag does not already exist");
            Tag tempTag = tagService.findByName(tag.getName());
            if (tempTag != null) {
                LOGGER.debug("Tag already exists.  Adding Field Error for 'Name'");
                addFieldError("tag.name", getText("error.tag.exists"));
            }
        }
    }

    @Override
    public Tag getModel() {
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(final Tag tag) {
        this.tag = tag;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(final List<Tag> tagList) {
        this.tagList = tagList;
    }
}
