/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

import CoursifyApp.entities.Instructor;
import CoursifyApp.utilities.Myconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author ghanm
 */
public class InstructorService implements CrudInterface<Instructor> {

    private Connection cnx;
    private Statement st;
    private ResultSet rs;

    public InstructorService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void create(Instructor entity) {
        PreparedStatement pst;
        int n = 0;
        int generatedId = -1;

        try {

            pst = cnx.prepareStatement("INSERT INTO Instructor (username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, yearsOfExperience, bio,isActive) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, entity.getUsername());
            pst.setString(2, entity.getPassword());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getFirstName());
            pst.setString(5, entity.getLastName());
            pst.setDate(6, entity.getDateOfBirth());
            pst.setString(7, entity.getGender());
            pst.setString(8, entity.getAddress());
            pst.setString(9, entity.getPhoneNumber());
            pst.setString(10, entity.getProfilePicture());
            pst.setString(11, entity.getUserType());
            pst.setDate(12, entity.getRegistrationDate());
            pst.setInt(13, entity.getYearsOfExperience());
            pst.setString(14, entity.getBio());
            pst.setBoolean(15, entity.isIsActive());
            

            n = pst.executeUpdate();

            if (n > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1); // Get the generated ID
                }
                entity.setId(generatedId);
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Instructor entity) {
        PreparedStatement pst;
        int n = 0;

        try {
            pst = cnx.prepareStatement("UPDATE Instructor SET username=?, password=?, email=?, firstName=?, lastName=?, dateOfBirth=?, gender=?, address=?, phoneNumber=?, profilePicture=?, userType=?, registrationDate=?, yearsOfExperience=?, bio=? WHERE id=?");

            pst.setString(1, entity.getUsername());
            pst.setString(2, entity.getPassword());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getFirstName());
            pst.setString(5, entity.getLastName());
            pst.setDate(6, entity.getDateOfBirth());
            pst.setString(7, entity.getGender());
            pst.setString(8, entity.getAddress());
            pst.setString(9, entity.getPhoneNumber());
            pst.setString(10, entity.getProfilePicture());
            pst.setString(11, entity.getUserType());
            pst.setDate(12, entity.getRegistrationDate());
            pst.setInt(13, entity.getYearsOfExperience());
            pst.setString(14, entity.getBio());
            pst.setInt(15, entity.getId()); // Set the ID for the WHERE clause

            n = pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        int n = 0;
        PreparedStatement st;
        try {

            st = cnx.prepareStatement("DELETE FROM `Instructor` WHERE `id`=?");

            st.setInt(1, id);

            n = st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Instructor getById(int id) {
        int n = 0;
        PreparedStatement st;
        Instructor instructor = new Instructor();
        try {

            st = cnx.prepareStatement("SELECT * FROM `Instructor` WHERE `id`=?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {

                instructor.setId(rs.getInt("id"));
                instructor.setUsername(rs.getString("username"));
                instructor.setAddress(rs.getString("address"));
                instructor.setPassword(rs.getString("password"));
                instructor.setEmail(rs.getString("email"));
                instructor.setFirstName(rs.getString("firstName"));
                instructor.setLastName(rs.getString("lastName"));
                instructor.setDateOfBirth(rs.getDate("dateOfBirth"));
                instructor.setGender(rs.getString("gender"));
                instructor.setPhoneNumber(rs.getString("phoneNumber"));
                instructor.setProfilePicture(rs.getString("profilePicture"));
                instructor.setUserType(rs.getString("userType"));
                instructor.setRegistrationDate(rs.getDate("registrationDate"));
                instructor.setBio(rs.getString("bio"));
                instructor.setYearsOfExperience(rs.getInt("yearsOfExperience"));
                instructor.setIsActive(rs.getBoolean("isActive"));

            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return instructor;
    }

    @Override
    public List<Instructor> getAll() {
        List<Instructor> instructorList = new ArrayList<>();
        PreparedStatement st;

        try {
            st = cnx.prepareStatement("SELECT * FROM Instructor");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Instructor instructor = new Instructor();
                instructor.setId(rs.getInt("id"));
                instructor.setUsername(rs.getString("username"));
                instructor.setAddress(rs.getString("address"));
                instructor.setPassword(rs.getString("password"));
                instructor.setEmail(rs.getString("email"));
                instructor.setFirstName(rs.getString("firstName"));
                instructor.setLastName(rs.getString("lastName"));
                instructor.setDateOfBirth(rs.getDate("dateOfBirth"));
                instructor.setGender(rs.getString("gender"));
                instructor.setPhoneNumber(rs.getString("phoneNumber"));
                instructor.setProfilePicture(rs.getString("profilePicture"));
                instructor.setUserType(rs.getString("userType"));
                instructor.setRegistrationDate(rs.getDate("registrationDate"));
                instructor.setBio(rs.getString("bio"));
                instructor.setYearsOfExperience(rs.getInt("yearsOfExperience"));
                instructor.setIsActive(rs.getBoolean("isActive"));
                instructorList.add(instructor);
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return instructorList;
    }
    
     public boolean isEmailExists(String email) {
        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT COUNT(*) FROM Instructor WHERE email = ?");
            pst.setString(1, email);
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0; // Return true if email exists (count > 0)
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception by returning false
        }
    }
     
     public boolean isUsernameExists(String username) {
    try {
        PreparedStatement pst = cnx.prepareStatement("SELECT COUNT(*) FROM Instructor WHERE username = ?");
        pst.setString(1, username);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
     
     public boolean isPhoneNumberExists(String phoneNumber) {
    try {
        PreparedStatement pst = cnx.prepareStatement("SELECT COUNT(*) FROM Instructor WHERE phoneNumber = ?");
        pst.setString(1, phoneNumber);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
     public String getPassByEmail(String email) {
        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT password FROM Instructor WHERE email = ?");
            pst.setString(1, email);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                
                return password;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if the email doesn't exist or if there was an error
    }
     
      public boolean isActiveById(int id) {
    try {
        PreparedStatement pst = cnx.prepareStatement("SELECT COUNT(*) FROM instructor WHERE id = ? AND isActive = 1");
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        return count > 0; // Return true if a valid student with the given ID exists (count > 0)
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Handle the exception by returning false
    }
     
     
}
      
      
      public void toggleAccountStatus(int id) {
    try {
        // First, check the current isActive status of the student
        PreparedStatement checkStmt = cnx.prepareStatement("SELECT isActive FROM instructor WHERE id = ?");
        checkStmt.setInt(1, id);
        ResultSet resultSet = checkStmt.executeQuery();

        if (resultSet.next()) {
            boolean currentStatus = resultSet.getBoolean("isActive");
            // Toggle the isActive status to the opposite
            boolean newStatus = !currentStatus;

            // Update the isActive status in the database
            PreparedStatement updateStmt = cnx.prepareStatement("UPDATE instructor SET isActive = ? WHERE id = ?");
            updateStmt.setBoolean(1, newStatus);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
      
  public  String getPhoneNumberdByEmail(String email) {
    try {
        PreparedStatement pst = cnx.prepareStatement("SELECT phoneNumber FROM instructor WHERE email = ?");
        pst.setString(1, email);
        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            String phoneNumber = resultSet.getString("phoneNumber");
            

            return phoneNumber;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Return null if the email doesn't exist or if there was an error
}
  
  public List<Instructor> getInstructorByEmail(String email) {
    List<Instructor> instructorsByEmail = new ArrayList<>();
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        // Prepare the SQL query
        String query = "SELECT * FROM Instructor WHERE email = ?";
        pst = cnx.prepareStatement(query);
        pst.setString(1, email);

        rs = pst.executeQuery();

        while (rs.next()) {
            Instructor instructor = new Instructor();
            instructor.setId(rs.getInt("id"));
            instructor.setUsername(rs.getString("username"));
            instructor.setPassword(rs.getString("password"));
            instructor.setEmail(rs.getString("email"));
            instructor.setFirstName(rs.getString("firstName"));
            instructor.setLastName(rs.getString("lastName"));
            instructor.setDateOfBirth(rs.getDate("dateOfBirth"));
            instructor.setGender(rs.getString("gender"));
            instructor.setAddress(rs.getString("address"));
            instructor.setPhoneNumber(rs.getString("phoneNumber"));
            instructor.setProfilePicture(rs.getString("profilePicture"));
            instructor.setUserType(rs.getString("userType"));
            instructor.setRegistrationDate(rs.getDate("registrationDate"));
            instructor.setYearsOfExperience(rs.getInt("yearsOfExperience"));
            instructor.setBio(rs.getString("bio"));
            instructor.setIsActive(rs.getBoolean("isActive"));

            instructorsByEmail.add(instructor);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return instructorsByEmail;
}

  


}

