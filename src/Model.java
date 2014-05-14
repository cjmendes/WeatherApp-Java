import java.net.URL;

public interface Model {
    String getWeather();
    double getTemperature();
    double getDHigh(int dayIndex);
    double getDLow(int dayIndex);
    String getIconText();
    String getElevation();
    String getHumidity();
    String getPrecipitation();
    double getFeelsLike();
    String getWindDir();
    double getWindSpeed();
    URL getRadar();
    String getLocation();
    ForecastModel getForecast();
    ForecastModel getLunarPhase();

    void refresh() throws WundergroundModel.WundergroundException;
}
