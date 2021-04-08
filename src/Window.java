import user.User;
import user.Wallet;
import xml.XMLTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Window extends JFrame implements ActionListener{

   private final JButton keyNumber1, keyNumber2, keyNumber3, keyNumber4, keyNumber5, keyNumber6;
   private final JButton keyNumber7, keyNumber8, keyNumber9, keyNumber0, keyNumber000, keyLeft1;
   private final JButton keyEnter, keyDelete, keyClear,keyCancel,keyCardtestonly;
   private final JButton [] WalletOps;
   private final JLabel [] WLabels;
   private final JMenuBar Menubar;
   private final JMenu Help,MUser;
   private final JMenuItem About,Save,Load,SwitchUser,SwitchCard;
   private final JButton keyLeft2, keyLeft3, keyRight1,keyRight2,keyRight3;
   private StateManager State;
   private int currentUser,ANumberOfUsers;
   private String currency;
   private Wallet operational,temporary;
   private User[] users;
   private void saveState()
   {
       try
       {
           FileWriter writer = new FileWriter("userdata/settings.xml");
           writer.write("<?xml version=\"1.0\"?>\n");
           writer.write("<users>\n"+XMLTools.toXML(currency,"currency")+"\n");
           if(users != null){
               writer.write("  <current>"+currentUser+"</current>\n");
               for(int i=0;i<users.length;++i)if(users[i] != null)writer.write(users[i].toXML("   ","   "));
           }
           else writer.write("null");
           writer.write("</users>");
           writer.close();
       }
       catch(IOException exception)
       {
           System.out.println("Save failed!");
       }
   }
    Window()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,900);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(this.getClass().getResource("textures/logo.png")).getImage());
        Menubar = new JMenuBar();
        Menubar.setBounds(0,0,800,25);
        Help = new JMenu("Help");
        MUser = new JMenu("User");
        SwitchCard = new JMenuItem("Switch Card");
        SwitchUser = new JMenuItem("Switch User");
        About = new JMenuItem("About");
        Save = new JMenuItem("Save");
        Load = new JMenuItem("Load");
        Load.addActionListener(this);
        Save.addActionListener(this);
        About.addActionListener(this);
        SwitchUser.addActionListener(this);
        SwitchCard.addActionListener(this);
        Help.add(Load);
        Help.add(Save);
        Help.add(About);
        MUser.add(SwitchUser);
        MUser.add(SwitchCard);
        Menubar.add(Help);
        Menubar.add(MUser);
        try {
            File input = new File("userdata/settings.xml");
            Scanner  reader = new Scanner(input);
            String data = "";
            while(reader.hasNextLine())
            {
                data += reader.nextLine();
            }
            reader.close();
            data = XMLTools.getData(data,"<users>","</users>");
            currency = XMLTools.getData(data,"<currency>","</currency>");
            currentUser = Integer.parseInt(XMLTools.getData(data,"<current>","</current>"));
            ANumberOfUsers = XMLTools.countOccurrence(data,"<user>");
            users = new User[ANumberOfUsers];
            for(int i=0;i<ANumberOfUsers;i++)
            {
                users[i] = User.getFromXML(data);
                data = User.moveToNext(data);
            }
            State = new StateManager(users[currentUser]);
            State.currency = currency;
            this.setTitle("ATM: "+users[currentUser].Name+" "+users[currentUser].Surname);
        }
        catch(NullPointerException | FileNotFoundException exception)
        {
            System.out.println("Cannot open file!");
            System.out.println(exception.getMessage());
            System.exit(1);
        }
        operational = new Wallet(true);
        temporary = users[currentUser].getWallet();
        JPanel top = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        JPanel bottom = new JPanel();
        JPanel center = new JPanel();
        JPanel keypad = new JPanel();


        bottom.setLayout(null);
        left.setLayout(null);
        right.setLayout(null);
        keypad.setLayout(null);
        center.setLayout(null);

        top.setBackground(new Color(0xcfcfcf));
        left.setBackground(new Color(0xcfcfcf));
        right.setBackground(new Color(0xcfcfcf));
        bottom.setBackground(new Color(0xcfcfcf));
        //center.setBackground(Color.WHITE);
        keypad.setBackground(new Color(0xe6e6e6));

        top.setPreferredSize(new Dimension(100,100));
        left.setPreferredSize(new Dimension(100,100));
        right.setPreferredSize(new Dimension(100,100));
        bottom.setPreferredSize(new Dimension(200,400));
        center.setPreferredSize(new Dimension(300,300));
        keypad.setSize(400,350);
        keypad.setLocation(120,40);

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
        WalletOps = new JButton[12];
        WLabels = new JLabel[12];
        for(int i=0;i<12;i++)
        {
            WalletOps[i] = new JButton();
            WalletOps[i].setBounds(600 + i%2*40,150 + 40 * (i / 2) ,30,30);
            WalletOps[i].setFont(new Font("Comic Sans",Font.PLAIN,5));
            WalletOps[i].setFocusable(false);
            WalletOps[i].addActionListener(this);

            WLabels[i] = new JLabel("x0");
            WLabels[i].setFont(new Font("Comic Sans",Font.BOLD,15));
            WLabels[i].setBounds(575 + i%2*105,150+ 40 * (i / 2) ,60,30);
            if(i%2==1)WLabels[i].setHorizontalTextPosition(SwingConstants.RIGHT);
        }

        WLabels[0].setText("x"+temporary.getAmount("10"));
        WLabels[2].setText("x"+temporary.getAmount("20"));
        WLabels[4].setText("x"+temporary.getAmount("50"));
        WLabels[6].setText("x"+temporary.getAmount("100"));
        WLabels[8].setText("x"+temporary.getAmount("200"));
        WLabels[10].setText("x"+temporary.getAmount("500"));

        keyEnter = new JButton("Enter");
        keyDelete = new JButton("Delete");
        keyClear = new JButton("Clear");
        keyCancel = new JButton("Cancel");
        keyCardtestonly = new JButton("Insert Card");

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

        keyEnter.setBounds(250,60,100,50);
        keyEnter.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyEnter.setFocusable(false);
        keyEnter.addActionListener(this);

        keyDelete.setBounds(250,130,100,50);
        keyDelete.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyDelete.setFocusable(false);
        keyDelete.addActionListener(this);

        keyClear.setBounds(250,200,100,50);
        keyClear.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyClear.setFocusable(false);
        keyClear.addActionListener(this);

        keyCancel.setBounds(250,270,100,50);
        keyCancel.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyCancel.setFocusable(false);
        keyCancel.addActionListener(this);

        keyCardtestonly.setBounds(550,40,175,100);
        keyCardtestonly.setFont(new Font("Comic Sans",Font.BOLD,20));
        keyCardtestonly.setFocusable(false);
        keyCardtestonly.addActionListener(this);

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
        keypad.add(keyEnter);
        keypad.add(keyDelete);
        keypad.add(keyClear);
        keypad.add(keyCancel);
        bottom.add(keypad);
        bottom.add(keyCardtestonly);
        for(int i=0;i<12;i++)
        {
            bottom.add(WalletOps[i]);
            bottom.add(WLabels[i]);
        }
        center.add(State);

        left.add(keyLeft1);
        left.add(keyLeft2);
        left.add(keyLeft3);

        right.add(keyRight1);
        right.add(keyRight2);
        right.add(keyRight3);

        this.setJMenuBar(Menubar);
        this.add(top, BorderLayout.NORTH);
        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);
        this.add(bottom, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);
        this.repaint();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==keyNumber0)State.sendSignal(0,operational);
        else if(e.getSource()==keyNumber1)State.sendSignal(1,operational);
        else if(e.getSource()==keyNumber2)State.sendSignal(2,operational);
        else if(e.getSource()==keyNumber3)State.sendSignal(3,operational);
        else if(e.getSource()==keyNumber4)State.sendSignal(4,operational);
        else if(e.getSource()==keyNumber5)State.sendSignal(5,operational);
        else if(e.getSource()==keyNumber6)State.sendSignal(6,operational);
        else if(e.getSource()==keyNumber7)State.sendSignal(7,operational);
        else if(e.getSource()==keyNumber8)State.sendSignal(8,operational);
        else if(e.getSource()==keyNumber9)State.sendSignal(9,operational);
        else if(e.getSource()==keyNumber000)State.sendSignal(10,operational);
        else if(e.getSource()==keyLeft1)State.sendSignal(-1,operational);
        else if(e.getSource()==keyLeft2)State.sendSignal(-2,operational);
        else if(e.getSource()==keyLeft3)State.sendSignal(-3,operational);
        else if(e.getSource()==keyRight1)State.sendSignal(-4,operational);
        else if(e.getSource()==keyRight2)State.sendSignal(-5,operational);
        else if(e.getSource()==keyRight3)State.sendSignal(-6,operational);
        else if(e.getSource()==keyEnter)State.sendSignal(-7,operational);
        else if(e.getSource()==keyDelete)State.sendSignal(-8,operational);
        else if(e.getSource()==keyClear)State.sendSignal(-9,operational);
        else if(e.getSource()==keyCancel)State.sendSignal(-10,operational);
        else if(e.getSource()==keyCardtestonly)
        {
            if(!State.isCardInserted())
            {
                if(State.insertCard() == 0)
                    keyCardtestonly.setEnabled(false);
            }
            else keyCardtestonly.setEnabled(true);

            State.updateVisible();
        }
        else if(e.getSource()==About)
        {
            JOptionPane.showMessageDialog(null,"ATM simulator v0.5.3.1","Info",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource()==Save)
        {
            saveState();
        }
        else if(e.getSource()==Load)
        {
            System.out.println("Hontoni argiato!");
        }
        else if(e.getSource()==SwitchUser)
        {

        }
        else if(e.getSource()==SwitchCard)
        {

        }
        else
        {
            String [] nominals={"10","20","50","100","200","500"};
            for(int i=0;i<12;i++)
            {
                if(e.getSource()==WalletOps[i]) {
                    switch (i % 2)
                    {
                        case 0:
                            if (temporary.cashOut(nominals[i / 2], 1)) operational.cashIn(nominals[i / 2], 1);
                            WLabels[i].setText("x" + temporary.getAmount(nominals[i / 2]));
                            WLabels[i + 1].setText("x" + operational.getAmount(nominals[i / 2]));
                            break;
                        case 1:
                            if (operational.cashOut(nominals[i / 2], 1)) temporary.cashIn(nominals[i / 2], 1);
                            WLabels[i - 1].setText("x" + temporary.getAmount(nominals[i / 2]));
                            WLabels[i].setText("x" + operational.getAmount(nominals[i / 2]));
                            break;
                    }
                    break;
                }
            }
        }
        if(!State.getCurrentState().equals("INPUT")) {
            temporary = users[currentUser].getWallet();
            operational.cashOut(operational.getAll());
        }
        keyCardtestonly.setEnabled(!State.isCardInserted());
        saveState();
        this.repaint();
    }
}
