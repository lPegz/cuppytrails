package de.hybris.platform.cuppytrail.daos;

import de.hybris.platform.cuppytrail.model.StadiumModel;

import java.util.List;

/**
 * An interface for the Stadium DAO.
 * This incorporates the CRUD functionality we require for our DAO tests to pass.
 */
public interface StadiumDAO {

    /**
     * Find all stadiums models currently persisted. If none are found an empty list is returned.
     * @return
     */
    List<StadiumModel> findStadiums();


    /**
     * Find all stadiums matching the param <code>code</code>
     * @param code
     * @return
     */
    List<StadiumModel> findStadiumsByCode(String code);

}
