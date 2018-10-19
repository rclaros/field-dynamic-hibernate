
package com.system.hibernate;

import com.system.model.Contact;
import org.hibernate.*;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance;
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;

    public synchronized static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory();
        }
        return sessionFactory;
    }

    public synchronized Session getCurrentSession() {
        if (session == null) {
            session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.COMMIT);
        }
        return session;
    }

    private synchronized Configuration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new Configuration().configure();
                configuration.addClass(Contact.class);
            } catch (HibernateException e) {
                System.out.println("failure");
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public void reset() {
        Session session = getCurrentSession();
        if (session != null) {
            session.flush();
            if (session.isOpen()) {
                session.close();;
            }
        }
        SessionFactory sf = getSessionFactory();
        if (sf != null) {
            sf.close();
        }
        this.configuration = null;
        this.sessionFactory = null;
        this.session = null;
    }

    public PersistentClass getClassMapping(Class entityClass) {
        return getConfiguration().getClassMapping(entityClass.getName());
    }
}
