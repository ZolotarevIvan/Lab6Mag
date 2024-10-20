package org.example;

public class Main {
    public static void main(String[] args) {

        CRUDAlbum.fetchAlbumAndArtist();

        CRUDTrack.findShortestTracks();

        System.out.println(CRUDTrack.readTrackData("select * from track"));

        //System.out.println(CRUDArtist.readArtistData("select * from artist"));
        //System.out.println(CRUDAlbum.readAlbumData("select * from album"));


    }
}


