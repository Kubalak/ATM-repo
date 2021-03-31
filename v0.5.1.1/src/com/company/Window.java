package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window extends JFrame implements ActionListener{

    JButton keyNumber1, keyNumber2, keyNumber3, keyNumber4, keyNumber5, keyNumber6;
    JButton keyNumber7, keyNumber8, keyNumber9, keyNumber0, keyNumber000, keyLeft1;
    JButton keyEnter, keyDelete, keyClear,keyCancel,keyCardtestonly;
    JMenuBar Menubar;
    JMenu Help;
    JMenuItem About;
    JButton keyLeft2, keyLeft3, keyRight1,keyRight2,keyRight3;
    JLabel ActionIn, ActionOut;
    StateManager State;
    Window(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,900);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("Bankomat");
        Menubar = new JMenuBar();
        Menubar.setBounds(0,0,800,25);
        Help = new JMenu("Help");
        About = new JMenuItem("About");
        About.addActionListener(this);
        Help.add(About);
        Menubar.add(Help);
        State = new StateManager();

        JPanel top = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        JPanel bottom = new JPanel();
        JPanel center = new JPanel();
        JPanel keypad = new JPanel();
        ActionIn = new JLabel("Wpłata");
        ActionIn.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        ActionIn.setSize(new Dimension(100,25));
        ActionIn.setLocation(500,65);
        ActionOut = new JLabel("Wypłata");
        ActionOut.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        ActionOut.setSize(new Dimension(100,25));
        ActionOut.setLocation(500,160);

        bottom.setLayout(null);
        left.setLayout(null);
        right.setLayout(null);
        keypad.setLayout(null);
        center.setLayout(null);

        top.setBackground(new Color(0xcfcfcf));
        left.setBackground(new Color(0xcfcfcf));
        right.setBackground(new Color(0xcfcfcf));
        bottom.setBackground(new Color(0xcfcfcf));
        center.setBackground(Color.WHITE);
        keypad.setBackground(new Color(0xe6e6e6));

        top.setPreferredSize(new Dimension(100,100));
        left.setPreferredSize(new Dimension(100,100));
        right.setPreferredSize(new Dimension(100,100));
        bottom.setPreferredSize(new Dimension(200,400));
        center.setPreferredSize(new Dimension(300,300));
        keypad.setSize(250,350);
        keypad.setLocation(125,75);

        top.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        left.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        right.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        center.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
        keypad.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));


        keyNumber1 = new JButton();
        keyNumber2 = new JButton();
        keyNumber3 = new JButton();
        keyNumber4 = new JButton();
        keyNumber5 = new JButton();
        keyNumber6 = new JButton();
        keyNumber7 = new JButton();
        keyNumber8 = new JButton();
        keyNumber9 = new JButton();
        keyNumber0 = new JButton();
        keyNumber000 = new JButton();

       keyLeft1 = new JButton();
       keyLeft2 = new JButton();
       keyLeft3 = new JButton();

       keyRight1 = new JButton();
       keyRight2 = new JButton();
       keyRight3 = new JButton();


        keyNumber1.setBounds(20,60,50,50);
        keyNumber1.setText("1");
        keyNumber1.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber1.setFocusable(false);
        keyNumber1.addActionListener(this);

        keyNumber2.setBounds(90,60,50,50);
        keyNumber2.setText("2");
        keyNumber2.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber2.setFocusable(false);
        keyNumber2.addActionListener(this);

        keyNumber3.setBounds(160,60,50,50);

        keyNumber3.setText("3");
        keyNumber3.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber3.setFocusable(false);
        keyNumber3.addActionListener(this);

        keyNumber4.setBounds(20,130,50,50);
        keyNumber4.setText("4");
        keyNumber4.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber4.setFocusable(false);
        keyNumber4.addActionListener(this);

        keyNumber5.setBounds(90,130,50,50);
        keyNumber5.setText("5");
        keyNumber5.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber5.setFocusable(false);
        keyNumber5.addActionListener(this);

        keyNumber6.setBounds(160,130,50,50);
        keyNumber6.setText("6");
        keyNumber6.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber6.setFocusable(false);
        keyNumber6.addActionListener(this);

        keyNumber7.setBounds(20,200,50,50);
        keyNumber7.setText("7");
        keyNumber7.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber7.setFocusable(false);
        keyNumber7.addActionListener(this);

        keyNumber8.setBounds(90,200,50,50);
        keyNumber8.setText("8");
        keyNumber8.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber8.setFocusable(false);
        keyNumber8.addActionListener(this);

        keyNumber9.setBounds(160,200,50,50);
        keyNumber9.setText("9");
        keyNumber9.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber9.setFocusable(false);
        keyNumber9.addActionListener(this);

        keyNumber0.setBounds(60,270,50,50);
        keyNumber0.setText("0");
        keyNumber0.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber0.setFocusable(false);
        keyNumber0.addActionListener(this);

        keyNumber000.setBounds(128,270,80,50);
        keyNumber000.setText("000");
        keyNumber000.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyNumber000.setFocusable(false);
        keyNumber000.addActionListener(this);
        //keyNumber1.setBackground(new Color(0xd9d9d9));

        keyLeft1.setBounds(25,50,50,50);
        keyLeft1.addActionListener(this);

        keyLeft2.setBounds(25,160,50,50);
        keyLeft2.addActionListener(this);

        keyLeft3.setBounds(25,270,50,50);
        keyLeft3.addActionListener(this);

        keyRight1.setBounds(25,50,50,50);
        keyRight1.addActionListener(this);

        keyRight2.setBounds(25,160,50,50);
        keyRight2.addActionListener(this);

        keyRight3.setBounds(25,270,50,50);
        keyRight3.addActionListener(this);


        this.setJMenuBar(Menubar);
        this.add(top, BorderLayout.NORTH);
        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);



        keypad.add(keyNumber1);
        keypad.add(keyNumber2);
        keypad.add(keyNumber3);
        keypad.add(keyNumber4);
        keypad.add(keyNumber5);
        keypad.add(keyNumber6);
        keypad.add(keyNumber7);
        keypad.add(keyNumber8);
        keypad.add(keyNumber9);
        keypad.add(keyNumber0);
        keypad.add(keyNumber000);
        bottom.add(keypad);
        center.add(State);
        center.add(ActionIn);
        center.add(ActionOut);

        left.add(keyLeft1);
        left.add(keyLeft2);
        left.add(keyLeft3);

        right.add(keyRight1);
        right.add(keyRight2);
        right.add(keyRight3);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==keyNumber0)
        {
            //System.out.println("0");
            State.sendSignal(0);
        }
        else if(e.getSource()==keyNumber1)
        {
            //System.out.println("1");
            State.sendSignal(1);
        }
        else if(e.getSource()==keyNumber2)
        {
           // System.out.println("2");
            State.sendSignal(2);
        }
        else if(e.getSource()==keyNumber3)
        {
          //  System.out.println("3");
            State.sendSignal(3);
        }
        else if(e.getSource()==keyNumber4)
        {
           // System.out.println("4");
            State.sendSignal(4);
        }
        else if(e.getSource()==keyNumber5)
        {
           // System.out.println("5");
            State.sendSignal(5);
        }
        else if(e.getSource()==keyNumber6)
        {
         //   System.out.println("6");
            State.sendSignal(6);
        }
        else if(e.getSource()==keyNumber7)
        {
         //   System.out.println("7");
            State.sendSignal(7);
        }
        else if(e.getSource()==keyNumber8)
        {
           // System.out.println("8");
            State.sendSignal(8);
        }
        else if(e.getSource()==keyNumber9)
        {
         //   System.out.println("9");
            State.sendSignal(9);
        }
        else if(e.getSource()==keyNumber000)
        {
        //    System.out.println("000");
            //State.sendSignal(10);
            State.insertCard(1111);
        }
        else if(e.getSource()==keyLeft1)
        {
       //     System.out.println("Left1");
            //State.sendSignal(-1);
            State.sendSignal(-7);
        }
        else if(e.getSource()==keyLeft2)
        {
         //   System.out.println("Left2");
            //State.sendSignal(-2);
            State.sendSignal(-8);
        }
        else if(e.getSource()==keyLeft3)
        {
         //   System.out.println("Left3");
            //State.sendSignal(-3);
            State.sendSignal(-9);
        }
        else if(e.getSource()==keyRight1)
        {
         //   System.out.println("Right1");
            State.sendSignal(-4);
        }
        else if(e.getSource()==keyRight2)
        {
         //   System.out.println("Right2");
            State.sendSignal(-5);
        }
        else if(e.getSource()==keyRight3)
        {
          //  System.out.println("Right3");
            State.sendSignal(-6);
        }
        else if(e.getSource()==About)
        {
            JOptionPane.showMessageDialog(null,"ATM simulator v0.5.1.1","Info",JOptionPane.INFORMATION_MESSAGE);
        }


    }
}
