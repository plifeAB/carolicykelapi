package com.caroli.cykel;

import javafx.event.ActionEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import org.json.simple.parser.JSONParser;


public class SettingsController {
    public void writeJsonAction(ActionEvent actionEvent) throws IOException {
/*
        JSONObject tomJsonObj = new JSONObject();
        tomJsonObj.put("name", "Tom");
        tomJsonObj.put("birthday", "1940-02-10");
        tomJsonObj.put("age", 76);
        tomJsonObj.put("married", false);

        // Cannot set null directly
        tomJsonObj.put("car", JSONObject.NULL);

        tomJsonObj.put("favorite_foods", new String[] { "cookie", "fish", "chips" });

        // {"id": 100001, "nationality", "American"}
        JSONObject passportJsonObj = new JSONObject();
        passportJsonObj.put("id", 100001);
        passportJsonObj.put("nationality", "American");
        // Value of a key is a JSONObject
        tomJsonObj.put("passport", passportJsonObj);
        System.out.println(tomJsonObj.toString());



        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(tomJsonObj.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        //First Employee
        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("firstName", "Lokesh");
        employeeDetails.put("lastName", "Gupta");
        employeeDetails.put("website", "howtodoinjava.com");

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("employee", employeeDetails);

        //Second Employee
        JSONObject employeeDetails2 = new JSONObject();
        employeeDetails2.put("firstName", "Brian");
        employeeDetails2.put("lastName", "Schultz");
        employeeDetails2.put("website", "example.com");

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("employee", employeeDetails2);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.put(employeeObject);
        employeeList.put(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void readJsonAction(ActionEvent actionEvent) throws FileNotFoundException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }

    private static void parseEmployeeObject(JSONObject employee)
    {

        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }
}
