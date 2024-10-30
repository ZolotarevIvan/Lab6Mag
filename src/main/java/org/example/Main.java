package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //System.out.println(CRUDAlbum.readAlbumData("select * from album"));
        //System.out.println(CRUDTrack.readTrackData("select * from track"));
        CRUDTrack.findShortestTracks();
        //CRUDTrack.getArtistsWithTrackCount();
        //CRUDTrack.getArtistsWithMoreThanTracks(3);
        /*
        List<String> tracks = CRUDArtist.getTracksByArtist("Morgenshtern");

        // Выводим названия треков
        for (String track : tracks) {
            System.out.println(track);
        } */
        /*
        CRUDAlbum.fetchAlbumAndArtist();

        CRUDTrack.findShortestTracks();
        CRUDAlbum.deleteAlbum(6);

        System.out.println(CRUDTrack.readTrackData("select * from track"));
        */
        //System.out.println(CRUDArtist.readArtistData("select * from artist"));
        //System.out.println(CRUDAlbum.readAlbumData("select * from album"));


    }
}


