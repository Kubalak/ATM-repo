package com.company;

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
    private String[] states = {"IDLE","INPUT","OUTPUT","PIN","SUMMARY"};
    private Vector<String> statesHistory;
    private String currentState;
    private int pinCode[];
    private int pinIndex,cardNo, failsNo;
    private boolean isCardInserted,stateChanged;

    StateManager()
    {
        pinCode = new int[4];
        pinIndex = 0;
        failsNo = 0;
        currentState = states[0];
        stateChanged = false;
        statesHistory = new Vector(0);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(null);
    }
    public int insertCard(int cardNo)
    {
        if(!isCardInserted&&(currentState.equals("INPUT")||currentState.equals("OUTPUT")))
        {
            isCardInserted = true;
            this.cardNo = cardNo;
            statesHistory.add(currentState);
            currentState = states[3];
            System.out.print("Enter PIN:");
            return 0;
        }
        return -1;
    }
    public int returnCard()
    {
        if(isCardInserted&&currentState.equals("SUMMARY"))
        {
            isCardInserted = false;
            cardNo=0;
            return 0;
        }
        return -1;
    }
    private void updateVisible()
    {
       // if(stateChanged)System.out.println("State changede from "+statesHistory.elementAt(statesHistory.size()-1)+" to "+currentState);
      //  else System.out.println("State remains unchanged");
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
                }
                else if(signal == -5)
                {
                    statesHistory.add(currentState);
                    currentState = states[2];
                    returnCode = 0;
                }
                else returnCode = -1;
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
                    if(pinIndex==4)
                    {
                        int code  = 0;
                        for(int i=0;i<4;i++)
                        {
                            code*=10;
                            code += pinCode[i];

                        }
                        System.out.println(code);
                        if(code == cardNo)
                        {
                            returnCode = 0;
                            System.out.println("Success");
                        }
                        else {
                            System.out.println("Failure");
                            System.out.print("Enter PIN again:");
                            pinIndex = 0;
                            failsNo++;
                            returnCode = -1;
                        }
                        System.out.println("");
                    }
                    //Pin verify here
                    else returnCode = -1;
                }
                else if(signal == -8)
                {
                    if(pinIndex>0)pinIndex--;
                    else returnCode = -1;
                }
                else if (signal == -9)pinIndex = 0;
                else if(signal == -10)
                {
                    statesHistory.clear();
                    currentState = states[0];
                    returnCode = 0;
                }

                break;
            case "SUMMARY":
                break;
        }
        if(returnCode==0)stateChanged = true;
        updateVisible();
        return returnCode;
    }


}
