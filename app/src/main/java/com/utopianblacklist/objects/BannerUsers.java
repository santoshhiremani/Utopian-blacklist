package com.utopianblacklist.objects;


public class BannerUsers {

    private Ban_start ban_start;
    private _id _id;
    private Banned_until banned_until;
    private String ban_length;
    private String name;
    private String banned;

    public BannerUsers (Ban_start ban_start, _id _id, Banned_until banned_until, String name, String banned) {
        this.ban_start = ban_start;
        this._id = _id;
        this.banned_until = banned_until;
        this.name = name;
        this.banned = banned;
    }

    public Ban_start getBan_start () {
        return ban_start;
    }

    public void setBan_start (Ban_start ban_start) {
        this.ban_start = ban_start;
    }

    public _id get_id () {
        return _id;
    }

    public void set_id (_id _id) {
        this._id = _id;
    }

    public Banned_until getBanned_until () {
        return banned_until;
    }

    public void setBanned_until (Banned_until banned_until) {
        this.banned_until = banned_until;
    }

    public String getBan_length () {
        return ban_length;
    }

    public void setBan_length (String ban_length) {
        this.ban_length = ban_length;
    }

    public String getName () {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getBanned () {
        return banned;
    }

    public void setBanned (String banned)
    {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "BannerUsers [ban_start = "+ban_start+", _id = "+_id+", banned_until = "+banned_until+", ban_length = "+ban_length+", name = "+name+", banned = "+banned+"]";
    }
}
