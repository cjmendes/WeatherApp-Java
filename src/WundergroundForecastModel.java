import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class WundergroundForecastModel implements ForecastModel
{

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement jse7 = null;

	public WundergroundForecastModel(String zipcode) {

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
    public String getDay(int dayIndex) {
		if (jse7 != null) {
			String weekDay = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("date")
                    .getAsJsonObject().get("weekday_short").getAsString();
            String day = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("date")
                    .getAsJsonObject().get("day").getAsString();
            String month = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("date")
                    .getAsJsonObject().get("month").getAsString();

			return weekDay+" "+day+"/"+month;
		} else {
			return null;
		}
	}

    public double getDayHigh(int dayIndex) {
		if (jse7 != null) {
            double high = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("high")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();

            return high;

		} else {
			Double Dhigh = Double.NaN;
			return Dhigh;
		}
	}

    public double getDayLow(int dayIndex) {
		if (jse7 != null) {
            double low = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("low")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();

            return low;
		} else {
			Double Dlow = Double.NaN;
			return Dlow;
		}
	}

    public double getHum(int dayIndex) {
		if (jse7 != null) {
            double hum = jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("avehumidity")
                    .getAsDouble();

            return hum;
		} else {
			Double hum = Double.NaN;
			return hum;
		}
	}
}