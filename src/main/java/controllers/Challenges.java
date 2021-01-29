package controllers;

import server.Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static server.Convertor.convertToJSONArray;
import static server.Convertor.convertToJSONObject;


@Path("challenges/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Challenges {

    // /challenges/list
    @GET
    @Path("list")
    public String challengesList() {
        System.out.println("Invoked Challenges.challengesList()");

        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT Challenge, Difficulty FROM Difficulties");
            ResultSet resultSet = statement.executeQuery(); //executes the SQL prepared statement
            JSONArray response = convertToJSONArray(resultSet); //converts results set to a JSON array
            return response.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage()); //not sent to the client
            return "{\"Error\": \"Something has gone wrong :/ Please contact the administrator. \"}"; //error message sent to the client
        }
    }

    // /challenges/get-num-levels
    @GET
    @Path("get-num-levels/{challenge}")
    public String numLevels(@PathParam("challenge") int challenge) {
        System.out.println("Invoked Challenges.numLevels() + challenge = " + challenge);

        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT Challenge, COUNT(LevelId) FROM Challenges WHERE Challenge = ?");
            statement.setInt(1, challenge); //sets the WHERE part of the SQL to the correct value inputted as a path parameter
            ResultSet resultSet = statement.executeQuery(); //executes the SQL prepared statement
            JSONObject response = convertToJSONObject(resultSet);
            return response.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage()); //not sent to the client
            return "{\"Error\": \"Something has gone wrong :/ Please contact the administrator. \"}"; //error message sent to the client
        }
    }

    // /challenges/get-level
    @GET
    @Path("get-level/{levelId}")
    public String levelList(@PathParam("levelId") String levelId) {
        System.out.println("Invoked Challenges.levelList() + levelId = " + levelId);

        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT LevelId, Ciphertext, Tool FROM Tasks WHERE LevelId = ?");
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
