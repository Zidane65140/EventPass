<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Details zur Event- EventPass</title>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>
<body>
	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />
<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean" scope="session" />
<form action="LoginAppl.jsp" method="get">
<header>
 
    <a class="logo" href="HomeAppl.jsp?action=Home">🎟 EventPass</a>
    <nav>
        <a href="HomeAppl.jsp?action=Home">Home</a>
        <% if(!myLogin.isLoggedIn()) {%>
			 <a href="LoginView.jsp">
            Login
        </a> 
        <%} %>
         <a href="Favoriten.jsp">
            ❤️ Meine Favoriten
        </a>
        		  <%  
      String html="";
      if (myLogin.isLoggedIn()){ 
    		html+="<a href='MeineBuchungen.jsp'>Meine Buchungen</a>";
    	 html+=" <a href='Warenkorb.jsp'>Warenkorb</a>";
    	   html+= "<input type='submit' name='Logout' value='Logout' >";
    	
      }
      %>
      <%=html %>
    </nav>
    
</header>
 </form>
<div class="container">
 <a href="HomeAppl.jsp?action=Home"  style="color: white; font-weight: bold; text-decoration: underline;">← Zurück zur Übersicht</a>
<jsp:getProperty name="MyEvent" property="HTMLFromEventdetail" />


  

</div>
</body>
</html>