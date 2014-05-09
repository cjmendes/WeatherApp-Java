import java.net.URL;

public interface Model {
    String getWeather();
    double getTemperature();
    String getIconText();
    String getElevation();
    String getHumidity();
    String getPrecipitation();
    double getFeelsLike();
    String getWindDir();
    double getWindSpeed();
    URL getRadar();
    ForecastModel getForecast();
}
