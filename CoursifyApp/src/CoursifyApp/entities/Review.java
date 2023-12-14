/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

/**
 *
 * @author hassa
 */
import java.time.LocalDateTime;
import java.util.Date;

public class Review {
    private long reviewId;
    private long courseId;
    private String coursetitle; // New attribute for the course title
    private String reviewerName;
    private int rating; // Rating out of 5 or 10
    private String commentTitle;
    private String commentContent;
    private Date createdAt;
    

    public Review() {
        // Default constructor
    }

    public Review(long reviewId, long courseId, String coursetitle, String reviewerName, int rating, String commentTitle, String commentContent, Date createdAt) {
        this.reviewId = reviewId;
        this.courseId = courseId;
        this.coursetitle = coursetitle; // Set coursetitle directly
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.commentTitle = commentTitle;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }

    public Review(String introduction_to_Java, String john_Doe, int i, String excellent_Course, String i_learned_a_lot, java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Review(int i, String introduction_to_Java, String john_Doe, int i0, String excellent_Course, String i_learned_a_lot, java.sql.Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Getters and Setters for all fields

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return coursetitle;
    }

    public void setCourseTitle(String coursetitle) {
        this.coursetitle = coursetitle;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", courseId=" + courseId +
                ", coursetitle='" + coursetitle + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", rating=" + rating +
                ", commentTitle='" + commentTitle + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}