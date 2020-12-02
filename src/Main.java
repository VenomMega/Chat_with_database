import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost/chat?serverTimezone=Europe/Moscow&useSSL=false";
    private static String user = "root";
    private static String pass = "";
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        int id = 1;
        Scanner sc = new Scanner(System.in);
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,pass);

            System.out.println("Connection");
        } catch (Exception e){
            e.printStackTrace();
        }
        while (true){
            System.out.println("\n" +
                    "[1] Registration\n" +
                    "[2] Login");
            int instr = sc.nextInt();
            if (instr == 1){
                System.out.println("Input a login");
                String login = sc.next();
                System.out.println("Input a password");
                String password = sc.next();
                System.out.println("Input a name");
                String name = sc.next();
                addUser(new User(null, login, password, name));
            }
            else if (instr == 2){
                System.out.println("Input a login");
                String login = sc.next();
                System.out.println("Input a password");
                String password = sc.next();
                log(login,password);

            }

        }
    }
    private static void addUser(User u){
        try {
            String query = "insert into users(login,password,name) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,u.getLogin());
            preparedStatement.setString(2,u.getPassword());
            preparedStatement.setString(3,u.getName());
            preparedStatement.executeUpdate();
            System.out.println("inserted successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private static void log(String loginn, String passwordd){
        User u = new User();
        try{
            Statement statement = connection.createStatement();
            String query = "select id,login,password,name from users where login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,loginn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                if (password.equals(passwordd)){
                    System.out.println("Good");
                } else System.out.println("bad");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    private static ArrayList<User> getUsers(ArrayList<User> users){

        try{
            Statement statement = connection.createStatement();
            String query = "select * from users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");

                users.add(new User(id,login,password,name));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
    private static void deleteUser(int index){
        try{
            Statement statement = connection.createStatement();
            String query = "delete from users where id ="+index;
            statement.executeUpdate(query);
            statement.close();
            System.out.println("User " + index + " was deleted");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void adminPanel(){
        ArrayList<User> users = new ArrayList<>();
        System.out.println("Input a password");
        Scanner sc = new Scanner(System.in);
        String adminPass = sc.next();
        if (adminPass.equals("admin")){
            while (true){
                System.out.println("[1] Add user\n" +
                        "[2] Show users\n" +
                        "[3] Delete user\n" +
                        "[0] Exit");
                int instr = sc.nextInt();
                if (instr == 1){
                    System.out.println("Input a login");
                    String login = sc.next();
                    System.out.println("Input a password");
                    String password = sc.next();
                    System.out.println("Input a name");
                    String name = sc.next();
                    addUser(new User(null, login, password, name));
                }
                else if (instr == 2){
                    getUsers(users);
                    for (User u: users){
                        System.out.println(u.toString());
                    }
                }
                else if (instr == 3){
                    System.out.println("Input index that you want to delete");
                    int index = sc.nextInt();
                    deleteUser(index);
                }
            }
        }
    }
}
