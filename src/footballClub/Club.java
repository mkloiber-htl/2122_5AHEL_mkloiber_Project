package footballClub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Club {
    private int clubID;
    private String name;

    private Club(int clubId, String name) {
        this.clubID = clubId;
        this.name = name;
    }

    public int getClubID() {
        return clubID;
    }

    public static Club getById(int clubID) {

        Club result = null;


        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * From Club WHERE clubID = ? ");
            statement.setInt(1, clubID);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                result = new Club(results.getInt("clubId"),
                        results.getString("clubName"));
            }

            results.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Club> loadAll() {
        ObservableList<Club> result = FXCollections.observableArrayList();

        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM mkloiber_club");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                result.add(new Club(results.getInt("clubId"),
                        results.getString("ClubName")));
            }

            results.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }


    public ObservableList<Player> loadPlayers() {


        ObservableList<Player> result = Player.loadAll();
        return result;
    }

    @Override
    public String toString() {
        return clubID + " - " + name;
    }
}
