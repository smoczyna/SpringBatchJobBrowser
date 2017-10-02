/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema.jsf.util;

import eu.squadd.springbatch.schema.jsf.AbstractController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author smorcja
 */
public abstract class AbstractFacesConverter implements Converter {
    private final String objectName;
    
    public AbstractFacesConverter(String name) {
        this.objectName = name;
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        AbstractController controller = (AbstractController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, this.objectName);
        return controller.getItemById(getKey(value));
    }

    protected Long getKey(String value) {
        java.lang.Long key;
        key = Long.valueOf(value);
        return key;
    }

    protected String getStringKey(Long value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public abstract String getAsString(FacesContext facesContext, UIComponent component, Object object);    
}
