package org.robbins.tests;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class BaseMockingTest {
    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
