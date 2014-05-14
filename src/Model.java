import java.net.URL;

public interface Model {
    String getWeather();
    double getTemperature();
    double getDHigh(int dayIndex);
    double getDLow(int dayIndex);
    String getIconText();
    String getElevation();
    String getHumidity();
    double getPrecipitation();
    double getFeelsLike();
    String getWindDir();
    double getWindSpeed();
    URL getRadar();
    String getLocation();

    // Seven Day
    String getDay(int dayIndex);
    double getDayHigh(int dayIndex);
    double getDayLow(int dayIndex);
    double getHum(int dayIndex);
    String getIconString(int dayIndex);
    String getConditions(int dayIndex);
    double getWindspeed(int dayIndex);
    int getWindDirection(int dayIndex);


    void refresh() throws WundergroundModel.WundergroundException;
}
