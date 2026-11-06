<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Meine Buchungen - EventPass</title>

<link type="text/css"
      rel="stylesheet"
      href="../css/BWI420.css">

</head>

<body>

<jsp:useBean id="MyEvent"
    class="de.hwg_lu.bwi420.beans.EventPassBean"
    scope="session"/>

<jsp:useBean id="myLogin"
    class="de.hwg_lu.bwi420.beans.LoginBean"
    scope="session"/>

<%
if (!myLogin.isLoggedIn()) {
    response.sendRedirect("LoginView.jsp");
    return;
}
%>

<header>

    <a class="logo" href="HomeLogIn.jsp">
        🎟 EventPass
    </a>

    <nav>
        <a href="HomeLogIn.jsp">Home</a>
        <a href="Favoriten.jsp">Favoriten</a>
        <a href="Warenkorb.jsp">Warenkorb</a>
        <a href="UeberUns.jsp">Über uns</a>
    </nav>

</header>


<div class="container">

    <h1>Meine Buchungen</h1>

    <p>Hier findest du deine bisherigen Buchungen.</p>

    <%
        out.println(
            MyEvent.getHTMLFromBookingsForUser(
                myLogin.getEmail()
            )
        );
    %>

</div>

</body>
</html>