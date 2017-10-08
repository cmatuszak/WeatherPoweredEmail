package weatherapp

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        // default homepage is the SignupController's index page
        "/"(controller:"signup")
        "/emailBlast"(controller:"emailBlast")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
