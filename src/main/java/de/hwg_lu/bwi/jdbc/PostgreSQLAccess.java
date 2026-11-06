package de.hwg_lu.bwi.jdbc;
import java.io.InputStream; import java.io.IOException; import java.util.Properties; import java.sql.SQLException;
public class PostgreSQLAccess extends JDBCAccess {
public PostgreSQLAccess() throws ClassNotFoundException, SQLException {
    super();
}

@Override
public void setDBParameter() {

    Properties prop = new Properties();

    try (InputStream input = getClass().getResourceAsStream("/db.properties")) {

        if (input == null) {
            throw new RuntimeException("db.properties nicht gefunden!");
        }

        prop.load(input);

        this.dbDrivername = prop.getProperty("db.driver");
        this.dbProt       = prop.getProperty("db.protocol");
        this.dbServer     = prop.getProperty("db.server");
        this.dbPort       = prop.getProperty("db.port");
        this.dbName       = prop.getProperty("db.name");
        this.dbUserid     = prop.getProperty("db.userid");
        this.dbPassword   = prop.getProperty("db.password");
        this.dbSchema     = prop.getProperty("db.schema");

    } catch (IOException e) {
        throw new RuntimeException("Fehler beim Laden der Datenbankkonfiguration!", e);
    }
}

@Override
public void setSchema() throws ClassNotFoundException, SQLException {

    String sql = "SET Schema '" + dbSchema + "'";

    System.out.println(sql);

    this.dbConn.prepareStatement(sql).executeUpdate();

    System.out.println("Schema " + dbSchema + " gesetzt");
}

public static void main(String[] args)
        throws ClassNotFoundException, SQLException {

    new PostgreSQLAccess();
}
}