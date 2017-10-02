package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobExecutionContext;
import eu.squadd.springbatch.schema.ejb.BatchJobExecutionContextFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("batchJobExecutionContextController")
@SessionScoped
public class BatchJobExecutionContextController extends AbstractController<BatchJobExecutionContext> {

    @EJB
    private BatchJobExecutionContextFacade ejbFacade;
    
    public BatchJobExecutionContextController() {
        super(BatchJobExecutionContext.class);
    }

    @Override
    protected BatchJobExecutionContextFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public BatchJobExecutionContext prepareCreate() {
        this.setSelected(new BatchJobExecutionContext());
        return this.getSelected();
    }

    @FacesConverter(forClass = BatchJobExecutionContext.class)
    public static class BatchJobExecutionContextControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobExecutionContextController controller = (BatchJobExecutionContextController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobExecutionContextController");
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
            if (object instanceof BatchJobExecutionContext) {
                BatchJobExecutionContext o = (BatchJobExecutionContext) object;
                return getStringKey(o.getJobExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobExecutionContext.class.getName()});
                return null;
            }
        }
    }
}
