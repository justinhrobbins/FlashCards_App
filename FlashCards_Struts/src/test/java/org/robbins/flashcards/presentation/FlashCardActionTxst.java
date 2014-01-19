
package org.robbins.flashcards.presentation;

import org.apache.struts2.StrutsTestCase;
import org.robbins.flashcards.presentation.action.FlashCardAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionProxy;

public class FlashCardActionTxst extends StrutsTestCase {

    static final Logger logger = LoggerFactory.getLogger(FlashCardActionTxst.class);

    public void testCreateFlashCardFailOnQuestion() throws Exception {
        request.setParameter("question", "");
        request.setParameter("answer", "my answer");

        ActionProxy proxy = getActionProxy("/createflashcard.action");

        FlashCardAction flashCardAction = (FlashCardAction) proxy.getAction();

        proxy.execute();

        assertTrue(
                "Problem There were no errors present in fieldErrors but there should have been one error present",
                flashCardAction.getFieldErrors().size() == 1);
        assertTrue(
                "Problem field 'question' not present in fieldErrors but it should have been",
                flashCardAction.getFieldErrors().containsKey("question"));
    }

    public void testCreateFlashCardFailOnAnswer() throws Exception {
        request.setParameter("question", "my question");
        request.setParameter("answer", "");

        ActionProxy proxy = getActionProxy("/createflashcard.action");

        FlashCardAction flashCardAction = (FlashCardAction) proxy.getAction();

        proxy.execute();

        assertTrue(
                "Problem There were no errors present in fieldErrors but there should have been one error present",
                flashCardAction.getFieldErrors().size() == 1);
        assertTrue(
                "Problem field 'question' not present in fieldErrors but it should have been",
                flashCardAction.getFieldErrors().containsKey("answer"));
    }

    public void testCreateFlashCard() throws Exception {
        request.setParameter("question", "my question");
        request.setParameter("answer", "my answer");

        ActionProxy proxy = getActionProxy("/createflashcard.action");

        FlashCardAction flashCardAction = (FlashCardAction) proxy.getAction();

        String result = proxy.execute();

        assertTrue(
                "Problem There were errors present in fieldErrors but there should not have been any errors present",
                flashCardAction.getFieldErrors().size() == 0);
        assertEquals(
                "Result returned form executing the action was not success but it should have been.",
                "success", result);
    }

    public void testListFlashCards() throws Exception {
        ActionProxy proxy = getActionProxy("/listflashcards.action");

        String result = proxy.execute();

        assertEquals(
                "Result returned form executing the action was not success but it should have been.",
                "success", result);
    }

    public void testDisplayFlashCard() throws Exception {
        request.setParameter("question", "my question");

        ActionProxy proxy = getActionProxy("/displayflashcard.action");

        String result = proxy.execute();

        assertEquals(
                "Result returned form executing the action was not success but it should have been.",
                "success", result);
    }

}
