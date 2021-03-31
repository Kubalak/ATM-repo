package com.company;
import com.company.user.User;

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
    private String[] states = {"IDLE","INPUT","OUTPUT","PIN","SUMMARY","FINALIZE"};
    private Vector<String> statesHistory;
    private String currentState;
    private int pinCode[];
    private int pinIndex, failsNo,cardIndex,moneyToBurn;
    private boolean isCardInserted,stateChanged,isOtherAmountSelected;
    private User user;
    StateManager(User user)
    {
        this.user = user;
        pinCode = new int[4];
        pinIndex = 0;
        failsNo = 3;
        isOtherAmountSelected = false;
        currentState = states[0];
        stateChanged = false;
        statesHistory = new Vector(0);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(null);
    }
    public int insertCard(int cardNo)
    {
        if(!isCardInserted&&(currentState.equals("INPUT")||currentState.equals("OUTPUT"))&&user.hasCard(cardNo))
        {
            cardIndex = cardNo;
            isCardInserted = true;
            statesHistory.add(currentState);
            currentState = states[3];
            System.out.print("Card insert success!\nPlease enter PIN:");
            return 0;
        }
        if(!user.hasCard(cardNo))System.out.println("Card check failed! Please insert another card.");
        if(isCardInserted) System.out.println("Card is already inserted!");
        else if(!currentState.equals("INPUT")&&!currentState.equals("OUTPUT")) System.out.println("Card insertion is now not allowed!");
        return -1;
    }
    public int returnCard()
    {
        if(isCardInserted && currentState.equals("FINALIZE"))
        {
            isCardInserted = false;
            System.out.println("Card returned.");
            return 0;
        }
        if(!isCardInserted) System.out.println("Card is not inserted!");
        if(!currentState.equals("FINALIZE")) System.out.println("You cannot get your card back now...");
        return -1;
    }
    private void updateVisible()
    {
       // if(stateChanged)System.out.println("State changede from "+statesHistory.elementAt(statesHistory.size()-1)+" to "+currentState);
      //  else System.out.println("State remains unchanged");
    }

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
                if(signal == -4)
                {
                    statesHistory.add(currentState);
                    currentState = states[1];
                    returnCode = 0;
                    System.out.println("Enter card.");
                }
                else if(signal == -5)
                {
                    statesHistory.add(currentState);
                    currentState = states[2];
                    returnCode = 0;
                    System.out.println("Enter card.");
                }
                else
                {
                    System.out.println("Please select operation!");
                    returnCode = -1;
                }
                break;
            case "INPUT":
            case "OUTPUT":
                if( signal == -9)
                {
                    statesHistory.clear();
                    currentState = states[0];
                    returnCode = 0;
                }
                else
                {
                    returnCode = -1;
                }
                break;
            case "PIN":
                if(signal>-1)
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
                    if(pinIndex==4 && failsNo > 0)
                    {
                        int code  = 0;
                        for(int i=0;i<4;i++)
                        {
                            code*=10;
                            code += pinCode[i];

                        }
                        System.out.println("\nYou entered: "+ code);
                        if(user.checkCard(0,code))
                        {
                            returnCode = 0;
                            System.out.println("Success\nPlease select amount of money you wish to take.");
                            currentState = states[4];
                        }
                        else {
                            failsNo--;
                            System.out.println("Failure! Number of trials remaining: " + failsNo);
                            System.out.print("Enter PIN again:");
                            pinIndex = 0;
                            returnCode = -1;
                        }
                    }
                    //Pin verify here
                    else
                    {
                        returnCode = -1;
                        if(failsNo == 0)
                        {
                            user.setBlockedCard(0);
                            System.out.println("Card locked!");
                        }
                    }
                }
                else if(signal == -8)
                {
                    if(pinIndex>0)pinIndex--;
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
                    currentState = states[0];
                    returnCode = 0;
                    System.out.println("Operation Cancelled! Going back to IDLE");
                }
                break;
            case "SUMMARY":
                //tutaj operacja wyboru kwoty wpłaty/wypłaty
                if(signal == -1 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(cardIndex,10.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        statesHistory.add(currentState);
                        currentState = states[5];
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -2 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(cardIndex,20.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        statesHistory.add(currentState);
                        currentState = states[5];
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -3 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(cardIndex,50.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        statesHistory.add(currentState);
                        currentState = states[5];
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -4 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(cardIndex,100.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        statesHistory.add(currentState);
                        currentState = states[5];
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");
                }
                else if(signal == -5 && !isOtherAmountSelected)
                {
                    returnCode = user.withdraw(cardIndex,200.0)?0:-1;
                    if(returnCode==0)
                    {
                        System.out.println("Withdraw success!");
                        statesHistory.add(currentState);
                        currentState = states[5];
                    }
                    else System.out.println("Withdraw failed!\nPlease select another option.");

                }
                else if(signal == -6 && !isOtherAmountSelected)
                {
                    isOtherAmountSelected = true;
                }
                else if(isOtherAmountSelected)
                {
                    if(signal>=1 && signal<10)
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
                        returnCode = user.withdraw(cardIndex,moneyToBurn)?0:-1;
                        if(returnCode==0)
                        {
                            System.out.println("Withdraw success!");
                            statesHistory.add(currentState);
                            currentState = states[5];
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
                        currentState = states[0];
                        returnCode = 0;
                        System.out.println("Operation Cancelled! Going back to IDLE");
                    }
                }
                else
                {
                    if(signal == -10)
                    {
                        statesHistory.clear();
                        currentState = states[0];
                        returnCode = 0;
                    }


                }
                break;
            case "FINALIZE":
                //tutaj operacja wyboru drukowania potwierdzenia
                if(signal == -4) {
                    System.out.println("Printing confirmation...");
                    System.out.println("Actual account credit is " + user.getCard(cardIndex).checkCredit() + " PLN");
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
                    System.out.println("Unrecognized operation!");
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
