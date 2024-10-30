package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDArtist {
    private static final String UPDATE_ARTIST = "UPDATE artist SET name = ? WHERE id = ?;";
    private static final String INSERT_ARTIST = "INSERT INTO artist(name) VALUES (?);";
    private static final String DELETE_ARTIST = "DELETE from artist WHERE name = ?;";
    private static final String SELECT_ARTIST_ID = "SELECT id FROM Artist WHERE name = ?;";
    private static final String ALL_TRACKS_BY_ARTIST_NAME = "SELECT t.title AS track_title FROM Artist a JOIN Album al ON a.id = al.artist_id JOIN Track t ON al.id = t.album_id WHERE a.name = ?;";

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

    public static List<String>  getTracksByArtist(String name){
        List<String> tracks = new ArrayList<>();
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ALL_TRACKS_BY_ARTIST_NAME)){
            preparedStatement.setString(1,name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                tracks.add(rs.getString("track_title")); // Добавляем названия треков в список
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return tracks;
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

    public static Integer getArtistIdByName(String artistName) {
        Integer artistId = null;
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ARTIST_ID)){
            preparedStatement.setString(1, artistName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Переход к первой строке результата
                artistId = resultSet.getInt("id");
            }else {
                System.out.println("Артист не найден.");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return artistId; // Возврат id исполнителя или null, если не найден
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
