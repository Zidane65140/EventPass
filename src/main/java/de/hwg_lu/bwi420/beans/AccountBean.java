package de.hwg_lu.bwi420.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import de.hwg_lu.bwi.jdbc.PostgreSQLAccess;

public class AccountBean {

	String vorname;
	String nachname;
	String password;
	String active;
	String admin;
	String email;
	String logmail;
	Connection dbConn;

	public AccountBean() throws ClassNotFoundException, SQLException {// Bean braucht Default-Konstruktor
		super();
		this.dbConn = new PostgreSQLAccess().getConnection();
		this.nachname = "";
		this.vorname = "";
		this.password = "";

		this.active = "N";
		this.admin = "N";
		this.email = "";
	}

	public boolean checkAccountExists() throws SQLException {
		// return true, wenn Account mit userid existiert
		// return false, wenn Account nicht existiert
		String sql = "select email from account where email = ?";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, this.email);
		ResultSet dbRes = prep.executeQuery();
		// Ergebnis: ResultSet, das entweder leer ist oder genau einen Datensatz enthält
		return dbRes.next();
//Alternativen:
//		boolean gefunden = dbRes.next();
		/*
		 * if (gefunden) { return true; }else{ return false; }
		 */
//		return (gefunden)?true:false;
	}

	public boolean insertAccountIfNotExists() throws SQLException {
		// true: Account inserted, alles gut
		// false: Account mit dieser userid existiert schon, nichts getan
		boolean accountExists = this.checkAccountExists();
		if (accountExists) {
			System.out.println("Account " + this.vorname + " existiert schon, kein insert");
			return false;
		} else {
			this.insertAccountNoCheck();
			return true;
		}
	}

	public void insertAccountNoCheck() throws SQLException {
		// schreibe einen Datensatz in die Account-Tabelle
		// mit den Werten der Attribute userid, password, etc.
		String sql = """
					insert into account
						(vorname, nachname, password, active, admin, email)
					values
						(?,?,?,?,?,?)
				""";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, this.vorname);
		prep.setString(2, this.nachname);
		prep.setString(3, this.password);
		prep.setString(4, this.active);
		prep.setString(5, this.admin);
		prep.setString(6, this.email);
		prep.executeUpdate();
		System.out.println("Benutzer " + this.vorname + " hinzugefügt");
	}

	public boolean emailExistiert() throws ClassNotFoundException, SQLException {

		String sql = "select email from account where email = ?";

		PreparedStatement prep = new PostgreSQLAccess().getConnection().prepareStatement(sql);

		prep.setString(1, this.email);

		ResultSet dbRes = prep.executeQuery();

		return dbRes.next();
	}

	public void drop(String email) throws SQLException {
		if (email.equals("zizou@test.de")) {
			return;
		}
		String sql1 = """
				    delete from bwi420_639331.favorit
				    where email = ?
				""";
		PreparedStatement prep = this.dbConn.prepareStatement(sql1);
		prep.setString(1, email);
		prep.executeUpdate();
		String sql = """
				    delete from bwi420_639331.account
				    where email = ?
				""";
		prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);

		prep.executeUpdate();
		System.out.println("Benutzer mit Email " + email + " ist jetzt gelöscht");

	}

	// Macht einen Benutzer zum Administrator.
	public void makeAdmin(String email) throws SQLException {

		String sql = """
				    update bwi420_639331.account
				    set admin = 'J'
				    where email = ?
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);

		prep.executeUpdate();

		prep.close();

		System.out.println("Benutzer " + email + " ist jetzt Admin");
	}

	// Entfernt den Administrator-Status eines Benutzers.
	public void removeAdmin(String email) throws SQLException {
		if (email.equals("zizou@test.de")) {
			return;
		}
		String sql = """
				    update bwi420_639331.account
				    set admin = 'N'
				    where email = ?
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);

		prep.setString(1, email);

		prep.executeUpdate();

		prep.close();

		System.out.println("Admin-Rechte von " + email + " entfernt");
	}

	// Gibt alle Benutzer für die AdminView als HTML-Tabelle zurück.
	public String getHTMLFromAccounts() throws SQLException {

		String html = "";

		String sql = """
				    select vorname, nachname, email, active, admin
				    from bwi420_639331.account
				    order by nachname, vorname
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		ResultSet dbRes = prep.executeQuery();

		while (dbRes.next()) {

			String vorname = dbRes.getString("vorname");
			String nachname = dbRes.getString("nachname");
			String email = dbRes.getString("email");
			String active = dbRes.getString("active");
			String admin = dbRes.getString("admin");

			html += "<tr>";

			html += "<td>" + vorname + " " + nachname + "</td>";
			html += "<td>" + email + "</td>";
			html += "<td>" + active + "</td>";

			if ("J".equals(admin)) {

				html += "<td>Admin</td>";

				html += "<td>" + "<a class='btn' " + "href='AdminAppl.jsp?action=AdminEntfernen&email=" + email + "'>"
						+ "Admin entfernen" + "</a>" + "</td>";

			} else {

				html += "<td>Benutzer</td>";

				html += "<td>" + "<a class='btn' " + "href='AdminAppl.jsp?action=AdminMachen&email=" + email + "'>"
						+ "Admin machen" + "</a>" + "</td>";
			}

			if (this.logmail.equals("zizou@test.de")) {

				html += "<td>" + "<a class='btn' " + "href='AdminAppl.jsp?action=Benutzerentfernen&email=" + email
						+ "'>" + "Benutzer Löschen" + "</a>" + "</td>";
			}
		}

		html += "</tr>";

		dbRes.close();
		prep.close();

		return html;
	}

	// prüft ob eine benutzer Admin ist
	public boolean isAdmin(String email) throws SQLException {

		String sql = """
				    select admin
				    from bwi420_639331.account
				    where email = ?
				""";

		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, email);

		ResultSet dbRes = prep.executeQuery();

		if (dbRes.next()) {

			String admin = dbRes.getString("admin");

			prep.close();

			return "J".equals(admin);
		}

		prep.close();

		return false;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogmail() {
		return logmail;
	}

	public void setLogmail(String logmail) {
		this.logmail = logmail;
	}

}
