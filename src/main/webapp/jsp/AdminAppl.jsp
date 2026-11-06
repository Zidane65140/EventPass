<%@page import="org.apache.catalina.manager.DummyProxySession"%>
<%@page import="java.sql.Date"%>
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
	<jsp:useBean id="myAccount" class="de.hwg_lu.bwi420.beans.AccountBean"
		scope="session" />

	<%
	String eventName = request.getParameter("title");
	String eventType = request.getParameter("category");
	String eventDatumString = request.getParameter("date");
	String eventOrt = request.getParameter("location");
	String eventBeschreibung = request.getParameter("description");
	String preisString = request.getParameter("price");
	String standardBestandString = request.getParameter("standardBestand");
	String vipBestandString = request.getParameter("vipBestand");
	String speichern = request.getParameter("EventSpeichern");
	String eventIdString = request.getParameter("eventId");
	String action = request.getParameter("action");
	if (speichern == null)
		speichern = "";
	if (action == null)
		action = "";
	double preis = 0;
	int standardBestand = 0;
	int vipBestand = 0;
	int ticketbestand = 0;
	Date eventDatum = null;
	int eventId = 0;

	if (eventIdString != null && !eventIdString.equals("")) {
		eventId = Integer.parseInt(eventIdString);
	}

	if (myLogin.isLoggedIn() &&  myAccount.isAdmin( myLogin.getEmail()))
	{

		if (speichern.equals("Event speichern")) {
			preis = Double.parseDouble(preisString);

			standardBestand = Integer.parseInt(standardBestandString);
			vipBestand = Integer.parseInt(vipBestandString);
			ticketbestand = vipBestand + standardBestand;
			if ((eventDatumString != null) && !eventDatumString.isEmpty()) {

		eventDatum = java.sql.Date.valueOf(eventDatumString);
			}
			if (eventId > 0) {

		// Bearbeiten
		MyEvent.setEventId(eventId);
		MyEvent.setEventName(eventName);
		MyEvent.setEventType(eventType);
		MyEvent.setEventDatum(eventDatum);
		MyEvent.setEventOrt(eventOrt);
		MyEvent.setEventBeschreibung(eventBeschreibung);
		MyEvent.setPreis(preis);
		MyEvent.setStandardBestand(standardBestand);
		MyEvent.setVipBestand(vipBestand);
		MyEvent.setTicketbestand(ticketbestand);
		MyEvent.updateEvent();

			} else {

		// Neues Event
		MyEvent.setEventName(eventName);
		MyEvent.setEventType(eventType);
		MyEvent.setEventDatum(eventDatum);
		MyEvent.setEventOrt(eventOrt);
		MyEvent.setEventBeschreibung(eventBeschreibung);
		MyEvent.setPreis(preis);
		MyEvent.setStandardBestand(standardBestand);
		MyEvent.setVipBestand(vipBestand);
		MyEvent.setTicketbestand(ticketbestand);

		MyEvent.insertEventNoCheck();
			}
			MyEvent.getEventFromDB();
			MyEvent.resetEvent();
			response.sendRedirect("./AdminView.jsp");
		} else if (action.equals("Löschen")) {

			eventId = Integer.parseInt(eventIdString);
			MyEvent.dropEvent(eventId);
			MyEvent.getEventFromDB();
			MyEvent.resetEvent();

			response.sendRedirect("./AdminView.jsp");
		} else if (action.equals("Bearbeiten")) {

			MyEvent.getEventForEdit(eventId);

			response.sendRedirect("./AdminView.jsp");
		}  else if (action.equals("AdminMachen")) {

		    String email = request.getParameter("email");

		    if (email != null && !email.equals("")) {
		        myAccount.makeAdmin(email);
		    }

		    response.sendRedirect("./AdminView.jsp");

		} else if (action.equals("AdminEntfernen")) {
			
		    String email = request.getParameter("email");

		    if (email != null && !email.equals("")) {
		        myAccount.removeAdmin(email);
		    }

		    response.sendRedirect("./AdminView.jsp");

		} else if (action.equals("Benutzerentfernen")) {
			
		    String email = request.getParameter("email");
		    System.out.println(myLogin.getEmail());
		    if (email != null && !email.equals("")) {
			    System.out.println(12);

		        myAccount.drop(email);
		        }
		    response.sendRedirect("./AdminView.jsp");

		    }  else {

			response.sendRedirect("./AdminView.jsp");
		
		}} else {

		response.sendRedirect("./LoginView.jsp");
	}
	%>
</body>
</html>