package de.hybris.platform.cuppytrail.facades.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.cuppytrail.facades.StadiumFacade;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 
 */
public class DefaultStadiumFacadeIntegrationTest extends ServicelayerTransactionalTest {


    @Resource
    private StadiumFacade stadiumFacade;

    @Resource
    private ModelService modelService;

    private StadiumModel stadiumModel;
    private final String STADIUM_NAME= "morumbi";
    private final Integer STADIUM_CAPACITY = 12345;

    @Before
    public void setUp() throws Exception {
        stadiumModel = new StadiumModel();
        stadiumModel.setCode(STADIUM_NAME);
        stadiumModel.setCapacity(STADIUM_CAPACITY);
        modelService.detachAll();
    }

    @Test(expected = UnknownIdentifierException.class)
    public void getStadiumwhenInvalidParameter() throws Exception {
        stadiumFacade.getStadiumForCode(STADIUM_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStadiumWhenNullParameter() throws Exception {
        stadiumFacade.getStadiumForCode(null);
    }

    @Test
    public void getStadiumHappyFlow() throws Exception {
        modelService.save(stadiumModel);
        assertEquals("Model got from is equals to ", stadiumModel.getCode(),
                stadiumFacade.getStadiumForCode(STADIUM_NAME).getName());
    }

    @Test
    public void getStadiumsReturnsEmptyList() throws Exception {
        assertTrue("List of StadiumData is empty", stadiumFacade.getStadiums().isEmpty());
    }

    @Test
    public void getStadiumsReturnsList() throws Exception {
        modelService.save(stadiumModel);
        assertEquals("List of StadiumData not empty, containing the stadiumModel", stadiumModel.getCode(),
                stadiumFacade.getStadiums().get(0).getName());

    }
}