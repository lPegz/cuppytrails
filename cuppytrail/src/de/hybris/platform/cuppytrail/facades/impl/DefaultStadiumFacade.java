package de.hybris.platform.cuppytrail.facades.impl;

import de.hybris.platform.cuppy.model.MatchModel;
import de.hybris.platform.cuppytrail.StadiumService;
import de.hybris.platform.cuppytrail.data.MatchSummaryData;
import de.hybris.platform.cuppytrail.data.StadiumData;
import de.hybris.platform.cuppytrail.facades.StadiumFacade;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.Transformer;
import org.springframework.beans.factory.annotation.Required;

import java.text.DateFormat;
import java.util.List;

/**
 * Default implementation of StadiumFacade
 */
public class DefaultStadiumFacade implements StadiumFacade {

    private StadiumService stadiumService;

    @Override
    public List<StadiumData> getStadiums(final String format) {
        return (List<StadiumData>) CollectionUtils.transform(stadiumService.getStadiums()
                , new StadiumDataModelTransformer().init(format, stadiumService));
    }

    @Override
    public StadiumData getStadiumForCode(final String code, final String format) {

        final StadiumModel stadiumModel = stadiumService.getStadiumForCode(code);
        StadiumData stadiumData = (StadiumData) new StadiumDataModelTransformer()
                .transform(stadiumModel);
        final List<MatchSummaryData> matchSummaryDataList = (List<MatchSummaryData>) CollectionUtils.transform(stadiumModel.getMatches(),
                new MatchToSummaryDataTransformer());
        stadiumData.setMatches(matchSummaryDataList);
        stadiumData.setImageUrl(stadiumService.getImageUrlFromStadium(stadiumModel, format));
        return stadiumData;
    }

    /**
     * Inner Class to transform MatchModel to MatchSummary used in CollectionUtils
     */
    class MatchToSummaryDataTransformer implements Transformer {
        @Override
        public Object transform(Object o) {
            MatchModel model = (MatchModel) o;
            MatchSummaryData data = new MatchSummaryData();
            data.setDate(model.getDate());
            data.setGuestGoals(model.getGuestGoals().toString());
            data.setGuestTeam(model.getGuestTeam().getName());
            data.setHomeGoals(model.getHomeGoals().toString());
            data.setHomeTeam(model.getHomeTeam().getName());
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM,  DateFormat.SHORT).format(data.getDate());
            final String matchSummaryFormatted = data.getHomeTeam() + ":( " + data.getHomeGoals() + " ) " + data.getGuestTeam() +
                    " ( " + data.getGuestGoals() + " ) "
                    + DateFormat.getDateTimeInstance(DateFormat.MEDIUM,  DateFormat.SHORT).format(data.getDate());
            data.setMatchSummaryFormatted(matchSummaryFormatted);
            return data;
        }
    }

    /**
     * Inner Class to transform StadiumModel into StadiumData
     */
    class StadiumDataModelTransformer implements Transformer {
        private String format;
        private StadiumService stadiumService;

        @Override
        public Object transform(final Object o) {
            final StadiumModel model = (StadiumModel) o;
            final StadiumData data = new StadiumData();
            try{
                data.setImageUrl(stadiumService.getImageUrlFromStadium(model, format));
            } catch (final Exception e) {
                data.setImageUrl("");
            }
            data.setCapacity(model.getCapacity().toString());
            data.setName(model.getCode());
            return data;
        }
        private StadiumDataModelTransformer init(String format, StadiumService stadiumService) {
            this.format = format;
            this.stadiumService = stadiumService;
            return this;
        }
    }

    @Required
    public void setStadiumService(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }
}
