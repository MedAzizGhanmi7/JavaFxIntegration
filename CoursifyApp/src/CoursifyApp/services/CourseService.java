/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

/**
 *
 * @author hassa
 */
import CoursifyApp.entities.TwilioSMS;
import CoursifyApp.entities.Course;
import CoursifyApp.utilities.Myconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CoursifyApp.entities.Course;
import CoursifyApp.utilities.Myconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseService implements CrudInterface<Course> {
    private Connection cnx;

    public CourseService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
   
public void create(Course course) {
    String query = "INSERT INTO course (title, description, price, image, isActive, updatedAt, createdAt, courseContent, instructorId, instructorName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setString(1, course.getTitle());
        pst.setString(2, course.getDescription());
        pst.setFloat(3, course.getPrice());
        pst.setString(4, course.getImage());
        pst.setBoolean(5, course.isActive());
        pst.setObject(6, course.getUpdatedAt());
        pst.setObject(7, course.getCreatedAt());
        pst.setString(8, String.join(",", course.getCourseContent()));
        pst.setLong(9, course.getInstructorId());
        pst.setString(10, course.getInstructorName());

        pst.executeUpdate();
        System.out.println("Course added successfully");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

//public void createCourseWithInstructor(Course course,int instructorId) {
//    String query = "INSERT INTO course (title, description, price, image, isActive, updatedAt, createdAt, courseContent, instructorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    try (PreparedStatement pst = cnx.prepareStatement(query)) {
//        pst.setString(1, course.getTitle());
//        pst.setString(2, course.getDescription());
//        pst.setFloat(3, course.getPrice());
//        pst.setString(4, course.getImage());
//        pst.setBoolean(5, course.isActive());
//        pst.setObject(6, course.getUpdatedAt());
//        pst.setObject(7, course.getCreatedAt());
//        pst.setString(8, String.join(",", course.getCourseContent()));
//        pst.setInt(9,instructorId );
//        pst.executeUpdate();
//        System.out.println("Course added successfully");
//    } catch (SQLException ex) {
//        System.err.println(ex.getMessage());
//    }
//}

    @Override
    public void update(Course course) {
        String query = "UPDATE course SET title=?, description=?, price=?, image=?, isActive=?, updatedAt=?, courseContent=?, instructorId=?, instructorName=? WHERE courseId=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, course.getTitle());
            pst.setString(2, course.getDescription());
            pst.setFloat(3, course.getPrice());
            pst.setString(4, course.getImage());
            pst.setBoolean(5, course.isActive());
            pst.setObject(6, course.getUpdatedAt());
            pst.setString(7, String.join(",", course.getCourseContent()));
            pst.setLong(8, course.getInstructorId());
            pst.setString(9, course.getInstructorName());
            pst.setLong(10, course.getCourseId());

            pst.executeUpdate();
            System.out.println("Course updated successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int courseId) {
        String query = "DELETE FROM course WHERE courseId=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, courseId);
            pst.executeUpdate();
            System.out.println("Course deleted successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Course getById(int courseId) {
        String query = "SELECT * FROM course WHERE courseId=?";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setLong(1, courseId);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                Course course = new Course(
                    rs.getLong("courseId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getFloat("price"),
                    rs.getString("image"),
                    rs.getBoolean("isActive"),
                    rs.getDate("updatedAt"),
                    rs.getDate("createdAt"),
                    new ArrayList<>(),
                    rs.getLong("instructorId"),
                    rs.getString("instructorName")
                );
                course.setUpdatedAt(new java.util.Date(course.getUpdatedAt().getTime()));
                course.setCreatedAt(new java.util.Date(course.getCreatedAt().getTime()));
                String courseContentString = rs.getString("courseContent");
                List<String> courseContentList = Arrays.asList(courseContentString.split(","));
                course.setCourseContent(courseContentList);
                return course;
            }
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return null; // Return null if course is not found
    }

    @Override
public List<Course> getAll() {
    List<Course> courseList = new ArrayList<>();
    String query = "SELECT * FROM course";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                    rs.getLong("courseId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getFloat("price"),
                    rs.getString("image"),
                    rs.getBoolean("isActive"),
                    rs.getDate("updatedAt"), // Use rs.getDate for java.sql.Date
                    rs.getDate("createdAt"), // Use rs.getDate for java.sql.Date
                    new ArrayList<>(),
                    rs.getLong("instructorId"),
                    rs.getString("instructorName")
                );
                course.setUpdatedAt(new java.util.Date(course.getUpdatedAt().getTime())); // Convert java.sql.Date to java.util.Date
                course.setCreatedAt(new java.util.Date(course.getCreatedAt().getTime())); // Convert java.sql.Date to java.util.Date
                String courseContentString = rs.getString("courseContent");
                List<String> courseContentList = Arrays.asList(courseContentString.split(","));
                course.setCourseContent(courseContentList);
                courseList.add(course);
            }
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return courseList;
}


//public List<Course> getTopRatedCourses(int limit) {
//    String query = "SELECT c.courseId, c.title, c.description, c.price, c.image, c.isActive, c.updatedAt, c.createdAt, c.courseContent, c.instructorId, c.instructorName, AVG(r.rating) AS averageRating " +
//                   "FROM course c " +
//                   "LEFT JOIN review r ON c.courseId = r.courseId " +
//                   "GROUP BY c.courseId " +
//                   "ORDER BY averageRating DESC, c.createdAt DESC " +
//                   "LIMIT ?";
//
//    List<Course> topRatedCourses = new ArrayList<>();
//
//    try (Connection connection = cnx;
//         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//        preparedStatement.setInt(1, limit);
//
//        try (ResultSet resultSet = preparedStatement.executeQuery()) {
//            while (resultSet.next()) {
//                Course course = new Course();
//                course.setCourseId(resultSet.getLong("courseId"));
//                course.setTitle(resultSet.getString("title"));
//                course.setDescription(resultSet.getString("description"));
//                course.setPrice(resultSet.getFloat("price"));
//                course.setImage(resultSet.getString("image"));
//                course.setActive(resultSet.getBoolean("isActive"));
//                course.setUpdatedAt(resultSet.getDate("updatedAt"));
//                course.setCreatedAt(resultSet.getDate("createdAt"));
//                course.setCourseContent(Arrays.asList(resultSet.getString("courseContent").split(",")));
//                course.setInstructorId(resultSet.getLong("instructorId"));
//                course.setInstructorName(resultSet.getString("instructorName"));
//                course.setAverageRating(resultSet.getFloat("averageRating"));
//
//                topRatedCourses.add(course);
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        // Handle exceptions
//    }
//
//    return topRatedCourses;
//}
// Import the necessary libraries


// ...

// Replace this with your actual logic to fetch top-rated courses
public List<Course> getTopRatedCourses(int limit) {
    String query = "SELECT c.title AS courseTitle, c.instructorName, AVG(r.rating) AS averageRating " +
                   "FROM Course c " +
                   "LEFT JOIN Review r ON c.courseId = r.courseId " +
                   "GROUP BY c.title, c.instructorName " +
                   "ORDER BY averageRating DESC " +
                   "LIMIT ?";
    
    List<Course> topRatedCourses = new ArrayList<>();

    try (Connection connection = cnx;
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, limit);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String courseTitle = resultSet.getString("courseTitle");
                String instructorName = resultSet.getString("instructorName");
                float averageRating = resultSet.getFloat("averageRating");

                Course course = new Course();
                course.setTitle(courseTitle);
                course.setInstructorName(instructorName);
                course.setAverageRating(averageRating);

                topRatedCourses.add(course);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions
    }

    return topRatedCourses;
}








public boolean isCourseTitleValid(String courseTitle) {
    List<String> availableCourseTitles = getAvailableCourseTitles();
    return availableCourseTitles.contains(courseTitle);
}

private List<String> getAvailableCourseTitles() {
    // Retrieve a list of available course titles from your application's data
    // This can be from a database query or any other source
    // For simplicity, let's assume you have a hardcoded list here
    List<String> availableTitles = Arrays.asList("Course 1", "Course 2");
    // Add more course titles as needed
    return availableTitles;
}



public List<Course> searchCoursesByName(String searchName) {
    List<Course> matchingCourses = new ArrayList<>();
    for (Course course : getAll()) {
        if (course.getTitle().toLowerCase().contains(searchName.toLowerCase())) {
            matchingCourses.add(course);
        }
    }
    return matchingCourses;
}

public List<Course> getTopPricedCoursesWithBadRating(int limit) {
    String query = "SELECT c.courseId, c.title, c.description, c.price, c.image, c.isActive, c.updatedAt, c.createdAt, c.courseContent, c.instructorId, c.instructorName, AVG(r.rating) AS averageRating " +
                   "FROM course c " +
                   "LEFT JOIN review r ON c.courseId = r.courseId " +
                   "GROUP BY c.courseId " +
                   "HAVING AVG(r.rating) < 2.5 " + // Set your threshold for "bad" rating here
                   "ORDER BY c.price DESC " +
                   "LIMIT ?";

    List<Course> courses = new ArrayList<>();

    try (Connection connection = cnx;
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, limit);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getLong("courseId"));
                course.setTitle(resultSet.getString("title"));
                course.setDescription(resultSet.getString("description"));
                course.setPrice(resultSet.getFloat("price"));
                course.setImage(resultSet.getString("image"));
                course.setActive(resultSet.getBoolean("isActive"));
                course.setUpdatedAt(resultSet.getDate("updatedAt"));
                course.setCreatedAt(resultSet.getDate("createdAt"));
                course.setCourseContent(Arrays.asList(resultSet.getString("courseContent").split(",")));
                course.setInstructorId(resultSet.getLong("instructorId"));
                course.setInstructorName(resultSet.getString("instructorName"));
                course.setAverageRating(resultSet.getFloat("averageRating"));

                courses.add(course);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions
    }

    return courses;
}


}































