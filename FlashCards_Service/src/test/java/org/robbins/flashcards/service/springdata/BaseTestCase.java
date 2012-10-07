package org.robbins.flashcards.service.springdata;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BaseTestCase {
    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
