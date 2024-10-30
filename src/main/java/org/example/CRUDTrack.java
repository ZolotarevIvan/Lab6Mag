package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDTrack {

    private static final String INSERT_TRACK = "INSERT INTO track(title,duration,album_id) VALUES (?,?,?);";
    private static final String DELETE_TRACK = "DELETE from track WHERE title = ?;";
    private static final String SHORTEST_TRACK = "SELECT a.title AS album_title, t.title AS shortest_track_title, t.duration AS shortest_track_duration FROM album a JOIN track t ON a.id = t.album_id WHERE t.duration >= '00:03:00' AND t.duration = ( SELECT MIN(t2.duration) FROM track t2 WHERE t2.album_id = a.id AND t2.duration >= '00:03:00') ORDER BY a.title";
    private static final String TRACKS_COUNTER = "SELECT a.id AS artist_id, a.name AS artist_name, COUNT(t.id) AS total_tracks FROM Artist a LEFT JOIN Album al ON a.id = al.artist_id LEFT JOIN Track t ON al.id = t.album_id GROUP BY a.id, a.name ORDER BY total_tracks DESC; ";
    private static final String TRACKS_COUNTER_MORE_THAN_5 = "SELECT a.id AS artist_id, a.name AS artist_name, COUNT(t.id) AS total_tracks FROM Artist a LEFT JOIN Album al ON a.id = al.artist_id LEFT JOIN Track t ON al.id = t.album_id GROUP BY a.id, a.name HAVING COUNT(t.id) > ? ORDER BY total_tracks DESC;";
    private static final String SHORTEST_TRACK_With_HAVING_AND_GROUPBY ="""
            SELECT 
                a.title AS album_title,
                t.title AS shortest_track_title,
                t.duration AS shortest_track_duration
            FROM 
                Album a
            JOIN 
                Track t ON a.id = t.album_id
            WHERE 
                t.duration = (
                    SELECT MIN(t2.duration)
                    FROM Track t2
                    WHERE t2.album_id = a.id
                )
            AND a.id IN (
                SELECT album_id
                FROM Track
                GROUP BY album_id
                HAVING COUNT(*) >= 5
            )
            ORDER BY 
                a.title;
            """;

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

    public static void findShortestTracks(){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHORTEST_TRACK_With_HAVING_AND_GROUPBY)){
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

    public static void getArtistsWithTrackCount() {
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TRACKS_COUNTER)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int artistId = resultSet.getInt("artist_id");
                String artistName = resultSet.getString("artist_name");
                int totalTracks = resultSet.getInt("total_tracks");

                System.out.println("Id артиста: "+ artistId + ", Имя артиста: "+ artistName + " , Кол-во треков: " + totalTracks );
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    public static void getArtistsWithMoreThanTracks(int minTrackCount) {
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TRACKS_COUNTER_MORE_THAN_5)){
            preparedStatement.setInt(1,minTrackCount);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int artistId = resultSet.getInt("artist_id");
                String artistName = resultSet.getString("artist_name");
                int totalTracks = resultSet.getInt("total_tracks");


                System.out.println("Id артиста: "+ artistId + ", Имя артиста: "+ artistName + " , Кол-во треков: " + totalTracks );
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


}
