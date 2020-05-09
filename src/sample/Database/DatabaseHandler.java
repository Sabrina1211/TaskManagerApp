package sample.Database;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import sample.model.Task;
import sample.model.User;

import java.sql.*;

//Method to connect my app with the database
//I need to provide my localhost number, Port Number, name and password
public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws  ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://"  + dbHost + ":"
                + dbPort + "/"
                + dbName
                +"?useTimezone=true&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);


        return dbConnection;


    }
    //This query allow the database to save the change if the user make any update for a task
    //in the app

    public void updateTask(Timestamp datecreated, String description, String task, int taskId) throws SQLException, ClassNotFoundException {

        String query = "UPDATE tasks SET datecreated=?, description=?, task=? WHERE taskid=?";


        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1, datecreated);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, task);
        // preparedStatement.setInt(4, userId);
        preparedStatement.setInt(4, taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    //Delete task will delete any task that user wish to delete
    public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.TASK_TABLE + " WHERE "+
                Const.USERS_ID + "=?" + " AND " + Const.TASKS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
       preparedStatement.setInt(2, taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }



    //This Method insert the information of the user when they create an account
    public void signUpUser (User user)
    {

        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME
                + "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + ","
                + Const.USERS_PASSWORD + "," + Const.USERS_LOCATION + ","
                + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);


            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2,  user.getLastName());
            preparedStatement.setString(3,  user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    // I use ResultSet which is a  Java object that contains
    // the results of executing an SQL query
    //Here getTaskByUser Method allow the database to get the correct userid for each task
    // and put it in the right table
    public ResultSet getTasksByUser(int userId){

        ResultSet resultTasks = null;

        String query = "SELECT * FROM " + Const.TASK_TABLE + " WHERE "
                + Const.USERS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);


            resultTasks = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTasks;

    }

    //Select all from users where username =name and pass = password
    public ResultSet getUser(User user){

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals(""))
        {

            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                            + Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD
                            + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

               resultSet= preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        else
        {
             System.out.println("Please, Enter your credentials");
        }
          return resultSet;
    }


  //This Method will get all the task by the Task_table and using the userid to know who create the task
    public int  getAllTasks(int userId) throws SQLException, ClassNotFoundException{

        String query = "SELECT COUNT(*) FROM " + Const.TASK_TABLE + " WHERE "
                + Const.USERS_ID + "=?";


        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return resultSet.getInt(1);


    }

    //This query  will allow the user to insert the task, description into the database
    public void insertTask(Task task)
    {

        String insert = "INSERT INTO " + Const.TASK_TABLE + "(" + Const.USERS_ID + ","
                + Const.TASKS_DATE + "," + Const.TASKS_DESCRIPTION + "," + Const.TASKS_TASK + ")"
                + "VALUES(?,?,?,?)";


        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);


            System.out.println("From DBHandler UserId: " + task.getUserId());

            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDatecreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }


    //read

    //Update
}
