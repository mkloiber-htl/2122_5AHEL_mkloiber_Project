package footballClub;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String driver;
    private static String user;
    private static String password;
    private static String host;
    private static int port;
    private static String database;
    private static String type;

    private static Connection dbConnection = null;

    //static-block wird NUR EINMAL ausgeführt
    static {

        //try-with-ressource - beim verlassen des "try's" wird der Stream automatisch wieder geschlossen
        //funktioniert nur mit Klassen die das Interface "Closeable" implementieren (z.B.: InputStream)
        try (InputStream file = new FileInputStream("config.properties")) {
            Properties config = new Properties();
            config.load(file);

            try {
                driver = config.getProperty("driver");
                user = config.getProperty("user");
                password = config.getProperty("password");
                host = config.getProperty("host");
                port = Integer.parseInt(config.getProperty("port"));
                database = config.getProperty("database");
                type = config.getProperty("type");

                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //file.close - ein InputStream muss am Ende immer geschlossen werden
            //wenn nicht mit try-with-ressource aufgerufen
        }

    }

    private Database() {
    }

    public static Connection getInstance() {
        if (dbConnection == null) {

            try {
                //neue Verbindung aufbauen
                dbConnection = DriverManager.getConnection("jdbc:" + type + "://" + host + ":" + port + "/"
                        + database, user, password);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dbConnection;
    }


}



