package de.hybris.platform.cuppytrail.workflow;

import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.cuppy.services.MailService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.workflow.jobs.AutomatedWorkflowTemplateJob;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * AbstractPlayerRegistrationActionJob class
 */
public abstract class AbstractPlayerRegistrationActionJob implements AutomatedWorkflowTemplateJob {

    @Autowired
    private ModelService modelService;

    @Autowired
    private MailService mailService;

    protected PlayerModel getAttachedPlayer( final WorkflowActionModel action) {
        return (PlayerModel) action.getAttachmentItems().stream()
                .filter(p -> p instanceof PlayerModel).findFirst().orElse(null);
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
