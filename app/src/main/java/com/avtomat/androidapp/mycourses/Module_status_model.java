package com.avtomat.androidapp.mycourses;

public class Module_status_model {

    private String arlabstatus;
    private String moduleid;
    private String quizstatus;

    public Module_status_model() {
    }

    public Module_status_model(String arlabstatus, String moduleid, String quizstatus) {
        this.arlabstatus = arlabstatus;
        this.moduleid = moduleid;
        this.quizstatus = quizstatus;
    }

    public String getArlabstatus() {
        return arlabstatus;
    }

    public void setArlabstatus(String arlabstatus) {
        this.arlabstatus = arlabstatus;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getQuizstatus() {
        return quizstatus;
    }

    public void setQuizstatus(String quizstatus) {
        this.quizstatus = quizstatus;
    }
}
