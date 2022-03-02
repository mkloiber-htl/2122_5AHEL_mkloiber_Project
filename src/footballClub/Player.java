package footballClub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {

    private int playerID;
    private String firstName;
    private String lastName;
    private int age;
    private int shirtNumber;
    private String marketValue;
    private String clubName;
    private int clubId;
    private int transferID;
    private Transfer clubLeft;
    private Transfer clubJoined;
    private Transfer transferFee;
    private String country;
    private String position;

    Player(int playerID, String firstName, String lastName, String marketValue, int shirtNumber, int age, String position, String country) {
        this.playerID = playerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.marketValue = marketValue;
        this.age = age;
        this.shirtNumber = shirtNumber;
        this.position = position;
        this.country = country;

    }

    public int getPlayerID() {
        return playerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public String getClubName() {
        return clubName;
    }

    public int getClubId() {
        return clubId;
    }

    public int getTransferID() {
        return transferID;
    }

    public Transfer getClubLeft() {
        return clubLeft;
    }

    public Transfer getClubJoined() {
        return clubJoined;
    }

    public Transfer getTransferFee() {
        return transferFee;
    }

    public String getCountry() {
        return country;
    }

    public String getPosition() {
        return position;
    }


    public static ObservableList<Player> loadAll() {
        ObservableList<Player> result = FXCollections.observableArrayList();


        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM mkloiber_Players ");


            ResultSet results = statement.executeQuery();

            while (results.next()) {

                result.add(new Player(results.getInt("playerId"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        results.getString("marketValue"),
                        results.getInt("age"),
                        results.getInt("shirtNumber"),
                        results.getString("Position"),
                        results.getString("country")));

            }
            results.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Player> getPlayerbyClubId(int clubId) {
        ObservableList<Player> player = FXCollections.observableArrayList();


        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM mkloiber_club mc INNER JOIN mkloiber_player mp ON (mc.clubId = mp.clubId) WHERE mc.clubId = ? ");
            statement.setInt(1, clubId);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                player.add(new Player(results.getInt("playerId"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        results.getString("marketValue"),
                        results.getInt("shirtNumber"),
                        results.getInt("age"),
                        results.getString("position"),
                        results.getString("country")));


            }

            results.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}