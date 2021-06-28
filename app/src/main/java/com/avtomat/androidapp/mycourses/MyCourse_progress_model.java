package com.avtomat.androidapp.mycourses;

public class MyCourse_progress_model {

    private String maintext;
    private String desctext;
    private String moduleid;
    private String quizlink;
    private String arlabid;
    private String youtubeid;
    private String youtubelink;

    public MyCourse_progress_model() {
    }

    public MyCourse_progress_model(String maintext, String desctext, String moduleid, String quizlink, String arlabid, String youtubeid, String youtubelink) {
        this.maintext = maintext;
        this.desctext = desctext;
        this.moduleid = moduleid;
        this.quizlink = quizlink;
        this.arlabid = arlabid;
        this.youtubeid = youtubeid;
        this.youtubelink = youtubelink;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getDesctext() {
        return desctext;
    }

    public void setDesctext(String desctext) {
        this.desctext = desctext;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getQuizlink() {
        return quizlink;
    }

    public void setQuizlink(String quizlink) {
        this.quizlink = quizlink;
    }

    public String getArlabid() {
        return arlabid;
    }

    public void setArlabid(String arlabid) {
        this.arlabid = arlabid;
    }

    public String getYoutubeid() {
        return youtubeid;
    }

    public void setYoutubeid(String youtubeid) {
        this.youtubeid = youtubeid;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }
}
