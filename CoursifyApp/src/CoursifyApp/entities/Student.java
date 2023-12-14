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
public class Student extends User{


    private String currentGradeLevel;
    private String major;
    //private String[] coursesEnrolled;
    //private String[] certifications;

    public Student() {
        this.setUserType("student");
        this.setIsActive(true);
    }

    public Student(String currentGradeLevel, String major, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
        this.currentGradeLevel = currentGradeLevel;
        this.major = major;
    }

    public Student(String currentGradeLevel, String major, int id, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, String gender, String address, String phoneNumber, String profilePicture, String userType, Date registrationDate, Boolean isActive) {
        super(id, username, password, email, firstName, lastName, dateOfBirth, gender, address, phoneNumber, profilePicture, userType, registrationDate, isActive);
        this.currentGradeLevel = currentGradeLevel;
        this.major = major;
    }



    public String getCurrentGradeLevel() {
        return currentGradeLevel;
    }

    public void setCurrentGradeLevel(String currentGradeLevel) {
        this.currentGradeLevel = currentGradeLevel;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "currentGradeLevel='" + currentGradeLevel + '\'' +
                ", major='" + major + '\'' + super.toString()+
                '}';
    }
}
