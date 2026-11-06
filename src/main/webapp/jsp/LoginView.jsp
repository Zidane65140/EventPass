<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Login Seite- EventPass</title>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">

</head>
<body>
<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />

<header>
    <a class="logo" href="HomeAppl.jsp?action=Home">🎟 EventPass</a>
    <nav>
        <a href="HomeAppl.jsp?action=Home">Home</a> 
        <a href="UeberUns.jsp">Über uns</a>
        <a href="RegView.jsp">Registrieren</a>
    </nav>
</header> 

<div class="form-box">
    <h1>Willkommen zurück!</h1>
    

    <form action="LoginAppl.jsp" method="post">
    
 <jsp:getProperty name="myLogin" property="message" />
 
        <label for="email">E-Mail-Adresse</label>
        <input type="email" id="email"
               name="email" required
               placeholder="name@beispiel.de">

        <label for="password">Passwort</label>
        <input type="password" id="password"
               name="password" required
               placeholder="Dein Passwort">
               

        <input class="btn"  name="btnLog" value="Login"  type="submit">
    </form>

    <p style="margin-top: 20px;">
        Noch kein Konto?
        <a href="RegView.jsp">Jetzt registrieren</a>
    </p>
</div>
</body>
</html>