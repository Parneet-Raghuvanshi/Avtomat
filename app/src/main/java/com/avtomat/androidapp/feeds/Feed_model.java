package com.avtomat.androidapp.feeds;

public class Feed_model {

    private String feedhead;
    private String feeduri;

    public Feed_model() {
    }

    public Feed_model(String feedhead, String feeduri) {
        this.feedhead = feedhead;
        this.feeduri = feeduri;
    }

    public String getFeedhead() {
        return feedhead;
    }

    public void setFeedhead(String feedhead) {
        this.feedhead = feedhead;
    }

    public String getFeeduri() {
        return feeduri;
    }

    public void setFeeduri(String feeduri) {
        this.feeduri = feeduri;
    }
}
