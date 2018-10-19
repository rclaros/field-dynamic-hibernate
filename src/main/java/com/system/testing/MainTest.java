
package com.system.testing;

import com.system.hibernate.CustomizableEntityManager;
import com.system.hibernate.CustomizableEntityManagerImpl;
import com.system.hibernate.HibernateUtil;
import com.system.model.Contact;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;

public class MainTest {

    private static final String TEST_FIELD_NAME = "v_city";
    private static final String TEST_VALUE = "Paris";

    public static void main(String[] args) {
        addCampoExtra();
    }

    /**
     * Agrega un registro con los campos establecidos en el modelo
     */
    public static void add() {
        HibernateUtil.getInstance().getCurrentSession();
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            /**
             * Instanciamos la clase Contacto
             */
            Contact contact = new Contact();
            contact.setName("Contact Name 1");
            /**
             * Guardamos
             */
            Serializable id = session.save(contact);
            tx.commit();
            /**
             * Vamos a consultar el registro creado
             */
            contact = (Contact) session.get(Contact.class, id);
            System.out.println("value = " + contact.getName());

        } catch (Exception e) {
            tx.rollback();
            System.out.println("e = " + e);
        }
    }

    public static void addCampoExtra() {
        HibernateUtil.getInstance().getCurrentSession();
        CustomizableEntityManager contactEntityManager = new CustomizableEntityManagerImpl(Contact.class);
        contactEntityManager.addCustomField(TEST_FIELD_NAME);
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            /**
             * Instanciamos la clase Contacto
             */
            Contact contact = new Contact();
            contact.setName("Contact Name 1");

            /**
             * Agregamos un nuevo campo al modelo *
             */
            contact.setValueOfCustomField(TEST_FIELD_NAME, TEST_VALUE);
            /**
             * Guardamos
             */
            Serializable id = session.save(contact);
            tx.commit();
            /**
             * Vamos a consultar el registro creado
             */
            contact = (Contact) session.get(Contact.class, id);
            Object value = contact.getValueOfCustomField(TEST_FIELD_NAME);
            System.out.println("value = " + value);

        } catch (Exception e) {
            tx.rollback();
            System.out.println("e = " + e);
        }
    }

}
