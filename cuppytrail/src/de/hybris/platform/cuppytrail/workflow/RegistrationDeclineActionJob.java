package de.hybris.platform.cuppytrail.workflow;

import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;
import org.apache.log4j.Logger;

/**
 * Created by luizhp on 2/17/17.
 */
public class RegistrationDeclineActionJob extends AbstractPlayerRegistrationActionJob {

    private static final Logger LOG = Logger.getLogger(RegistrationDeclineActionJob.class);

    @Override
    public WorkflowDecisionModel perform(WorkflowActionModel workflowActionModel) {

        PlayerModel player = getAttachedPlayer(workflowActionModel); ;
        LOG.info("Player " + player.getUid() + " declined and will be removed");
        getModelService().remove(player);
        return workflowActionModel.getDecisions().stream()
                .findFirst().orElse(null);
    }
}
