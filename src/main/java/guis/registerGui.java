package guis;

import db_objs.myJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Arrays;

public class registerGui extends baseFrame {
    public registerGui(){
        super("Banking App Registration");
    }
    @Override
    protected void addGuiComponents() {

        JLabel bankingAppTitle = new JLabel("Registeration");
        bankingAppTitle.setBounds(0,20,super.getWidth(),40);
        bankingAppTitle.setFont(new Font("dialog",Font.BOLD,32));
        bankingAppTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankingAppTitle);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20,120,getWidth()-50,24);
        usernameLabel.setFont(new Font("dialog",Font.PLAIN,20));
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20,150,getWidth()-50,40);
        usernameField.setFont(new Font("dialog",Font.PLAIN,28));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Type Password::");
        passwordLabel.setBounds(20,220,getWidth()-50,24);
        passwordLabel.setFont(new Font("dialog",Font.PLAIN,20));
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20,250,getWidth()-50,40);
        passwordField.setFont(new Font("dialog",Font.PLAIN,28));
        add(passwordField);

        JLabel repasswordLabel = new JLabel("Re-Type Password::");
        repasswordLabel.setBounds(20,320,getWidth()-50,24);
        repasswordLabel.setFont(new Font("dialog",Font.PLAIN,20));
        add(repasswordLabel);

        JPasswordField repasswordField = new JPasswordField();
        repasswordField.setBounds(20,350,getWidth()-50,40);
        repasswordField.setFont(new Font("dialog",Font.PLAIN,28));
        add(repasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20,450,getWidth()-50,40);
        registerButton.setFont(new Font("dialog",Font.BOLD,20));
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String repassword = String.valueOf(repasswordField.getPassword());
                if(validate(username,password,repassword)){
                    if(myJDBC.register(username,password)){

                        registerGui.this.dispose();

                        loginGui loginGui0 = new loginGui();
                        loginGui0.setVisible(true);

                        JOptionPane.showMessageDialog(loginGui0, "Registration Successful");
                    }
                    else{
                        JOptionPane.showMessageDialog(registerGui.this, "Error: User Name Already Taken");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(registerGui.this, "Error: User Name Should be more than 6 character\n"+
                            "And/Or Password Should Match");

                }

            }
        });
        add(registerButton);

        JLabel loginLabel = new JLabel(
                "<html><a href=\"#\">Already have an account? Login Here</a></html>");
        loginLabel.setBounds(0,500,getWidth()-10,30);
        loginLabel.setFont(new Font("dialog",Font.PLAIN,20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER );
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerGui.this.dispose();
                new loginGui().setVisible(true);
            }
        });
        add(loginLabel);



    }
    private boolean validate(String username,String password ,String rePassword){
        return username.length() >= 6 && password.length() >= 4 && password.equals(rePassword);
    }
}

