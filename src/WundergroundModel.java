
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class WundergroundModel implements Model {

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60 Derek's Key: 5faf852c94c0fe7a
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement jse = null;
	private JsonElement jse2 = null;
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
			
		    // forecast URL to get the day high's and low's
			URL forecastURL = new URL("http://api.wunderground.com/api/"+ ACCESS_TOKEN + "/forecast/q/CA/"+ zip +".json");
			
			//You can change the width and height by changing the numbers in the URL
			radarURL = new URL("http://api.wunderground.com/api/"
					+ ACCESS_TOKEN + "/animatedradar/q/"
					+ zip + ".gif?width=500&height=200&newmaps=1&timelabel=1&timelabel.y=10&num=15&delay=25");
          
			
			// Open the URL
			InputStream is = weatherURL.openStream(); // throws an IOException
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			InputStream is2 = forecastURL.openStream();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

			// Read the result into a JSON Element
			jse = new JsonParser().parse(br);
			jse2 = new JsonParser().parse(br2);

            // Grab forecast data
            forecast = new WundergroundForecastModel(zipcode);

			// Close the connection
			is.close();
			br.close();
			is2.close();
			br2.close();
			
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
			double temp = Double.NaN;
			return temp;
		}
	}
    
    public double getDHigh(int dayIndex) {
		if (jse != null) {
			double dayhigh = jse2.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("high")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();
			return dayhigh;
		} else {
			double dayhigh = Double.NaN;
			return dayhigh;
		}
	}
    public double getDLow(int dayIndex) {
		if (jse != null) {
			double daylow = jse2.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("low")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();
			return daylow;
		} else {
			double daylow = Double.NaN;
			return daylow;
		}
	}
    
    public String getIconText() {
		if (jse != null) {
			String icon = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("icon").getAsString();
			return icon;
		} else {
			return null;
		}
	}
    
    public String getElevation() {
		if (jse != null) {
			String elevation = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("observation_location")
					.getAsJsonObject().get("elevation").getAsString();
			return elevation;
		} else {
			return null;
		}
	}
    
    public String getHumidity() {
		if (jse != null) {
			String humidity = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("relative_humidity").getAsString();
			return humidity;
		} else {
			return null;
		}
	}
    
    public String getPrecipitation() {
		if (jse != null) {
			String precipitation = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("precip_today_string").getAsString();
			return precipitation;
		} else {
			return null;
		}
	}
    
    public double getFeelsLike() {
		if (jse != null) {
			double feelsLike = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("feelslike_f").getAsDouble();
			return feelsLike;
		} else {
			Double feelsLike = Double.NaN;
			return feelsLike;
		}
	}
    
    public String getWindDir() {
		if (jse != null) {
			String windDir = jse.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("wind_dir").getAsString();
			return windDir;
		} else {
			return null;
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