package sample.Windows.TugOfWar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.Person.MainJudge;
import sample.Team.TugOfWarTeam;
import sample.Team.VolleyballTeam;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class startSemiFinalsController {

    @FXML
    private ListView listView1;
    @FXML
    private ListView listView2;
    @FXML
    private Label judge1;
    @FXML
    private Label judge2;

    private List<TugOfWarTeam> teams;
    private List<MainJudge> mainJudges;


    @FXML
    public void initialize() {
        teams = new LinkedList<>();
        mainJudges = new LinkedList<>();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("TugOfWarTeams.ser"));
            ObjectInputStream input2 = new ObjectInputStream(new FileInputStream("TugOfWarJudges.ser"));
            try{
                try {
                    while (!(input == null)) teams.add((TugOfWarTeam) input.readObject());
                }
                catch(EOFException e){
                    System.out.println(e.getMessage());
                }
                try {
                    while (!(input2 == null)) mainJudges.add((MainJudge) input2.readObject());
                }
                catch(EOFException e){
                    System.out.println(e.getMessage());
                }
            }
            catch(ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
            input2.close();
            input.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        Random random = new Random();
        int j = random.nextInt(mainJudges.size());
        judge1.setText(mainJudges.get(j).getName() + " " + mainJudges.get(j).getSurname());
        j = random.nextInt(mainJudges.size());
        judge2.setText(mainJudges.get(j).getName() + " " + mainJudges.get(j).getSurname());
        mainJudges.clear();

        for(int i=0;i<teams.size();i++)teams.get(i).setPoints(0);

        ObservableList<String> obsTeams = FXCollections.observableArrayList();
        obsTeams.addAll(teams.get(0).getTeamName());
        obsTeams.addAll(teams.get(3).getTeamName());
        listView1.setItems(obsTeams);
        ObservableList<String> obsTeam2 = FXCollections.observableArrayList();
        obsTeam2.addAll(teams.get(1).getTeamName());
        obsTeam2.addAll(teams.get(2).getTeamName());
        listView2.setItems(obsTeam2);

    }


    @FXML
    public void startFinalAction() {
        if ((listView1.getSelectionModel().getSelectedIndex() == 0 || listView1.getSelectionModel().getSelectedIndex() == 1) && (listView2.getSelectionModel().getSelectedIndex() == 0 || listView2.getSelectionModel().getSelectedIndex() == 1)){
            Stage stage = (Stage) listView1.getScene().getWindow();
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("TugOfWarTeams.ser"));
            if (listView1.getSelectionModel().getSelectedIndex() == 0) output.writeObject((TugOfWarTeam) teams.get(0));
            else output.writeObject((TugOfWarTeam) teams.get(3));

            if (listView1.getSelectionModel().getSelectedIndex() == 0) output.writeObject((TugOfWarTeam) teams.get(1));
            else output.writeObject((TugOfWarTeam) teams.get(2));

            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("final.fxml"));
            Stage tournament = new Stage();
            tournament.setTitle("Final!");
            tournament.setScene(new Scene(root, 400, 250));
            tournament.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        stage.close();
    }

    }

}
