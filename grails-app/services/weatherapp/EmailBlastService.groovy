package weatherapp

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class EmailBlastService {

    /**
     * Get the weather data from the Wunderground API for the given location and the given request type
     * @param location City; State to get the data for
     * @param request either "conditions" or "almanac" - conditions will return the current weather data and
     * almanac will return the historical average on this day for the given location
     * @return the response as a JSONObject
     */
    public JSONObject getWeatherData(String location, String request) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultStr = "";

        try {
            // form the correct url
            String BASE_URL = "http://api.wunderground.com/api/";

            BASE_URL += "272816dd5808e3fd/" + request + "/q/";

            String[] cityState = location.split("; ");
            String city = cityState[0];
            String state = cityState[1];

            if (city.split(" ").length > 1) {
                city = city.split(" ").join("_");
            }

            if (state.split(" ").length > 1) {
                state = state.split(" ").join("_");
            }


            BASE_URL += state + "/" + city + ".json";

            URL url = new URL(BASE_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            // make the request
            urlConnection.connect();

            // read the results from the request
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do if the input stream is empty
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty, nothing left to read
                return null;
            }

            // the results of the request as a string
            resultStr = buffer.toString();
        } catch (IOException e) {
            // print any errors that occurred with the reading
            System.out.println(e.getMessage());
        } finally {
            // clean up
            // close the connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // close the reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // print any problems with closing the reader
                    System.out.println(e.getMessage());
                }
            }
        }

        // finally, return the results as a JSONObject
        return new JSONObject(resultStr);
    }

    /**
     * Send a weather driven email to the given recipient using the provided data for the current and average weather
     * @param recipient the email address of the recipient
     * @param location the location that the weather is for
     * @param todaysWeather the JSONObject response for getting the current weather conditions
     * @param averageWeather the JSONObject response for getting the historic average conditions
     */
    public void sendEmail(String recipient, String location, JSONObject todaysWeather,
                                  JSONObject averageWeather) {

        // parse the relevant data out of the json responses
        String weather = todaysWeather.getJSONObject("current_observation").getString("weather");
        double tempF = todaysWeather.getJSONObject("current_observation").getDouble("temp_f");
        String tempString = todaysWeather.getJSONObject("current_observation").getString("temperature_string");
        String iconUrl = todaysWeather.getJSONObject("current_observation").getString("icon_url");
        int avgHigh = averageWeather.getJSONObject("almanac").getJSONObject("temp_high")
                .getJSONObject("normal").getInt("F");

        // determine which subject line to use depending on the weather data
        String sub
        if (weather.toLowerCase().equals("clear") || tempF > avgHigh + 5) {
            sub = "It's nice out! Enjoy a discount on us.";
        } else if (weather.toLowerCase().contains("rain") || weather.toLowerCase().contains("snow") ||
                weather.toLowerCase().contains("thunderstorms") || tempF < avgHigh - 5) {
            sub = "Not so nice out? That's okay, enjoy a discount on us.";
        } else {
            sub = "Enjoy a discount on us";
        }

        // form the body of the email
        String body = "<p>Greetings!</p>" +
                "<p>It's currently " + tempString + " and " + weather + " in " + location + ".</p>" +
                "<img src=\"" + iconUrl + "\"/>" +
                "<p>Enjoy a discount on us!</p>" +
                "<p>\n</p>" +
                "<p>Sincerely,\n</p>" +
                "<p>The WeatherPoweredEmail team</p>";

        // use the grails mail plugin to send the email to the recipient
        sendMail {
            to recipient
            // bcc me for verification purposes
            bcc "cmatuszak225@gmail.com"
            subject sub
            html body
        }
    }
}
