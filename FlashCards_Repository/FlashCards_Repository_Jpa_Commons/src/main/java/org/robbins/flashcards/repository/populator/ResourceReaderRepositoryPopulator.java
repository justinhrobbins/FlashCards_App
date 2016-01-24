package org.robbins.flashcards.repository.populator;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.robbins.flashcards.facade.base.GenericCrudFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResourceReaderRepositoryPopulator implements RepositoryPopulator, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceReaderRepositoryPopulator.class);

    @Autowired
    private ObjectMapper mapper;

    @javax.annotation.Resource(name = "dtoToFacadeMap")
    private Map<String, GenericCrudFacade> dtoToFacadeMap;

    @javax.annotation.Resource(name = "initResourceNames")
    @Autowired
    private List<String> initResourceNames;

    private ResourceReader reader;
    private List<Resource> resources;

    @Override
    public void afterPropertiesSet() throws Exception {
        reader = new Jackson2ResourceReader(mapper);
        resources = getResources();
    }

    public void populate() {
        resources.forEach(this::populateResource);
    }

    private void populateResource(final Resource resource) {
        LOGGER.info(String.format("Reading resource: %s", resource));

        final Object result = readObjectFrom(resource);

        if (result instanceof Collection) {
            ((Collection<?>) result).forEach(this::populateElement);
        } else {
            persist(result);
        }
    }

    private void populateElement(final Object element) {
        if (element != null) {
            persist(element);
        } else {
            LOGGER.info("Skipping null element found in unmarshal result!");
        }
    }

    private Object readObjectFrom(Resource resource) {
        try {
            return getResourceReader().readFrom(resource, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(Object object) {

        final String dtoName = object.getClass().getSimpleName();
        final GenericCrudFacade facade = dtoToFacadeMap.get(dtoName);
        LOGGER.debug(String.format("Persisting %s using repository %s", object, facade));
        facade.save(object);
    }

    private List<Resource> getResources() {
        if (initResourceNames == null) return new ArrayList<>();

        return initResourceNames
                .stream()
                .map(ClassPathResource::new)
                .collect(Collectors.toList());
    }

    private ResourceReader getResourceReader() {
        return reader;
    }

}
