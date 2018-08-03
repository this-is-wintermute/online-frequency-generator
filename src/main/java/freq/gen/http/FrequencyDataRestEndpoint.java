package freq.gen.http;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/frequency")
public class FrequencyDataRestEndpoint
{

    /**
     * Returns a frequency reading in a broken JSON format.
     */
    @Get("/")
    public String getReading()
    {
        return String.format("{ \"frequencyReading\": { \"timestamp\": %d, \"frequency\": %.2f }, isValid: %s }",
                System.currentTimeMillis(), FrequencyGenerator.getLatestFrequency(), getRandomValidity());
    }


    /**
     * Returns the frequency reading in a valid JSON format
     */
    @Get("/validjson")
    public String getValidJsonReading()
    {
        return String.format("{ \"frequencyReading\": { \"timestamp\": %d, \"frequency\": %.2f }, \"isValid\": %s }",
                System.currentTimeMillis(), FrequencyGenerator.getLatestFrequency(), getRandomValidity());
    }


//    @Get("/resetToDefault")
//    public HttpResponse<Object> reset()
//    {
//        FrequencyGenerator.getLatestFrequency();
//        return HttpResponse.ok();
//    }


    private Object getRandomValidity()
    {
        return (Math.random() > 0.9) ? "false" : "true";
    }
}
