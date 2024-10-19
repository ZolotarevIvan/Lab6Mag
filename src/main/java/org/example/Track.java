package org.example;

public class Track {
    private int id;
    private String title;
    private int duration;
    private int album_id;

    public  Track(){}

    public Track(int id,String title,int duration,int album_id){
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.album_id = album_id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    @Override
    public String toString(){
        return "\n" + "{" + "id=" + id + ", "+ "название: "+title+", "+"длительность= "+duration+"сек, "+"id альбома="+album_id+"}";
    }
}
