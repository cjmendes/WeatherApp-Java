public interface ForecastModel
{
    String getDay(int dayIndex);
    double getDayHigh(int dayIndex);
    double getDayLow(int dayIndex);
    double getHum(int dayIndex);
    String getIconString(int dayIndex);
    String getConditions(int dayIndex);
    double getWindspeed(int dayIndex);
    String getWindDirection(int dayIndex);

}
