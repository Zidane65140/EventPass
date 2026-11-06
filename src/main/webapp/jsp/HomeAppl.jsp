<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />

	<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
		scope="session" />

	<%
	String eventIdString = request.getParameter("eventId");
	String btnAnsehen = request.getParameter("Ticketsansehen");
	String action = request.getParameter("action");
	String suchen = request.getParameter("suchen");
	String search = request.getParameter("search");
	
	if (btnAnsehen == null)
		btnAnsehen = "";
	if (action == null)
		action = "";
	if (suchen == null)
		suchen = "";

	int eventId = 0;

	if (eventIdString != null) {
		try {
			eventId = Integer.parseInt(eventIdString);

			if (btnAnsehen.equals("Tickets ansehen")) {
				MyEvent.getEventFromDB();
		MyEvent.prüfEventdetailId(eventId);

		response.sendRedirect("./EventDetails.jsp");
			} else {

		response.sendRedirect("./EventDetails.jsp");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			response.sendRedirect("./EventDetails.jsp");
		}
	}

	else if (action.equals("Home")) {
		MyEvent.getEventFromDB();
		if (myLogin.isLoggedIn()) {
	
			response.sendRedirect("./HomeLogIn.jsp");
		} else {

			response.sendRedirect("./HomeLogIn.jsp");

		}
	} else if (suchen.equals("Suchen")) {

	    MyEvent.sucheEvent(search);

	    response.sendRedirect("./HomeLogIn.jsp");
	    
	} 
	else {

		response.sendRedirect("./HomeLogIn.jsp");
	}
	%>
</body>
</html>