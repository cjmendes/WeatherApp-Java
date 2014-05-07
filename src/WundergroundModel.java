
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class WundergroundModel implements Model {

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement jse = null;
	private URL radarURL = null;
    private ForecastModel forecast;

	public WundergroundModel(String zipcode) throws WundergroundException {

		try 
		{
			// Encode given URL -- could throw UnsupportedEncodingException
			String zip = URLEncoder.encode(zipcode, "utf-8");

			// Construct Weather API URL
			URL weatherURL = new URL("http://api.wunderground.com/api/"
					+ ACCESS_TOKEN + "/conditions/q/" 
					+ zip + ".json");
			
			//You can change the width and height by changing the numbers in the URL
			radarURL = new URL("http://api.wunderground.com/api/"
					+ ACCESS_TOKEN + "/animatedradar/q/"
					+ zip + ".gif?width=400&height=200&newmaps=1&timelabel=1&timelabel.y=10&num=15&delay=25");

			// Open the URL
			InputStream is = weatherURL.openStream(); // throws an IOException
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// Read the result into a JSON Element
			jse = new JsonParser().parse(br);

            // Grab forecast data
            forecast = new WundergroundForecastModel(zipcode);

			// Close the connection
			is.close();
			br.close();
        } catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}

        if(jse != null) {
            if(jse.getAsJsonObject().get("response").getAsJsonObject().has("error")) {
                throw new WundergroundException(jse.getAsJsonObject().get("response").getAsJsonObject().get("error").getAsJsonObject().get("description").getAsString());
            }
        }

	}

    public String getWeather() {
		if (jse != null) {
			String weather = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("weather").getAsString();
			return weather;
		} else {
			return null;
		}
	}

    public double getTemperature() {
		if (jse != null) {
			double temp = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("temp_f").getAsDouble();
			return temp;
		} else {
			Double temp = Double.NaN;
			return temp;
		}
	}

    public double getWindSpeed() {
		if (jse != null) {
			double windmph = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("wind_mph").getAsDouble();
			return windmph;
		} else {
			Double windmph = Double.NaN;
			return windmph;
		}
	}
    
	public URL getRadar() {
		if (radarURL != null) {
			return radarURL;
		} else {
			radarURL = null;
			return radarURL;
		}
	}

    @Override
    public ForecastModel getForecast()
    {
        return forecast;
    }

    public class WundergroundException extends Exception
    {
        public WundergroundException(String message) {
            super(message);
        }
    }
}