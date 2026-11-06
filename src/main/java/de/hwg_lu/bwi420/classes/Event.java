package de.hwg_lu.bwi420.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.hwg_lu.bwi.jdbc.PostgreSQLAccess;

public class Event {
	int eventId;
	String eventName;
	String eventType;
	Date eventDatum;
	String eventOrt;
	String eventBeschreibung;
	double preis;
	int Ticketbestand;

	Connection dbConn;

	public Event(int eventId, String eventName, String eventType, Date eventDatum, String eventOrt,
			String eventBeschreibung, double preis, int ticketbestand) throws ClassNotFoundException, SQLException {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventType = eventType;
		this.eventDatum = eventDatum;
		this.eventOrt = eventOrt;
		this.eventBeschreibung = eventBeschreibung;
		this.preis = preis;
		Ticketbestand = ticketbestand;
		this.dbConn = new PostgreSQLAccess().getConnection();

	}

	// ein zufälliges Bild auswählen
	public String bild() {
		int bildNummer = ((eventId - 1) % 3) + 1;

		String bild = "";

		if (eventType.equalsIgnoreCase("Konzert")) {
			bild = "Konzert" + bildNummer + ".jpg";
		} else if (eventType.equalsIgnoreCase("Party")) {
			bild = "Party" + bildNummer + ".jpg";
		} else if (eventType.equalsIgnoreCase("Workshop")) {
			bild = "Workshop" + bildNummer + ".jpg";
		} else if (eventType.equalsIgnoreCase("Festival")) {
			bild = "Festival" + bildNummer + ".jpg";
		} else {
			bild = "Event" + bildNummer + ".gif";
		}
		return bild;
	}

	// Angeige des einzelnen Events
	public String toKaestchen() {
		String ausverkauf = "";
        String typebotton ="submit";
		String html = "";
		html += "<div class='event-card' style=\""
				+ "background-image: linear-gradient(rgba(20,10,30,0.58), rgba(20,10,30,0.58)), " + "url('../img/"
				+ bild() + "');" + "background-size: cover;" + "background-position: center;"
				+ "background-repeat: no-repeat;" + "color: white;" + "\">";

		html += "<span class='category'>" + this.eventType + "</span>";
		if (this.Ticketbestand == 0) {
			ausverkauf = "Ausverkauf";
			 typebotton ="button";
			html += "<span class='category'>" + ausverkauf + "</span>";
		}
		
		html += " <h1>" + this.eventName + "</h1>";
		html += "<p>📅 " + this.eventDatum + " </p>";
		html += "<p>📍 " + this.eventOrt + " </p>";
		html += "<p><strong>Ab " + this.preis + " €</strong></p>";
		html += " <form action='HomeAppl.jsp' method='get'>";
		html += "<input type='hidden'   name='eventId' value='" + this.eventId + "'>";
        
		html += "<input type='"+typebotton+"' class='btn'  name='Ticketsansehen' value='Tickets ansehen'>";
		
		html += "</form>";
		html += "<a class='btn' " + "href='FavoritAppl.jsp?action=add&eventId=" + eventId + "'>"
				+ "❤️ Zu Favoriten hinzufügen" + "</a>";
		html += "</div>";
		return html;

	}

//Anzeige von Event mit Ihrer Beschreibung
	public String toKaestchenMitBeschreibung() {
		String html = "";

		double vipPreis = this.preis * 2;

		html += "<div class='event-card' style=\""
				+ "background-image: linear-gradient(rgba(20,10,30,0.58), rgba(20,10,30,0.58)), " + "url('../img/"
				+ bild() + "');" + "background-size: cover;" + "background-position: center;"
				+ "background-repeat: no-repeat;" + "color: white;" + "\">";

		html += " <span class='category'>" + this.eventType + "</span>";
		html += " <h1>" + this.eventName + "</h1>";
		html += " <p>📅 " + this.eventDatum + "</p>";
		html += " <p>📍 " + this.eventOrt + "</p>";
		html += " <p>" + this.eventBeschreibung + "</p>";

		html += "</div>";

		html += "<h2>Tickets auswählen</h2>";

		// =========================
		// EIN GEMEINSAMES FORMULAR
		// =========================

		html += "<form action='EventDetailAppl.jsp' method='get'>";

		html += "<input type='hidden' name='eventId' value='" + this.eventId + "'>";

		// =========================
		// STANDARD
		// =========================

		html += "<div class='card'>";

		html += " <h3>🎟 Standard</h3>";
		html += " <p>Preis: " + this.preis + " €</p>";
		html += " <p>" + this.Ticketbestand + " Tickets verfügbar</p>";

		html += " <label for='standard'>Anzahl Standard</label>";

		html += " <input type='number' " + "id='standard' " + "name='standardQuantity' " + "value='0' " + "min='0' "
				+ "max='" + this.Ticketbestand + "'>";

		html += "</div>";

		// =========================
		// VIP
		// =========================

		html += "<div class='card'>";

		html += " <h3>⭐ VIP</h3>";
		html += " <p>Preis: " + vipPreis + " €</p>";
		html += " <p>" + this.Ticketbestand + " Tickets verfügbar</p>";

		html += " <label for='vip'>Anzahl VIP</label>";

		html += " <input type='number' " + "id='vip' " + "name='vipQuantity' " + "value='0' " + "min='0' " + "max='"
				+ this.Ticketbestand + "'>";

		html += "</div>";

		html += "<input type='submit' " + "class='btn' " + "name='InWarenkorb' " + "value='In den Warenkorb'>";

		html += "</form>";

		return html;
	}

	public String EventAdmin() {
		String html = "";

		html += "<tr>";
		html += " <td>" + this.eventName + "</td>";
		html += " <td>" + this.eventDatum + "</ptd>";
		html += "<td>" + this.eventOrt + "</td>";
		html += "<td>" + this.preis + " €</td>";
		html += "<td>" + this.Ticketbestand + " Tickets</td>";
		html += " <td>";

		html += "  <a href='AdminAppl.jsp?action=Bearbeiten&eventId=" + this.eventId + "'>Bearbeiten</a> ";
		html += "  <a href='AdminAppl.jsp?action=Löschen&eventId=" + this.eventId + "'>Löschen</a> ";
		html += " </td>";
		html += "</tr>";
		return html;
	}

	public String toWarenkorb() {

		String html = "";

		html += "<div  class='card'>";
		html += " <span class='category'>" + this.eventType + "</span>";
		html += " <h1>" + this.eventName + "</h1>";
		html += " <p>📅 " + this.eventDatum + "</p>";
		html += "<p>📍 " + this.eventOrt + "</p>";
		html += "<p>" + this.eventBeschreibung + "</p>";
		html += "</div>";

		return html;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventDatum() {
		return eventDatum;
	}

	public void setEventDatum(Date eventDatum) {
		this.eventDatum = eventDatum;
	}

	public String getEventOrt() {
		return eventOrt;
	}

	public void setEventOrt(String eventOrt) {
		this.eventOrt = eventOrt;
	}

	public String getEventBeschreibung() {
		return eventBeschreibung;
	}

	public void setEventBeschreibung(String eventBeschreibung) {
		this.eventBeschreibung = eventBeschreibung;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getTicketbestand() {
		return Ticketbestand;
	}

	public void setTicketbestand(int ticketbestand) {
		Ticketbestand = ticketbestand;
	}

}
