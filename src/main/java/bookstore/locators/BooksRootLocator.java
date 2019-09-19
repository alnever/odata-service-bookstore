package bookstore.locators;

import bookstore.components.BooksODataJPAServiceFactory;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.ODataRootLocator;

public class BooksRootLocator extends ODataRootLocator {
    private BooksODataJPAServiceFactory serviceFactory;

    public BooksRootLocator(BooksODataJPAServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public ODataServiceFactory getServiceFactory() {
        return this.serviceFactory;
    }

}
