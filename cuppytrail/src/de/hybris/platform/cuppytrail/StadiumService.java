package de.hybris.platform.cuppytrail;

import de.hybris.platform.cuppytrail.model.StadiumModel;

import java.util.List;

/**
 * Interface for Stadium Service
 */
public interface StadiumService {

    /**
     * @return all Stadiums
     */
    List<StadiumModel> getStadiums();

    /**
     * @param code
     * @return the <code>StadiumModel</code> for the given parameter <code>code</code>
     */
    StadiumModel getStadiumForCode(final String code);
}
