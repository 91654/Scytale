package controllers;

import server.Main;

import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static server.Convertor.convertToJSONObject;

@Path("tasks/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Tasks {
    // /tasks/validate-answer
    @GET
    @Path("validate-answer/{levelId}")
    public String tasksValidate(@PathParam("levelId") String levelId) {
        System.out.println("Invoked Challenges.tasksValidate() + levelId = " + levelId);

        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT LevelId, Plaintext FROM Tasks WHERE LevelId = ?");
            statement.setString(1, levelId); //sets the WHERE part of the SQL to the correct value inputted as a path parameter
            ResultSet resultSet = statement.executeQuery(); //executes the SQL prepared statement
            JSONObject response = convertToJSONObject(resultSet);
            return response.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage()); //not sent to the client
            return "{\"Error\": \"Something has gone wrong :/ Please contact the administrator. \"}"; //error message sent to the client
        }
    }
}