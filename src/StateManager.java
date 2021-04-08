import user.User;
import user.Wallet;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/*
Opis sygnałów:
0 do 9 cyfry z numpada
10  000 z numpada
-1 do -6 boczne przyciski przy ekranie
-7 do -10 przyciski ENTER DELETE CLEAR CANCEL

Zwracane kody:
 0 - powodzenie
-1 - niepowodzenie operacji
-2 - nierozpoznany kod sygnału

 */

public class StateManager extends JPanel
{
    private String[] states = {"IDLE","OUTPUT","INPUT","PIN","OP_SEL","SUMMARY","CREDIT"};
    private Vector<String> statesHistory;
    private String currentState;
    private int pinCode[];
    private int pinIndex, failsNo,moneyToBurn;
    private boolean isCardInserted,stateChanged,isOtherAmountSelected;
    public String currency;
    private User user;
    private JLabel LeftTop,LeftMiddle,LeftBottom,Top,Center,RightTop,RightMiddle,RightBottom;
    StateManager(User user)
    {
        this.user = user;
        this.setLayout(null);
        this.setBounds(0,0,600,500);
        this.setBorder(null);

        LeftTop = new JLabel("");
        LeftTop.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        LeftTop.setBounds(20,65,200,30);

        LeftMiddle = new JLabel("");
        LeftMiddle.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        LeftMiddle.setBounds(20,175,200,30);

        LeftBottom = new JLabel("");
        LeftBottom.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        LeftBottom.setBounds(20,285,200,30);

        RightTop = new JLabel("");
        RightTop.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        RightTop.setBounds(350,65,200,30);
        RightTop.setHorizontalTextPosition(SwingConstants.RIGHT);
        RightTop.setHorizontalAlignment(SwingConstants.RIGHT);

        RightMiddle = new JLabel("");
        RightMiddle.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        RightMiddle.setBounds(350,175,200,30);
        RightMiddle.setHorizontalTextPosition(SwingConstants.RIGHT);
        RightMiddle.setHorizontalAlignment(SwingConstants.RIGHT);

        RightBottom = new JLabel("");
        RightBottom.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        RightBottom.setBounds(350,285,200,30);
        RightBottom.setHorizontalTextPosition(SwingConstants.RIGHT);
        RightBottom.setHorizontalAlignment(SwingConstants.RIGHT);

        Top = new JLabel("");
        Top.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        Top.setBounds(200,100,200,30);
        Top.setHorizontalTextPosition(SwingConstants.CENTER);
        Top.setHorizontalAlignment(SwingConstants.CENTER);

        Center = new JLabel("Please insert card...");
        Center.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        Center.setBounds(200,230,200,30);
        Center.setHorizontalTextPosition(SwingConstants.CENTER);
        Center.setHorizontalAlignment(SwingConstants.CENTER);

        pinCode = new int[4];
        pinIndex = 0;
        failsNo = 3;
        isOtherAmountSelected = false;
        currentState = states[0];
        stateChanged = false;
        statesHistory = new Vector(0);

        this.add(LeftTop);
        this.add(LeftMiddle);
        this.add(LeftBottom);
        this.add(RightTop);
        this.add(RightMiddle);
        this.add(RightBottom);
        this.add(Top);
        this.add(Center);
        this.repaint();
        System.out.println("Insert card.");
    }
    public int insertCard()
    {
        if(!isCardInserted && currentState.equals("IDLE"))
        {
            isCardInserted = true;
            statesHistory.add(currentState);
            currentState = states[3];
            System.out.print("Card insert success!\nPlease enter PIN:");
            return 0;
        }
        if(user.isCardLocked())System.out.println("Card check failed! Please insert another card.");
        if(isCardInserted) System.out.println("Card is already inserted!");
        else if(!currentState.equals("INPUT")&&!currentState.equals("OUTPUT")) System.out.println("Card insertion is not allowed at the moment!");
        else System.out.println("Card insertion is now not allowed!");
        return -1;
    }
    private int returnCard()
    {
        if(isCardInserted && (currentState.equals("SUMMARY") || currentState.equals("ABORT") || currentState.equals("PIN") || currentState.equals("CREDIT")))
        {
            isCardInserted = false;
            System.out.println("Please take your card back.");
            return 0;
        }
        if(!isCardInserted) System.out.println("Card is not inserted!");
        if(!currentState.equals("FINALIZE")) System.out.println("You cannot get your card back now...");
        return -1;
    }
    public void updateVisible()
    {
       if(currentState.equals("IDLE"))
       {
            LeftTop.setText("");
            LeftMiddle.setText("");
            LeftBottom.setText("");
            Top.setText("");
            Center.setText("Please insert card...");
            RightTop.setText("");
           RightMiddle.setText("");
           RightBottom.setText("");
       }
       else if(currentState.equals("PIN"))
       {

           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("PIN:");
           StringBuilder code = new StringBuilder("");
           for(int i=0;i<pinIndex;++i)code.append("*");
           Center.setText(code.toString());
           RightTop.setText("");
           RightMiddle.setText("");
           RightBottom.setText("");
       }
       else if(currentState.equals("OP_SEL"))
       {
           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("Choose operation");
           Center.setText("");
           RightTop.setText("Withdraw");
           RightMiddle.setText("Deposit");
           RightBottom.setText("Check account state");

       }
       else if(currentState.equals("CREDIT"))
       {
           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("Your credit is:");
           Center.setText(user.getCard().checkCredit()+" "+currency);
           RightTop.setText("");
           RightMiddle.setText("");
           RightBottom.setText("");
       }
       else if(currentState.equals("OUTPUT"))
       {
           if(!isOtherAmountSelected)
           {
               LeftTop.setText("10");
               LeftMiddle.setText("20");
               LeftBottom.setText("50");
               Top.setText("Please select amount of money to withdraw");
               Center.setText("");
               RightTop.setText("100");
               RightMiddle.setText("200");
               RightBottom.setText("Other...");
           }
           else
           {
               LeftTop.setText("");
               LeftMiddle.setText("");
               LeftBottom.setText("");
               Top.setText("Please enter value:");
               Center.setText(""+moneyToBurn);
               RightTop.setText("");
               RightMiddle.setText("");
               RightBottom.setText("");
           }
       }
       else if(currentState.equals("SUMMARY"))
       {
           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("Do you wish to take receipt?");
           Center.setText("");
           RightTop.setText("Yes");
           RightMiddle.setText("");
           RightBottom.setText("No");
       }

        this.repaint();
    }
    public boolean isCardInserted(){return this.isCardInserted;}
    public void switchUser(User newUser)
    {
        user = newUser;
    }
    public int sendSignal(int signal)
    {
        int returnCode = -2;
        switch (currentState)
        {
            case "IDLE":
                if(signal>=-10&&signal<=10)
                    currentState = states[3];

                break;
            case "OP_SEL":
                if(signal == -4)
                {
                    statesHistory.add(currentState);
                    currentState = states[1];
                    returnCode = 0;
                    System.out.println("You selected withdraw. Please select amount of money you wish to take");
                }
                else if(signal == -5)
                {
                    statesHistory.add(currentState);
                    currentState = states[2];
                    returnCode = 0;
                    System.out.println("You selected deposit. Please give me your money!");
                }
                else if(signal == -6)
                {
                    statesHistory.add(currentState);
                    currentState = states[6];
                    returnCode = 0;
                    System.out.println("You selected current account state.");
                }
                else
                {
                    System.out.println("Please select operation!");
                    returnCode = -1;
                 }
                break;
            case "CREDIT":
                if(signal == -7)
                {
                    System.out.println("Your current credit is "+user.getCard().checkCredit()+" "+currency);
                    this.returnCard();
                    statesHistory.clear();
                    currentState = states[0];
                    returnCode = 0;
                }
                else if(signal == -10)
                {
                    System.out.println("You aborted operation");
                    currentState = "ABORT";
                    this.returnCard();
                    statesHistory.clear();
                    currentState = states[0];
                    returnCode = 0;
                }
                break;

            case "INPUT":
                System.out.println("This operation is not supported right now.");
                currentState = states[0];
                break;
            case "OUTPUT":
                if(signal == -1 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(10.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        Wallet wallet =  user.getWallet();
                        wallet.cashIn(10);
                        user.setWallet(wallet);
                        statesHistory.add(currentState);
                        currentState = states[5];
                        this.returnCard();
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -2 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(20.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        Wallet wallet =  user.getWallet();
                        wallet.cashIn(20);
                        user.setWallet(wallet);
                        statesHistory.add(currentState);
                        currentState = states[5];
                        this.returnCard();
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -3 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(50.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        Wallet wallet =  user.getWallet();
                        wallet.cashIn(50);
                        user.setWallet(wallet);
                        statesHistory.add(currentState);
                        currentState = states[5];
                        this.returnCard();
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -4 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(100.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        Wallet wallet =  user.getWallet();
                        wallet.cashIn(100);
                        user.setWallet(wallet);
                        statesHistory.add(currentState);
                        currentState = states[5];
                        this.returnCard();
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -5 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(200.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        Wallet wallet =  user.getWallet();
                        wallet.cashIn(200);
                        user.setWallet(wallet);
                        statesHistory.add(currentState);
                        currentState = states[5];
                        this.returnCard();
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");

                }
                else if(signal == -6 && !isOtherAmountSelected)
                {
                    isOtherAmountSelected = true;
                    System.out.println("YES");
                }
                else if(isOtherAmountSelected)
                {
                    if(signal>=0 && signal<10)
                    {
                        moneyToBurn *= 10;
                        moneyToBurn += signal;
                        returnCode = 0;
                    }
                    else if (signal == 10)
                    {
                        moneyToBurn *= 1000;
                        returnCode = 0;
                    }
                    else if(signal == -7)
                    {
                        returnCode = user.withdraw(moneyToBurn)?0:-1;
                        if(returnCode==0)
                        {
                            System.out.println("Withdraw success!");
                            Wallet wallet =  user.getWallet();
                            wallet.cashIn(moneyToBurn);
                            user.setWallet(wallet);
                            statesHistory.add(currentState);
                            currentState = states[5];
                            this.returnCard();
                        }
                        else System.out.println("Withdraw failed!\nPlease select another option.");
                    }
                    else if(signal == -8)
                    {
                        moneyToBurn /= 10;
                        returnCode = 0;
                    }
                    else if(signal == -9)
                    {
                        moneyToBurn = 0;
                        returnCode = 0;
                    }
                    else if(signal == -10)
                    {
                        statesHistory.clear();
                        currentState = "ABORT";
                        returnCode = 0;
                        this.returnCard();
                        System.out.println("Operation Cancelled! Going back to IDLE");
                        currentState = states[0];
                    }
                }
                else
                {
                    if(signal == -10)
                    {
                        statesHistory.clear();
                        currentState = "ABORT";
                        returnCode = 0;
                        this.returnCard();
                        System.out.println("Operation Cancelled! Going back to IDLE");
                        currentState = states[0];
                    }
                }
                break;
            case "PIN":
                if(signal>-1 && signal < 10)
                {
                    if(pinIndex<4)
                    {
                        pinCode[pinIndex] = signal;
                        System.out.print(signal);
                        pinIndex++;
                        returnCode = 0;
                    }
                    else
                    {
                        returnCode = -1;
                    }
                }
                else if(signal == -7)
                {
                    if(pinIndex == 4 && !user.isCardLocked())
                    {
                        int code  = 0;
                        for(int i=0;i<4;i++)
                        {
                            code*=10;
                            code += pinCode[i];
                        }
                        System.out.println("\nYou entered: "+ code);
                        if(user.checkCard(code))
                        {
                            returnCode = 0;
                            System.out.println("Success\nPlease select operation.");
                            pinIndex = 0;
                            currentState = states[4];
                        }
                        else {
                            failsNo--;
                            if(failsNo == 0)
                            {
                                user.blockCard();
                                System.out.println("Card locked!");
                            }
                            else {
                                System.out.println("Failure! Number of trials remaining: " + failsNo);
                                System.out.print("Enter PIN again:");
                                pinIndex = 0;
                            }
                            returnCode = -1;

                        }
                    }
                    //Pin verify here
                    else
                    {
                        returnCode = -1;
                        if(user.isCardLocked())
                        {
                            System.out.println("Card locked!");
                        }
                    }
                }
                else if(signal == -8)
                {
                    if(pinIndex>0)
                    {
                        pinIndex--;
                        returnCode = 0;
                    }
                    else returnCode = -1;
                }
                else if (signal == -9)
                {
                    pinIndex = 0;
                    returnCode = 0;
                }
                else if(signal == -10)
                {
                    statesHistory.clear();
                    currentState = "ABORT";
                    pinIndex = 0;
                    returnCode = 0;
                    this.returnCard();
                    System.out.println("Operation Cancelled! Going back to IDLE");
                    currentState = states[0];
                }
                break;
            case "SUMMARY":
                 //tutaj operacja wyboru drukowania potwierdzenia
                if(signal == -4) {
                    System.out.println("Printing confirmation...");
                    System.out.println("Actual account credit is " + user.getCard().checkCredit() + " "+currency);
                    System.out.println("Thank you!");
                    returnCode = 0;
                    statesHistory.clear();
                    currentState = states[0];
                }
                else if(signal == -6)
                {
                    System.out.println("Thank you!");
                    returnCode = 0;
                    statesHistory.clear();
                    currentState = states[0];
                }
                else
                {
                    returnCode = -2;
                }
                break;
        }
        if(returnCode != -2)stateChanged = true;
        else System.out.println("Unrecognized operation!");
        updateVisible();
        return returnCode;
    }
}
