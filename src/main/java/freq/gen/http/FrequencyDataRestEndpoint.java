package freq.gen.http;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/frequency")
public class FrequencyDataRestEndpoint {

    private static final Logger log = LoggerFactory.getLogger(FrequencyDataRestEndpoint.class);
    FrequencyGenerator freqGen = FrequencyGenerator.getTimeBasedInstance();

    /**
     * Returns a frequency reading in a broken JSON format.
     */
    @Get("/")
    public String getReading(HttpHeaders headers) {
        String userAgentString = headers.get(HttpHeaders.USER_AGENT);
        log.info("Frequency reading requested by {} - {} Hz", userAgentString, freqGen.getLatestFrequency() );
        return String.format("{ \"frequencyReading\": { " +
                "\"timestamp\": %d, " +
                "\"frequency\": %.2f }, " +
                "isValid: true }", System.currentTimeMillis(), freqGen.getLatestFrequency());
    }

    @Get("/validjson")
    public String getValidJsonReading(HttpHeaders headers) {
        log.info("Valid JSON Frequency reading requested by {} - {} Hz", headers.get(HttpHeaders.USER_AGENT), freqGen.getLatestFrequency() );
        return String.format("{ \"frequencyReading\": { " +
                "\"timestamp\": %d, " +
                "\"frequency\": %.2f }, " +
                "\"isValid\": true }", System.currentTimeMillis(), freqGen.getLatestFrequency());
    }
}
