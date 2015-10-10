package com.example.akash.top25songs;

/**
 * Created by akash on 10/10/15.
 */
public class Applications {

    private String id;
    private String artist;
    private String price;
    private String releasedate;
    private String title;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString(){
        return "Name : " + getName() +"\n"  + "ID :" + getId() + "\n" + "Artist :" + getArtist() + "\n"+ "Price :" + getPrice() + "\n" +
                "Release Date :" + getReleasedate() + "\n" + "Title: " + getTitle() + "\n";
    }
}
