package guis;

import db_objs.user;

import javax.swing.*;

public abstract class baseFrame extends JFrame {
    protected user curuser;

    public baseFrame(String title){
        initialize(title);
    }
    public baseFrame(String title,user curuser){
        this.curuser = curuser;
        initialize(title);
    }
    public void initialize(String title) {
        //jframe initiated and title
        setTitle(title);

        setSize(420, 600);

        //terminate when gui is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //setting layout null -> manual size and position for each gui comp
        setLayout(null);

        setResizable(false);

        //launch gui in center of page
        setLocationRelativeTo(null);

        addGuiComponents();
    }
    // will be overridden by subclass
    protected abstract void addGuiComponents();
}
