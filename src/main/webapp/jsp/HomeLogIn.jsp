<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Homepage- EventPass</title>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>  
<body>
<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean" scope="session" />
<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean" scope="session" />
<jsp:useBean id="myAccount" class="de.hwg_lu.bwi420.beans.AccountBean" scope="session" />
		<form action="LoginAppl.jsp" method="get">
		

<header> 
    <a class="logo" href="HomeAppl.jsp?action=Home">🎟 EventPass</a>
    <nav>
        <a href="HomeAppl.jsp?action=Home">Home</a>
        <a href="HomeLogIn.jsp#events">Events</a>
        <a href="UeberUns.jsp">Über uns</a>
         
      <% if(!myLogin.isLoggedIn()) {%>
			 <a href="LoginView.jsp">
            Login
        </a>
        
        <%} %>
         <%  
      String html="";
      if( myLogin.isLoggedIn()&&myLogin.getEmail()!=null&& myAccount.isAdmin( myLogin.getEmail()))
      {
     html+=" <a href='AdminView.jsp'>Zum Adminbereich</a>";
     MyEvent.resetEvent();
      }
      if (myLogin.isLoggedIn()){
    	  html+=" <a href='Warenkorb.jsp'>Warenkorb</a>";
    	html+="<a href='Favoriten.jsp'>  ❤️ Meine Favoriten </a>";
    	html+="<a href='MeineBuchungen.jsp'>Meine Buchungen</a>";
        html+="<a>Angemeldet als : " +myLogin.getEmail() +"</a>";
      html+= "<input type='submit' name='Logout' value='Logout' >";

      } %>
      <%=html %>
       
    </nav>
</header>
  </form>
<div class="container">
<div class="card start-card">
        <h1>Deine Tickets. Deine Erlebnisse.</h1>
        <p>Entdecke Veranstaltungen und sichere dir deine Tickets.</p>

<p>
      <form action="HomeAppl.jsp" method="get">
    <input type="text" name="search"
           placeholder="Nach Veranstaltungen suchen...">

    <button class="btn" type="submit" name="suchen" value="Suchen">
        Suchen
    </button>
</form>
    </div>

    <h2 id="events">Beliebte Veranstaltungen</h2>

    <jsp:getProperty name="MyEvent" property="HTMLFromEvent" />

</div>
<footer>© 2026 EventPass</footer>

</body>
</html>