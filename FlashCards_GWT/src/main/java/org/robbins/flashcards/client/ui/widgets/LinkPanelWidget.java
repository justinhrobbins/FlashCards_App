
package org.robbins.flashcards.client.ui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.ui.AppConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class LinkPanelWidget extends Composite {

    private static LinkPanelUiBinder uiBinder = GWT.create(LinkPanelUiBinder.class);

    interface LinkPanelUiBinder extends UiBinder<Widget, LinkPanelWidget> {
    }

    private final AppConstants constants;

    @UiField
    HTMLPanel linksPanel;

    @UiField
    Anchor addLink;

    public LinkPanelWidget(ClientFactory clientFactory) {
        this.constants = clientFactory.getConstants();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("addLink")
    void onClick(ClickEvent event) {
        GWT.log("LinkPanelWidget: 'Add Link' clicked");
        addLink(null);
    }

    public void addLinks(List<String> linksList) {
        clearLinks();

        if ((linksList == null) || (linksList.size() < 1)) {
            addLink(null);
            return;
        }

        for (String link : linksList) {
            addLink(link);
        }
    }

    private void clearLinks() {
        linksPanel.clear();
    }

    private void addLink(String linkUrl) {
        int linkCount = getLinkCount(linksPanel);
        LinkWidget linkWidget = new LinkWidget();

        linkWidget.getLinkLabel().setText(
                constants.linkNumber() + Integer.toString(linkCount + 1) + ": ");
        if (linkUrl != null) {
            linkWidget.setLink(linkUrl);
        }
        linksPanel.add(linkWidget);
    }

    private int getLinkCount(HasWidgets linksPanel) {
        int counter = 0;
        Iterator<Widget> iterator = linksPanel.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            counter++;
        }
        return counter;
    }

    public List<String> getLinks() {
        List<String> linksList = new ArrayList<String>();
        Iterator<Widget> linksIterator = linksPanel.iterator();
        while (linksIterator.hasNext()) {
            LinkWidget linkWidget = (LinkWidget) linksIterator.next();
            String link = linkWidget.getLink();
            if (link != null && link.length() > 0) {
                linksList.add(link);
            }
        }
        return linksList;
    }
}
