package footballClub;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transfer {
    private int transferId;
    private String clubLeft;
    private String clubJoined;
    private String transferWindow;
    private String transferFee;
    private int playerId;

    public Transfer(String clubLeft, String clubJoined, String transferWindow, String transferFee, int transferId, int playerId) {
        this.clubLeft = clubLeft;
        this.clubJoined = clubJoined;
        this.transferWindow = transferWindow;
        this.transferFee = transferFee;
        this.playerId = playerId;
        this.transferId = transferId;
    }

    public int getTransferId() {
        return transferId;
    }

    public String getClubLeft() {
        return clubLeft;
    }

    public String getClubJoined() {
        return clubJoined;
    }

    public String getTransferWindow() {
        return transferWindow;
    }

    public String getTransferFee() {
        return transferFee;
    }

    public int getPlayerId() {
        return playerId;
    }

    public static ObservableList<Transfer> getTransferByPlayerId(int playerId) {
        ObservableList<Transfer> transfer = FXCollections.observableArrayList();


        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM mkloiber_player mp INNER JOIN mkloiber_transfer mt ON (mp.playerId = mt.playerId) WHERE mp.playerId = ?");
            statement.setInt(1, playerId);
            ResultSet results = statement.executeQuery();


            while (results.next()) {
                transfer.add(new Transfer(results.getString("clubLeft"),
                        results.getString("clubJoined"),
                        results.getString("transferWindow"),
                        results.getString("transferFee"),
                        results.getInt("transferId"),
                        results.getInt("playerId")));
            }

            results.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transfer;
    }

    @Override
    public String toString() {
        return clubLeft + " -> " + clubJoined + " Transferwindow: " + transferWindow + " Transferfee: " + transferFee;
    }
}
