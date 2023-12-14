/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;

/**
 *
 * @author ghanm
 */
public class Instructor extends User{
    //private String [] coursesTaught;
    //private String[] studentsEnrolled;
    // private String [] expertiseAreas;
    private int yearsOfExperience;
    private String  bio;
   // private Double [] ratings;

    public Instructor() {
        this.setUserType("instructor");
        this.setIsActive(true);
    }

    public Instructor(int yearsOfExperience, String bio, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
        this.yearsOfExperience = yearsOfExperience;
        this.bio = bio;
    }

    public Instructor(int yearsOfExperience, String bio, int id, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(id, username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
        this.yearsOfExperience = yearsOfExperience;
        this.bio = bio;
    }


    
    /*public String[] getExpertiseAreas() {
        return expertiseAreas;
    }*/

   /* public void setExpertiseAreas(String[] expertiseAreas) {
        this.expertiseAreas = expertiseAreas;
    }*/

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

   /* public Double[] getRatings() {
        return ratings;
    }

    public void setRatings(Double[] ratings) {
        this.ratings = ratings;
    }*/

    @Override
    public String toString() {
        return "Instructor{" +
              //  "expertiseAreas=" Arrays.toString(expertiseAreas) +
                "yearsOfExperience=" + yearsOfExperience +
                ", bio='" + bio + '\'' +
                //", ratings=" + Arrays.toString(ratings) 
                 super.toString() +
                '}';
    }
}

