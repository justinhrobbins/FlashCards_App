package org.robbins.flashcards.presentation;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsTestCase;
import org.robbins.flashcards.presentation.action.TagAction;

import com.opensymphony.xwork2.ActionProxy;

public class TagActionTxst extends StrutsTestCase {
	
	static Logger logger = Logger.getLogger(TagActionTxst.class);

	public void testCreateTagFail() throws Exception {
		logger.debug("Entering testCreateTagFail()");

    	try {
			request.setParameter("name", "");

			ActionProxy proxy = getActionProxy("/tag/saveOrUpdate.action");

			TagAction tagAction = (TagAction) proxy.getAction();

			proxy.execute();

			assertTrue("Problem There were no errors present in fieldErrors but there should have been one error present", tagAction.getFieldErrors().size() == 1);
			assertTrue("Problem field 'name' not present in fieldErrors but it should have been",
					tagAction.getFieldErrors().containsKey("name") );
		} catch (Exception e) {
			logger.debug("Error running testCreateTagFail()");
			e.printStackTrace();
			
			assertTrue("Error running testCreateTagFail()", false);
		}
    }
	
    public void testCreateTag() throws Exception {
    	logger.debug("Entering testCreateTag()");

    	request.setParameter("name", "my tag name");

    	ActionProxy proxy = getActionProxy("/tag/saveOrUpdate.action");
   	
    	TagAction tagAction = (TagAction) proxy.getAction();

        String result = proxy.execute();

        assertTrue("Problem There were errors present in fieldErrors but there should not have been any errors present", tagAction.getFieldErrors().size() == 0);
        assertEquals("Result returned form executing the action was not success but it should have been.", "success", result);
    }
    
    public void testListTags() throws Exception {
    	logger.debug("Entering testlistTags()");
    	
    	ActionProxy proxy = getActionProxy("/tag/list.action");
       	
        String result = proxy.execute();
        
        assertEquals("Result returned form executing the action was not success but it should have been.", "success", result);
    }
    
    public void testDisplayTag() throws Exception {
    	logger.debug("Entering testDisplayTag()");
    	
    	
    	String result = "";
    	
    	try {
			request.setParameter("name", "my tag name");
			
			ActionProxy proxy = getActionProxy("/tag/display.action");
			
			result = proxy.execute();
		
		} catch (Exception e) {
			logger.debug("Error running testDisplayTag()");
			e.printStackTrace();
		}
		
		assertEquals("Result returned form executing the action was not success but it should have been.", "success", result);
    }
    
}
