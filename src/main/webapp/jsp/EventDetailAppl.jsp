<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
	scope="session" />

<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
	scope="session" />

<%
String eventIdString = request.getParameter("eventId");
String standardQuantityString = request.getParameter("standardQuantity");

String vipQuantityString = request.getParameter("vipQuantity");

String inWarenkorb = request.getParameter("InWarenkorb");
if (inWarenkorb == null) {
	inWarenkorb = request.getParameter("inWarenkorb");
}

if (inWarenkorb == null) {
	inWarenkorb = "";
}

/* =========================
   EVENT-ID PRÜFEN
   ========================= */

if (eventIdString == null || eventIdString.equals("")) {

	response.sendRedirect("./HomeLogIn.jsp");
	return;
}

int eventId = Integer.parseInt(eventIdString);

/* =========================
   MENGEN
   ========================= */

int standardQuantity = 0;
int vipQuantity = 0;

if (standardQuantityString != null && !standardQuantityString.equals("")) {

	standardQuantity = Integer.parseInt(standardQuantityString);
}

if (vipQuantityString != null && !vipQuantityString.equals("")) {

	vipQuantity = Integer.parseInt(vipQuantityString);
}

/* =========================
   MINDESTENS EIN TICKET
   ========================= */

if (standardQuantity <= 0 && vipQuantity <= 0) {

	response.sendRedirect("./EventDetails.jsp?EventDetailId=" + eventId);
	return;
}

/* =========================
   EVENT AUS DB LADEN
   ========================= */

MyEvent.prüfEventdetailId(eventId);

/* =========================
   PREISE
   ========================= */

double standardPreis = MyEvent.getPreis();

double vipPreis = standardPreis * 2;

/* =========================
   GESAMTPREIS
   ========================= */

double gesamtpreis = (standardQuantity * standardPreis) + (vipQuantity * vipPreis);

/* =========================
   LOGIN PRÜFEN
   ========================= */

if (!myLogin.isLoggedIn()) {

	response.sendRedirect("./LoginView.jsp?redirect=Warenkorb" + "&eventId=" + eventId + "&standardQuantity="
	+ standardQuantity + "&vipQuantity=" + vipQuantity);

	return;
}

/* =========================
   IN DEN WARENKORB
   ========================= */

if (inWarenkorb.equals("In den Warenkorb")) {
	MyEvent.prüfEventdetailId(eventId);

	String eventName = MyEvent.getEventName();

	if (standardQuantity <= MyEvent.getStandardBestand() && vipQuantity <= MyEvent.getVipBestand()) {

		MyEvent.addToWarenkorb(eventId, standardQuantity, vipQuantity, standardPreis, vipPreis, gesamtpreis, eventName);

		response.sendRedirect("./Warenkorb.jsp");
		return;

	} else {

		response.sendRedirect("./EventDetails.jsp?EventDetailId=" + eventId);
		return;
	}
}
%>