package com.example.mj.parkingsystem.UsersPackage;

/**
 * Created by MJ on 3/19/2018.
 */


public class Booking {

    String user_id;
    String plote_no;
    String startTime;
    String endTime;
    String status;
    String price;
    String selectedDate;
    String duration;

    public Booking() {


    }



    public Booking(String user_id, String plote_no, String startTime, String endTime, String status,
                   String price, String duration, String selectedDate) {
        this.user_id= user_id;
        this.plote_no= plote_no;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status=status;
        this.price=price;
        this.duration=duration;
        this.selectedDate=selectedDate;
    }




    public String getPlote_no() {
        return plote_no;
    }

    public void setPlote_no(String plote_no) {
            this.plote_no= plote_no;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}

