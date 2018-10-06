package jeorgius.DbAccess;

public class Sql {

    private static final String table = "bankdemo.accounts";
    static final String link = "jdbc:postgresql://localhost:5432/jeorgius?currentSchema=bankdemo";
    static final String JDBC = "org.postgresql.Driver";
    static final String admin = "postgres";
    static final String pw = "1234";

    static String INSERT(String account_number){
        return "INSERT into " + table + " (account_number, balance) " +
                "VALUES (\'" + account_number + "\'," + "\'0\');";
    }

    static String SELECT(String account_number){
        return "SELECT * FROM " + table +
               " WHERE account_number = \'" + account_number+"\';";
    }

    static String createTable(){
        return "CREATE TABLE IF NOT EXISTS " + table + "\n" +
                "(\n" +
                "    id serial PRIMARY KEY,\n" +
                "    account_number varchar(5),\n" +
                "    balance integer\n" +
                ");";
    }

    static String findTable(){
        return "SELECT TABLE_NAME " +
                "FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA=\'bankdemo\' " +
                "AND TABLE_NAME=\'accounts\';";
    }

    static String UPDATE(String account_number, Integer sum){
        return "UPDATE " + table +
                " SET balance = " + sum + "" +
                " WHERE account_number = \'" +account_number+"\';";
    }

    static String DELETE(String account_number){
        return "DELETE FROM " + table +
                " WHERE account_number = \'" + account_number+"\';";
    }
}
