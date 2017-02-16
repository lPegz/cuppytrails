package de.hybris.platform.cuppytrail.interceptors;

import de.hybris.platform.cuppytrail.events.CapacityEvent;
import de.hybris.platform.cuppytrail.model.StadiumModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import static de.hybris.platform.servicelayer.model.ModelContextUtils.getItemModelContext;

/**
 * Created by luizhp on 2/16/17.
 */
public class StadiumCapacityInterceptor implements ValidateInterceptor, PrepareInterceptor {


    private static final int BIG_STADIUM = 50000;
    private static final int HUGE_STADIUM = 50000;

    @Autowired
    private EventService eventService;


    @Override
    public void onPrepare(Object model, InterceptorContext interceptorContext) throws InterceptorException {
        if (model instanceof StadiumModel)
        {
            final StadiumModel stadium = (StadiumModel) model;
            if (hasBecomeBig(stadium, interceptorContext))
            {
                eventService.publishEvent(new CapacityEvent(stadium.getCapacity(), stadium.getCode()));
            }
        }
    }

    @Override
    public void onValidate(Object model, InterceptorContext interceptorContext) throws InterceptorException {
        if (model instanceof StadiumModel) {
            final StadiumModel stadium = (StadiumModel) model;
            final Integer capacity = stadium.getCapacity();
            if (capacity != null && capacity.intValue() >= HUGE_STADIUM) {
                throw new InterceptorException("Capacity is too high");
            }
        }

    }

    private boolean hasBecomeBig(final StadiumModel stadium, final InterceptorContext ctx) {
        final Integer  capacity = stadium.getCapacity();
        if(capacity != null && capacity.intValue() >= BIG_STADIUM && capacity.intValue() < HUGE_STADIUM) {
            if (ctx.isNew(stadium))
            {
                return true;
            }
            else
            {
                final Integer oldValue = getItemModelContext(stadium).getOriginalValue(StadiumModel.CAPACITY);
                if (oldValue == null || oldValue.intValue() < 50000)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
