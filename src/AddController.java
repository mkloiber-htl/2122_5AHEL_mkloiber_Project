import footballClub.Database;
import footballClub.Transfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddController {
    public ListView Transferlistview;
    public TextField firstnameTextfield;
    public TextField lastnametextfield;
    public TextField ageTextfield;
    public TextField countryTextfield;
    public TextField marketvalueTextfield;
    public TextField positionTextfield;
    public TextField playerIdTextfield;
    public TextField transferIdTextfield;
    public Button addtransferButton;
    public TextField clubLeftTextfield;
    public TextField clubjoinedTextfield;
    public TextField transferFeeTextfield;
    public TextField transferwindowTextfield;
    public Button AddButton;
    public TextField transferplayerIdTextfield;
    public TextField clubnameTextfield;
    public TextField shirtNumberTextfield;
    public TextField clubIdTextfield;

    public void AddButtonClicked(MouseEvent mouseEvent) {
        System.out.println("player added to the Club");

        if (!playerIdTextfield.getText().equals("")) {
            try {
                Connection c = Database.getInstance();
                PreparedStatement statement = c.prepareStatement("Insert INTO mkloiber_player (playerId, clubName, lastName, firstName, ShirtNumber, MarketValue, Age, Position, Country, clubId) VALUES (?,?,?,?,?,?,?,?,?,?)");
                statement.setInt(1, Integer.parseInt(playerIdTextfield.getText()));
                statement.setString(2, clubnameTextfield.getText());
                statement.setString(3, lastnametextfield.getText());
                statement.setString(4, firstnameTextfield.getText());
                statement.setInt(5, Integer.parseInt(shirtNumberTextfield.getText()));
                statement.setString(6, marketvalueTextfield.getText());
                statement.setInt(7, Integer.parseInt(ageTextfield.getText()));
                statement.setString(8, positionTextfield.getText());
                statement.setString(9, countryTextfield.getText());
                statement.setInt(10, Integer.parseInt(clubIdTextfield.getText()));
                statement.executeUpdate();

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (!transferIdTextfield.getText().equals("")) {
            try {
                Connection c = Database.getInstance();
                PreparedStatement statement = c.prepareStatement("Insert INTO mkloiber_transfer (transferId, clubLeft, clubJoined, transferWindow, playerId, transferFee) VALUE (?,?,?,?,?,?)");
                statement.setInt(1, Integer.parseInt(transferIdTextfield.getText()));
                statement.setString(2, clubLeftTextfield.getText());
                statement.setString(3, clubjoinedTextfield.getText());
                statement.setString(4, transferwindowTextfield.getText());
                statement.setInt(5, Integer.parseInt(transferplayerIdTextfield.getText()));
                statement.setString(6, transferFeeTextfield.getText());
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void AddTransferButtonClicked(MouseEvent mouseEvent) {
        ObservableList<Transfer> transfers = FXCollections.observableArrayList();

            transfers.add(new Transfer(clubLeftTextfield.getText(),
                    clubjoinedTextfield.getText(),
                    transferwindowTextfield.getText(),
                    transferFeeTextfield.getText(),
                    Integer.parseInt(transferIdTextfield.getText()),
                    Integer.parseInt(transferplayerIdTextfield.getText())));
            Transferlistview.setItems(transfers);
        }

}
