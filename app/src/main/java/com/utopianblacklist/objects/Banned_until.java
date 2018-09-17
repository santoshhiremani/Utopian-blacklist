package com.utopianblacklist.objects;

public class Banned_until {
    private long $date;

    public long get$date () {
        return $date;
    }

    public void set$date (long $date) {
        this.$date = $date;
    }

    @Override
    public String toString() {
        return "ClassPojo [$date = "+$date+"]";
    }
}
