package de.hybris.platform.cuppytrail.impl;

import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.cuppytrail.StadiumService;
import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.media.MediaService;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.List;

/**
 * DefaultStadiumService
 */
public class DefaultStadiumService implements StadiumService {

    private StadiumDAO stadiumDAO;

    private MediaService mediaService;

    @Override
    public List<StadiumModel> getStadiums() {
        return stadiumDAO.findStadiums();
    }

    @Override
    public StadiumModel getStadiumForCode(final String code) {
        final List<StadiumModel> result = stadiumDAO.findStadiumsByCode(code);
        if (result.isEmpty())
        {
            throw new UnknownIdentifierException("Stadium with code '" + code + "' not found!");
        }
        else if (result.size() > 1)
        {
            throw new AmbiguousIdentifierException("Stadium code '" + code + "' is not unique, " + result.size()
                    + " stadiums found!");
        }
        return result.get(0);
    }

    @Override
    public String getImageUrlFromStadium(StadiumModel stadium, String format) {

        final MediaFormatModel mediaFormat = mediaService.getFormat(format);
        MediaModel mediaModel = null;
        if(stadium.getStadiumImage() != null && mediaFormat != null) {
            mediaModel = mediaService.getMediaByFormat(stadium.getStadiumImage(), mediaFormat);
        }
        if(mediaModel != null) {
            return mediaModel.getURL();
        } else {
            return null;
        }
    }

    @Required
    public void setStadiumDAO(final StadiumDAO stadiumDAO)
    {
        this.stadiumDAO = stadiumDAO;
    }

    @Required
    public void setMediaService(final MediaService mediaService) {
        this.mediaService = mediaService;
    }
}
