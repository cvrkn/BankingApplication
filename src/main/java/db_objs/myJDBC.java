package db_objs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class myJDBC {
    private static final String DB_URL= "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_Username = "root";
    private static final String DB_password = "1234";

    public static user validateLogin(String username, String password){
        try{
            String Salt;
            String HashedPassword;
            Connection con = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement getSalt = con.prepareStatement(
                    "select * from users where username =? "
            );
            getSalt.setString(1,username);
            ResultSet resultSet = getSalt.executeQuery();
            if(resultSet.next()) {
                Salt = resultSet.getString("salt");
                int userId = resultSet.getInt("id");
                BigDecimal currentBalance = resultSet.getBigDecimal("currentBalance");
                HashedPassword = resultSet.getString("password");
                if (passwordHandler.verifyPassword(password, Salt, HashedPassword)) {
                    return new user(userId, username, null, currentBalance);
                }
                return  null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //confrimation
    public static boolean validateLogin(String username, String password,boolean bool){
        try{
            String Salt;
            String HashedPassword;
            Connection con = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement getSalt = con.prepareStatement(
                    "select * from users where username =? "
            );
            getSalt.setString(1,username);
            ResultSet resultSet = getSalt.executeQuery();
            if(resultSet.next()) {
                Salt = resultSet.getString("salt");
                BigDecimal currentBalance = resultSet.getBigDecimal("currentBalance");
                HashedPassword = resultSet.getString("password");
                if (passwordHandler.verifyPassword(password, Salt, HashedPassword)) {
                    return true;
                }
                return  false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Boolean register(String username, String password){
        if(!CheckUser(username)){
            try{
                Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);

                //hashing password
                String salt = passwordHandler.generateSalt();
                String HashedPassword = passwordHandler.hashPassword(password,salt);

                PreparedStatement preparedStatement = connection.prepareStatement("insert into users(username,password,currentBalance,salt)"+
                        "values(?,?,0.0,?)"
                );
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,HashedPassword);
                preparedStatement.setString(3,salt);
                preparedStatement.executeUpdate();
                return true;

            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;

    }
    private static Boolean CheckUser(String username){
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean addTransactionToDatabase(Transaction transaction){
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement insertStatement = connection.prepareStatement("insert into transactions(u_Id,t_Amount,t_Date,t_Type)"+
                    "values(?,?,NOW(),?)"
            ); // NOW()-> current date
            insertStatement.setInt(1,transaction.getUserId());
            insertStatement.setBigDecimal(2,transaction.getTransactionAmount());
            insertStatement.setString(3, transaction.getTransactionType());
            insertStatement.executeUpdate();
            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateCurrentBalance(user curuser){
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET currentBalance = ? WHERE id = ?"
            );
            updateStatement.setBigDecimal(1,curuser.getCurrentBalance());
            updateStatement.setInt(2,curuser.getId());

            updateStatement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }
    public static boolean transfer(user curuser,String receiver, float amount){
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement queryUser = connection.prepareStatement(
                    "SELECT * FROM USERS WHERE username = ?"
            );
            queryUser.setString(1,receiver);

            ResultSet resultSet = queryUser.executeQuery();
            while(resultSet.next()){
                user receiverUser = new user(
                        resultSet.getInt("id"),
                        receiver,
                        null,
                        resultSet.getBigDecimal("currentBalance")
                );

                //transaction sneder side
                Transaction transferTxn = new Transaction(
                        curuser.getId(),
                        "Transfer",
                        new BigDecimal(-amount),
                        null
                );
                //transaction reciever side
                Transaction recievedTxn = new Transaction(
                        receiverUser.getId(),
                        "Transfer",
                        new BigDecimal(+amount),
                        null
                );
                 //update
                curuser.setCurrentBalance(curuser.getCurrentBalance().add(BigDecimal.valueOf(-amount)));
                updateCurrentBalance(curuser);

                receiverUser.setCurrentBalance(receiverUser.getCurrentBalance().add(BigDecimal.valueOf(amount)));
                updateCurrentBalance(receiverUser);

                //transaction table update

                addTransactionToDatabase(recievedTxn);
                addTransactionToDatabase(transferTxn);

                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false; // transfeer failed

    }

    public static ArrayList<Transaction> getPastTransactions(user curuser){
        ArrayList<Transaction> pasttransactions = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement selectAllTransaction = connection.prepareStatement(
                    "Select * from transactions WHERE u_id = ?"
            );
            selectAllTransaction.setInt(1,curuser.getId());

            ResultSet resultSet= selectAllTransaction.executeQuery();
            while(resultSet.next()){
                Transaction transaction = new Transaction(
                        resultSet.getInt("u_id"),
                        resultSet.getString("t_Type"),
                        resultSet.getBigDecimal("t_Amount"),
                        resultSet.getDate("t_Date")
                );
                pasttransactions.add(transaction);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return pasttransactions;

    }







    //////////////////
    public static String updateUser(String username, String password,BigDecimal amount){
        try{
            Connection con = DriverManager.getConnection(DB_URL,DB_Username,DB_password);
            PreparedStatement preparedStatement = con.prepareStatement(
                    "select * from users where username =?"
            );

            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
// checks whethera user already exist using same username if yes id is assigned 0
            if(resultSet.next()){
                return "User Already Exist";
            }
            else {
                PreparedStatement preparedStatementinsert = con.prepareStatement(
                        "insert into users (username,password,currentBalance) values(?,?,?)"
                );

                preparedStatementinsert.setString(1, username);
                preparedStatementinsert.setString(2, password);
                preparedStatementinsert.setString(3,String.valueOf(amount));

                ResultSet resultSetinsert = preparedStatement.executeQuery();
                if(resultSetinsert.next()){
                    System.out.println(resultSetinsert.getString(username));
                }

            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
