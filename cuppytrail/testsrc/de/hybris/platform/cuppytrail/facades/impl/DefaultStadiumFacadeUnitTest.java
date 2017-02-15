package de.hybris.platform.cuppytrail.facades.impl;

import de.hybris.platform.cuppytrail.StadiumService;
import de.hybris.platform.cuppytrail.data.StadiumData;
import de.hybris.platform.cuppytrail.impl.DefaultStadiumService;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by luizhp on 2/15/17.
 */
public class DefaultStadiumFacadeUnitTest {

    private DefaultStadiumFacade stadiumFacade;

    private StadiumService stadiumService;


    private final static String STADIUM_NAME = "wembley";
    private final static Integer STADIUM_CAPACITY = Integer.valueOf(12345);

    // Convenience method for returning a list of Stadium
    private List<StadiumModel> dummyDataStadiumList()
    {
        final StadiumModel wembley = new StadiumModel();
        wembley.setCode(STADIUM_NAME);
        wembley.setCapacity(STADIUM_CAPACITY);
        final List<StadiumModel> stadiums = new ArrayList<StadiumModel>();
        stadiums.add(wembley);
        return stadiums;
    }

    // Convenience method for returning a Stadium with code and capacity set for wembley
    private StadiumModel dummyDataStadium()
    {
        final StadiumModel wembley = new StadiumModel();
        wembley.setCode(STADIUM_NAME);
        wembley.setCapacity(STADIUM_CAPACITY);
        return wembley;
    }

    @Before
    public void setUp() throws Exception {
        stadiumService = new DefaultStadiumService();
        stadiumService = mock(StadiumService.class);
        stadiumFacade.setStadiumService(stadiumService);
    }

    /**
     * The aim of this test is to test that:
     *
     * 1) The facade's method getStadiums makes a call to the StadiumService's method getStadiums
     *
     * 2) The facade then correctly wraps StadiumModels that are returned to it from the StadiumService's getStadiums
     * into Data Transfer Objects of type StadiumData.
     */
    @Test
    public void testGetAllStadiums()
    {
        /**
         * We instantiate an object that we would like to be returned to StadiumFacade when the mocked out
         * StadiumService's method getStadiums is called. This will be a list of two StadiumModels.
         */
        final List<StadiumModel> stadiums = dummyDataStadiumList();
        // create wembley stadium for the assert comparison
        final StadiumModel wembley = dummyDataStadium();
        // We tell Mockito we expect StadiumService's method getStadiums to be called, and that when it is, stadiums should be returned
        when(stadiumService.getStadiums()).thenReturn(stadiums);

        /**
         * We now make the call to StadiumFacade's getStadiums. If within this method a call is made to StadiumService's
         * getStadiums, Mockito will return the stadiums instance to it. Mockito will also remember that the call was
         * made.
         */
        final List<StadiumData> dto = stadiumFacade.getStadiums();
        // We now check that dto is a DTO version of stadiums..
        assertNotNull(dto);
        assertEquals(stadiums.size(), dto.size());
        assertEquals(wembley.getCode(), dto.get(0).getName());
        assertEquals(wembley.getCapacity().toString(), dto.get(0).getCapacity());
    }

    @Test
    public void testGetStadium()
    {
        /**
         * We instantiate an object that we would like to be returned to StadiumFacade when the mocked out
         * StadiumService's method getStadium is called. This will be the StadiumModel for wembley stadium.
         */
        // create wembley stadium
        final StadiumModel wembley = dummyDataStadium();

        // We tell Mockito we expect StadiumService's method getStadiumForCode to be called, and that when it is, wembley should be returned
        when(stadiumService.getStadiumForCode("wembley")).thenReturn(wembley);

        /**
         * We now make the call to StadiumFacade's getStadium. If within this method a call is made to StadiumService's
         * getStadium, Mockito will return the wembley instance to it. Mockito will also remember that the call was made.
         */
        final StadiumData stadium = stadiumFacade.getStadiumForCode("wembley");
        // We now check that stadium is a correct DTO representation of the ServiceModel wembley
        assertEquals(wembley.getCode(), stadium.getName());
        assertEquals(wembley.getCapacity().toString(), stadium.getCapacity());
    }


}