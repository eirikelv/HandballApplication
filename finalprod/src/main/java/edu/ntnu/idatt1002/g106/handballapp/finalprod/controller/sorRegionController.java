package edu.ntnu.idatt1002.g106.handballapp.finalprod.controller;

import edu.ntnu.idatt1002.g106.handballapp.finalprod.backend.AlertBox;
import edu.ntnu.idatt1002.g106.handballapp.finalprod.backend.SwitchScene;;
import edu.ntnu.idatt1002.g106.handballapp.finalprod.backend.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class sorRegionController implements Initializable {

    @FXML
    public TableColumn<Tournament, Integer> startDateColumn;
    @FXML
    public TableColumn<Tournament, Integer> endDateColumn;
    @FXML
    public TableColumn<Tournament, String> tournamentNameColumn;
    @FXML
    public javafx.scene.control.TableView<Tournament> tableView;

    AtomicInteger currentTournamentId = new AtomicInteger();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTournamentId = new AtomicInteger();

        tableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                Tournament selectedTournament = tableView.getSelectionModel().getSelectedItem();
                HandballApplication.adminList.get(0).getTournamentRegister().getTournaments()
                        .forEach(t -> {
                            if (t.getTournamentName().equals(selectedTournament.getTournamentName())
                                    && t.getRegion().equals(SwitchScene.getCurrentRegion())){
                                currentTournamentId.set(t.getTournamentID());

                                try {
                                    goToCurrentPage(event);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });

        updateList();
    }

    public void goToCurrentPage(Event event) throws IOException {
        HandballApplication.setChosenTournament(currentTournamentId.intValue());
        SwitchScene.switchScene("MainPage", event);
    }

    @FXML
    public void backToRegionChoice(ActionEvent event) throws IOException {
        HandballApplication.setChosenTournament(currentTournamentId.intValue());
        SwitchScene.switchScene("RegionChoice", event);
    }

    @FXML
    public void logOutMethod() {
        if (AlertBox.logOut() == 1){
            System.exit(-1);
        }
    }

    @FXML
    private void updateList(){
        tournamentNameColumn.setCellValueFactory(new PropertyValueFactory<Tournament, String>("tournamentName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Tournament, Integer>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Tournament, Integer>("endDate"));

        tableView.setItems(FXCollections.observableArrayList(
                HandballApplication.adminList.get(0).getTournamentRegister().getTournaments().stream()
                        .filter(t -> t.getRegion().equals("RegionSor")).toList()));

        tableView.refresh();
    }

    @FXML
    public void toSetUpTournament(ActionEvent event) throws IOException {
        SwitchScene.switchScene("SetUpTournament", event);
    }
}
