package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecutionContext;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionContextFacade;
import eu.squadd.springbatch.schema.jsf.util.AbstractFacesConverter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@Named("batchStepExecutionContextController")
@SessionScoped
public class BatchStepExecutionContextController extends AbstractController<BatchStepExecutionContext> {

    @EJB
    private BatchStepExecutionContextFacade ejbFacade;
    
    public BatchStepExecutionContextController() {
       super(BatchStepExecutionContext.class);
    }

    @Override
    protected BatchStepExecutionContextFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public BatchStepExecutionContext prepareCreate() {
        this.setSelected(new BatchStepExecutionContext());
        return this.getSelected();
    }

    @FacesConverter(forClass = BatchStepExecutionContext.class)
    public static class BatchStepExecutionContextControllerConverter extends AbstractFacesConverter {

        public BatchStepExecutionContextControllerConverter() {
            super("batchStepExecutionContextController");
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BatchStepExecutionContext) {
                BatchStepExecutionContext o = (BatchStepExecutionContext) object;
                return getStringKey(o.getStepExecutionId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchStepExecutionContext.class.getName()});
                return null;
            }
        }
    }
}
