package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecution;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionFacade;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("batchStepExecutionController")
@SessionScoped
public class BatchStepExecutionController extends AbstractController<BatchStepExecution> {

    @EJB
    private BatchStepExecutionFacade ejbFacade;
    
    public BatchStepExecutionController() {
        super(BatchStepExecution.class);
    }

    @Override
    protected BatchStepExecutionFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public BatchStepExecution prepareCreate() {
        this.setSelected(new BatchStepExecution());
        return this.getSelected();
    }

    @FacesConverter(forClass = BatchStepExecution.class)
    public static class BatchStepExecutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchStepExecutionController controller = (BatchStepExecutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchStepExecutionController");
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
            if (object instanceof BatchStepExecution) {
                BatchStepExecution o = (BatchStepExecution) object;
                return getStringKey(o.getStepExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchStepExecution.class.getName()});
                return null;
            }
        }
    }
}
