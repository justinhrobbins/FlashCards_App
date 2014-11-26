
package org.robbins.flashcards.client.ui.desktop.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface ShellStyleResource extends ClientBundle {

    public static final ShellStyleResource INSTANCE = GWT.create(ShellStyleResource.class);

    @Source("ShellStyles.css")
    @CssResource.NotStrict
    public ShellStyles shellStyles();

    @Source("content.jpg")
    @ImageOptions(repeatStyle = RepeatStyle.Vertical)
    ImageResource contentImage();

    @Source("footer.jpg")
    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource footerImage();

    @Source("header.jpg")
    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource headerImage();

    @Source("sidebar.jpg")
    @ImageOptions(repeatStyle = RepeatStyle.None)
    ImageResource sidebarImage();

}
