<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Über uns - EventPass</title>
    <link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>
<body>
<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
		scope="session" />
	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />
<header>

    <a class="logo" href="HomeAppl.jsp?action=Home">
        🎟 EventPass
    </a>

    <nav>
        <a href="HomeAppl.jsp?action=Home">
            Veranstaltungen
        </a>

        

        <% if(!myLogin.isLoggedIn()) {%>
			 <a href="LoginView.jsp">
            Login
        </a>
        <%} %>
    </nav>

</header>


<div class="container">

    <h1>Über uns</h1>

    <div class="card">

        <h2>Willkommen bei EventPass!</h2>

        <p>
            EventPass ist eine Plattform für Veranstaltungen.
            Hier kannst du verschiedene Events entdecken und
            deine Tickets einfach und schnell buchen.
        </p>

        <p>
            Ob Konzert, Party, Workshop oder Festival –
            bei EventPass findest du Veranstaltungen an einem Ort.
        </p>

    </div>


    <div class="card">

        <h2>Du möchtest ein Event veröffentlichen?</h2>

        <p>
            Du möchtest deine eigene Veranstaltung auf
            EventPass anbieten?
        </p>

        <p>
            Dann kannst du uns kontaktieren. Wir helfen dir
            gerne dabei, dein Event auf unserer Plattform
            zu veröffentlichen.
        </p>

        <p>
            <strong>📧 E-Mail:</strong>
            eventpass@example.de
        </p>

        <p>
            <strong>📞 Telefon:</strong>
            +49 621 12345678
        </p>

    </div>


    <div class="card">

        <h2>Du brauchst Hilfe?</h2>

        <p>
            Hast du Fragen zu deiner Buchung, einem Ticket
            oder einer Veranstaltung?
        </p>

        <p>
            Unser Support hilft dir gerne weiter.
        </p>

        <p>
            <strong>📧 Support:</strong>
            support@eventpass.de
        </p>

        <p>
            <strong>📞 Telefon:</strong>
            +49 621 12345678
        </p>

        <p>
            <strong>🕐 Erreichbarkeit:</strong>
            Montag – Freitag, 09:00 – 17:00 Uhr
        </p>

    </div>


    <div class="card">

        <h2>Kontakt</h2>

        <p>
            <strong>EventPass</strong>
        </p>

        <p>
            Musterstraße 10<br>
            67059 Ludwigshafen am Rhein<br>
            Deutschland
        </p>

    </div>

</div>
</body>
</html>