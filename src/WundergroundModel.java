import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WundergroundModel implements Model {

	/*
	 * Loren's Key: 4a505977e3e86a2c Chris's Key: 0757ee8cfe589f60 Derek's Key: 5faf852c94c0fe7a
	 */
	private final String ACCESS_TOKEN = "4a505977e3e86a2c";
	private JsonElement weatherJson = null;
	private JsonElement forecastJson = null;
	private JsonElement lunarJson = null;
	private URL radarURL = null;
    private ForecastModel forecast, lunar;

    private URL weatherURL, forecastURL, lunarURL;

	public WundergroundModel(String query) throws WundergroundException {


		try 
		{
            query = query.replace(' ', '_');

            // Do these guys before we encode to avoid double encoding
            forecast = new WundergroundForecastModel(query);

            lunar = new WundergroundForecastModel(query);
			// Encode given URL -- could throw UnsupportedEncodingException
			query = URLEncoder.encode(query, "utf-8");

			// Construct Weather API URL
			weatherURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/conditions/q/" + query + ".json");

		    // forecast URL to get the day high's and low's
			forecastURL = new URL("http://api.wunderground.com/api/"+ ACCESS_TOKEN + "/forecast/q/"+ query +".json");

			radarURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/animatedradar/q/" + query + ".gif?width="+WeatherPanel.WIDTH+"&height="+WeatherPanel.HEIGHT+"&newmaps=1&timelabel=1&timelabel.y=10&num=15&delay=25");

			//Construct Astronomy API URL
			lunarURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/astronomy/q/" + query + ".json");

            refresh();
        } catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (java.net.MalformedURLException mue) {
			mue.printStackTrace();
		}
	}

    public void refresh() throws WundergroundException
    {
        try {
            // Open the URL
            InputStream is = weatherURL.openStream(); // throws an IOException
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            InputStream is2 = forecastURL.openStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            
            InputStream is3 = lunarURL.openStream();
            BufferedReader br3 = new BufferedReader(new InputStreamReader(is3));

            // Read the result into a JSON Element
            weatherJson = new JsonParser().parse(br);
            forecastJson = new JsonParser().parse(br2);
            lunarJson = new JsonParser().parse(br3);

            // Check for ambiguous results
            if(weatherJson != null) {
                if(weatherJson.getAsJsonObject().get("response").getAsJsonObject().has("error")) {
                    throw new WundergroundException(weatherJson.getAsJsonObject().get("response").getAsJsonObject().get("error").getAsJsonObject().get("description").getAsString());
                }
                if(weatherJson.getAsJsonObject().get("response").getAsJsonObject().has("results")) {
                    ArrayList<String> locationNames = new ArrayList<String>();
                    ArrayList<String> locationCodes = new ArrayList<String>();

                    JsonArray resultArray = weatherJson.getAsJsonObject().get("response").getAsJsonObject().get("results").getAsJsonArray();
                    for(int i = 0; i < resultArray.size(); i++) {
                        JsonObject result = resultArray.get(i).getAsJsonObject();
                        String code = "zmw:"+result.get("zmw").getAsString();
                        String city = result.get("city").getAsString();
                        String state = result.get("state").getAsString();
                        String country = result.get("country_name").getAsString();

                        String name = city+", ";
                        if(!state.isEmpty()) name+= state+", ";
                        name += country;

                        locationNames.add(i, name);
                        locationCodes.add(i, code);
                    }

                    throw new MultipleResultsException(locationNames, locationCodes);
                }
            }

            // Grab forecast data
            forecast.refresh();

            // Close the connection
            is.close();
            br.close();
            is2.close();
            br2.close();
            is3.close();
            br3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWeather() {
		if (weatherJson != null) {
			String weather = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("weather").getAsString();
			return weather;
		} else {
			return null;
		}
	}

    public double getTemperature() {
		if (weatherJson != null) {
			double temp = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("temp_f").getAsDouble();
			return temp;
		} else {
			double temp = Double.NaN;
			return temp;
		}
	}
    
    public double getDHigh(int dayIndex) {
		if (weatherJson != null) {
			double dayhigh = forecastJson.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("high")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();
			return dayhigh;
		} else {
			double dayhigh = Double.NaN;
			return dayhigh;
		}
	}
    public double getDLow(int dayIndex) {
		if (weatherJson != null) {
			double daylow = forecastJson.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                    .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject().get("low")
                    .getAsJsonObject().get("fahrenheit").getAsDouble();
			return daylow;
		} else {
			double daylow = Double.NaN;
			return daylow;
		}
	}
    
    public String getIconText() {
		if (weatherJson != null) {
			String icon = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("icon").getAsString();
			return icon;
		} else {
			return null;
		}
	}
    
    public String getElevation() {
		if (weatherJson != null) {
			String elevation = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("observation_location")
					.getAsJsonObject().get("elevation").getAsString();
			return elevation;
		} else {
			return null;
		}
	}
    
    public String getHumidity() {
		if (weatherJson != null) {
			String humidity = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("relative_humidity").getAsString();
			return humidity;
		} else {
			return null;
		}
	}
    
    public String getPrecipitation() {
		if (weatherJson != null) {
			String precipitation = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("precip_today_in").getAsString();
			return precipitation;
		} else {
			return null;
		}
	}
    
    public double getFeelsLike() {
		if (weatherJson != null) {
			double feelsLike = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("feelslike_f").getAsDouble();
			return feelsLike;
		} else {
			Double feelsLike = Double.NaN;
			return feelsLike;
		}
	}
    
    public String getWindDir() {
		if (weatherJson != null) {
			String windDir = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("wind_dir").getAsString();
			return windDir;
		} else {
			return null;
		}
	}

    public double getWindSpeed() {
		if (weatherJson != null) {
			double windmph = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("wind_mph").getAsDouble();
			return windmph;
		} else {
			Double windmph = Double.NaN;
			return windmph;
		}
	}
    
    public int getMoonPhase(int dayIndex)
    {
        if(lunarJson != null)
        {
        	return lunarJson.getAsJsonObject().get("moon_phase")
            .getAsJsonObject().get("percentIlluminated").getAsInt();
        }
        else
        {
            return -1;
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

    public String getLocation() {
        if(weatherJson != null) {
            return weatherJson.getAsJsonObject().get("current_observation").getAsJsonObject().get("display_location")
                    .getAsJsonObject().get("full").getAsString();
        } else {
            return null;
        }
    }

    @Override
    public ForecastModel getForecast()
    {
        return forecast;
    }
    
    public ForecastModel getLunarPhase()
    {
    	return lunar;
    }

    @SuppressWarnings("serial")
	public class WundergroundException extends Exception
    {
        public WundergroundException(String message) {
            super(message);
        }
    }

    public class MultipleResultsException extends WundergroundException
    {
        private List<String> names;
        private List<String> codes;

        public MultipleResultsException(List<String> names, List<String> codes)
        {
            super("Multiple results found.");
            this.names = names;
            this.codes = codes;
        }

        public List<String> getNames()
        {
            return names;
        }

        public List<String> getCodes()
        {
            return codes;
        }
    }
}