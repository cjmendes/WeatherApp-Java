import java.net.URL;

public interface Model {
    String getWeather();
    double getTemperature();
    double getWindSpeed();
    URL getRadar();
    ForecastModel getForecast();
}
