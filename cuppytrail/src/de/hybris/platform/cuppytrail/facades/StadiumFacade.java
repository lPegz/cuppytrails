package de.hybris.platform.cuppytrail.facades;

import de.hybris.platform.cuppytrail.data.StadiumData;

import java.util.List;

/**
 * StadiumFacade Interface
 */
public interface StadiumFacade {

    List<StadiumData> getStadiums(final String format);

    StadiumData getStadiumForCode(final String code, final String format);

}
