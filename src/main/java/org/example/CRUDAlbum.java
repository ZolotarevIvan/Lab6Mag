package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDAlbum {

    private static final String INSERT_ALBUM = "INSERT INTO album(title,genre,artist_id) VALUES (?,?,?);";
    private static final String DELETE_ALBUM = "DELETE from album WHERE title = ?;";
    private static final String DELETE_ALBUM_BY_ID = "DELETE from album WHERE id = ?;";
    private static final String ALL_ALBUMS_WITH_ARTISTS = "SELECT a.title AS album_title, ar.name AS artist_name FROM Album a JOIN Artist ar ON a.artist_id = ar.id;";
    private static final String SELECT_ALBUM_ID = "SELECT id FROM Album WHERE title = ?;";

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

    public static void addAlbum(String title,String genre,String artist_name){
        Integer artist_id = CRUDArtist.getArtistIdByName(artist_name);
        if (artist_id == null){
            System.out.println("Невозможно добавить альбом");
        }
        else {
            try (Connection connection = DBWorker.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ALBUM)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, genre);
                preparedStatement.setInt(3, artist_id);
                preparedStatement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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

    public  static void deleteAlbum(int id){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALBUM_BY_ID)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void fetchAlbumAndArtist(){
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ALL_ALBUMS_WITH_ARTISTS)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String albumTitle = resultSet.getString("album_title");
                String artistName = resultSet.getString("artist_name");
                System.out.println("Album: " + albumTitle + ", Artist: " + artistName);
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static Integer getAlbumIdByTitle(String albumTitle) {
        Integer albumId = null;
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALBUM_ID)){
            preparedStatement.setString(1, albumTitle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Переход к первой строке результата
                albumId = resultSet.getInt("id");
            }else {
                System.out.println("Альбом не найден.");
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return albumId; // Возврат id исполнителя или null, если не найден
    }




}
