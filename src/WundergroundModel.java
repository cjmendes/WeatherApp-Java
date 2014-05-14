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
	private URL radarURL = null;

    private URL weatherURL;

	public WundergroundModel(String query) throws WundergroundException {


		try 
		{
            query = query.replace(' ', '_');
			// Encode given URL -- could throw UnsupportedEncodingException
			query = URLEncoder.encode(query, "utf-8");

			// Construct Weather API URL
			weatherURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/conditions/forecast/astronomy/forecast10day/q/" + query + ".json");

			radarURL = new URL("http://api.wunderground.com/api/" + ACCESS_TOKEN + "/animatedradar/q/" + query + ".gif?width="+WeatherPanel.WIDTH+"&height="+WeatherPanel.HEIGHT+"&newmaps=1&timelabel=1&timelabel.y=10&num=15&delay=25");

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


            // Read the result into a JSON Element
            weatherJson = new JsonParser().parse(br);

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

            // Close the connection
            is.close();
            br.close();
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
			double dayhigh = weatherJson.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
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
			double daylow = weatherJson.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
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
    
    public double getPrecipitation() {
		if (weatherJson != null) {
			double precipitation = weatherJson.getAsJsonObject().get("current_observation")
					.getAsJsonObject().get("precip_today_in").getAsDouble();
			if(precipitation < 0)
				precipitation = 0.00;
			return precipitation;
		} else {
			Double precipitation = Double.NaN;
			return precipitation;
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

    private JsonObject getForecastObject(int dayIndex)
    {
        return weatherJson.getAsJsonObject().get("forecast").getAsJsonObject().get("simpleforecast")
                .getAsJsonObject().get("forecastday").getAsJsonArray().get(dayIndex).getAsJsonObject();
    }

    public String getDay(int dayIndex) {
        if (weatherJson != null) {
            String weekDay = getForecastObject(dayIndex).get("date").getAsJsonObject().get("weekday_short").getAsString();
            String day = getForecastObject(dayIndex).get("date").getAsJsonObject().get("day").getAsString();
            String month = getForecastObject(dayIndex).get("date").getAsJsonObject().get("month").getAsString();

            return weekDay+" "+day+"/"+month;
        } else {
            return null;
        }
    }
    public String getIconString(int dayIndex) {
        if (weatherJson != null) {
            return getForecastObject(dayIndex).get("icon").getAsString();
        }
        else {
            return null;
        }
    }

    public double getDayHigh(int dayIndex) {
        if (weatherJson != null) {
            return getForecastObject(dayIndex).get("high").getAsJsonObject().get("fahrenheit").getAsDouble();

        } else {
            return Double.NaN;
        }
    }

    public double getDayLow(int dayIndex) {
        if (weatherJson != null) {
            return getForecastObject(dayIndex).get("low").getAsJsonObject().get("fahrenheit").getAsDouble();
        } else {
            return Double.NaN;
        }
    }

    public double getHum(int dayIndex) {
        if (weatherJson != null) {
            return getForecastObject(dayIndex).get("avehumidity").getAsDouble();
        } else {
            Double hum = Double.NaN;
            return hum;
        }
    }

    public String getConditions(int dayIndex)
    {
        if(weatherJson != null)
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
        if(weatherJson != null)
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
        if(weatherJson != null)
        {
            return getForecastObject(dayIndex).get("avewind").getAsJsonObject().get("degrees").getAsInt();
        }
        else
        {
            return -1;
        }
    }
    
    public int getMoonPhase(int dayIndex)
    {
        if(weatherJson != null)
        {
        	return weatherJson.getAsJsonObject().get("moon_phase")
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