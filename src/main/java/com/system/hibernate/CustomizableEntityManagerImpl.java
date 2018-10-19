
package com.system.hibernate;

import java.util.Iterator;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;

public class CustomizableEntityManagerImpl implements CustomizableEntityManager {
    private Component customProperties;
    private Class entityClass;
    public CustomizableEntityManagerImpl(Class entityClass) {
        this.entityClass = entityClass;
    }
    @Override
    public Class getEntityClass() {
        return entityClass;
    }
    @Override
    public Component getCustomProperties() {
        if (customProperties == null) {
            Property property = getPersistentClass().getProperty(CUSTOM_COMPONENT_NAME);
            customProperties = (Component) property.getValue();
        }
        return customProperties;
    }
    @Override
    public void addCustomField(String name) {
        SimpleValue simpleValue = new SimpleValue();
        simpleValue.addColumn(new Column(name));
        simpleValue.setTypeName(String.class.getName());
        PersistentClass persistentClass = getPersistentClass();
        simpleValue.setTable(persistentClass.getTable());
        Property property = new Property();
        property.setName(name);
        property.setValue(simpleValue);
        getCustomProperties().addProperty(property);
        updateMapping();
    }
    @Override
    public void removeCustomField(String name) {
        Iterator propertyIterator = customProperties.getPropertyIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property.getName().equals(name)) {
                propertyIterator.remove();
                updateMapping();
                return;
            }
        }
    }
    private synchronized void updateMapping() {
        MappingManager.updateClassMapping(this);
        HibernateUtil.getInstance().reset();
    }
    private PersistentClass getPersistentClass() {
        return HibernateUtil.getInstance().getClassMapping(this.entityClass);
    }
}
