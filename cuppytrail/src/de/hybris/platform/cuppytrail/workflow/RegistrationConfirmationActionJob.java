package de.hybris.platform.cuppytrail.workflow;

import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.workflow.model.WorkflowActionModel;
import de.hybris.platform.workflow.model.WorkflowDecisionModel;
import org.apache.log4j.Logger;

/**
 * Created by luizhp on 2/17/17.
 */
public class RegistrationConfirmationActionJob extends AbstractPlayerRegistrationActionJob {

    private static final Logger LOGGER = Logger.getLogger(RegistrationConfirmationActionJob.class);


    @Override
    public WorkflowDecisionModel perform(WorkflowActionModel workflowActionModel) {

        PlayerModel playerModel = getAttachedPlayer(workflowActionModel);

        LOGGER.info("Player " + playerModel.getUid() + "confirmed. Sending email.");
        if(!playerModel.isConfirmed()) {
            playerModel.setConfirmed(true);
            getModelService().save(playerModel);
        }

        getMailService().sendConfirmationMail(playerModel);

        return workflowActionModel.getDecisions().stream().findFirst().orElse(null);
    }
}

/*
 pt-br
    Mudei o fonte de ao invés de ser pelo modo for-each
     Fazer pelo stream() do Java 8 usando Optional<PlayerModel>
     Acredito que junta a leitura e a performance
 */

/*
 en
    I changed the way from the for-each to Java 8 using stream() and Optional<PlayerModel>
    I guess it joins the readability and performance
 */

/*
    Source from Hybris Trails using old ancients

    private static final Logger LOG = Logger.getLogger(RegistrationConfirmationActionJob.class);

    @Override
    public WorkflowDecisionModel perform(final WorkflowActionModel action)
    {
        final PlayerModel player = getAttachedPlayer(action);

        LOG.info("Player " + player.getUid() + " confirmed. Confirmation email will be sent.");

        if (!player.isConfirmed())
        {
            player.setConfirmed(true);
            getModelService().save(player);
        }

        getMailService().sendConfirmationMail(player);

        for (final WorkflowDecisionModel decision : action.getDecisions())
        {
            return decision;
        }
        return null;
    }
 */