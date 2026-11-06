<%@page
	import="org.apache.tomcat.jakartaee.commons.io.output.StringBuilderWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
	scope="session" />

<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
	scope="session" />

<%
String btnkasse = request.getParameter("zurkasse");
if (btnkasse == null)
	btnkasse = "";
if (btnkasse.equals("Zur Kasse")) {
	if (!myLogin.isLoggedIn()) {
		response.sendRedirect("./LoginView.jsp");
		return;
	}
	

	String email = myLogin.getEmail();

	try {
		int vipQuantity = 0;
		int standardQuantity = 0;
		for (Integer menge : MyEvent.getWarenkorbMengenVIP()) {
	vipQuantity += menge;
		}
		for (Integer menge : MyEvent.getWarenkorbMengenStandard()) {
	standardQuantity += menge;
		}
		if (standardQuantity <= MyEvent.getStandardBestand() && vipQuantity <= MyEvent.getVipBestand()) {
	MyEvent.saveWarenkorbBookings(email);
	MyEvent.getEventFromDB();
	response.sendRedirect("./BookingConfirmation.jsp");
	return;
		} else {
	String ticket = "";
	if (standardQuantity > MyEvent.getStandardBestand())
		ticket = "Standard Tickets";
	else if (vipQuantity > MyEvent.getVipBestand())
		ticket = "VIP Tickets";
	if (standardQuantity + vipQuantity > MyEvent.getTicketbestand())
		ticket = "Tickets";
	out.println("<script>");
	out.println(
			"alert('Nicht genügend " + ticket + " vorhanden. Bitte prüfen sie nochmal die Anzahl an Ticket');");

	out.println("history.back();");

	out.println("</script>");
		}

	} catch (Exception e) {

		out.println("<h2>Fehler bei der Buchung</h2>");
		out.println("  <a href='HomeLogIn.jsp'>zurück zur Startseite</a>");
		out.println("<p>" + e.getMessage() + "</p>");
	}
}
%>