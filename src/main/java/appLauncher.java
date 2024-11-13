import db_objs.user;
import guis.bankingAppGui;
import guis.loginGui;
import guis.registerGui;

import javax.swing.*;
import java.math.BigDecimal;

public class appLauncher {
    public static void main(String[] args) {
        // using invokeLater to make updates to gui more thread safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new loginGui().setVisible(true);
                //new registerGui().setVisible(true);
                //new bankingAppGui(new user( 1,"user1","password", new BigDecimal(20.10))).setVisible(true);
            }
        });
    }
}
