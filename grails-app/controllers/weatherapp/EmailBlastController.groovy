package weatherapp

import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired

/**
 * Controller for sending out the email blast
 */
class EmailBlastController {
    @Autowired
    private EmailBlastService service;

    def index() {}

    /**
     * Send out the email blast to all subscribed users
     */
    def emailBlast() {
        try {
            // get all users in the db
            def userList = User.getAll();
            // fetch the weather data and send a personalized email for each user
            for (User user : userList) {
                String location = user.location;
                JSONObject todaysWeather = service.getWeatherData(location, "conditions");
                JSONObject averageWeather = service.getWeatherData(location, "almanac");
                service.sendEmail(user.email, location, todaysWeather, averageWeather);
            }
            flash.message = "Success! Email blast sent.";
        } catch (Exception e) {
            flash.error = "Error. Something went wrong. Please try again.";
        }
        redirect action: 'index'
    }
}
