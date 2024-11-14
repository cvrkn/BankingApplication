package guis;

import db_objs.myJDBC;
import db_objs.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class loginGui extends baseFrame{
    public loginGui(){
        super("Banking App");
    }
    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("Banking Application");
        bankingAppLabel.setBounds(0, 20 , super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("dialog",Font.BOLD,32));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(bankingAppLabel);

        //Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20,120,getWidth()-30,24);
        usernameLabel.setFont(new Font("dialog",Font.PLAIN,20));
        add(usernameLabel);
        //Username TextField
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20,150,getWidth()-50,40);
        usernameField.setFont(new Font("dialog",Font.PLAIN,28));
//for testing while developemnt

        usernameField.setText("kaush1");

//remove while deployment
        add(usernameField);

        //Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20,220,getWidth()-30,24);
        passwordLabel.setFont(new Font("dialog",Font.PLAIN,20));

        add(passwordLabel);
        //password passwordField
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20,250,getWidth()-50,40);
        passwordField.setFont(new Font("dialog",Font.PLAIN,28));
//for testing while developemnt

        passwordField.setText("1234");

//remove while deployment
        add(passwordField);

        //Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20,450,getWidth()-50,40);
        loginButton.setFont(new Font("dialog",Font.BOLD,20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                user curuser = myJDBC.validateLogin(username,password);

                if(curuser!=null){
                    loginGui.this.dispose();;

                    bankingAppGui bankingAppGuiO = new bankingAppGui(curuser);
                    bankingAppGuiO.setVisible(true);

                    JOptionPane.showMessageDialog(bankingAppGuiO,"Login Successful");

                }
                else{
                    JOptionPane.showMessageDialog(loginGui.this,"Login Failed");
                }

            }
        });
        add(loginButton);

        //register label

        JLabel registerLabel = new JLabel(
                "<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0,500,getWidth()-10,30);
        registerLabel.setFont(new Font("dialog",Font.PLAIN,20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER );
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginGui.this.dispose();

                new registerGui().setVisible(true);
            }
        });
        add(registerLabel);

    }
}
