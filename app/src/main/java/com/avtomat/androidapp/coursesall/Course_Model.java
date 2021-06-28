package com.avtomat.androidapp.coursesall;

public class Course_Model {

    private String coursename;
    private String coursetype;
    private String coursedetail;
    private String courseusers;
    private String courselength;
    private String coursebody;
    private String courseuri;
    private String courseid;
    private String courserating;

    public Course_Model(){
    }

    public Course_Model(String coursename, String coursetype, String coursedetail, String courseusers, String courselength, String coursebody, String courseuri, String courseid, String courserating) {
        this.coursename = coursename;
        this.coursetype = coursetype;
        this.coursedetail = coursedetail;
        this.courseusers = courseusers;
        this.courselength = courselength;
        this.coursebody = coursebody;
        this.courseuri = courseuri;
        this.courseid = courseid;
        this.courserating = courserating;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCoursetype() {
        return coursetype;
    }

    public void setCoursetype(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getCoursedetail() {
        return coursedetail;
    }

    public void setCoursedetail(String coursedetail) {
        this.coursedetail = coursedetail;
    }

    public String getCourseusers() {
        return courseusers;
    }

    public void setCourseusers(String courseusers) {
        this.courseusers = courseusers;
    }

    public String getCourselength() {
        return courselength;
    }

    public void setCourselength(String courselength) {
        this.courselength = courselength;
    }

    public String getCoursebody() {
        return coursebody;
    }

    public void setCoursebody(String coursebody) {
        this.coursebody = coursebody;
    }

    public String getCourseuri() {
        return courseuri;
    }

    public void setCourseuri(String courseuri) {
        this.courseuri = courseuri;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCourserating() {
        return courserating;
    }

    public void setCourserating(String courserating) {
        this.courserating = courserating;
    }
}
