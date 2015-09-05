package org.robbins.flashcards.springdata.repository;

import org.robbins.flashcards.model.BulkLoadingReceipt;
import org.robbins.flashcards.repository.BulkLoadingReceiptRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class BulkLoadingReceiptRepositoryImpl extends AbstractCrudRepositoryImpl<BulkLoadingReceipt, String> implements
        BulkLoadingReceiptRepository<BulkLoadingReceipt, String> {

    @Inject
    private BulkLoadingReceiptSpringDataRepository repository;

    @Override
    public BulkLoadingReceiptSpringDataRepository getRepository() {
        return repository;
    }
}
