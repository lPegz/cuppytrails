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


    /**
     * Gets the url for an image with the given format
     *
     * @param format
     *           format to be taken to identify the image
     * @throws de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException
     *            in case no format can be found
     * @throws de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException
     *            in case more than one format is found
     * @throws IllegalArgumentException
     *            if given <code>format</code> is null
     */
    String getImageUrlFromStadium(StadiumModel stadium, String format);
}
