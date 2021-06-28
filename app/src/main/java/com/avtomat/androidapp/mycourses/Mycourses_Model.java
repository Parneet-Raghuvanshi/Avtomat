package com.avtomat.androidapp.mycourses;

public class Mycourses_Model {

    public String courseid;
    public String status;

    public Mycourses_Model() {
    }

    public Mycourses_Model(String courseid, String status) {
        this.courseid = courseid;
        this.status = status;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
