package guis;

import db_objs.Transaction;
import db_objs.myJDBC;
import db_objs.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class bankingAppDialog extends JDialog implements ActionListener {

    private user curuser;
    private bankingAppGui bankingAppGui0;
    private JLabel balanceLabel,enterAmountLabel,enterUserLabel,confirmPasswordLabel;
    private JTextField enterAmountField,enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionsPanel;
    private ArrayList<Transaction> pastTransactions;
    //for confirmation
    private bankingAppDialog bankingAppDialog0;
    private JPasswordField confirmPasswordField;
    public static boolean verified = false;


    public bankingAppDialog(bankingAppGui bankingAppGui0, user curuser){

        setSize(400,400);

        setModal(true); //adds focus to dialog (cant access anything else)

        setLocationRelativeTo(bankingAppGui0);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);

        setLayout(null);//enables us to manual position and size ele

        this.bankingAppGui0 = bankingAppGui0;
        this.curuser = curuser;
    }
    public bankingAppDialog(bankingAppDialog bankingAppDialog0, user curuser){

        setSize(400,200);

        setModal(true); //adds focus to dialog (cant access anything else)

        setLocationRelativeTo(bankingAppDialog0);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);
        setLayout(null);//enables us to manual position and size ele



        this.bankingAppDialog0 = bankingAppDialog0;
        this.curuser = curuser;

    }
    //confirmation
    public void addConfirmPassword(){
        confirmPasswordLabel = new JLabel("Password:");
        confirmPasswordLabel.setBounds(15,20,getWidth()-30,24);
        confirmPasswordLabel.setFont(new Font("dialog",Font.PLAIN,20));

        add(confirmPasswordLabel);
        //password passwordField
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(15,50,getWidth()-50,40);
        confirmPasswordField.setFont(new Font("dialog",Font.PLAIN,28));
        add(confirmPasswordField);
    }
    public void addCurrentBalanceAndAmount(){
        balanceLabel = new JLabel("Balance: ₹"+ curuser.getCurrentBalance() );
        balanceLabel.setBounds(0,10,getWidth()-20,20);
        balanceLabel.setFont(new Font("dialog", Font.BOLD,16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter Amount" );
        enterAmountLabel.setBounds(0,50,getWidth()-20,20);
        enterAmountLabel.setFont(new Font("dialog", Font.BOLD,16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15,80,getWidth()-50,40);
        enterAmountField.setFont(new Font("dialog", Font.BOLD,20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
//
        //enterAmountField.setText("10");
//
        add(enterAmountField);

    }
    public void addActionButton(String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15,300,getWidth()-50,40);
        actionButton.setFont(new Font("dialog", Font.BOLD,20));
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);
        actionButton.addActionListener(this);
        add(actionButton);

    }
    public void addActionButton(String actionButtonType,Boolean flag){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15,100,getWidth()-50,40);
        actionButton.setFont(new Font("dialog", Font.BOLD,20));
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);
        actionButton.addActionListener(this);
        add(actionButton);

    }
    public void addEnterUserField() {
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);

        add(enterUserField);

    }
    public void addPastTransactionsComponent(){
        pastTransactionsPanel = new JPanel(); // container for past transactions

        pastTransactionsPanel.setLayout(new BoxLayout(pastTransactionsPanel,BoxLayout.Y_AXIS));

        //panel scrollability

        JScrollPane scrollPane = new JScrollPane(pastTransactionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0,20,getWidth()-20,getHeight()-30);

        //db call to get txns
        pastTransactions = myJDBC.getPastTransactions(curuser);
        ////
        JPanel labelContainer = new JPanel();
        labelContainer.setLayout(new BorderLayout());

        JLabel label1  = new JLabel("Type  ");
        label1.setFont(new Font("Dialog",Font.BOLD,20));

        JLabel label2  = new JLabel("      Amount ");
        label2.setFont(new Font("Dialog",Font.BOLD,20));

        JLabel label3  = new JLabel("Date         ");
        label3.setFont(new Font("Dialog",Font.BOLD,20));

        labelContainer.add(label3, BorderLayout.WEST);
        labelContainer.add(label1, BorderLayout.EAST);
        labelContainer.add(label2, BorderLayout.CENTER);

        labelContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        pastTransactionsPanel.add(labelContainer);
        ////

        for(int i = 0; i<pastTransactions.size();i++){
            Transaction pasttransaction = pastTransactions.get(i);
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel  = new JLabel(pasttransaction.getTransactionType()+"  ");
            transactionTypeLabel.setFont(new Font("Dialog",Font.BOLD,20));

            JLabel AmountLabel  = new JLabel(String.valueOf(pasttransaction.getTransactionAmount()));
            AmountLabel.setFont(new Font("Dialog",Font.BOLD,20));

            JLabel DateLabel  = new JLabel(String.valueOf(pasttransaction.getTransactionDate())+"    ");
            DateLabel.setFont(new Font("Dialog",Font.BOLD,20));

            pastTransactionContainer.add(DateLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.EAST);
            pastTransactionContainer.add(AmountLabel, BorderLayout.CENTER);


            pastTransactionContainer.setBackground(Color.WHITE);

            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pastTransactionsPanel.add(pastTransactionContainer);
        }
        add(scrollPane);
    }
    private void handleTransaction(String transactionType,float amountVal){
        Transaction transaction;
        if(transactionType.equalsIgnoreCase("Deposit")){
            curuser.setCurrentBalance(curuser.getCurrentBalance().add(new BigDecimal(amountVal)));
            transaction = new Transaction(curuser.getId(),transactionType,new BigDecimal(amountVal),null);
        }
        else{
            curuser.setCurrentBalance(curuser.getCurrentBalance().subtract(new BigDecimal(amountVal)));
            transaction = new Transaction(curuser.getId(),transactionType,new BigDecimal(-amountVal),null);

        }
        if(myJDBC.addTransactionToDatabase(transaction) && myJDBC.updateCurrentBalance(curuser)){
            JOptionPane.showMessageDialog(this,transactionType+ " Successfully");
            resetFieldsAndUpdateCurrentBalance();
        }
        else{
            JOptionPane.showMessageDialog(this,transactionType+ " Failed");

        }
    }
    private void handleTransfer(user curuser,String receiver, float amount){

        if(myJDBC.transfer(curuser,receiver,amount)){
            JOptionPane.showMessageDialog(this,"Transfer Successful");
            resetFieldsAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this,"Transfer Failed");

        }

    }

    private void resetFieldsAndUpdateCurrentBalance(){
        enterAmountField.setText("");

        if(enterUserField!=null){
            enterUserField.setText("");
        }

        balanceLabel.setText("Balance: ₹"+curuser.getCurrentBalance());
        //update in db

        bankingAppGui0.getCurrentBalanceField().setText("₹"+curuser.getCurrentBalance());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        if(buttonPressed.equalsIgnoreCase("confirm")){
            String passsword = String.valueOf(confirmPasswordField.getPassword());
            verified = myJDBC.validateLogin(curuser.getUsername() , passsword, true);
            this.dispose();
            return;
        }
        float amountVal = Float.parseFloat(enterAmountField.getText());

        if(buttonPressed.equalsIgnoreCase("deposit")){
            handleTransaction(buttonPressed,amountVal);
        }
        else{
            int validAmount = curuser.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal)); // return -1 when less, 0 equal, 1 more
            if(validAmount<0){
                JOptionPane.showMessageDialog(this,"Error:Insufficient Funds");
                return;
            }
            if(buttonPressed.equalsIgnoreCase("withdraw")){
                handleTransaction(buttonPressed,amountVal);
            }
            else{
                String RecivingUser = enterUserField.getText();
                if(curuser.getUsername().equals(RecivingUser)){
                    JOptionPane.showMessageDialog(this,"Error:Invalid Username");
                    return;
                }
                bankingAppDialog bankingAppDialog0 = new bankingAppDialog(this,curuser);
                bankingAppDialog0.setTitle("Confirmation");
                bankingAppDialog0.addConfirmPassword();
                bankingAppDialog0.addActionButton("Confirm",true);
                bankingAppDialog0.setVisible(true);
                if(verified) {
                    handleTransfer(curuser, RecivingUser, amountVal);
                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:Verification Failed");
                }
            }
        }

    }
}
