/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.test;


import CoursifyApp.entities.Abonnement;
import CoursifyApp.entities.Course;
import CoursifyApp.entities.Notification;
import CoursifyApp.entities.Review;
import CoursifyApp.services.AbonnementService;
import CoursifyApp.services.CourseService;
import CoursifyApp.services.ReviewService;
import CoursifyApp.utilities.Myconnection;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ghanm
 */
public class Main {
    public static final String ACCOUNT_SID = "ACc3a71326ea77cad937017da841c1a175";
    public static final String AUTH_TOKEN = "864564c1c893f9c39cbc67bc45405818";

    public static void main(String[] args) {
        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Initialize database connection
        Myconnection myconnection = Myconnection.getInstance();

        // Sample date strings  
        String str = "2022-08-10";
        String str2 = "2022-06-10";

        // Sample course content array
        String[] courseContentArray = {"Module 1", "Module 2", "Module 3"};

        // Create an instance of CourseService
        CourseService courseService = new CourseService();

        // Sample Course data
        
        //CourseService courseService = new CourseService();
//List<Course> topRatedCourses = courseService.getTopRatedCourses(2); // Récupérer les 10 meilleurs cours
//for (Course course : topRatedCourses) {
//    System.out.println("Course: " + course.getTitle() + " - Instructor: " + course.getInstructorName() + " - Average Rating: " + course.getAverageRating());
//}

//      
//  Course newCourse = new Course(
//            12345, "springboot", "Course Description",
//            49.99f, "springimage.jpg", true,
//            new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
//            Arrays.asList(courseContentArray), 20, "hsouna"
//        );
//        courseService.create(newCourse);
//        String messageText = "New course added: " + newCourse.getTitle() + " by " + newCourse.getInstructorName();
//        System.out.println("Course added successfully");
//        Message message = Message.creator(
//            new com.twilio.type.PhoneNumber("+21623671593"),
//            new com.twilio.type.PhoneNumber("+12315257545"),
//            messageText
//        ).create();
//        System.out.println("SMS sent: " + message.getSid());

        // Test CourseService functionalities

        // Update a course
        // Course courseToUpdate = courseService.getById(39);
        // if (courseToUpdate != null) {
        //     courseToUpdate.setTitle("Updated Title");
        //     courseToUpdate.setDescription("Updated Description");
        //     courseToUpdate.setPrice(59.99f);
        //     courseToUpdate.setImage("updated_image.jpg");
        //     courseService.update(courseToUpdate);
        //     System.out.println("Course updated successfully");
        // } else {
        //     System.out.println("Course not found with the given ID.");
        // }

        // Get all courses
        // List<Course> courses = courseService.getAll();
        // for (Course course : courses) {
        //     System.out.println(course);
        // }

        // Get a course by ID
        // int courseIdToFetch = 41; // Replace with the actual course ID you want to retrieve
        // Course retrievedCourse = courseService.getById(courseIdToFetch);
        // if (retrievedCourse != null) {
        //     System.out.println("Course retrieved: " + retrievedCourse);
        // } else {
        //     System.out.println("Course not found with courseId: " + courseIdToFetch);
        // }

//        // Create an instance of ReviewService
//        ReviewService reviewService = new ReviewService();
//
//        // Create a new review
//       Review newReview = new Review(2, 86, "Introduction to Java", "John Doe", 5, "Excellent Course", "I learned a lot!", new Date(System.currentTimeMillis()));
//              reviewService.create(newReview);

        // Retrieve and update a review (commented out for now)
        // Review retrievedReview = reviewService.getById(3);
        // if (retrievedReview != null) {
        //     System.out.println("Retrieved Review: " + retrievedReview);
        // }
        // if (retrievedReview != null) {
        //     retrievedReview.setRating(4);
        //     reviewService.update(retrievedReview);
        //     System.out.println("Review updated: " + retrievedReview);
        // }

        // Get all reviews and print them
        // List<Review> allReviews = reviewService.getAll();
        // System.out.println("All Reviews:");
        // for (Review review : allReviews) {
        //     System.out.println(review);
        // }

        // Retrieve and update a review
        // int reviewIdToUpdate = 8;
        // Review retrievedReview = reviewService.getById(reviewIdToUpdate);
        // if (retrievedReview != null) {
        //     retrievedReview.setRating(4);
        //     reviewService.update(retrievedReview);
        //     System.out.println("Review updated successfully: " + retrievedReview);
        // } else {
        //     System.out.println("Review not found with reviewId: " + reviewIdToUpdate);
        // }
        
//        int reviewIdToDelete = 16; 
//        reviewService.delete(reviewIdToDelete);

   
//           List<Course> topRatedCourses = courseService.getTopRatedCourses(1);
//
//    // Display the top-rated courses
//    if (topRatedCourses.isEmpty()) {
//        System.out.println("No top-rated courses found.");
//    } else {
//        System.out.println("Top Rated Courses:");
//        for (Course course : topRatedCourses) {
//            //System.out.println("Course ID: " + course.getCourseId());
//            System.out.println("Title: " + course.getTitle());
//            System.out.println("Average Rating: " + course.getAverageRating());
//          //  System.out.println("Description: " + course.getDescription());
//            // Print other course details as needed
//            System.out.println();
//        }
//    }
   

//     AbonnementService abonnementService = new AbonnementService();
//
//        // Create a new Abonnement
//        Abonnement abonnement = new Abonnement(
//            0, // idAbonnement (0 because it's auto-generated in your database)
//            "John Doe", // user
//            "Java Course", // cours
//            99.99f, // price
//            "year", // typeAbonnement
//            new java.util.Date(), // dateDebut
//            new java.util.Date(), // dateExpiration
//            "Pending" // statutPaiement
//        );
//
//        // Add the Abonnement to the database
//        abonnementService.create(abonnement);
//
//     AbonnementService abonnementService = new AbonnementService();
//
//        // Retrieve all abonnements
//
//Abonnement newAbonnement = new Abonnement(1, "username", "course", 99.99f, "Type 1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 86400000)); // Adjust dates as needed
//abonnementService.create(newAbonnement);

//AbonnementService abonnementService = new AbonnementService();
//
//        // Retrieve an Abonnement by username
//        String username = "John Doe";
//        Abonnement retrievedAbonnement = abonnementService.getById(2);
//        if (retrievedAbonnement != null) {
//            System.out.println("Retrieved Abonnement: " + retrievedAbonnement.getUser());
//
//            // Update the Abonnement
//            retrievedAbonnement.setPrice((float) 59.99);
//            abonnementService.update(retrievedAbonnement);
//
//            // Check if the update was successful
//            Abonnement updatedAbonnement = abonnementService.getByName(username);
//            if (updatedAbonnement != null) {
//                System.out.println("Updated Price: " + updatedAbonnement.getPrice());
//            } else {
//                System.out.println("Abonnement not found for user: " + username);
//            }
//        } else {
//            System.out.println("Abonnement not found for user: " + username);
//        }
   

        // Spécifiez le nombre limite de cours que vous souhaitez récupérer
//        int limit = 5;
//
//        List<Course> topRatedCourses = courseService.getTopRatedCourses(limit);
//
//        System.out.println("Top " + limit + " Rated Courses:");
//        for (Course course : topRatedCourses) {
//            System.out.println("Course Title: " + course.getTitle());
//            System.out.println("Instructor Name: " + course.getInstructorName());
//            System.out.println("Average Rating: " + course.getAverageRating());
//            System.out.println();
//        }
    
    

//     AbonnementService abonnementService = new AbonnementService();
//
//        // Call the findLongestAbonnement method
//        Abonnement longestAbonnement = abonnementService.findLongestAbonnement();
//
//        if (longestAbonnement != null) {
//            System.out.println("Longest Abonnement found:");
//            System.out.println("User: " + longestAbonnement.getUser());
//            System.out.println("Course: " + longestAbonnement.getCours());
//            System.out.println("Type: " + longestAbonnement.getTypeAbonnement());
//            System.out.println("Price: " + longestAbonnement.getPrice());
//            System.out.println("Date Debut: " + longestAbonnement.getDateDebut());
//            System.out.println("Date Expiration: " + longestAbonnement.getDateExpiration());
//        } else {
//            System.out.println("No Abonnement found in the database.");
//        }
   //  CourseService courseService = new CourseService();

        // Specify the number of courses you want to retrieve
       // Display the retrieved courses
 List<Course> courses = courseService.getTopPricedCoursesWithBadRating(10); // Adjust the limit as needed

        if (courses.isEmpty()) {
            System.out.println("No courses found with bad ratings and high prices.");
        } else {
            System.out.println("Top Priced Courses with Bad Ratings:");
            for (Course course : courses) {
                System.out.println("Course Title: " + course.getTitle());
                System.out.println("Instructor Name: " + course.getInstructorName());
                System.out.println("Average Rating: " + course.getAverageRating());
                System.out.println("----------------------");
            }
        }

    }
    
    
    






    }








    
    
    
    
    
    
    
    
    
   
     
    
    
    







































































      
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
   

