package org.robbins.flashcards.repository.populator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RepositoryPopulatorListener implements ApplicationListener<ContextRefreshedEvent>,
        ApplicationContextAware {

    @Autowired
    private RepositoryPopulator populator;

    private ApplicationContext context;

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().equals(context)) {
            populator.populate();
        }
    }
}
