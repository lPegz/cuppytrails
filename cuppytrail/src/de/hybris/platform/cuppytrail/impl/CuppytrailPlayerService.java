package de.hybris.platform.cuppytrail.impl;

import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.cuppy.services.impl.DefaultPlayerService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.workflow.WorkflowProcessingService;
import de.hybris.platform.workflow.WorkflowService;
import de.hybris.platform.workflow.WorkflowTemplateService;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.model.WorkflowTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Extenteded PlayerService for Cuppy.
 */
public class CuppytrailPlayerService extends DefaultPlayerService {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowTemplateService workflowTemplateService;

    @Autowired
    private WorkflowProcessingService workflowProcessingService;

    @Autowired
    private UserService userService;

    @Autowired
    protected ModelService modelService;

    private static final String NEW_PLAYER_REGISTRATION = "NewPlayerRegistration";

    @Override
    public void registerPlayer(PlayerModel player) {
        super.registerPlayer(player);
        final WorkflowTemplateModel workflowTemplateModel = this.workflowTemplateService
                .getWorkflowTemplateForCode(NEW_PLAYER_REGISTRATION);

        final WorkflowModel workflow = this.workflowService.createWorkflow(workflowTemplateModel, player, userService.getAdminUser());
        modelService.save(workflow);

        modelService.save(workflow.getActions().stream().findFirst().get());

        this.workflowProcessingService.startWorkflow(workflow);
    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void setWorkflowTemplateService(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    public void setWorkflowProcessingService(WorkflowProcessingService workflowProcessingService) {
        this.workflowProcessingService = workflowProcessingService;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
