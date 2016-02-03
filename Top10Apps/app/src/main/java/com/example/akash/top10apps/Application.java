package com.example.akash.top10apps;

/**
 * Created by akash on 10/2/15.
 */
public class Application {
    private String name;
    private String releasedate;
    private String content;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String category;
    private String summary;


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {

        return "Name: " + getName() + "\n" + "Artist: " + getCategory() + "Release Date : " + getReleasedate()
                +"\n";

    }
}
