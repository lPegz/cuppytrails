package de.hybris.platform.cuppytrail.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.cuppy.model.PlayerModel;
import de.hybris.platform.cuppy.services.MailService;
import de.hybris.platform.cuppy.services.PlayerService;
import de.hybris.platform.cuppy.services.RankingData;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * SendRankingJob Class
 */
public class SendRankingJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOGGER = Logger.getLogger(SendRankingJob.class);

    private PlayerService playerService;
    private MailService mailService;

    @Override
    public PerformResult perform(CronJobModel cronModel) {
        LOGGER.info("Starting SendRankingJob - Sending ranking mails");
        List<RankingData> rankingDataList = playerService.getRankings();
        if(rankingDataList.isEmpty()) {
            LOGGER.info("No competitions have changed, skipping send of ranking mails");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }

        for (final PlayerModel player : playerService.getAllPlayers())
        {
            final List<RankingData> playerRankings = playerService.filterRankingsForPlayer(rankingDataList, player);
            if (!playerRankings.isEmpty() && player.isSendNewsletter())
            {
                mailService.sendRankingMail(player, playerRankings);
            }
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public void setPlayerService(final PlayerService playerService) {
        this.playerService = playerService;
    }

    public void setMailService(final MailService mailService) {
        this.mailService = mailService;
    }
}
