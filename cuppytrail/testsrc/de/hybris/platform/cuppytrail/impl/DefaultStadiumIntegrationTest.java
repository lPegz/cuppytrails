package de.hybris.platform.cuppytrail.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.cuppytrail.StadiumService;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * DefaultStadiumIntegrationTest
 */
@IntegrationTest
public class DefaultStadiumIntegrationTest extends ServicelayerTransactionalTest {

    private StadiumModel stadiumModel;
    private static final String STADIUM_NAME = "morumbi";
    private static final Integer STADIUM_CAPACITY = 12345;

    @Resource
    private StadiumService stadiumService;

    @Resource
    private ModelService modelService;

    @Before
    public void setup() {
        stadiumModel = new StadiumModel();
        stadiumModel.setCode(STADIUM_NAME);
        stadiumModel.setCapacity(STADIUM_CAPACITY);
    }

    @Test
    public void getStadiumsReturnsEmptyList() throws Exception {
        modelService.detachAll();
        assertTrue("getStadiums returns empty list",stadiumService.getStadiums().isEmpty());
    }

    @Test(expected = UnknownIdentifierException.class )
    public void testFailBehavior() throws Exception {
        modelService.detachAll();
        stadiumService.getStadiumForCode(STADIUM_NAME);
    }

    @Test
    public void getStadiumsReturnsListValue() throws Exception {
        modelService.detachAll();
        modelService.save(stadiumModel);
        assertEquals("getStadiums returns stadiumModel",
                stadiumService.getStadiumForCode(STADIUM_NAME), stadiumModel);
    }


    @Test(expected = IllegalArgumentException.class )
    public void getStadiumForCodeNull() throws Exception {
        modelService.detachAll();
        stadiumService.getStadiumForCode(null);
    }

}