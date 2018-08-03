package freq.event.http;

import java.util.stream.Stream;

import freq.gen.http.FrequencyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freq.event.EventAction;
import freq.event.EventEnum;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpRequest;
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
    public MutableHttpResponse<Object> newEventNotification(HttpRequest request, String eventName, String eventAction)
    {
        EventEnum type = toEventEnum(eventName);
        EventAction action = toEventAction(eventAction);
        if ((type != null) && (action != null)) {
            notifyEventHandler(type, action, getUserAgent(request));
            return HttpResponse.ok();
        }
        LOGGER.warn("Got invalid request /event/{}/{}", eventName, eventAction);
        return HttpResponse.badRequest();
    }


    /**
     * Currently just a simple logger, could trigger just about anything
     */
    private void notifyEventHandler(EventEnum eventEnum, EventAction eventAction, String userAgent)
    {
        LOGGER.info("Received notification event '{}': {} from {} at current frequency of {} Hz",
                eventEnum.getEventName(), eventAction.name(), userAgent, String.format( "%.2f", FrequencyGenerator.getLatestFrequency()));
    }

    protected static String getUserAgent(HttpRequest request) {
        String userAgent = request.getParameters().get("User-Agent");
        if( userAgent == null || userAgent.equals("") ) {
            return request.getRemoteAddress().toString();
        } else {
            return request.getRemoteAddress().getAddress() + "/'" + userAgent + "'";
        }
    }

    private EventAction toEventAction(String eventAction)
    {
        if (eventAction.toUpperCase().equals(EventAction.START.name()))
            return EventAction.START;
        else if (eventAction.toUpperCase().equals(EventAction.STOP.name()))
            return EventAction.STOP;
        return null;
    }


    protected static EventEnum toEventEnum(String eventName)
    {
        return Stream.of(EventEnum.values()).filter(e -> e.getEventName().equals(eventName)).findFirst().orElse(null);
    }

}
