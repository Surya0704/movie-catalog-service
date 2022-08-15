package io.javabrains.moviecatalogservice.models;

import java.util.List;

public class UserRating {
    private List<Rating> userRating;
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /*public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }*/

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> ratingsList) {
        this.userRating = ratingsList;
    }
}
