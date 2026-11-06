<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminBereich- EventPass</title>
<link type="text/css" rel="stylesheet" href="../css/BWI420.css">
</head>
<body>
	<jsp:useBean id="MyEvent" class="de.hwg_lu.bwi420.beans.EventPassBean"
		scope="session" />
	<jsp:useBean id="myLogin" class="de.hwg_lu.bwi420.beans.LoginBean"
		scope="session" />
	<jsp:useBean id="myAccount" class="de.hwg_lu.bwi420.beans.AccountBean"
		scope="session" />
	<%
	String bearbeitenID = request.getParameter("eventId");

	if (bearbeitenID != null && !bearbeitenID.equals("")) {
		int eventId = Integer.parseInt(bearbeitenID);
		MyEvent.getEventForEdit(eventId);
	}
	%>
	<form action="LoginAppl.jsp" method="get">
		<header>
			<a class="logo" href="HomeAppl.jsp?action=Home">🎟 EventPass
				Admin</a>
			<nav>
				<a href="HomeAppl.jsp?action=Home">Shop ansehen</a>

				<%
				if (!myLogin.isLoggedIn()) {
				%>
				<a href="LoginView.jsp"> Login </a>
				<%
				}
				%>
				<a href="UeberUns.jsp">Über uns</a>
				<%
				String html = "";
				if (myLogin.isLoggedIn()) {
					html += "<a>Angemeldet als : " + myLogin.getEmail() + "</a>";
					html += "<input type='submit' name='Logout' value='Logout' >";

				}
				%>
				<%=html%>

			</nav>
		</header>
	</form>
	<form action="AdminAppl.jsp" method="post">
		<div class="container">

			<h1>Admin-Dashboard</h1>
			<p>Hier kannst du deine Veranstaltungen verwalten.</p>

			<div class="card">
				<h2>Neue Veranstaltung hinzufügen</h2>



				<label for="title">Veranstaltungstitel</label> <input type="text"
					id="title" name="title" value="<%=MyEvent.getEventName()%>"
					required> <label for="category">Kategorie</label> <select
					id="category" name="category">
					<option><%=MyEvent.getEventType()%></option>
					<option>Konzert</option>
					<option>Party</option>
					<option>Workshop</option>
					<option>Festival</option>
					<option>Event</option>
				</select> <label for="date">Datum</label> <input type="date" id="date"
					name="date" value="<%=MyEvent.getEventDatum()%>" required>
				<label for="location">Ort</label> <input type="text" id="location"
					name="location" value="<%=MyEvent.getEventOrt()%>" required>
				<label for="description">Beschreibung</label>
				<textarea id="description" name="description"><%=MyEvent.getEventBeschreibung()%></textarea>

				<label for="price">Ticketpreis in Euro</label> <input type="number"
					id="price" name="price" value="<%=MyEvent.getPreis()%>" min="0"
					step="0.01" required> <label for="quantity">Ticketbestand</label>
				<label for="standardBestand">Standard-Tickets</label> <input
					type="number" id="standardBestand" name="standardBestand"
					value="<%=MyEvent.getStandardBestand()%>" min="0" required>

				<label for="vipBestand">VIP-Tickets</label> <input type="number"
					id="vipBestand" name="vipBestand"
					value="<%=MyEvent.getVipBestand()%>" min="0" required> <input
					type="submit" name="EventSpeichern" class="btn"
					value="Event speichern"> <input type="hidden"
					name="eventId" value="<%=MyEvent.getEventId()%>">

			</div>

			<%
			if (myLogin.isLoggedIn()) {
			%>	

			<div class="card">
			<h2>Veranstaltungen verwalten</h2>
				<table>
					<thead>
						<tr>
							<th>Event</th>
							<th>Datum</th>
							<th>Ort</th>
							<th>Preis</th>
							<th>Ticketbestand</th>
							<th>Aktionen</th>
						</tr>
					</thead>
					<jsp:getProperty name="MyEvent" property="HTMLFromEventAdmin" />


				</table>
			</div>

			<div class="card">
				<h2>Benutzer verwalten</h2>
				<table>

					<thead>

						<tr>
							<th>Benutzer</th>
							<th>E-Mail</th>
							<th>Status</th>
							<th>Rolle</th>
							<th>Aktion</th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<%=myAccount.getHTMLFromAccounts()%>

					</tbody>

				</table>

			</div>


			<div class="card">

				<%=MyEvent.getHTMLFromBookingsForAdmin()%>

			</div>

			<%
			}
			%>
		</div>
	</form>
</body>
</html>