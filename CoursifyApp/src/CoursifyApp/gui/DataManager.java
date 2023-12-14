package CoursifyApp.gui;

import CoursifyApp.entities.*;
import CoursifyApp.entities.Message;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Message> messageList;
    private List<CoursifyApp.entities.Comment> commentList;

    private DataManager() {
        messageList = new ArrayList<>();
        commentList = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public List<CoursifyApp.entities.Comment> getCommentList() {
        return commentList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void setCommentList(List<CoursifyApp.entities.Comment> commentList) {
        this.commentList = commentList;
    }
}
