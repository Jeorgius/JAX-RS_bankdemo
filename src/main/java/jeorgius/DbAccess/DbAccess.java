package jeorgius.DbAccess;

import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import jeorgius.Account.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccess {

    public String dbActionResult;

    public DbAccess() {
        createTable(Sql.createTable());
    }

    private Connection dbConnect() {
        try {
        Connection conn = DriverManager.getConnection(Sql.link, Sql.admin, Sql.pw);
        //conn.setAutoCommit(false);
        Class.forName(Sql.JDBC);
        return conn;
        } catch (Exception e) {
            System.out.println("Error connecting to DB" + e.getMessage());
            return null;
        }
    }

    protected List<Account> dbSelectAcc (String sqlQuery) throws SQLException {

        List<Account> accounts = new ArrayList<>();

        try {
            PreparedStatement stmt = dbConnect().prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery();

            // GET ACCOUNT DATA
            while(rs.next()){
                Account account = new Account();

                account.setId((Integer) rs.getObject(1));
                account.setAccount_number((String) rs.getObject(2));
                account.setBalance((Integer) rs.getObject(3));

                accounts.add(account);
                stmt.close();
                rs.close();
            }
        } catch (Exception e) {
            dbActionResult = "failed: " + e.getMessage();
        }
        return accounts;
    }

    private void createTable (String sqlQuery) {
        try {
            PreparedStatement stmt = dbConnect().prepareStatement(sqlQuery);
            Integer rs = stmt.executeUpdate();
            if(rs>0) System.out.println("DB has been created" + rs);
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error creating table");
        }
    }

    protected String updateBalance(String sqlQuery) {
        try {
            PreparedStatement stmt = dbConnect().prepareStatement(sqlQuery);

            Integer rs = stmt.executeUpdate();
            return "OK: Balance changed";
        } catch (Exception e){
            return "Error changing balance" + e.getMessage();
        }

    }

    protected boolean createAccount(String sqlQuery) throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement stmt = dbConnect().prepareStatement(sqlQuery);
            Integer rs = stmt.executeUpdate();

            if (rs.equals(0)) {
                stmt.close();
                System.out.println("Error creating account in database");
                return false;
            } else {
                stmt.close();
                System.out.println("Account created successfully");
                return true;
            }
        }catch (Exception e) {
            System.out.println("Error creating account in database");
        }
        return false;
    }

    protected boolean deleteAccount(String sqlQuery) throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement stmt = dbConnect().prepareStatement(sqlQuery);
            Integer rs = stmt.executeUpdate();

            if (rs.equals(0)) {
                stmt.close();
                System.out.println("Error deleting account in database");
                return false;
            } else {
                stmt.close();
                System.out.println("Account deleted successfully");
                return true;
            }
        }catch (Exception e) {
            System.out.println("Error deleting account in database");
        }
        return false;
    }
}