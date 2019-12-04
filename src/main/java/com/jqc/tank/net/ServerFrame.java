package com.jqc.tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    //Button btnStart = new Button("start");
    TextArea taLeft = new TextArea();
    TextArea taRight = new TextArea();
    Server server = new Server();
    private ServerFrame(){
        this.setSize(1600,600);
        this.setLocation(300,30);
        //this.add(btnStart, BorderLayout.NORTH);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taLeft);
        p.add(taRight);
        this.add(p);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

//        this.btnStart.addActionListener((e) ->{
//            server.serverStart();
//        });
    }

    public void updateServerMsg(String msg) {
        taLeft.setText(taLeft.getText() + System.getProperty("line.separator") + msg);
    }

    public void updateClientMsg(String msg) {
        taRight.setText(taRight.getText() + System.getProperty("line.separator") + msg);
    }

    public static void main(String[] args) {
        ServerFrame frame= ServerFrame.INSTANCE;
        frame.setVisible(true);
        frame.server.serverStart();
    }

}
