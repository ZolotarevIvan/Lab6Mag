package org.example;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //CRUDWorker.addAlbum("Трамвайные пути","Hip-hop",2);
        System.out.println(CRUDWorker.readArtistData("select * from artist"));
        System.out.println(CRUDWorker.readAlbumData("select * from album"));

        //CRUDWorker.addTrack("Ратататта","00:01:58",1);
        //CRUDWorker.changeNameArtist(5,"Lady Gaga");

        //System.out.println(CRUDWorker.readTrackData("select * from track"));

    }
}


