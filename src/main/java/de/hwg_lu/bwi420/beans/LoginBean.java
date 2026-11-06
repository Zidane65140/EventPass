package de.hwg_lu.bwi420.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hwg_lu.bwi.jdbc.PostgreSQLAccess;

public class LoginBean {

	String email;
	String password;
    String message;
	boolean isLoggedIn=false;
	Connection dbConn;
	
	public LoginBean() throws ClassNotFoundException, SQLException {
		this.dbConn = new PostgreSQLAccess().getConnection();
		this.email = "";
		this.password = "";
		this.message="Bitte melde dich in deinem Konto an.";
		this.isLoggedIn = false;
	}
	
	public boolean checkUseridPassword() throws ClassNotFoundException, SQLException {
		//gibt es in der account-Tabelle einen Datensatz mit
		//passenden userid/password?
		String sql = "select email ,password from account "
				+ "where email = ? and password = ?";
		System.out.println(sql);
		PreparedStatement prep = new PostgreSQLAccess().getConnection().prepareStatement(sql);
		prep.setString(1,  this.email);
		prep.setString(2,  this.password);
		ResultSet dbRes = prep.executeQuery();
		return dbRes.next();
	}
	public void Active(String active) throws SQLException {
		 String sql = """
			        update bwi420_639331.account
			        set active = ?
			        where email = ?
			    """;
		 

		    PreparedStatement prep = this.dbConn.prepareStatement(sql);
		    prep.setString(1, active);
		    prep.setString(2, this.email);

		    prep.executeUpdate();

		    prep.close();
		    if ("J".equals(active))
		    System.out.println("Benutzer " + email + " ist jetzt eingeloggt");
else {
	
	System.out.println("Benutzer " + email + " ist jetzt ausgeloggt");
	}
	
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoggedIn() {
		
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String messsage) {
		this.message = messsage;
	}
	
}
