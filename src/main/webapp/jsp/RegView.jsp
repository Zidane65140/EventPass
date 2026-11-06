<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrierungsseite- EventPass</title>
<script src="../js/bwi420.js"></script>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>
<body>
<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean" scope="session" />

	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />
 
<header>
    <a class="logo" href="HomeAppl.jsp?action=Home">🎟 EventPass</a>
    <nav> 
        <a href="HomeAppl.jsp?action=Home">Home</a>
        <a href="UeberUns.jsp">Über uns</a>
       <% if(!myLogin.isLoggedIn()) {%>
			 <a href="LoginView.jsp">
            Login
        </a>
        <%} %> 
    </nav>
</header>
 
<div class="form-box">
    <h1>Konto erstellen</h1>
    <p>Registriere dich und entdecke spannende Events.</p>

    <form action="RegAppl.jsp" method="post" onsubmit="return pruefePasswort();">

        <label for="firstname">Vorname</label>
        <input type="text" id="firstname"
               name="vorname" required>

        <label for="lastname">Nachname</label>
        <input type="text" id="lastname"
               name="nachname" >

        <label for="email">E-Mail-Adresse</label>
        <input type="email" id="email"
               name="email" required>
<%
String fehler = request.getParameter("fehler");

if ("email".equals(fehler)) {
%>

<p style="color: red;">
    Diese E-Mail-Adresse ist bereits registriert.
    Bitte geben Sie eine andere E-Mail-Adresse ein.
</p>

<%
}
%>
        <label for="password">Passwort</label>
        <input type="password" id="password"
               name="password" required>
               <p id="passwortFehler" style="color: red;"></p>

        <label for="password2">Passwort bestätigen</label>
        <input type="password" id="password2"
               name="password2" required>
               <p id="bestaetigungFehler" style="color: red;"></p>

        <input class="btntreg"  name="btnReg" value="Registrieren"  type="submit">
           
        
    </form>

    <p style="margin-top: 20px;">
        Bereits ein Konto?
        <a href="LoginView.jsp">Zum Login</a>
    </p>
</div>
</body>
</html>