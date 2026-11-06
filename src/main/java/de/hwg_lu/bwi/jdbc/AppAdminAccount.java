package de.hwg_lu.bwi.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppAdminAccount {

	Connection dbConn;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AppAdminAccount myApp = new AppAdminAccount();
		myApp.dbConn = new PostgreSQLAccess().getConnection();
		myApp.doSomething();
	}

	public void doSomething() throws SQLException {
		// this.createSchema();
//this.createTableFavorit();
		//this.dropTableEvent();
		//this.createTableEvent();
		// this.createTableAccount();
		this.showAllAccounts();
	//	this.createTableBooking();
	}

	public void showAllAccounts() throws SQLException {
		String sql = """
					select email , password, active, admin
					from account
					-- where email = ?
				""";
		System.out.println(sql);
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		ResultSet dbRes = prep.executeQuery();
		while (dbRes.next()) {
			System.out.println(dbRes.getString("email") + "\t" + dbRes.getString("password") + dbRes.getString("active")
					+ dbRes.getString("admin")

			);
		}
	}

	public void createSchema() throws SQLException {
		String sql = "create schema if not exists bwi420_639331";
		System.out.println(sql);
		this.dbConn.prepareStatement(sql).executeUpdate();
		System.out.println("Schema 'bwi420_639331' muesste jetzt existieren");
	}

	public void dropTableEvent() throws SQLException {
		String sql = "drop table if exists bwi420_639331.event";
		System.out.println(sql);
		this.dbConn.prepareStatement(sql).executeUpdate();
		System.out.println("Tabelle account geloescht");
	}

	public void createTableAccount() throws SQLException {
		String sql = """
					create table bwi420_639331.account (

						vorname   varchar(50)      not null       ,
						nachname   varchar(50)   not null         ,
						password  char(50)  not null              ,
						active    char(1)                         ,
						admin     char(1)                         ,
						email     varchar(256)  not null unique primary key
					)
				""";
		System.out.println(sql);
		this.dbConn.prepareStatement(sql).executeUpdate();
		System.out.println("Tabelle account angelegt");
	}

	public void createTableEvent() throws SQLException {
		String sql = """
					create table bwi420_639331.event (

						eventId   serial      not null unique  primary key     ,
						eventName   varchar(100)   not null         ,
						eventType  varchar(32)  not null              ,
						eventDatum    date                         ,
						eventOrt     varchar(20)                         ,
						Ticketbestand integer                           ,
						eventBeschreibung     varchar(200)                         ,
						preis        decimal(2)   ,
						 standardBestand integer,
                          vipBestand integer       
					)
				""";
		System.out.println(sql);
		this.dbConn.prepareStatement(sql).executeUpdate();
		System.out.println("Tabelle event angelegt");
	}
	public void createTableFavorit() throws SQLException {

	    String sql = """

	            create table bwi420_639331.favorit (

	                favoritId serial primary key,

	                email varchar(256) not null,

	                eventId integer not null,

	                favoritDatum timestamp default current_timestamp,

	                foreign key (email)
	                    references bwi420_639331.account(email),

	                foreign key (eventId)
	                    references bwi420_639331.event(eventId),

	                unique (email, eventId)

	            )

	        """;

	    System.out.println(sql);

	    this.dbConn.prepareStatement(sql).executeUpdate();

	    System.out.println("Tabelle favorit angelegt");

	}

	public void createTableBooking() throws SQLException {

		String sql = """

				    create table bwi420_639331.booking (

				        bookingId       serial          not null unique primary key,

				        eventId         integer         not null,

				        email           varchar(100)    not null,

				        ticket          varchar(32),

				        quantity        integer         not null,

				        preis           decimal(10,2),

				        gesamtpreis     decimal(10,2),

				        bookingDatum    timestamp

				    )

				""";

		System.out.println(sql);

		this.dbConn.prepareStatement(sql).executeUpdate();

		System.out.println("Tabelle booking angelegt");

	}
	

}
