package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobInstance;
import eu.squadd.springbatch.schema.ejb.BatchJobInstanceFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("batchJobInstanceController")
@SessionScoped
public class BatchJobInstanceController extends AbstractController<BatchJobInstance> {

    @EJB
    private BatchJobInstanceFacade ejbFacade;
    
    public BatchJobInstanceController() {
        super(BatchJobInstance.class);
    }

    @Override
    protected BatchJobInstanceFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public BatchJobInstance prepareCreate() {
        this.setSelected(new BatchJobInstance());
        return this.getSelected();
    }

    @FacesConverter(forClass = BatchJobInstance.class)
    public static class BatchJobInstanceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobInstanceController controller = (BatchJobInstanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobInstanceController");
            return controller.getItemById(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BatchJobInstance) {
                BatchJobInstance o = (BatchJobInstance) object;
                return getStringKey(o.getJobInstanceId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobInstance.class.getName()});
                return null;
            }
        }
    }
}
