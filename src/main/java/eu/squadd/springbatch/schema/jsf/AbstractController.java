/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.ejb.AbstractFacade;
import eu.squadd.springbatch.schema.jsf.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;

/**
 *
 * @author smorcja
 * @param <T>
 */
public abstract class AbstractController<T> implements Serializable {
    private final Class payload;
    private List<T> items;
    private T selected;
    
    public AbstractController(Class<T> payload) {
        this.payload = payload;
    }
    
    protected abstract AbstractFacade getFacade();
    protected abstract T prepareCreate();

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
    
    protected List getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BatchJobExecutionContextDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (this.selected != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(this.selected);
                } else {
                    getFacade().remove(this.selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public T getItemById(Long id) {
        return (T) getFacade().find(id);
    }

    public List<T> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<T> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

}
