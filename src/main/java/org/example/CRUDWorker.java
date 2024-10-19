package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDWorker {

    private static  String INSERT_STUDENT = "INSERT INTO students(name,surname,cource_name) VALUES (?,?,?);";
    private static  String UPDATE_STUDENT = "UPDATE students SET cource_name = ? WHERE id = ?;";
    private static  String DELETE_STUDENT = "DELETE from students  WHERE id = ?;";

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
                int artist_id = resultSet.getInt("id");

                artists.add(new Album(id,title,genre,artist_id));
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return artists;
    }

    public static List<Track> readTrackData(String query){
        List<Track> tracks = new ArrayList<>();
        try (Connection connection = DBWorker.getConnection();               //Ресурсы в скобках автоматически закроются, если что-то пойдет не так
             PreparedStatement preparedStatement = connection.prepareStatement(query)){        //Statement - Варианты, которыми мы можем отправлять запрос
            ResultSet resultSet = preparedStatement.executeQuery(); //Resultset хранит данные из запроса,

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                int album_id = resultSet.getInt("album_id");

                tracks.add(new Track(id,title,duration,album_id));
            }

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return tracks;
    }

    /*
    public static void createStudent(Student student){
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();               //Ресурсы в скобках автоматически закроются, если что-то пойдет не так
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)){        //Statement - Варианты, которыми мы можем отправлять запрос
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getSurname());
            preparedStatement.setString(3,student.getCoursname());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public  static void updateStudent(int id, String courceName){
        List<Student> updateStudents = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT)){
            preparedStatement.setString(1,courceName);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public  static void deleteStudent(int id){
        List<Student> updateStudents = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    */

}
