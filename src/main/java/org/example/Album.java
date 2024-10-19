package org.example;

public class Album {
    private int id;
    private String title;
    private  String genre;
    private int artist_id;

    public Album(){}

    public Album(int id,String title,String genre,int artist_id){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.artist_id = artist_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    @Override
    public String toString(){
        return "\n" + "{" + "id=" + id + ", "+ "название: "+ title +", "+ "жанр: "+ genre + ", "+ "id артиста: "+ artist_id + "}";
    }
}
