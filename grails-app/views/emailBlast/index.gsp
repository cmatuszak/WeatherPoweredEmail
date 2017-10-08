<%@ page import="weatherapp.SignupController" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Weather Powered Email</title>
    <asset:link rel="icon" href="weatherFavicon.ico" type="image/x-ico" />
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
            <h1>Send Email Blast:</h1>
            <form action="/emailBlast/emailBlast" method="post" style="margin: 0 auto; width:320px">
                <div class="form-group">
                    <input class="btn-primary col-xs-12" type="submit" name="blast" value="Email" id="blast">
                </div>
            </form>
        </div>

    </section>
</div>

</body>
</html>