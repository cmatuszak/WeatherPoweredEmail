<%@ page import="weatherapp.SignupController" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Weather Powered Email</title>
    <asset:link rel="icon" href="weatherfavicon.ico" type="image/x-ico" />
    <asset:stylesheet src="application.css"/>
</head>
<body>

<div id="content" role="main">
    <section>
        <g:if test="${flash.message}">
            <div class="alert alert-success col-xs-12">
                <strong>Success!</strong> ${flash.message}
            </div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="alert alert-danger col-xs-12">
                <strong>Error.</strong> ${flash.error}
            </div>
        </g:if>

        <div class="row">
        <h1>Weather Powered Email</h1>
            <form action="/signup/subscribe" method="post" style="margin: 0 auto; width:320px">
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input class="form-control" required="true" type="email" name="email" value="" id="email">
                </div>
                <div class="form-group extraPadding">
                    <label for="location">Location:</label>
                    <select class="dropdown col-xs-12" name="location" id="location">
                        <g:each in="${SignupController.locations}">
                            <option value="${it}">${it}</option>
                        </g:each>
                    </select>
                </div>
                <div class="form-group">
                    <input class="btn-primary col-xs-12" type="submit" name="subscribe" value="Subscribe" id="subscribe">
                </div>
            </form>
        </div>

    </section>
</div>

</body>
</html>