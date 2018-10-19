
package com.system.model;

import java.util.HashMap;
import java.util.Map;

public class CustomizableEntity {

    private Map customProperties;

    public Map getCustomProperties() {
        if (customProperties == null) {
            customProperties = new HashMap();
        }
        return customProperties;
    }

    public void setCustomProperties(Map customProperties) {
        this.customProperties = customProperties;
    }

    public Object getValueOfCustomField(String name) {
        return getCustomProperties().get(name);
    }

    public void setValueOfCustomField(String name, Object value) {
        getCustomProperties().put(name, value);
    }

}
