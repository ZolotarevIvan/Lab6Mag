package org.example;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Artist> artists = CRUDWorker.readArtistData("SELECT * FROM artist");
        System.out.println(artists);

        List<Album> albums = CRUDWorker.readAlbumData("SELECT * FROM album");
        System.out.println(albums);

        List<Track> tracks = CRUDWorker.readTrackData("SELECT * FROM track");
        System.out.println(tracks);


    }
}