import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    private static final String BASE_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final HttpClient client;

    public WeatherClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String fetch(String apiKey, double lat, double lon, int limit) throws IOException, InterruptedException {
        URI uri = URI.create(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&limit=" + limit);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-Yandex-Weather-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException(String.format(
                    "Ошибка API. HTTP-код: %d. Тело ошибки:\n%s",
                    response.statusCode(), response.body()
            ));
        }

        return response.body();
    }
}
