package com.example.mj.parkingsystem.UsersPackage;

/**
 * Created by MJ on 4/8/2018.
 */

public class FeedbacksPojo {




    String feedbacks;
    String user_id;


    String usename;


    public FeedbacksPojo(String feedbacks, String user_id, String username) {


        this.feedbacks=feedbacks;
        this.usename= username;
        this.user_id=user_id;

    }

    public FeedbacksPojo() {



    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = feedbacks;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }


}
