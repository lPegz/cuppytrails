package de.hybris.platform.cuppytrail.facades;

import de.hybris.platform.cuppytrail.data.StadiumData;

import java.util.List;

/**
 * StadiumFacade Interface
 */
public interface StadiumFacade {

    List<StadiumData> getStadiums();

    StadiumData getStadiumForCode(final String code);

}
