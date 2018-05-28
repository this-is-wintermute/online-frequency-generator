package freq.gen.http;

import io.micronaut.http.annotation.*;

@Controller("/frequency")
public class FrequencyDataRestEndpoint {

    FrequencyGenerator freqGen = FrequencyGenerator.getTimeBasedInstance();

    /**
     * Returns a frequency reading in a broken JSON format.
     */
    @Get("/")
    public String getReading() {
        return String.format("{ \"frequencyReading\": { " +
                "\"timestamp\": %d, " +
                "\"frequency\": %.2f }, " +
                "isValid: true }", System.currentTimeMillis(), freqGen.getLatestFrequency());
    }

    @Get("/validjson")
    public String getValidJsonReading() {
        return String.format("{ \"frequencyReading\": { " +
                "\"timestamp\": %d, " +
                "\"frequency\": %.2f }, " +
                "\"isValid\": true }", System.currentTimeMillis(), freqGen.getLatestFrequency());
    }
}
