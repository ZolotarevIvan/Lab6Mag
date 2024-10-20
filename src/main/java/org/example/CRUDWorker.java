package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDWorker {
    private static final String UPDATE_ARTIST = "UPDATE artist SET name = ? WHERE id = ?;";
    private static final String INSERT_ARTIST = "INSERT INTO artist(name) VALUES (?);";
    private static final String INSERT_ALBUM = "INSERT INTO album(title,genre,artist_id) VALUES (?,?,?);";
    private static final String INSERT_TRACK = "INSERT INTO track(title,duration,album_id) VALUES (?,?,?);";
    private static final String DELETE_ARTIST = "DELETE from artist WHERE name = ?;";
    private static final String DELETE_ALBUM = "DELETE from album WHERE title = ?;";
    private static final String DELETE_TRACK = "DELETE from track WHERE title = ?;";
    private static final String SHORTEST_TRACK = "SELECT a.title AS album_title,t.title AS shortest_track_title,t.duration AS shortest_track_duration FROM album a JOIN track t ON a.id = t.album_id WHERE t.duration >= '250' GROUP BY a.id, t.title, t.duration HAVING t.duration = MIN(t.duration) ORDER BY a.title;";

    public static List<Artist> readArtistData(String query){
        List<Artist> artists = new ArrayList<>();
        try (Connection connection = DBWorker.getConnection();               //Ресурсы в скобках автоматически закроются, если что-то пойдет не так
             PreparedStatement preparedStatement = connection.prepareStatement(query)){        //Statement - Варианты, которыми мы можем отправлять запрос
            ResultSet resultSet = preparedStatement.executeQuery(); //Resultset хранит данные из запроса,

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                artists.add(new Artist(id,name));
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return artists;
    }

    public static List<Album> readAlbumData(String query){
        List<Album> artists = new ArrayList<>();
        try (Connection connection = DBWorker.getConnection();               //Ресурсы в скобках автоматически закроются, если что-то пойдет не так
             PreparedStatement preparedStatement = connection.prepareStatement(query)){        //Statement - Варианты, которыми мы можем отправлять запрос
            ResultSet resultSet = preparedStatement.executeQuery(); //Resultset хранит данные из запроса,

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                int artist_id = resultSet.getInt("artist_id");

                artists.add(new Album(id,title,genre,artist_id));
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return artists;
    }

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

    public static void findShortestTracks(){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHORTEST_TRACK)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String albumTitle = resultSet.getString("album_title");
                String shortestTrackTitle = resultSet.getString("shortest_track_title");
                String shortestTrackDuration = resultSet.getString("shortest_track_duration");

                System.out.println(albumTitle + " "+ shortestTrackTitle + " " + shortestTrackDuration );
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void addArtist(String name){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ARTIST)){
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public  static void deleteArtist(String name){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ARTIST)){
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void addAlbum(String title,String genre,int artist_id){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUM)){
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,genre);
            preparedStatement.setInt(3,artist_id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public  static void deleteAlbum(String title){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALBUM)){
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
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

    public  static void deleteTrack(String title){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRACK)){
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public  static void changeNameArtist(int id, String newName){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ARTIST)){
            preparedStatement.setString(1,newName);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }



}
