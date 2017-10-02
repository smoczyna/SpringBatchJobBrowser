package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchJobInstance;
import eu.squadd.springbatch.schema.ejb.BatchJobInstanceFacade;
import eu.squadd.springbatch.schema.jsf.util.AbstractFacesConverter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
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
    public static class BatchJobInstanceControllerConverter extends AbstractFacesConverter {

        public BatchJobInstanceControllerConverter() {
            super("batchJobInstanceController");
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof BatchJobInstance) {
                BatchJobInstance o = (BatchJobInstance) object;
                return this.getStringKey(o.getJobInstanceId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), BatchJobInstance.class.getName()});
                return null;
            }
        }
    }
}
