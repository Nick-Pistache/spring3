package com.wildcodeschool.spring3.com.wildcodeschool.spring3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;

@Controller
@ResponseBody
public class SchoolController {
    private static String DB_URL = "jdbc:mysql://localhost:3306/wild_db_quest?serverTimezone=GMT";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "Booba92izi*";

    @GetMapping("/api/school")
    public ArrayList<School> getSchool(@RequestParam(defaultValue = "%") String country) {
        try(
        Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD
        );

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM school WHERE country LIKE ? "
        );
        ){
            statement.setString(1, country);
            try(ResultSet resultSet = statement.executeQuery();) {

                ArrayList<School> school = new ArrayList();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    String countries = resultSet.getString("country");
                    school.add(new School(id, name, capacity, countries));
                }
                return school;
            }

        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "", e
            );
        }
    }

    public class School{
        int id;
        String name;
        int capacity;
        String country;


        public School(int id, String name, int capacity, String country) {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
            this.country = country;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
