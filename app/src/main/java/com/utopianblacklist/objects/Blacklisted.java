package com.utopianblacklist.objects;

public class Blacklisted {
    private String[] blacklisted;

    private String user;

    public Blacklisted (String[] blacklisted, String user) {
        this.blacklisted = blacklisted;
        this.user = user;
    }

    public String[] getBlacklisted () {
        return blacklisted;
    }

    public void setBlacklisted (String[] blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getUser () {
        return user;
    }

    public void setUser (String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo [blacklisted = "+blacklisted+", user = "+user+"]";
    }
}
