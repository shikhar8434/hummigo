package com.hummo.hummigo.stories;

public class Slide {

    private int id;
    private String author;
    private String date;
    private String heading;
    private String desc;
    private String imageURL;
    private String readURL;
    private int likesCount;

    public Slide(int id, String author, String date, String heading, String desc, String imageURL, String readURL, int likesCount) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.heading = heading;
        this.desc = desc;
        this.imageURL = imageURL;
        this.readURL = readURL;
        this.likesCount = likesCount;
    }

    public Slide() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getReadURL() {
        return readURL;
    }

    public void setReadURL(String readURL) {
        this.readURL = readURL;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
