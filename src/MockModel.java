import java.net.URL;

public class MockModel implements Model {

	public MockModel(String zipcode)
	{
	}
	
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
    
    @Override
    public URL getRadar() {
    	URL radURL = null;
    	try{
    	radURL = new URL("http://www.google.com");
    	} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		}
        return radURL;
    }

}
