<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Favoriten-Eventpass</title>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>

<body>

<jsp:useBean id="MyEvent"
    class="de.hwg_lu.bwi420.beans.EventPassBean"
    scope="session" />

<jsp:useBean id="myLogin"
    class="de.hwg_lu.bwi420.beans.LoginBean"
    scope="session" />

<%
if (!myLogin.isLoggedIn()) {
    response.sendRedirect("LoginView.jsp");
    return;
}
%>

<header>

    <a class="logo" href="HomeAppl.jsp?action=Home">
        🎟 EventPass
    </a>

    <nav> 

        <a href="HomeAppl.jsp?action=Home">
            Home
        </a>

        <a href="HomeLogIn.jsp#events">
            Events
        </a>
        <a href="MeineBuchungen.jsp">Meine Buchungen</a>

       

        <a href="UeberUns.jsp">
            Über uns
        </a>

    </nav>

</header>


<div class="container">

    <h1>❤️ Meine Favoriten</h1>

    <%
    java.util.Vector<de.hwg_lu.bwi420.classes.Favorit> favoriten =
        MyEvent.getFavoriten(myLogin.getEmail());
    %>


    <%
    if (favoriten.isEmpty()) {
    %>

        <div class="card">

            <h2>Keine Favoriten vorhanden</h2>

            <p>
                Du hast noch keine Veranstaltungen zu deinen
                Favoriten hinzugefügt.
            </p>

            <a class="btn"
               href="HomeLogIn.jsp#events">
                Veranstaltungen entdecken
            </a>

        </div>

    <%
    } else {
    %>


        <%
        for (de.hwg_lu.bwi420.classes.Favorit favorit : favoriten) {

            int eventId = favorit.getEventId();

            MyEvent.prüfEventdetailId(eventId);

            if (MyEvent.getEventDetailId() != null) {
        %>


            <div class="event-card">

                <h2>
                    <%= MyEvent.getEventDetailId().getEventName() %>
                </h2>

                <p>
                    <strong>Kategorie:</strong>
                    <%= MyEvent.getEventDetailId().getEventType() %>
                </p>

                <p>
                    <strong>Datum:</strong>
                    <%= MyEvent.getEventDetailId().getEventDatum() %>
                </p>

                <p>
                    <strong>Ort:</strong>
                    <%= MyEvent.getEventDetailId().getEventOrt() %>
                </p>

                <p>
                    <strong>Preis:</strong>
                    <%= MyEvent.getEventDetailId().getPreis() %> €
                </p>


                <div style="margin-top: 15px;">

                    <a class="btn"
                       href="HomeAppl.jsp?Ticketsansehen=Tickets ansehen&eventId=<%= MyEvent.getEventDetailId().getEventId()  %>">
                        Event ansehen
                    </a>


                    <a class="btn"
                       href="FavoritAppl.jsp?action=delete&eventId=<%= eventId %>">
                        ❤️ Aus Favoriten entfernen
                    </a>

                </div>

            </div>


        <%
            }
        }
        %>


    <%
    }
    %>

</div>


<footer>
    © 2026 EventPass
</footer>


</body>

</html>