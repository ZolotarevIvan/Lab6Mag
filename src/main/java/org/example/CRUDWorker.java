package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDWorker {

    private static final String INSERT_STUDENT = "INSERT INTO students(name,surname,cource_name) VALUES (?,?,?);";
    private static final String UPDATE_STUDENT = "UPDATE students SET cource_name = ? WHERE id = ?;";
    private static final String DELETE_STUDENT = "DELETE from students  WHERE id = ?;";
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
        try (Connection connection = DBWorker.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();

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
