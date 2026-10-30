import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherResponse {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final JsonNode rootNode;
    private final int limit;

    public WeatherResponse(String responseBody, int limit) throws JsonProcessingException {
        rootNode = MAPPER.readTree(responseBody);
        this.limit = limit;
    }

    public String getPrettyResponse() throws JsonProcessingException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }

    public double getFactTemp() {
        return numberAt(rootNode, "/fact/temp");
    }

    public double getAverageForecastTemp() {
        double sum = 0.0;
        for (int i = 0; i < limit; i++) {
            sum += numberAt(rootNode, "/forecasts/" + i + "/parts/day/temp_avg");
        }
        return sum / limit;
    }

    private static double numberAt(JsonNode rootNode, String path) {
        JsonNode value = rootNode.at(path);
        if (!value.isNumber()) {
            throw new IllegalArgumentException("Поле " + path + " отсутствует или не содержит число");
        }
        return value.doubleValue();
    }
}
