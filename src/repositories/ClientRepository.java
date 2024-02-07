package repositories;

import data.interfaces.IDB;
import entities.Client;
import repositories.interfaces.IClientRepository;

import java.sql.*;
import java.util.*;

public class ClientRepository implements IClientRepository {
    private final IDB db;
    public ClientRepository(IDB db){
        this.db = db;
    }
    @Override
    public boolean createClient(Client client){
        Connection con = null;
        try{
            con = db.getConnection();
            String query = "INSERT INTO clients(name, surname, login, password, balance) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(query);
            //System.out.println(client.getSurname());
            st.setString(1, client.getName());
            st.setString(2, client.getSurname());
            st.setString(3, client.getLogin());
            st.setString(4, client.getPassword());
            st.setInt(5, client.getBalance());

            st.executeUpdate();
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            try {
                if (con != null)
                    con.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Client getClientByID(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id,name,surname,login,password,balance FROM clients WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Client(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("balance"));
            }
            return null;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Runtime error: " + e);
            return null;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }

    @Override
    public Client getClientByLogin(String login) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id,name,surname,login,password,balance FROM clients WHERE login=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, login);

            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                return new Client(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getInt("balance"));
            }
            return null;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Runtime error: " + e);
            return null;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Client> getAllClients() {
        Connection con = null;

        try {
            con = db.getConnection();
            String sql = "SELECT id,name,surname,login,password,balance FROM clients";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<Client> clients = new LinkedList<>();
            while (rs.next()) {
                Client client = new Client(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getInt("balance"));
                clients.add(client);
            }

            return clients;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean updateClientAccount(String login, int newBalance) {
        Connection con = null;
        try {
            con = db.getConnection();

            String sql = "UPDATE clients SET balance = ? WHERE login = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, newBalance);
            st.setString(2, login);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Runtime error: " + e);
            return false;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean changePassword(String login, String newPassword) {
        Connection con = null;
        try {
            con = db.getConnection();

            String sql = "UPDATE clients SET password = ? WHERE login = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, newPassword);
            st.setString(2, login);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Runtime error: " + e);
            return false;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean deleteClient(String login) {
        Connection con = null;
        try {
            con = db.getConnection();

            String sql = "DELETE FROM clients WHERE login = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, login);
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Runtime error: " + e);
            return false;
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println("sql error: " + e.getMessage());
            }
        }
    }
}
