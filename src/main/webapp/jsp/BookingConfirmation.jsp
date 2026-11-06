<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="MyEvent"
    class="de.hwg_lu.bwi420.beans.EventPassBean"
    scope="session"/>

<jsp:useBean id="myLogin"
    class="de.hwg_lu.bwi420.beans.LoginBean"
    scope="session"/>

<!DOCTYPE html>
<html>
  
<head>

    <meta charset="UTF-8">

    <title>Buchung erfolgreich</title>

    <link type="text/css"
          rel="stylesheet"
          href="../css/BWI420.css">

</head>

<body>

<header>

    <a class="logo" href="HomeAppl.jsp">
        🎟 EventPass
    </a>

    <nav>

        <a href="HomeAppl.jsp?Home=Home">
            Home
        </a>

        <a href="UeberUns.jsp">
            Über uns
        </a>

        <a href="Warenkorb.jsp">
            Warenkorb
        </a>

    </nav>

</header>


<div class="form-box">

    <div class="success">

        <h1>✓ Buchung erfolgreich!</h1>

        <p>
            Vielen Dank für deine Buchung.
        </p>

    </div>


    <h2>Deine Buchungsdetails</h2>


    <p>
        <strong>Buchungsnummer:</strong>
        <%= MyEvent.getLetzteBuchungsNummer() %>
    </p>


    <p>
        <strong>E-Mail:</strong>
        <%= myLogin.getEmail() %>
    </p>


    <hr>


    <%= MyEvent.getLetzteBuchungsDetails() %>


    <h2>
        Gesamtpreis:
        <%= String.format(
                "%.2f",
                MyEvent.getLetzteBuchungsGesamtpreis()
            ) %>
        €
    </h2>


    <br>
<h3> <strong>Sie haben eine Buchungsbestätigung per Mail bekommen</strong></h3>

    <a class="btn"
       href="HomeAppl.jsp?Home=Home">
        Zurück zur Startseite
    </a>

</div>

</body>

</html>