<%@page import="de.hwg_lu.bwi420.beans.LoginBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
		scope="session" />
	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />
	<jsp:useBean id="myAccount" class="de.hwg_lu.bwi420.beans.AccountBean"
		scope="session" />
	<%
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String btnLogin = request.getParameter("btnLog");
	String btnLogOut = request.getParameter("Logout");
	
	if (btnLogin == null)
		btnLogin = "";
	if (btnLogOut == null)
		btnLogOut = "";

	if (btnLogin.equals("Login")) {
		myLogin.setEmail(email);
		myLogin.setPassword(password);
		try {
			boolean pwOk = myLogin.checkUseridPassword();
			if (pwOk) {
				
		myAccount.setLogmail(email);
		myLogin.Active("J");
		myLogin.setLoggedIn(true);
		if (myAccount.isAdmin(myLogin.getEmail())) {
			
			MyEvent.resetEvent();
			response.sendRedirect("./AdminView.jsp");

		} else {
		
			response.sendRedirect("./HomeLogIn.jsp");

		}

			} else {
		myLogin.setMessage("<Div style='color: red;'>" + "Die einegegebenen Daten stimmen nicht überrein" + "<br>"
				+ "Bitte versuchen Sie erneut" + "</Div>");

		response.sendRedirect("./LoginView.jsp");
			}
		} catch (Exception e) {

			e.printStackTrace();
			myLogin.Active("N");
			response.sendRedirect("./LoginView.jsp");
		}

	} else if (btnLogOut.equals("Logout")) {
		myLogin.Active("N");
		myLogin.setLoggedIn(false);
		MyEvent.WarenkorbLeeren();
		MyEvent.resetEvent();
		myLogin.setMessage("Bitte melde dich in deinem Konto an.");
		response.sendRedirect("./LoginView.jsp");
	} else {
		
		response.sendRedirect("./LoginView.jsp");
	}
	%>
</body>
</html>