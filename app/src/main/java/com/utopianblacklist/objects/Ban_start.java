package com.utopianblacklist.objects;

public class Ban_start {
    private long $date;

    public long get$date () {
        return $date;
    }

    public void set$date (long $date) {
        this.$date = $date;
    }

    @Override
    public String toString() {
        return "Ban_start [$date = "+$date+"]";
    }
}
