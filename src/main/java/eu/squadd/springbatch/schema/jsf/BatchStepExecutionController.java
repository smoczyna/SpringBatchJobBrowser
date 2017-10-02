package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecution;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionFacade;
import eu.squadd.springbatch.schema.jsf.util.AbstractFacesConverter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
    public static class BatchStepExecutionControllerConverter extends AbstractFacesConverter {

        public BatchStepExecutionControllerConverter() {
            super("batchStepExecutionController");
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
