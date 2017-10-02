package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobExecution;
import eu.squadd.springbatch.schema.ejb.BatchJobExecutionFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("batchJobExecutionController")
@SessionScoped
public class BatchJobExecutionController extends AbstractController<BatchJobExecution> {

    @EJB
    private BatchJobExecutionFacade ejbFacade;
    
    public BatchJobExecutionController() {
        super(BatchJobExecution.class);
    }

    @Override
    protected BatchJobExecutionFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public BatchJobExecution prepareCreate() {
        this.setSelected(new BatchJobExecution());
        return this.getSelected();
    }

    @FacesConverter(forClass = BatchJobExecution.class)
    public static class BatchJobExecutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchJobExecutionController controller = (BatchJobExecutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchJobExecutionController");
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
            if (object instanceof BatchJobExecution) {
                BatchJobExecution o = (BatchJobExecution) object;
                return getStringKey(o.getJobExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobExecution.class.getName()});
                return null;
            }
        }
    }
}
