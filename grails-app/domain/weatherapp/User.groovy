package weatherapp

/**
 * Domain class for the user
 * Creates a User table in the db with two fields:
 * email: must be a valid email and it must be unique
 * location: must be one in the list defined in the SignupController
 */
class User {

    String email;
    String location;

    static constraints = {
        email email: true
        email unique: true
        location inList: SignupController.locations
    }
}
