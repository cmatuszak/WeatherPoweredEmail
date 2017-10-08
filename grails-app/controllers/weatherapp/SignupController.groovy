package weatherapp

import grails.validation.ValidationException

/**
 * Controller for the signup flow
 */
class SignupController {
    // top 100 US cities by population, data as of 2012 from http://www.citymayors.com/gratis/uscities_100.html
    public static locations = ['New York City; New York',
                     'Los Angeles; California',
                     'Chicago; Illinois',
                     'Houston; Texas',
                     'Philadelphia; Pennsylvania',
                     'Phoenix; Arizona',
                     'San Antonio; Texas',
                     'San Diego; California',
                     'Dallas; Texas',
                     'San Jose; California',
                     'Austin; Texas',
                     'Jacksonville; Florida',
                     'Indianapolis; Indiana',
                     'San Francisco; California',
                     'Columbus; Ohio',
                     'Fort Worth; Texas',
                     'Charlotte; North Carolina',
                     'Detroit; Michigan',
                     'El Paso; Texas',
                     'Memphis; Tennessee',
                     'Boston; Massachusetts',
                     'Seattle; Washington',
                     'Denver; Colorado',
                     'Washington; DC',
                     'Nashville; Tennessee',
                     'Baltimore; Maryland',
                     'Louisville; Kentucky',
                     'Portland; Oregon',
                     'Oklahoma ; Oklahoma',
                     'Milwaukee; Wisconsin',
                     'Las Vegas; Nevada',
                     'Albuquerque; New Mexico',
                     'Tucson; Arizona',
                     'Fresno; California',
                     'Sacramento; California',
                     'Long Beach; California',
                     'Kansas City; Missouri',
                     'Mesa; Arizona',
                     'Virginia Beach; Virginia',
                     'Atlanta; Georgia',
                     'Colorado Springs; Colorado',
                     'Raleigh; North Carolina',
                     'Omaha; Nebraska',
                     'Miami; Florida',
                     'Oakland; California',
                     'Tulsa; Oklahoma',
                     'Minneapolis; Minnesota',
                     'Cleveland; Ohio',
                     'Wichita; Kansas',
                     'Arlington; Texas',
                     'New Orleans; Louisiana',
                     'Bakersfield; California',
                     'Tampa; Florida',
                     'Honolulu; Hawaii',
                     'Anaheim; California',
                     'Aurora; Colorado',
                     'Santa Ana; California',
                     'St. Louis; Missouri',
                     'Riverside; California',
                     'Corpus Christi; Texas',
                     'Pittsburgh; Pennsylvania',
                     'Lexington-Fayette; Kentucky',
                     'Anchorage; Alaska',
                     'Stockton; California',
                     'Cincinnati; Ohio',
                     'St. Paul; Minnesota',
                     'Toledo; Ohio',
                     'Newark; New Jersey',
                     'Greensboro; North Carolina',
                     'Plano; Texas',
                     'Henderson; Nevada',
                     'Lincoln; Nebraska',
                     'Buffalo; New York',
                     'Fort Wayne; Indiana',
                     'Jersey ; New Jersey',
                     'Chula Vista; California',
                     'Orlando; Florida',
                     'St. Petersburg; Florida',
                     'Norfolk; Virginia',
                     'Chandler; Arizona',
                     'Laredo; Texas',
                     'Madison; Wisconsin',
                     'Durham; North Carolina',
                     'Lubbock; Texas',
                     'Winston-Salem; North Carolina',
                     'Garland; Texas',
                     'Glendale; Arizona',
                     'Hialeah; Florida',
                     'Reno; Nevada',
                     'Baton Rouge; Louisiana',
                     'Irvine; California',
                     'Chesapeake; Virginia',
                     'Irving; Texas',
                     'Scottsdale; Arizona',
                     'North Las Vegas; Nevada',
                     'Fremont; California',
                     'Gilbert town; Arizona',
                     'San Bernardino; California',
                     'Boise; Idaho',
                     'Birmingham; Alabama']

    // default index action does nothing in this case
    def index() {
    }

    /**
     * Creates a new user and subscribes them to the email blast by adding them to the db
     * @param email the email of the new user
     * @param location the location of the new user
     */
    def subscribe(String email, String location) {
        User user = User.create();
        user.setProperty("email", email)
        user.setProperty("location", location)

        // set either a success message or an error message to be displayed
        try {
            user.save(failOnError: true);
            flash.message = "You have been subscribed."
        } catch (ValidationException e) {
            flash.error = "Error has occurred on saving. Please enter a valid email and try again."
        }
        redirect action: 'index'
    }
}
