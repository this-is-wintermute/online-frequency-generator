package freq.event.http;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freq.event.EventAction;
import freq.event.EventEnum;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/event")
public class FrequencyEventRestEndpoint
{

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyEventRestEndpoint.class);


    /**
     * Returns a frequency reading in a broken JSON format.
     */
    @Get("/{eventName}/{eventAction}")
    public MutableHttpResponse<Object> newEventNotification(String eventName, String eventAction)
    {
        EventEnum type = toEventEnum(eventName);
        EventAction action = toEventAction(eventAction);
        if ((type != null) && (action != null)) {
            notifyEventHandler(type, action);
            return HttpResponse.ok();
        }
        LOGGER.warn("Got invalid request /event/{}/{}", eventName, eventAction);
        return HttpResponse.badRequest();
    }


    /**
     * Currently just a simple logger, could trigger just about anything
     */
    private void notifyEventHandler(EventEnum eventEnum, EventAction eventAction)
    {
        LOGGER.info("Received notification event '{}': {}", eventEnum.getEventName(), eventAction.name());
    }


    private EventAction toEventAction(String eventAction)
    {
        if (eventAction.toUpperCase().equals(EventAction.START.name()))
            return EventAction.START;
        else if (eventAction.toUpperCase().equals(EventAction.STOP.name()))
            return EventAction.STOP;
        return null;
    }


    private EventEnum toEventEnum(String eventName)
    {
        return Stream.of(EventEnum.values()).filter(e -> e.getEventName().equals(eventName)).findFirst().orElse(null);
    }

}
