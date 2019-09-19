package bookstore.providers;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class EntityManagerFilter implements ContainerRequestFilter, ContainerResponseFilter {
    public static final String EM_REQUEST_ATTRIBUTE = EntityManagerFilter.class.getName() + "_ENTITY_MANAGER";
    private final EntityManagerFactory emf;

    @Context
    private HttpServletRequest httpRequest;

    public EntityManagerFilter(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        EntityManager em = this.emf.createEntityManager();
        httpRequest.setAttribute(EM_REQUEST_ATTRIBUTE, em);
        if ( !"GET".equalsIgnoreCase(containerRequestContext.getMethod())) {
            em.getTransaction().begin();
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
        EntityManager em = (EntityManager) httpRequest.getAttribute(EM_REQUEST_ATTRIBUTE);
        if (! "GET".equalsIgnoreCase(containerRequestContext.getMethod())) {
            EntityTransaction transaction = em.getTransaction();
            if (transaction.isActive() && ! transaction.getRollbackOnly()) {
                transaction.commit();
            }
        }
        em.close();
    }
}
