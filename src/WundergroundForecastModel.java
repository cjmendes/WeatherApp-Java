import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
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
    private URL sevendayURL;

	public WundergroundForecastModel(String zipcode) {

		try {
			// Encode given URL -- could throw UnsupportedEncodingException
			String zip = URLEncoder.encode(zipcode, "utf-8");

			// Construct Weather API URL
			sevendayURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/forecast10day/q/" + zip + ".json");
			
		} catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		}

	}

    public void refresh()
    {
        try {
            // Open the URL
            InputStream is2 = sevendayURL.openStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

            // Read the result into a JSON Element
            jse7 = new JsonParser().parse(br2);

            System.out.println(jse7);

            // Close the connection
            is2.close();
            br2.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private JsonObject getForecastObject(int dayIndex)
    {
        return jse7.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject();
    }

	// Seven Day Forecast
    public String getDay(int dayIndex) {
		if (jse7 != null) {
			String weekDay = getForecastObject(dayIndex).get("date").getAsJsonObject().get("weekday_short").getAsString();
            String day = getForecastObject(dayIndex).get("date").getAsJsonObject().get("day").getAsString();
            String month = getForecastObject(dayIndex).get("date").getAsJsonObject().get("month").getAsString();

			return weekDay+" "+day+"/"+month;
		} else {
			return null;
		}
	}
    public String getIconString(int dayIndex) {
		if (jse7 != null) {
            String iconString = getForecastObject(dayIndex).get("icon")
                    .getAsString();

            return iconString;

		}
		else {
			return null;
		}
	}

    public double getDayHigh(int dayIndex) {
		if (jse7 != null) {
            double high = getForecastObject(dayIndex).get("high").getAsJsonObject().get("fahrenheit").getAsDouble();

            return high;

		} else {
			Double Dhigh = Double.NaN;
			return Dhigh;
		}
	}

    public double getDayLow(int dayIndex) {
		if (jse7 != null) {
            double low = getForecastObject(dayIndex).get("low").getAsJsonObject().get("fahrenheit").getAsDouble();

            return low;
		} else {
			Double Dlow = Double.NaN;
			return Dlow;
		}
	}

    public double getHum(int dayIndex) {
		if (jse7 != null) {
            double hum = getForecastObject(dayIndex).get("avehumidity").getAsDouble();

            return hum;
		} else {
			Double hum = Double.NaN;
			return hum;
		}
	}

    public String getConditions(int dayIndex)
    {
        if(jse7 != null)
        {
            return getForecastObject(dayIndex).get("conditions").getAsString();
        }
        else
        {
            return null;
        }
    }

    public double getWindspeed(int dayIndex)
    {
        if(jse7 != null)
        {
            return getForecastObject(dayIndex).get("avewind").getAsJsonObject().get("mph").getAsDouble();
        }
        else
        {
            return Double.NaN;
        }
    }

    public int getWindDirection(int dayIndex)
    {
        if(jse7 != null)
        {
            return getForecastObject(dayIndex).get("avewind").getAsJsonObject().get("degrees").getAsInt();
        }
        else
        {
            return -1;
        }
    }
}