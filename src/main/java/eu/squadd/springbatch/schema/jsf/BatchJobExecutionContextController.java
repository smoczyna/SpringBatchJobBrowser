package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobExecutionContext;
import eu.squadd.springbatch.schema.ejb.BatchJobExecutionContextFacade;
import eu.squadd.springbatch.schema.jsf.util.AbstractFacesConverter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
    public static class BatchJobExecutionContextControllerConverter extends AbstractFacesConverter {

        public BatchJobExecutionContextControllerConverter() {
            super("batchJobExecutionContextController");
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
