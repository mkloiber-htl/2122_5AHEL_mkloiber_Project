import footballClub.Club;
import footballClub.Database;
import footballClub.Player;
import footballClub.Transfer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Controller {

    public Label shirtNumberLabel;
    public Label ageLabel;
    public Label marketValueLabel;

    public ListView<Club> clubListview;
    public ListView<Player> playerListview;
    public Label positionlabel;
    public Label countryLabel;
    public ListView<Transfer> transferListview;


    public void initialize() {
        shirtNumberLabel.setVisible(false);
        ageLabel.setVisible(false);
        marketValueLabel.setVisible(false);
        transferListview.setVisible(false);
        countryLabel.setVisible(false);
        positionlabel.setVisible(false);

        System.out.println("Loading Clubs ...");
        clubListview.setItems(Club.loadAll());
        System.out.println("All clubs loaded");
    }

    public void clubListviewClicked(MouseEvent mouseEvent) {
        Club selectedItem = clubListview.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            System.out.println(selectedItem.getClubID());

            playerListview.setItems(Player.getPlayerbyClubId(selectedItem.getClubID()));

            System.out.println(">> Players loaded");
        }
    }

    public void playerListviewClicked(MouseEvent mouseEvent) {
        Player selectedItem = playerListview.getSelectionModel().getSelectedItem();



        if (selectedItem != null) {
            shirtNumberLabel.setVisible(true);
            shirtNumberLabel.setText(Integer.toString(selectedItem.getShirtNumber()));
            ageLabel.setVisible(true);
            ageLabel.setText(Integer.toString(selectedItem.getAge()));
            marketValueLabel.setVisible(true);
            marketValueLabel.setText(selectedItem.getMarketValue());
            positionlabel.setVisible(true);
            positionlabel.setText(selectedItem.getPosition());
            countryLabel.setVisible(true);
            countryLabel.setText(selectedItem.getCountry());

            transferListview.setVisible(true);
            transferListview.setItems(Transfer.getTransferByPlayerId(selectedItem.getPlayerID()));


        }
    }

    public void addButtonClicked(MouseEvent mouseEvent) {

        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("AddPlayer.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Add new Player");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeButtonClicked(MouseEvent mouseEvent) {
        Player selectedItem = playerListview.getSelectionModel().getSelectedItem();
        int playerId;

        playerId = selectedItem.getPlayerID();
        playerListview.getItems().remove(selectedItem);
        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = null;
            PreparedStatement statement1 = null;
            statement = c.prepareStatement("DELETE From mkloiber_player WHERE playerId = ?");
            statement1 = c.prepareStatement("DELETE FROM mkloiber_transfer WHERE playerId = ?");
            statement.setInt(1, playerId);
            statement1.setInt(1, playerId);
            statement.executeUpdate();
            statement1.executeUpdate();
            statement.close();
            statement1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

