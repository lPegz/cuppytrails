package de.hybris.platform.cuppytrail.impl;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.cuppytrail.daos.StadiumDAO;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * DefaultStadiumService UnitTest class
 */
@UnitTest
public class DefaultStadiumServiceUnitTest {

    private DefaultStadiumService stadiumService;

    private StadiumDAO stadiumDAO;

    private StadiumModel stadiumModel;
    private final String STADIUM_NAME = "morumbi";
    private final Integer STADIUM_CAPACITY = 12345;

    @Before
    public void setup() {
        // We will be testing StadiumServiceImpl - an implementation of StadiumService
        stadiumService = new DefaultStadiumService();
        // So as not to implicitly also test the DAO, we will mock out the DAO using Mockito
        stadiumDAO = mock(StadiumDAO.class);
        // and inject this mocked DAO into the StadiumService
        stadiumService.setStadiumDAO(stadiumDAO);
        // This instance of a StadiumModel will be used by the tests
        stadiumModel = new StadiumModel();
        stadiumModel.setCode(STADIUM_NAME);
        stadiumModel.setCapacity(STADIUM_CAPACITY);
    }

    @Test
    public void getStadiums() throws Exception {
        // We construct the data we would like the mocked out DAO to return when called
        final List<StadiumModel> stadiumModels = Arrays.asList(stadiumModel);
        //Use Mockito and compare results
        when(stadiumDAO.findStadiums()).thenReturn(stadiumModels);
        // Now we make the call to StadiumService's getStadiums() which we expect to call the DAOs' findStadiums() method
        final List<StadiumModel> result = stadiumService.getStadiums();
        // We then verify that the results returned from the service match those returned by the mocked-out DAO
        assertEquals("We should find one", 1, result.size());
        assertEquals("And should equals what the mock returned", stadiumModel, result.get(0));
    }

    @Test
    public void getStadiumForCode() throws Exception {
        // Tell Mockito we expect a call to the DAO's getStadium(), and, if it occurs, Mockito should return StadiumModel instance
        when(stadiumDAO.findStadiumsByCode(STADIUM_NAME)).thenReturn(Collections.singletonList(stadiumModel));
        // We make the call to the Service's getStadiumForCode() which we expect to call the DAO's findStadiumsByCode()
        final StadiumModel result = stadiumService.getStadiumForCode(STADIUM_NAME);
        // We then verify that the result returned from the Service is the same as that returned from the DAO
        assertEquals("Stadium should equals() what the mock returned", stadiumModel, result);

    }

}