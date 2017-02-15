package de.hybris.platform.cuppytrail.daos;

import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for
 */
public class DefaultStadiumDAOIntegrationTest extends ServicelayerTransactionalTest {

    @Resource
    private StadiumDAO stadiumDAO;

    @Resource
    private ModelService modelService;

    /*
        Stadium name for persisting
     */
    private static final String STADIUM_NAME = "morumbi";

    /*
        Stadium capacity for persisting
     */
    private static final Integer STADIUM_CAPACITY = 12345;


    public void persistModel() {
        final StadiumModel stadiumModel = new StadiumModel();
        stadiumModel.setCode(STADIUM_NAME);
        stadiumModel.setCapacity(STADIUM_CAPACITY);
        modelService.save(stadiumModel);
    }

    @Test
    public void findStadiumWhenNone() throws Exception {
        modelService.detachAll();
        assertTrue("No Stadium should be returned",
                stadiumDAO.findStadiums().isEmpty());
    }

    @Test
    public void findStadiumsByCodeWhenNone() throws Exception {
        modelService.detachAll();
        assertTrue("No Stadium should be returned",
                stadiumDAO.findStadiumsByCode(STADIUM_NAME).isEmpty());
    }

    @Test
    public void findStadiumsByCodeWhenEmptyParam() throws Exception {
        modelService.detachAll();
        assertTrue("No Stadium should be returned",
                stadiumDAO.findStadiumsByCode("").isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findStadiumsByCodeWhenNullParam() throws Exception {
        assertTrue("No Stadium should be returned",
                stadiumDAO.findStadiumsByCode(null).isEmpty());
    }

    @Test
    public void findStadiumWhenReturns1() throws Exception {
        modelService.detachAll();
        persistModel();
        assertTrue("Persisted Stadium "+ STADIUM_NAME +" should be returned",
                stadiumDAO.findStadiums().size() > 0 );
    }

    @Test
    public void findStadiumsByCodeWhenReturns1() throws Exception {
        modelService.detachAll();
        persistModel();
        assertEquals("Persisted Stadium "+ STADIUM_NAME +" should be returned",
                stadiumDAO.findStadiums().get(0).getCode(), STADIUM_NAME);
    }

}