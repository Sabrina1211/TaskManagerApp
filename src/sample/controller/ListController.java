package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import java.awt.*;
import java.net.URL;

import java.util.ResourceBundle;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import sample.Database.DatabaseHandler;
import sample.model.Task;
import java.util.Calendar;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView lisRefreshButton;

    @FXML
    private JFXListView<Task> listTask;

    @FXML
    private JFXTextField listTaskField;

    @FXML
    private JFXTextField listDescriptionField;

    @FXML
    private JFXButton listSaveTaskButton;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;


    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {

        System.out.println("initialize called");

        tasks = FXCollections.observableArrayList();


        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);

        }


        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());


        lisRefreshButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        listSaveTaskButton.setOnAction(event -> {
            addNewTask();

        });



    }

    public void refreshList() throws SQLException {


        System.out.println("refreshList in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();


        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            refreshedTasks.addAll(task);

        }


        listTask.setItems(refreshedTasks);
        listTask.setCellFactory(CellController -> new CellController());

    }

    public void addNewTask()
    {
        if (!listTaskField.getText().equals("")
                || !listDescriptionField.getText().equals("")) {
            Task myNewTask = new Task();

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());


            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setDatecreated(timestamp);

            databaseHandler.insertTask(myNewTask);

            listTaskField.setText("");
            listDescriptionField.setText("");

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

}
