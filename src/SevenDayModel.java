import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.URL;
import java.util.*;

import com.google.gson.*;

public class SevenDayModel {

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement jse7 = null;

	List<String> Days = new ArrayList<String>();
	List<Double> DHigh = new ArrayList<Double>();
	List<Double> DLow = new ArrayList<Double>();
	List<Double> Humidity = new ArrayList<Double>();

	public SevenDayModel(String zipcode) {

		try {
			// Encode given URL -- could throw UnsupportedEncodingException
			String zip = URLEncoder.encode(zipcode, "utf-8");

			// Construct Weather API URL
			URL sevendayURL = new URL("http://api.wunderground.com/api/"
					+ ACCESS_TOKEN + "/forecast10day/q/CA/" + zip + ".json");

			// Open the URL
			InputStream is2 = sevendayURL.openStream();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

			// Read the result into a JSON Element
			jse7 = new JsonParser().parse(br2);

			// Close the connection
			is2.close();
			br2.close();

		} catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}

	}

	// Seven Day Forecast

	public String getDay() {
		if (jse7 != null) {
			String day = "";
			for (int i = 0; i < 7; i++) {
				day = jse7.getAsJsonObject().get("current_observation")
						.getAsJsonObject().get("weekday_short").getAsString();
				Days.add(day);
			}

			return day;
		} else {
			return null;
		}
	}

	public double getDayHigh() {
		if (jse7 != null) {
			double Dhigh = 0;
			for (int i = 0; i < 7; i++) {
				Dhigh = jse7.getAsJsonObject().get("current_observation")
						.getAsJsonObject().get("high").getAsJsonObject()
						.get("fahrenheit").getAsDouble();
				DHigh.add(Dhigh);
			}
			return Dhigh;

		} else {
			Double Dhigh = Double.NaN;
			return Dhigh;
		}
	}

	public double getDayLow() {
		if (jse7 != null) {

			double Dlow = 0;

			for (int i = 0; i < 7; i++) {
				Dlow = jse7.getAsJsonObject().get("current_observation")
						.getAsJsonObject().get("low").getAsJsonObject()
						.get("fahrenheit").getAsDouble();

				DLow.add(Dlow);
			}

			return Dlow;
		} else {
			Double Dlow = Double.NaN;
			return Dlow;
		}
	}

	public double getHum() {
		if (jse7 != null) {
			double hum = 0;
			for (int i = 0; i < 7; i++) {
				hum = jse7.getAsJsonObject().get("current_observation")
						.getAsJsonObject().get("avehumidity").getAsDouble();
				Humidity.add(hum);

			}
			return hum;
		} else {
			Double hum = Double.NaN;
			return hum;
		}
	}
}