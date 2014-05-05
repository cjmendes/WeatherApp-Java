public class MockModel implements Model {
    @Override
    public String getWeather() {
        return "Super Awesome";
    }

    @Override
    public double getTemperature() {
        return 70.2;
    }

    @Override
    public double getWindSpeed() {
        return 1.4;
    }
}
