package eu.squadd.springbatch.schema.jsf;

import eu.squadd.springbatch.schema.BatchStepExecutionContext;
import eu.squadd.springbatch.schema.ejb.BatchStepExecutionContextFacade;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
    public static class BatchStepExecutionContextControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BatchStepExecutionContextController controller = (BatchStepExecutionContextController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "batchStepExecutionContextController");
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
