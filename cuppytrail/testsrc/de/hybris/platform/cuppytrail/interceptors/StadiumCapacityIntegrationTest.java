package de.hybris.platform.cuppytrail.interceptors;

import de.hybris.platform.cuppy.model.NewsModel;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import javax.annotation.Resource;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

/**
 * StadiumCapacityIntegrationTest
 */
public class StadiumCapacityIntegrationTest extends ServicelayerTransactionalTest {

    @Resource
    private ModelService modelService;

    @Resource
    private FlexibleSearchService flexibleSearchService;

    @SuppressWarnings("unused")
    static final private Logger LOG = Logger.getLogger(StadiumCapacityIntegrationTest.class);

    @Before
    public void setUp() throws Exception
    {
        createCoreData();
        createDefaultCatalog();
    }


    @Test
    public void testValidationInterceptor()
    {
        //given
        final StadiumModel stadium = modelService.create(StadiumModel.class);
        stadium.setCode("invalid");
        stadium.setCapacity(Integer.valueOf(200000));

        //when
        try
        {
            modelService.save(stadium);
            Assert.fail();
        }
        //then
        catch (final ModelSavingException e)
        {
            assertThat(e.getCause().getClass()).isEqualTo(InterceptorException.class);
            assertThat(e.getMessage()).contains("Capacity is too high");
        }
    }

    @Test
    @Ignore
    public void testEventSending() throws Exception
    {
        //given
        final StadiumModel stadium = modelService.create(StadiumModel.class);
        final Random rnd = new Random();
        final String code = "stadium(" + System.currentTimeMillis() + "|" + rnd.nextInt() + ")";
        stadium.setCode(code);
        stadium.setCapacity(75000);
        //when
        modelService.save(stadium);
        //then
        Thread.sleep(4000);
        assertThat(findLastNews().getContent(Locale.ENGLISH)).contains(code);
    }

    private NewsModel findLastNews()
    {
        String builder = "SELECT {n:" + NewsModel.PK + "} " +
                "FROM {" + NewsModel._TYPECODE + " AS n} " +
                "WHERE " + "{n:" + NewsModel.COMPETITION + "} IS NULL " +
                "ORDER BY " + "{n:" + NewsModel.CREATIONTIME + "} DESC";

        final List<NewsModel> list = flexibleSearchService.<NewsModel> search(builder).getResult();
        if (list.isEmpty())
        {
            return null;
        }
        else
        {
            return list.get(0);
        }
    }
}