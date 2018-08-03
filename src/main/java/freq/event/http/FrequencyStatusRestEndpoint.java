package freq.event.http;

import freq.event.EventAction;
import freq.event.EventEnum;
import freq.gen.http.FrequencyGenerator;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

@Controller("/status")
public class FrequencyStatusRestEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyStatusRestEndpoint.class);

    /**
     * Returns a frequency reading in a broken JSON format.
     */
    @Get("/{eventName}")
    public MutableHttpResponse<Object> newEventNotification(HttpRequest request, String eventName)
    {
        EventEnum type = FrequencyEventRestEndpoint.toEventEnum(eventName);
        if ((type != null) ) {

            notifyEventHandler(type, FrequencyEventRestEndpoint.getUserAgent(request));
            return HttpResponse.ok();
        }
        LOGGER.warn("Got invalid request /status/{}", eventName);
        return HttpResponse.badRequest();
    }


    /**
     * Currently just a simple logger, could trigger just about anything
     */
    private void notifyEventHandler(EventEnum eventEnum, String userAgent)
    {
        LOGGER.info("Received status notification: '{}' from {} at current frequency of {} Hz",
                eventEnum.getEventName(), userAgent, String.format( "%.2f", FrequencyGenerator.getLatestFrequency()));
    }
}
