/*
 * Copyright 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.robbins.flashcards.client.mvp;

import org.robbins.flashcards.client.activity.NavigationActivity;
import org.robbins.flashcards.client.factory.ClientFactory;
import org.robbins.flashcards.client.place.LoginPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

/**
 * Activity mapper handles the clicks on of the left side panel.
 */
public class NavigationActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    public NavigationActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        GWT.log("NavigationActivityMapper - Place called: " + place);

        if (place instanceof LoginPlace) {
            return null;
        } else {
            return new NavigationActivity(clientFactory);
        }
    }
}
