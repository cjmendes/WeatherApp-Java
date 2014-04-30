
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.URL;

import com.google.gson.*;

public class Model {

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement jse = null;

	public void getInfo(String zipcode) {

		try 
		{
			// Encode given URL -- could throw UnsupportedEncodingException
			String zip = URLEncoder.encode(zipcode, "utf-8");

			// Construct Weather API URL
			URL weatherURL = new URL("http://api.wunderground.com/api/"
					+ ACCESS_TOKEN + "/conditions/q/" 
					+ zip + ".json");

			// Open the URL
			InputStream is = weatherURL.openStream(); // throws an IOException
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			// Read the result into a JSON Element
			jse = new JsonParser().parse(br);

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
}