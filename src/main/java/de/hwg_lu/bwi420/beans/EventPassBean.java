package de.hwg_lu.bwi420.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.hwg_lu.bwi.jdbc.PostgreSQLAccess;
import de.hwg_lu.bwi420.classes.Event;
import de.hwg_lu.bwi420.classes.Favorit;

public class EventPassBean {
	int CurrentId;
	int eventId;
	String eventName;
	String eventType;
	Date eventDatum;
	String eventOrt;
	String eventBeschreibung;
	double preis;
	int Ticketbestand;
	int standardBestand;
	int vipBestand;

	Event EventDetailId = null;

	Vector<String> warenkorbTickets;
	Vector<Integer> warenkorbEventIds;
	Vector<Integer> warenkorbMengenVIP;
	Vector<Integer> warenkorbMengenStandard;

	Vector<Double> warenkorbPreise;
	Vector<Double> warenkorbGesamtpreise;
	Vector<String> warenkorbEventName;

	Vector<Event> eventListe;
	Connection dbConn;

	String letzteBuchungsDetails = "";
	double letzteBuchungsGesamtpreis = 0.0;
	String letzteBuchungsNummer = "";

	
	
	
	public EventPassBean() throws ClassNotFoundException, SQLException {
		this.eventListe = new Vector<Event>();

		this.warenkorbTickets = new Vector<String>();
		this.warenkorbEventIds = new Vector<Integer>();

		this.warenkorbMengenVIP = new Vector<Integer>();
		this.warenkorbMengenStandard = new Vector<Integer>();
		this.warenkorbPreise = new Vector<Double>();
		this.warenkorbGesamtpreise = new Vector<Double>();
		this.warenkorbEventName = new Vector<String>();

		this.letzteBuchungsDetails = "";
		this.letzteBuchungsGesamtpreis = 0.0;
		this.letzteBuchungsNummer = "";

		this.dbConn = new PostgreSQLAccess().getConnection();
		this.eventName = "";
		this.eventType = "";
		this.eventOrt = "";
		this.eventBeschreibung = "";
		getEventFromDB();

		// TODO Auto-generated constructor stub
	}

	public void insertEventNoCheck() throws SQLException {
		// schreibe einen Datensatz in die Account-Tabelle
		// mit den Werten der Attribute userid, password, etc.
		String sql = """
				    insert into bwi420_639331.event
				        (eventName, eventType, eventDatum, eventOrt, preis,
				         eventBeschreibung, Ticketbestand, standardBestand, vipBestand)
				    values
				        (?,?,?,?,?,?,?,?,?)
				""";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, this.eventName);
		prep.setString(2, this.eventType);
		prep.setDate(3, this.eventDatum);
		prep.setString(4, this.eventOrt);
		prep.setDouble(5, this.preis);
		prep.setString(6, this.eventBeschreibung);
		prep.setInt(7, this.Ticketbestand);
		prep.setInt(8, this.standardBestand);
		prep.setInt(9, this.vipBestand);

		prep.executeUpdate();
		System.out.println("Event " + this.eventName + " angelegt");
	}

	// fügt die Einzelne Event in der Eventliste
	public void getEventFromDB() throws SQLException, ClassNotFoundException {
		this.eventListe.clear();
		String sql = "select eventId,eventName, eventType,eventDatum,eventOrt,eventBeschreibung,preis,Ticketbestand,standardBestand, vipBestand from bwi420_639331.event";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		ResultSet dbRes = prep.executeQuery();
		while (dbRes.next()) {
			this.eventListe.add(new Event(dbRes.getInt("eventId"), dbRes.getString("eventName"),
					dbRes.getString("eventType"), dbRes.getDate("eventDatum"), dbRes.getString("eventOrt"),
					dbRes.getString("eventBeschreibung"), dbRes.getDouble("preis"), dbRes.getInt("Ticketbestand")));
			this.standardBestand = dbRes.getInt("standardBestand");
			this.vipBestand = dbRes.getInt("vipBestand");
		}

	}

	public Event getEventDetailId() {
		return this.EventDetailId;
	}

// liest alle Event aus
	public String getHTMLFromEvent() {
		String html = "";
		html += "<div class='event-grid'>";

		for (Event currentEvent : this.eventListe) {

			html += currentEvent.toKaestchen();

		}
		html += "</div>";
		return html;
	}

	public String getHTMLFromEventAdmin() {
		String html = "";
		html += " <tbody>";

		for (Event currentEvent : this.eventListe) {

			html += currentEvent.EventAdmin();

		}
		html += "</tbody>";
		return html;
	}

	// Zeigt die Details eines Events
	public String getHTMLFromEventdetail() {

		if (this.EventDetailId == null) {
			return "";
		}

		double standardPreis = this.preis;
		double vipPreis = standardPreis * 2;

		String html = "";

		html += "<div class='event-detail-card'>";

		html += "<h1>" + this.EventDetailId.getEventName() + "</h1>";

		html += "<p><strong>Kategorie:</strong> " + this.EventDetailId.getEventType() + "</p>";

		html += "<p><strong>Datum:</strong> " + this.EventDetailId.getEventDatum() + "</p>";

		html += "<p><strong>Ort:</strong> " + this.EventDetailId.getEventOrt() + "</p>";

		html += "<p><strong>Beschreibung:</strong><br>" + this.EventDetailId.getEventBeschreibung() + "</p>";

		html += "<hr>";

		html += "<form action='EventDetailAppl.jsp' method='get'>";

		html += "<input type='hidden' name='eventId' value='" + this.EventDetailId.getEventId() + "'>";

		// menge prüfen
		String standard = "";
		String vip = "";
		if (this.standardBestand == 0) {
			standard = "ausverkauft";
		} else {
			standard = String.valueOf(this.standardBestand) + " Tickets";
		}
		if (this.vipBestand == 0) {
			vip = "ausverkauft";
		} else {
			vip = String.valueOf(this.vipBestand) + " Tickets";
		}

		// STANDARD
		html += "<div class='ticket-option'>";

		html += "<h2>Standard-Ticket</h2>";

		html += "<p><strong>Preis:</strong> " + String.format("%.2f", standardPreis) + " €</p>";

		html += "<p><strong>Verfügbar:</strong> " + standard + "</p>";

		html += "<label for='standardQuantity'>Anzahl:</label>";

		html += "<input type='number' " + "id='standardQuantity' " + "name='standardQuantity' " + "value='0' "
				+ "min='0' " + "max='" + this.standardBestand + "'>";

		html += "</div>";

		// VIP
		html += "<div class='ticket-option'>";

		html += "<h2>VIP-Ticket</h2>";

		html += "<p><strong>Preis:</strong> " + String.format("%.2f", vipPreis) + " €</p>";

		html += "<p><strong>Verfügbar:</strong> " + vip + "</p>";

		html += "<label for='vipQuantity'>Anzahl:</label>";

		html += "<input type='number' " + "id='vipQuantity' " + "name='vipQuantity' " + "value='0' " + "min='0' "
				+ "max='" + this.vipBestand + "'>";

		html += "</div>";

		html += "<br>";

		html += "<button class='btn' " + "type='submit' " + "name='inWarenkorb' " + "value='In den Warenkorb'>"
				+ "In den Warenkorb</button>";

		html += "</form>";

		html += "</div>";

		return html;
	}

	// prüf ob eien Event mikt dem ID existiert und speichert die Info wenn
	// vorhanden
	public void prüfEventdetailId(int Id) throws SQLException, ClassNotFoundException {

		String sql = "select eventId, eventName, eventType, eventDatum, eventOrt, "
				+ "eventBeschreibung, preis, Ticketbestand, standardBestand, vipBestand " + "from bwi420_639331.event "
				+ "where eventId = ?";

		System.out.println(sql);

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setInt(1, Id);

		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {

			this.EventDetailId = new Event(dbRes.getInt("eventId"), dbRes.getString("eventName"),
					dbRes.getString("eventType"), dbRes.getDate("eventDatum"), dbRes.getString("eventOrt"),
					dbRes.getString("eventBeschreibung"), dbRes.getDouble("preis"), dbRes.getInt("Ticketbestand"));

			this.preis = dbRes.getDouble("preis");
			this.Ticketbestand = dbRes.getInt("Ticketbestand");
			this.standardBestand = dbRes.getInt("standardBestand");
			this.vipBestand = dbRes.getInt("vipBestand");
			this.eventName = dbRes.getString("eventName");
			this.eventType = dbRes.getString("eventType");
		}
	}

// ein ticket zum warenkorb 
	public void addToWarenkorb(int eventId, int standardQuantity, int vipQuantity, double standardPreis,
			double vipPreis, double gesamtpreis, String EventName) {

		if (standardQuantity > 0) {
			this.warenkorbEventIds.add(eventId);
			this.warenkorbTickets.add("Standard");
			this.warenkorbMengenStandard.add(standardQuantity);
			this.warenkorbMengenVIP.add(0);
			this.warenkorbPreise.add(standardPreis);
			this.warenkorbGesamtpreise.add(standardQuantity * standardPreis);
			this.warenkorbEventName.add(EventName);
		}

		if (vipQuantity > 0) {
			this.warenkorbEventIds.add(eventId);
			this.warenkorbTickets.add("VIP");
			this.warenkorbMengenVIP.add(vipQuantity);
			this.warenkorbMengenStandard.add(0);
			this.warenkorbPreise.add(vipPreis);
			this.warenkorbGesamtpreise.add(vipQuantity * vipPreis);
			this.warenkorbEventName.add(EventName);
		}
	}

	// ein ticket bearbeiten
	public void updateEvent() throws SQLException {

		String sql = "update bwi420_639331.event set " + "eventName=?, " + "eventType=?, " + "eventDatum=?, "
				+ "eventOrt=?, " + "eventBeschreibung=?, " + "preis=?, " + "Ticketbestand=?, " + "standardBestand=?, "
				+ "vipBestand=? " + "where eventId=?";
		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, this.eventName);
		prep.setString(2, this.eventType);
		prep.setDate(3, this.eventDatum);
		prep.setString(4, this.eventOrt);
		prep.setString(5, this.eventBeschreibung);
		prep.setDouble(6, this.preis);
		prep.setInt(7, this.Ticketbestand);
		prep.setInt(8, this.standardBestand);
		prep.setInt(9, this.vipBestand);
		prep.setInt(10, this.eventId);

		prep.executeUpdate();

		System.out.println("Event mit ID " + this.eventId + " aktualisiert");
	}

	// eine event löschern
	public void dropEvent(int EventID) throws SQLException {
		String sql = "delete from bwi420_639331.event" + " where eventId=?";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setInt(1, EventID);
		prep.executeUpdate();
		System.out.println("Event mit ID  " + EventID + " geloescht");
	}

	// info zum Barbeiten eines Events 
	public void getEventForEdit(int Id) throws SQLException, ClassNotFoundException {

		String sql = "select eventId, eventName, eventType, eventDatum, eventOrt, "
				+ "eventBeschreibung, preis, Ticketbestand, standardBestand, vipBestand " + "from bwi420_639331.event"
				+ " where eventId = ?";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setInt(1, Id);
		;
		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {
			setEventId(dbRes.getInt("eventId"));
			setEventName(dbRes.getString("eventName"));
			setEventType(dbRes.getString("eventType"));
			setEventDatum(dbRes.getDate("eventDatum"));
			setEventOrt(dbRes.getString("eventOrt"));
			setEventBeschreibung(dbRes.getString("eventBeschreibung"));
			setPreis(dbRes.getDouble("preis"));
			setTicketbestand(dbRes.getInt("Ticketbestand"));
			setStandardBestand(dbRes.getInt("standardBestand"));
			setVipBestand(dbRes.getInt("vipBestand"));

		}

	}

	public void resetEvent() {
		this.eventId = 0;
		this.eventName = "";
		this.eventType = "";
		this.eventDatum = null;
		this.eventOrt = "";
		this.eventBeschreibung = "";
		this.preis = 0.0;
		this.Ticketbestand = 0;
		this.standardBestand = 0;
		this.vipBestand = 0;
	}

	// ein Event suchen
	public void sucheEvent(String search) throws SQLException, ClassNotFoundException {

		this.eventListe.clear();

		String sql = "SELECT * FROM bwi420_639331.event " + "WHERE LOWER(eventName) LIKE ? "
				+ "OR LOWER(eventType) LIKE ? " + "OR LOWER(eventOrt) LIKE ?";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		System.out.println(sql);
		String suchtext = "%" + search.toLowerCase() + "%";

		prep.setString(1, suchtext);
		prep.setString(2, suchtext);
		prep.setString(3, suchtext);

		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {

			Event meinEvent = new Event(eventId, eventName, eventType, eventDatum, eventOrt, eventBeschreibung, preis,
					Ticketbestand);

			meinEvent.setEventId(dbRes.getInt("eventId"));
			meinEvent.setEventName(dbRes.getString("eventName"));
			meinEvent.setEventType(dbRes.getString("eventType"));
			meinEvent.setEventDatum(dbRes.getDate("eventDatum"));
			meinEvent.setEventOrt(dbRes.getString("eventOrt"));
			meinEvent.setEventBeschreibung(dbRes.getString("eventBeschreibung"));
			meinEvent.setPreis(dbRes.getDouble("preis"));
			meinEvent.setTicketbestand(dbRes.getInt("Ticketbestand"));

			this.eventListe.add(meinEvent);
		}
	}

// Bestellung nach Email und nach Event anzeigen
	public String getHTMLFromBookingsForAdmin() throws SQLException {

		String html = "";

		/*
		 * ===================================================== 1. BESTELLUNGEN NACH
		 * E-MAIL =====================================================
		 */

		html += "<h2>Bestellungen nach Kunde</h2>";

		String sql = """
				    SELECT
				        b.email,
				        b.eventId,
				        e.eventName,
				        b.ticket,
				        b.quantity,
				        b.preis,
				        b.gesamtpreis,
				        b.bookingDatum
				    FROM bwi420_639331.booking b
				    LEFT JOIN bwi420_639331.event e
				        ON b.eventId = e.eventId
				    ORDER BY b.email, e.eventName, b.bookingDatum
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		ResultSet rs = prep.executeQuery();

		String aktuelleEmail = "";
		int anzahlBestellungen = 0;
		double gesamtKunde = 0.0;

		boolean ersteBestellung = true;

		while (rs.next()) {

			String email = rs.getString("email");

			/*
			 * Neue E-Mail
			 */
			if (!email.equals(aktuelleEmail)) {

				/*
				 * Vorherigen Kunden abschließen
				 */
				if (!ersteBestellung) {
					html += "<p><strong>Anzahl Bestellungen: " + anzahlBestellungen + "</strong></p>";

					html += "<p><strong>Gesamtbetrag: " + String.format("%.2f", gesamtKunde) + " €</strong></p>";

					html += "</div>";
				}

				aktuelleEmail = email;
				anzahlBestellungen = 0;
				gesamtKunde = 0.0;
				ersteBestellung = false;

				html += "<div class='admin-customer'>";

				html += "<h3>E-Mail: " + email + "</h3>";
			}

			/*
			 * Bestellung
			 */
			int eventId = rs.getInt("eventId");
			String eventName = rs.getString("eventName");
			String ticket = rs.getString("ticket");
			int quantity = rs.getInt("quantity");
			double preis = rs.getDouble("preis");
			double gesamtpreis = rs.getDouble("gesamtpreis");

			anzahlBestellungen++;
			gesamtKunde += gesamtpreis;

			html += "<div class='admin-order'>";

			html += "<p><strong>Event:</strong> " + eventName + "</p>";

			html += "<p><strong>Event-ID:</strong> " + eventId + "</p>";

			html += "<p><strong>Ticket:</strong> " + ticket + "</p>";

			html += "<p><strong>Anzahl Tickets:</strong> " + quantity + "</p>";

			html += "<p><strong>Preis pro Ticket:</strong> " + String.format("%.2f", preis) + " €</p>";

			html += "<p><strong>Gesamt:</strong> " + String.format("%.2f", gesamtpreis) + " €</p>";

			html += "<hr>";

			html += "</div>";
		}

		/*
		 * Letzten Kunden abschließen
		 */
		if (!ersteBestellung) {

			html += "<p><strong>Anzahl Bestellungen: " + anzahlBestellungen + "</strong></p>";

			html += "<p><strong>Gesamtbetrag: " + String.format("%.2f", gesamtKunde) + " €</strong></p>";

			html += "</div>";
		}

		/*
		 * ===================================================== 2. BESTELLUNGEN NACH
		 * EVENT =====================================================
		 */

		html += "<hr>";
		html += "<h2>Anzahl Bestellungen pro Event</h2>";

		String sqlEvent = """
				    SELECT
				        b.eventId,
				        e.eventName,
				        COUNT(b.bookingId) AS anzahlBestellungen,
				        SUM(b.quantity) AS anzahlTickets,
				        SUM(b.gesamtpreis) AS umsatz
				    FROM bwi420_639331.booking b
				    LEFT JOIN bwi420_639331.event e
				        ON b.eventId = e.eventId
				    GROUP BY b.eventId, e.eventName
				    ORDER BY e.eventName
				""";

		PreparedStatement prepEvent = this.dbConn.prepareStatement(sqlEvent);

		ResultSet rsEvent = prepEvent.executeQuery();

		while (rsEvent.next()) {

			int eventId = rsEvent.getInt("eventId");
			String eventName = rsEvent.getString("eventName");

			int anzahlBestellung = rsEvent.getInt("anzahlBestellungen");

			int anzahlTickets = rsEvent.getInt("anzahlTickets");

			double umsatz = rsEvent.getDouble("umsatz");

			html += "<div class='admin-event'>";

			html += "<h2>" + eventName + "</h2>";

			html += "<p><strong>Event-ID:</strong> " + eventId + "</p>";

			html += "<p><strong>Anzahl Bestellungen:</strong> " + anzahlBestellung + "</p>";

			html += "<p><strong>Verkaufte Tickets:</strong> " + anzahlTickets + "</p>";

			html += "<p><strong>Umsatz:</strong> " + String.format("%.2f", umsatz) + " €</p>";

			html += "</div>";
		}

		return html;
	}

// Zeigt den ausgewählen Event
	public String getHTMLFromWarenkorb() {

		if (warenkorbEventIds.isEmpty()) {
			return "";
		}

		String html = "";

		double gesamt = 0.0;

		html += "<div class='cart-container'>";

		for (int i = 0; i < warenkorbEventIds.size(); i++) {

			String eventName = warenkorbEventName.get(i);
			String ticket = warenkorbTickets.get(i);
			int menge = 0;
			if (ticket == "VIP") {
				menge = warenkorbMengenVIP.get(i);
			} else if (ticket == "Standard") {
				menge = warenkorbMengenStandard.get(i);
			}

			double preis = warenkorbPreise.get(i);
			double gesamtpreis = warenkorbGesamtpreise.get(i);

			gesamt += gesamtpreis;

			html += "<div class='cart-item'>";

			html += "<h3>Event: " + eventName + "</h3>";

			html += "<p><strong>Ticket:</strong> " + ticket + "</p>";

			html += "<p><strong>Anzahl:</strong> " + menge + "</p>";

			html += "<p><strong>Preis pro Ticket:</strong> " + String.format("%.2f", preis) + " €</p>";

			html += "<p><strong>Preis:</strong> " + String.format("%.2f", gesamtpreis) + " €</p>";

			html += "<a class='btn' " + "href='WarenkorbAppl.jsp?action=entfernen&index=" + i + "'>" + "Entfernen"
					+ "</a>";

			html += "</div>";
		}

		html += "<hr>";

		html += "<h2>Gesamtpreis: " + String.format("%.2f", gesamt) + " €</h2>";

		html += "</div>";

		return html;
	}

// eine Booking in die Datenbank schreiben
	public void insertBooking(int eventId, String email, String ticket, int quantity, double preis, double gesamtpreis)
			throws SQLException {

		String sql = """
				   insert into bwi420_639331.booking
				   (eventId, email, ticket, quantity, preis, gesamtpreis, bookingDatum)
				   values (?, ?, ?, ?, ?, ?, current_timestamp)
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setInt(1, eventId);
		prep.setString(2, email);
		prep.setString(3, ticket);
		prep.setInt(4, quantity);
		prep.setDouble(5, preis);
		prep.setDouble(6, gesamtpreis);

		prep.executeUpdate();

		System.out.println("Buchung gespeichert");
	}

// Update nach dem kauf eines ticket
	public void updateTicketbestand(int eventId, String ticket, int menge) throws SQLException {

		String sql = "";

		if (ticket.equals("Standard")) {

			sql = """
					    UPDATE bwi420_639331.event
					    SET standardBestand = standardBestand - ?,
					        Ticketbestand = Ticketbestand - ?
					    WHERE eventId = ?
					    AND standardBestand >= ?
					""";

		} else if (ticket.equals("VIP")) {

			sql = """
					    UPDATE bwi420_639331.event
					    SET vipBestand = vipBestand - ?,
					        Ticketbestand = Ticketbestand - ?
					    WHERE eventId = ?
					    AND vipBestand >= ?
					""";
		}

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setInt(1, menge);
		prep.setInt(2, menge);
		prep.setInt(3, eventId);
		prep.setInt(4, menge);

		int rows = prep.executeUpdate();

		if (rows == 0) {
			throw new SQLException("Nicht genügend " + ticket + "-Tickets vorhanden.");
		}
	}

	// Bestellung abschließen
	public void saveWarenkorbBookings(String email) throws SQLException {

		double gesamt = 0.0;

		String details = "";

		for (int i = 0; i < warenkorbEventIds.size(); i++) {

			int eventId = warenkorbEventIds.get(i);
			String ticket = warenkorbTickets.get(i);
			int menge;
			if (ticket == "VIP") {
				menge = warenkorbMengenVIP.get(i);
			} else {
				menge = warenkorbMengenStandard.get(i);
			}
			double preis = warenkorbPreise.get(i);
			double gesamtpreis = warenkorbGesamtpreise.get(i);

			// In Datenbank speichern
			this.insertBooking(eventId, email, ticket, menge, preis, gesamtpreis);
			updateTicketbestand(eventId, ticket, menge);
			// Gesamtpreis berechnen
			gesamt += gesamtpreis;

			// Details für Bestätigungsseite
			details += "<p>";
			details += "<strong>Ticket:</strong> " + ticket + "<br>";
			details += "<strong>Anzahl:</strong> " + menge + "<br>";
			details += "<strong>Preis pro Ticket:</strong> " + String.format("%.2f", preis) + " €<br>";
			details += "<strong>Preis:</strong> " + String.format("%.2f", gesamtpreis) + " €";
			details += "</p>";

			details += "<hr>";
		}

		// Daten für BookingConfirmation speichern
		this.letzteBuchungsDetails = details;
		this.letzteBuchungsGesamtpreis = gesamt;

		this.letzteBuchungsNummer = "EP-" + System.currentTimeMillis();

		// Warenkorb leeren
		this.WarenkorbLeeren();
	}

	public void WarenkorbLeeren() {
		warenkorbEventIds.clear();
		warenkorbTickets.clear();
		warenkorbMengenVIP.clear();
		warenkorbMengenStandard.clear();
		warenkorbPreise.clear();
		warenkorbGesamtpreise.clear();
		this.warenkorbEventName.clear();
	}

	public void warenkorbArtikelEntfernen(int index) {
		warenkorbEventIds.remove(index);
		warenkorbTickets.remove(index);

		warenkorbMengenVIP.remove(index);

		warenkorbMengenStandard.remove(index);
		warenkorbPreise.remove(index);
		warenkorbGesamtpreise.remove(index);
		warenkorbEventName.remove(index);
	}

	public void insertFavorit(String email, int eventId) throws SQLException {

		String sql = """

				insert into bwi420_639331.favorit
				(email, eventId)
				values (?, ?)
				on conflict (email, eventId) do nothing

				""";

		System.out.println(sql);

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);
		prep.setInt(2, eventId);

		prep.executeUpdate();

		prep.close();

		System.out.println("Favorit gespeichert");
	}

	public void deleteFavorit(String email, int eventId) throws SQLException {

		String sql = """

				delete from bwi420_639331.favorit
				where email = ?
				and eventId = ?

				""";

		System.out.println(sql);

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);
		prep.setInt(2, eventId);

		prep.executeUpdate();

		prep.close();

		System.out.println("Favorit gelöscht");
	}

	// Prüft, ob ein bestimmtes Event bereits Favorit ist.
	public boolean isFavorit(String email, int eventId) throws SQLException {

		String sql = """

				select favoritId
				from bwi420_639331.favorit
				where email = ?
				and eventId = ?

				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);
		prep.setInt(2, eventId);

		ResultSet dbRes = prep.executeQuery();

		boolean gefunden = dbRes.next();

		dbRes.close();
		prep.close();

		return gefunden;
	}

	// Lädt alle Favoriten eines bestimmten Benutzers aus der Datenbank.
	public Vector<Favorit> getFavoriten(String email) throws SQLException {

		Vector<Favorit> favoritListe = new Vector<Favorit>();

		String sql = """

				select favoritId, email, eventId
				from bwi420_639331.favorit
				where email = ?
				order by favoritDatum desc

				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);

		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {

			Favorit favorit = new Favorit();

			favorit.setFavoritId(dbRes.getInt("favoritId"));
			favorit.setEmail(dbRes.getString("email"));
			favorit.setEventId(dbRes.getInt("eventId"));

			favoritListe.add(favorit);
		}

		dbRes.close();
		prep.close();

		return favoritListe;
	}

	// Buchung eines Benutzer ansehen
	public String getHTMLFromBookingsForUser(String email) throws SQLException {

		String html = "";

		String sql = """
				    select b.bookingId,
				           b.eventId,
				           b.ticket,
				           b.quantity,
				           b.preis,
				           b.gesamtpreis,
				           b.bookingDatum,
				           e.eventName,
				           e.eventDatum,
				           e.eventOrt
				    from bwi420_639331.booking b
				    join bwi420_639331.event e
				      on b.eventId = e.eventId
				    where b.email = ?
				    order by b.bookingDatum desc
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, email);

		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {

			int bookingId = dbRes.getInt("bookingId");
			String eventName = dbRes.getString("eventName");
			String ticket = dbRes.getString("ticket");
			int quantity = dbRes.getInt("quantity");
			double preis = dbRes.getDouble("preis");
			double gesamtpreis = dbRes.getDouble("gesamtpreis");
			String eventDatum = dbRes.getString("eventDatum");
			String eventOrt = dbRes.getString("eventOrt");

			html += "<div class='event-card'>";

			html += "<h2>" + eventName + "</h2>";

			html += "<p><strong>Buchungsnummer:</strong> " + bookingId + "</p>";

			html += "<p><strong>Datum:</strong> " + eventDatum + "</p>";

			html += "<p><strong>Ort:</strong> " + eventOrt + "</p>";

			html += "<p><strong>Ticket:</strong> " + ticket + "</p>";

			html += "<p><strong>Anzahl:</strong> " + quantity + "</p>";

			html += "<p><strong>Preis pro Ticket:</strong> " + String.format("%.2f", preis) + " €</p>";

			html += "<p><strong>Gesamtpreis:</strong> " + String.format("%.2f", gesamtpreis) + " €</p>";

			html += "</div>";
		}

		dbRes.close();
		prep.close();

		if (html.equals("")) {
			html = "<p>Du hast noch keine Buchungen.</p>";
		}

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

	public String getEventBeschreibung() {
		return eventBeschreibung;
	}

	public void setEventBeschreibung(String eventBeschreibung) {
		this.eventBeschreibung = eventBeschreibung;
	}

	public int getTicketbestand() {
		return Ticketbestand;
	}

	public void setTicketbestand(int ticketbestand) {
		Ticketbestand = ticketbestand;
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

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String getLetzteBuchungsDetails() {
		return letzteBuchungsDetails;
	}

	public void setLetzteBuchungsDetails(String letzteBuchungsDetails) {
		this.letzteBuchungsDetails = letzteBuchungsDetails;
	}

	public double getLetzteBuchungsGesamtpreis() {
		return letzteBuchungsGesamtpreis;
	}

	public void setLetzteBuchungsGesamtpreis(double letzteBuchungsGesamtpreis) {
		this.letzteBuchungsGesamtpreis = letzteBuchungsGesamtpreis;
	}

	public String getLetzteBuchungsNummer() {
		return letzteBuchungsNummer;
	}

	public void setLetzteBuchungsNummer(String letzteBuchungsNummer) {
		this.letzteBuchungsNummer = letzteBuchungsNummer;
	}

	public Vector<Integer> getWarenkorbEventIds() {
		return warenkorbEventIds;
	}

	public void setWarenkorbEventIds(Vector<Integer> warenkorbEventIds) {
		this.warenkorbEventIds = warenkorbEventIds;
	}

	public int getStandardBestand() {
		return standardBestand;
	}

	public void setStandardBestand(int standardBestand) {
		this.standardBestand = standardBestand;
	}

	public int getVipBestand() {
		return vipBestand;
	}

	public void setVipBestand(int vipBestand) {
		this.vipBestand = vipBestand;
	}

	public Vector<Integer> getWarenkorbMengenVIP() {
		return warenkorbMengenVIP;
	}

	public void setWarenkorbMengenVIP(Vector<Integer> warenkorbMengenVIP) {
		this.warenkorbMengenVIP = warenkorbMengenVIP;
	}

	public Vector<Integer> getWarenkorbMengenStandard() {
		return warenkorbMengenStandard;
	}

	public void setWarenkorbMengenStandard(Vector<Integer> warenkorbMengenStandard) {
		this.warenkorbMengenStandard = warenkorbMengenStandard;
	}

}
