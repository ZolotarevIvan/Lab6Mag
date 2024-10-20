package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDTrack {

    private static final String INSERT_TRACK = "INSERT INTO track(title,duration,album_id) VALUES (?,?,?);";
    private static final String DELETE_TRACK = "DELETE from track WHERE title = ?;";
    private static final String SHORTEST_TRACK = "SELECT a.title AS album_title, t.title AS shortest_track_title, t.duration AS shortest_track_duration FROM album a JOIN track t ON a.id = t.album_id WHERE t.duration >= '00:03:00' AND t.duration = ( SELECT MIN(t2.duration) FROM track t2 WHERE t2.album_id = a.id AND t2.duration >= '00:03:00') ORDER BY a.title";

    public static List<Track> readTrackData(String query){
        List<Track> tracks = new ArrayList<>();
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                int album_id = resultSet.getInt("album_id");

                tracks.add(new Track(id,title,duration,album_id));
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return tracks;
    }

    public static void addTrack(String title, String duration, int album_id){
        Time timeDuration = Time.valueOf(duration);
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRACK)){
            preparedStatement.setString(1,title);
            preparedStatement.setTime(2,timeDuration);
            preparedStatement.setInt(3,album_id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void addTrack(String title, String duration, String albumTitle){
        Time timeDuration = Time.valueOf(duration);
        Integer albumId = CRUDAlbum.getAlbumIdByTitle(albumTitle);
        if (albumId == null){
            System.out.println("Невозможно добавить трек");
        }
        else {
            try (Connection connection = DBWorker.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRACK)){
                preparedStatement.setString(1,title);
                preparedStatement.setTime(2,timeDuration);
                preparedStatement.setInt(3,albumId);
                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public  static void deleteTrack(String title){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRACK)){
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    //Чекнуть как работает
    public static void findShortestTracks(){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHORTEST_TRACK)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String albumTitle = resultSet.getString("album_title");
                String shortestTrackTitle = resultSet.getString("shortest_track_title");
                String shortestTrackDuration = resultSet.getString("shortest_track_duration");

                System.out.println("Название альбома: "+ albumTitle + ", Название самой короткой песни: "+ shortestTrackTitle + " , Длительность: " + shortestTrackDuration );
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

}
