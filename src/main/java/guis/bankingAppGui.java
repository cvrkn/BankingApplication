package guis;

import db_objs.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bankingAppGui extends baseFrame implements ActionListener {
    private JTextField currentBalanceField;
    public JTextField getCurrentBalanceField(){return currentBalanceField;}
    public bankingAppGui(user curuser){
        super("Banking App",curuser);

    }


    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html>"+"<body style='text-align:center'>"+
                "<b>Hello "+ curuser.getUsername() + "</b><br>"+
                "what would you like to do today></body></html>";
        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0,20,getWidth()-10,50);
        welcomeMessageLabel.setFont(new Font("dialog", Font.PLAIN,18));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Current Balance");
        currentBalanceLabel.setBounds(0,90,getWidth(),40);
        currentBalanceLabel.setFont(new Font("dialog",Font.BOLD,22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        currentBalanceField = new JTextField("₹"+curuser.getCurrentBalance());
        currentBalanceField.setBounds(20,130,getWidth()-50,50);
        currentBalanceField.setFont(new Font("dialog",Font.BOLD,28));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);
        add(currentBalanceField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(20,200,getWidth()-50,50);
        depositButton.setFont(new Font("dialog",Font.BOLD,22));
        depositButton.addActionListener(this);
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(20,260,getWidth()-50,50);
        withdrawButton.setFont(new Font("dialog",Font.BOLD,22));
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        JButton pastTransactionsButton = new JButton("Past Transactions");
        pastTransactionsButton.setBounds(20,320,getWidth()-50,50);
        pastTransactionsButton.setFont(new Font("dialog",Font.BOLD,22));
        pastTransactionsButton.addActionListener(this);
        add(pastTransactionsButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(20,380,getWidth()-50,50);
        transferButton.setFont(new Font("dialog",Font.BOLD,22));
        transferButton.addActionListener(this);
        add(transferButton);

        JButton logOutButton = new JButton("LogOut");
        logOutButton.setBounds(20,500,getWidth()-50,50);
        logOutButton.setFont(new Font("dialog",Font.BOLD,22));
        logOutButton.addActionListener(this);

        add(logOutButton);





    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        if( buttonPressed.equalsIgnoreCase("Logout")){
            new loginGui().setVisible(true);

            this.dispose();
            return;
        }
        bankingAppDialog bankingAppDialog0 = new bankingAppDialog(this,curuser);
        bankingAppDialog0.setTitle(buttonPressed);

        if(buttonPressed.equalsIgnoreCase("deposit")||
                buttonPressed.equalsIgnoreCase("withdraw")||
                buttonPressed.equalsIgnoreCase("transfer")){
            bankingAppDialog0.addCurrentBalanceAndAmount();
            bankingAppDialog0.addActionButton(buttonPressed);

            if(buttonPressed.equalsIgnoreCase("Transfer")){
                bankingAppDialog0.addEnterUserField();
            }
            bankingAppDialog0.setVisible(true);
        }
        else if(buttonPressed.equalsIgnoreCase("Past Transactions" )){
            bankingAppDialog0.addPastTransactionsComponent();
            bankingAppDialog0.setVisible(true);



        }



    }
}
