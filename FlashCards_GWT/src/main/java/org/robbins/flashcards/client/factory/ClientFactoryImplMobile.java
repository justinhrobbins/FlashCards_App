
package org.robbins.flashcards.client.factory;

import org.robbins.flashcards.client.ui.EditTagView;
import org.robbins.flashcards.client.ui.TagsView;
import org.robbins.flashcards.client.ui.mobile.EditTagViewImplMobile;
import org.robbins.flashcards.client.ui.mobile.TagsViewImplMobile;

public class ClientFactoryImplMobile extends ClientFactoryImpl {

    @Override
    public TagsView getTagsView() {
        return new TagsViewImplMobile(getEventBus());
    }

    @Override
    public EditTagView getEditTagView() {
        return new EditTagViewImplMobile();
    }

}
