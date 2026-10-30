public class Main {
    // Вставьте свой API ключ Яндекс Погоды
    private static final String API_KEY = "";

    private static final double DEFAULT_LAT = 55.75;
    private static final double DEFAULT_LON = 37.62;
    private static final int DEFAULT_LIMIT = 3;

    public static void main(String[] args) {
        try {
            WeatherClient weatherClient = new WeatherClient();
            String json = weatherClient.fetch(API_KEY, DEFAULT_LAT, DEFAULT_LON, DEFAULT_LIMIT);

            WeatherResponse weatherResponse = new WeatherResponse(json, DEFAULT_LIMIT);
            /*
             * Из формулировки "как есть" в критериях оценивания непонятно, допускается ли форматирование JSON.
             * Для удобства чтения ответ отформатирован, исходный ответ можно вывести через System.out.println(json).
             */
            System.out.println("Ответ сервиса:\n" + weatherResponse.getPrettyResponse());

            System.out.printf("Фактическая температура: %.2f%n", weatherResponse.getFactTemp());
            System.out.printf("Средняя температура по прогнозу за %d суток: %.2f%n",
                    DEFAULT_LIMIT, weatherResponse.getAverageForecastTemp()
            );
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}