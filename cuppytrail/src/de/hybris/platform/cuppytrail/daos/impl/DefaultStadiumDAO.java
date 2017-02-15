package de.hybris.platform.cuppytrail.daos.impl;

import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@Component("stadiumDAO")
public class DefaultStadiumDAO implements StadiumDAO {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<StadiumModel> findStadiums() {
        final String query = "SELECT {p:" + StadiumModel.PK + "}" +
                "FROM {" + StadiumModel._TYPECODE + " AS p}";
        FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        return flexibleSearchService.<StadiumModel>search(flexibleSearchQuery).getResult();
    }

    @Override
    public List<StadiumModel> findStadiumsByCode(final String code) {
        final String query = "SELECT {p:" + StadiumModel.PK + "}" +
                "FROM {" + StadiumModel._TYPECODE + " AS p}" +
                "WHERE {p:" + StadiumModel.CODE + "}=?code";
        FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        flexibleSearchQuery.addQueryParameter("code", code);
        return flexibleSearchService.<StadiumModel>search(flexibleSearchQuery).getResult();
    }
}
