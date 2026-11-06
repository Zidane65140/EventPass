
<%@page import="java.sql.SQLException"%>
<%@page import="de.hwg_lu.bwi420.beans.AccountBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="myAccount"
		class="de.hwg_lu.bwi420.beans.AccountBean" scope="session" />

	
<%
	
String vorname      = request.getParameter("vorname");
String nachname      = request.getParameter("nachname");
String password    = request.getParameter("password");
String email       = request.getParameter("email");
String btnRegister = request.getParameter("btnReg");
 

if (btnRegister == null) btnRegister = "";



 if(btnRegister.equals("Registrieren")) {
	
	myAccount.setVorname(vorname);
	myAccount.setNachname(nachname);
	myAccount.setPassword(password);
	
	myAccount.setActive("N");
	myAccount.setAdmin("N");
	myAccount.setEmail(email);

	try {
 
	    if (myAccount.emailExistiert()) {

	        response.sendRedirect("./RegView.jsp?fehler=email");
	        return;

	    } else {

	        myAccount.insertAccountNoCheck();

	        response.sendRedirect("./LoginView.jsp");
	        return;
	    }

	} catch (SQLException e) {

	    e.printStackTrace();
	    response.sendRedirect("./RegView.jsp");
	    return;
	}
 }
%>
</body>
</html>