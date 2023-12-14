package CoursifyApp.services;

import CoursifyApp.entities.Review;
import CoursifyApp.utilities.Myconnection;
import CoursifyApp.entities.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ReviewService implements CrudInterface<Review> {
    private Connection cnx;

    public ReviewService() {
        cnx = Myconnection.getInstance().getCnx();
    }

//    @Override
//    public void create(Review review) {
//        String query = "INSERT INTO review (courseId, coursetitle, reviewerName, rating, commentTitle, commentContent, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement pst = cnx.prepareStatement(query)) {
//            pst.setLong(1, review.getCourseId());
//            pst.setString(2, review.getCourseTitle());
//            pst.setString(3, review.getReviewerName());
//            pst.setInt(4, review.getRating());
//            pst.setString(5, review.getCommentTitle());
//            pst.setString(6, review.getCommentContent());
//            pst.setObject(7, review.getCreatedAt());
//            pst.executeUpdate();
//            System.out.println("Review added successfully");
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
    
    
    @Override
public void create(Review review) {
    if (review == null) {
        System.err.println("Review is null. Cannot create a null review.");
        return;
    }

    String query = "INSERT INTO review (courseId, coursetitle, reviewerName, rating, commentTitle, commentContent, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setLong(1, review.getCourseId());
        pst.setString(2, review.getCourseTitle());
        pst.setString(3, review.getReviewerName());
        pst.setInt(4, review.getRating());

        // Check if other properties are null and set them accordingly
        if (review.getCommentTitle() != null) {
            pst.setString(5, review.getCommentTitle());
        } else {
            // Handle null comment title (e.g., set a default value or skip)
            pst.setNull(5, Types.VARCHAR);
        }

        if (review.getCommentContent() != null) {
            pst.setString(6, review.getCommentContent());
        } else {
            // Handle null comment content (e.g., set a default value or skip)
            pst.setNull(6, Types.VARCHAR);
        }

        // Ensure createdAt is not null (you may set a default timestamp if needed)
        if (review.getCreatedAt() != null) {
            pst.setObject(7, review.getCreatedAt());
        } else {
            // Handle null createdAt (e.g., set a default timestamp or skip)
            pst.setNull(7, Types.TIMESTAMP);
        }

        pst.executeUpdate();
        System.out.println("Review added successfully");
    } catch (SQLException ex) {
        System.err.println("Error adding review: " + ex.getMessage());
    }
}

public void update(Review review) {
    String query = "UPDATE review SET courseTitle = ?, reviewerName = ?, rating = ?, commentTitle = ?, commentContent = ?, createdAt = ? WHERE reviewId = ?";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setString(1, review.getCourseTitle());
        pst.setString(2, review.getReviewerName());
        pst.setInt(3, review.getRating());
        pst.setString(4, review.getCommentTitle());
        pst.setString(5, review.getCommentContent());
        pst.setObject(6, review.getCreatedAt());
        pst.setLong(7, review.getReviewId());

        pst.executeUpdate();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

//    @Override
//    public void update(Review review) {
//        String query = "UPDATE review SET courseId=?, coursetitle=?, reviewerName=?, rating=?, commentTitle=?, commentContent=?, createdAt=? WHERE reviewId=?";
//        try (PreparedStatement pst = cnx.prepareStatement(query)) {
//            pst.setLong(1, review.getCourseId());
//            pst.setString(2, review.getCourseTitle());
//            pst.setString(3, review.getReviewerName());
//            pst.setInt(4, review.getRating());
//            pst.setString(5, review.getCommentTitle());
//            pst.setString(6, review.getCommentContent());
//            pst.setObject(7, review.getCreatedAt());
//            pst.setLong(8, review.getReviewId());
//
//            pst.executeUpdate();
//            System.out.println("Review updated successfully");
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }

    @Override
    public void delete(int reviewId) {
        String query = "DELETE FROM review WHERE reviewId=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, reviewId);
            pst.executeUpdate();
            System.out.println("Review deleted successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Review getById(int reviewId) {
        String query = "SELECT * FROM review WHERE reviewId = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, reviewId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Review review = new Review(
                        rs.getLong("reviewId"),
                        rs.getLong("courseId"),
                        rs.getString("coursetitle"),
                        rs.getString("reviewerName"),
                        rs.getInt("rating"),
                        rs.getString("commentTitle"),
                        rs.getString("commentContent"),
                        rs.getTimestamp("createdAt")
                );
                return review;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null; // Return null if the review with the given reviewId is not found
    }

    @Override
    public List<Review> getAll() {
        List<Review> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                        rs.getLong("reviewId"),
                        rs.getLong("courseId"),
                        rs.getString("coursetitle"),
                        rs.getString("reviewerName"),
                        rs.getInt("rating"),
                        rs.getString("commentTitle"),
                        rs.getString("commentContent"),
                        rs.getTimestamp("createdAt")
                );
                reviewList.add(review);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return reviewList;
    }


    public boolean isCourseTitleValid(String courseTitle) {
        List<String> availableCourseTitles = getAvailableCourseTitles();
        return availableCourseTitles.contains(courseTitle);
    }

    private List<String> getAvailableCourseTitles() {

        List<String> availableTitles = new ArrayList<>();
        availableTitles.add("Course 1");
        availableTitles.add("Course 2");
        return availableTitles;
    }




}
