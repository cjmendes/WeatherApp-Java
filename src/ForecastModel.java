public interface ForecastModel
{
    String getDay(int dayIndex);
    double getDayHigh(int dayIndex);
    double getDayLow(int dayIndex);
    double getHum(int dayIndex);
}