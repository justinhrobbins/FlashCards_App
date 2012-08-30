package org.robbins.flashcards.client.ui.desktop.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface FormStyleResource extends ClientBundle  {
    public static final FormStyleResource INSTANCE = GWT.create(FormStyleResource.class);
    
    @Source("FormStyles.css")
    public FormStyles formStyles();

}
