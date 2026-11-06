<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="MyEvent"
    class="de.hwg_lu.bwi420.beans.EventPassBean"
    scope="session"/>
 
<!DOCTYPE html> 
<html>
<head>
    <meta charset="UTF-8">
    <title>Warenkorb - EventPass</title>

    <link type="text/css"
          rel="stylesheet"
          href="../css/BWI420.css">
          <script src="../js/bwi420.js"></script>
</head>

<body>
 
<header>

    <a class="logo" href="HomeLogIn.jsp">
        🎟 EventPass
    </a>

    <nav>
        <a href="HomeLogIn.jsp">Home</a>
        <a href="MeineBuchungen.jsp">Meine Buchungen</a>
        <a href="EventDetails.jsp">Züruck zum Auswahl</a>
        <a href="UeberUns.jsp">Über uns</a>
    </nav>

</header>


<div class="form-box">

    <h1>🛒 Dein Warenkorb</h1>

    <%
    
        if (MyEvent.getWarenkorbEventIds().isEmpty()) {
    %>

        <p>Dein Warenkorb ist leer.</p>

        <br>

        <a class="btn" href="EventDetails.jsp">
           Züruck zur Auswahl
        </a>

    <%
        } else {
    %>

        <%= MyEvent.getHTMLFromWarenkorb() %>

        <br><br>

        <form action="Kasse.jsp" method="post"  onsubmit="return bestaetigeKasse();">

            <input type="submit"
                   class="btn"
                   name="zurkasse"
                   value="Zur Kasse">

        </form>

    <%
        }
    %>

</div>

</body>
</html>