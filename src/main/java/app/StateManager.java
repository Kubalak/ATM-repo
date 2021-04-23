package app;
import settings.Settings;
import user.User;
import user.Wallet;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

/**
 * Klasa odpowiadająca za menadżer stanów wewnętrznych bankomatu.<br>
 * Jest ona odpowiedzialna za prawidłową rekację na sygnały wysyłane z klasy <i>Window</i> i reagowanie na nie.
 * @author Jakub Jach
 * @version 1.51
 * @since 2021-01-10
 */

public class StateManager extends JPanel
{
    /**
     * Stany weenętrzne bankomatu.
     */
    private final String[] states = {"IDLE","OUTPUT","INPUT","PIN","OP_SEL","SUMMARY","CREDIT","CHANGE"};
    /**
     * Ostatni stan bankomatu.
     */
    private String lastState;
     /**
     * Bieżący stan bankomatu - to na jego podstawie podejmuje decyzję o odpowiedniej reakcji na akcje ze strony użytkownika.
     */
    private String currentState;
    /**
     * Tablica przechowująca cyfry odpowiadające kolejnym cyfrom w kodzie PIN.
     */
    private final int[] pinCode;
    /**
     * Zmienne odpowiedzialne za wprowadzanie PIN / ilości gotówki.
     */
    private int pinIndex, failsNo,moneyToBurn, code  = 0;
    /**
     * Zmienne logiczne do sterowania zachowaniem bankomatu.
     */
    private boolean isCardInserted,stateChanged,isOtherAmountSelected;
    /**
     * Waluta używana przez bankomat - jedynie do celów estetycznych (bankomat nie ma modułu do przewalutowania).
     */
    public String currency;
    /**
     * Użytkownik, który obsługuje bankomat.
     */
    private User user;
    /**
     * Etykiety używane do komunikacji ze światem zewnętrznym.
     */
    private final JLabel LeftTop,LeftMiddle,LeftBottom,Top,Center,RightTop,RightMiddle,RightBottom;

    /**
     * Jedyny konstruktor klasy.
     * @param user <b style="color:#541704;">User</b> - Użytkownik, który będzie korzystał z bankomatu.
     */
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

        Top = new JLabel("Please insert card...");
        Top.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        Top.setBounds(150,100,300,30);
        Top.setHorizontalTextPosition(SwingConstants.CENTER);
        Top.setHorizontalAlignment(SwingConstants.CENTER);

        Center = new JLabel("");
        Center.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        Center.setBounds(170,230,260,30);
        Center.setHorizontalTextPosition(SwingConstants.CENTER);
        Center.setHorizontalAlignment(SwingConstants.CENTER);

        pinCode = new int[4];
        pinIndex = 0;
        failsNo = 3;
        isOtherAmountSelected = false;
        currentState = states[0];
        stateChanged = false;

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

    /**
     * Metoda zwraca aktualny stan bankomatu.
     * @return <b style="color:#0B5E03;">String</b> - Aktualny stan w postaci tekstu.
     */
    public final String getCurrentState(){return currentState;}

    /**
     * Metoda zwraca informację o tym czy stan uległ zmianie.
     * @return <b style="color:#B45700;">boolean</b> - Zwracana wartość <i style="color:#B45700;">true</i> jeśli stan uległ zmianie lub <i style="color:#B45700;">false</i> jeśli tak się nie stało.
     */
    public boolean hasChanged(){return stateChanged;}

    /**
     * Metoda pozwalająca uzyskać informację z jakiego stanu na jaki nastapiła zmiana
     * @return <b style="color:#0B5E03;">String[]</b> - Zwracana dwuelementowa tablica gdzie pod indeksem 0 znajduje się stan z którego nastapiła zmiana, a pod 1 obecny stan.
     */
    public final String[] stateChange()
    {
        String[] retVal = new String[2];
        retVal[0] = stateChanged?lastState:currentState;
        retVal[1] = currentState;
        stateChanged = false;
        return retVal;
    }

    /**
     * Metoda umożliwiająca włożenie karty do bankoamtu.<br>
     * Włożenie karty jest możliwe tylko w poszczególnych stanach bankomatu.
     * @return <b style="color:#B45700;">int</b> - Zwracana wartość - 0 w przypadku powodzenia lub -1 w przypadku niepowodzenia.
     */
    public int insertCard()
    {
        if(!isCardInserted && currentState.equals("IDLE"))
        {
            isCardInserted = true;
            lastState = currentState;
            stateChanged = true;
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

    /**
     * Metoda umożliwająca zdeponowanie gotówki.
     * @param cash <b style="color:#541704;">Wallet</b> - Portfel, z którego chcemy zdeponowac gotówkę.
     * @return <b style="color:#B45700;">boolean</b>
     */
    private boolean deposit(Wallet cash)
    {
        if(!currentState.equals("INPUT"))return false;
        return user.deposit(cash);
    }

    /**
     * Metoda (prywatna) służąca do zwrotu karty.
     * @return <b style="color:#B45700;">int</b> - Zwracana wartość 0 w przypadku powodzenia lub -1 w przypadku niepowodzenia.
     */
    private int returnCard()
    {
        if(isCardInserted && (currentState.equals("SUMMARY") || currentState.equals("ABORT") || currentState.equals("PIN") || currentState.equals("CREDIT") || currentState.equals("CHANGE")))
        {
            isCardInserted = false;
            System.out.println("Please take your card back.");
            return 0;
        }
        if(!isCardInserted) System.out.println("Card is not inserted!");
        if(!currentState.equals("FINALIZE")) System.out.println("You cannot get your card back now...");
        return -1;
    }

    /**
     * Metoda służąca do odświeżania zawartości ekranu.
     */
     public void updateVisible()
    {
       if(currentState.equals("IDLE"))
       {
            LeftTop.setText("");
            LeftMiddle.setText("");
            LeftBottom.setText("");
            Top.setText("Please insert card...");
            Center.setText("");
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
           LeftTop.setText("Withdraw");
           LeftMiddle.setText("Deposit");
           LeftBottom.setText("Check account state");
           Top.setText("Choose operation");
           Center.setText("");
           RightTop.setText("Change PIN");
           RightMiddle.setText("");
           RightBottom.setText("");

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
       else if(currentState.equals("INPUT"))
       {
           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("Put your banknotes into deposit");
           Center.setText("slot and press ENTER");
           RightTop.setText("");
           RightMiddle.setText("");
           RightBottom.setText("");

       }
       else if(currentState.equals("CHANGE"))
       {
           LeftTop.setText("");
           LeftMiddle.setText("");
           LeftBottom.setText("");
           Top.setText("Enter new PIN");
           Center.setText("" + "*".repeat(Math.max(0, pinIndex)));
           RightTop.setText("");
           RightMiddle.setText("");
           RightBottom.setText("");
       }

        this.repaint();
    }

    /**
     * Metoda zwraca informację o tym, czy karta jest włożona do bankomatu.
     * @return <b style="color:#B45700;">boolean</b>  - Zwraca <i style="color:#B45700;">true</i>, gdy karta jest włożona lub <i style="color:#B45700;">false</i> gdy nie jest.
     */
    public boolean isCardInserted(){return this.isCardInserted;}

    /**
     * Metoda resetująca ustawienia karty (liczbę prób i kod PIN).
     */
     private void resetCard()
    {
        pinIndex = 0;
        failsNo = 3;
    }
    /**
    * Metoda pozwalająca na zmianę użytkownika bankomatu.
    * @param newUser <b style="color:#541704;">User</b> - Nowy użytkownik bankomatu.
    */
    public void switchUser(User newUser)
    {
        user = newUser;
        resetCard();
    }

    /**
     * Metoda umożliwiająca zmianę stanu bankomatu.
     * @param state <b style="color:#0B5E03;">String</b> - Nowy stan, który ma przyjąć bankomat.
     */
    private void changeState(String state)
    {
        lastState = currentState;
        stateChanged = true;
        currentState = state;
        if(state.equals("OUTPUT"))
        {
            moneyToBurn = 0;
        }
        if(state.equals("IDLE"))
        {
            resetCard();
        }
    }
    /**
     * Metoda obsługująca sygnały w stanie <i>OP_SEL</i>.
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany do metody.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia, -1 w przypadku niepowodzenia lub -2 jeśli sygnał nie jest obsługiwany.
     */
    private int proceedOp(int signal)
    {
        if(signal == -1)
        {
            changeState(states[1]);
            System.out.println("You selected withdraw. Please select amount of money you wish to take");
            return 0;
        }
        else if(signal == -2)
        {
            changeState(states[2]);
            System.out.println("You selected deposit. Please give me your money!");
            return 0;
        }
        else if(signal == -3)
        {
            changeState(states[6]);
            System.out.println("You selected current account state.");
            return  0;
        }
        else if(signal == -4)
        {
            changeState(states[7]);
            System.out.println("You selected changing your PIN.");
            return 0;
        }
        System.out.println("Please select operation!");
        return -2;
    }

    /**
     * Metoda obsługująca sygnały w stanie <i>CHANGE</i>
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany do metody.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia, -1 w przypadku niepowodzenia lub -2 jeśli sygnał nie jest obsługiwany.
     */
    private int proceedChange(int signal)
    {
        if(signal >= 0 && signal < 10)
        {
            if(pinIndex < 4)
            {
                pinCode[pinIndex] = signal;
                pinIndex++;
                return 0;
            }
        }
        else if(signal == -7)
        {
            int tmp = 0;
            for(int i=0;i<4;i++)
            {
                tmp*=10;
                tmp+=pinCode[i];
            }
            if(pinIndex == 4)
            {
                changeState(states[5]);
                if(user.getCard().changePIN(code,tmp) == 0)
                {
                    this.returnCard();
                    return 0;
                }
                 return -1;
            }
            return -1;
        }
        else if(signal == -8)
        {
            if(pinIndex > 0)
            {
                pinIndex--;
                return 0;
            }
            else return -1;
        }
        else if (signal == -9)
        {
            pinIndex = 0;
            return 0;
        }
        else if(signal == -10)
        {
            changeState(states[0]);
            this.returnCard();
            return 0;
        }
        return -2;
    }
    /**
     * Metoda obsługująca sygnały w stanie <i>CREDIT</i>
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany do metody.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia, -1 w przypadku niepowodzenia lub -2 jeśli sygnał nie jest obsługiwany.
     */
    private int proceedCredit(int signal)
    {
        if(signal == -7)
        {
            System.out.println("Your current credit is "+user.getCard().checkCredit()+" "+currency);
            this.returnCard();
            changeState(states[5]);
            return 0;
        }
        else if(signal == -10)
        {
            System.out.println("You aborted operation");
            currentState = "ABORT";
            this.returnCard();
            changeState(states[0]);
            return 0;
        }
        return -2;
    }

    /**
     * Metoda służąca do próby wypłacenia ilości gotówki określonej przez <i>value</i>.
     * @param value <b style="color:#B45700;">int</b> - Ilość gotówki do wypłacenia.
     * @return <b style="color:#B45700;">int</b>- Zwraca 0 w przypadku powodzenia lub -1 w przypadku niepowodzenia.
     */
    private int tryWithdraw(int value)
    {
        if(user.withdraw(value))
        {
            System.out.println("Withdraw success!");
            changeState(states[5]);
            this.returnCard();
            return 0;
        }
        System.out.println("Withdraw failed!\nPlease select another option.");
        return -1;
    }
    /**
     * Metoda obsługująca sygnały w stanie <i>OUTPUT</i>
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany do metody.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia, -1 w przypadku niepowodzenia lub -2 jeśli sygnał nie jest obsługiwany.
     */
    private int proceedWithdraw(int signal)
    {
        if(signal == -1 && !isOtherAmountSelected)
        {
            moneyToBurn = 10;
            return tryWithdraw(10);
        }
        else if(signal == -2 && !isOtherAmountSelected)
        {
            moneyToBurn = 20;
           return  tryWithdraw(20);
        }
        else if(signal == -3 && !isOtherAmountSelected)
        {
            moneyToBurn = 50;
            return  tryWithdraw(50);
        }
        else if(signal == -4 && !isOtherAmountSelected)
        {
            moneyToBurn = 100;
            return tryWithdraw(100);
        }
        else if(signal == -5 && !isOtherAmountSelected)
        {
            moneyToBurn = 200;
            return tryWithdraw(200);
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
                return 0;
            }
            else if (signal == 10)
            {
                moneyToBurn *= 1000;
                return 0;
            }
            else if(signal == -7)
            {
                if(user.withdraw(moneyToBurn))
                {
                    System.out.println("Withdraw success!");
                   changeState(states[5]);
                    this.returnCard();
                    return 0;
                }
                System.out.println("Withdraw failed!\nPlease select another option.");
                return -1;
            }
            else if(signal == -8)
            {
                moneyToBurn /= 10;
                return 0;
            }
            else if(signal == -9)
            {
                moneyToBurn = 0;
                return 0;
            }
            else if(signal == -10)
            {

               System.out.println("Operation Cancelled! Going back to IDLE");
               changeState(states[0]);
               this.returnCard();
               return 0;
            }
        }
        else
        {
            if(signal == -10)
            {
               System.out.println("Operation Cancelled! Going back to IDLE");
                changeState(states[0]);
                this.returnCard();
                return 0;
            }
        }
        return -2;
    }
    /**
     * Metoda obsługująca sygnały w stanie <i>PIN</i>
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany do metody.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia, -1 w przypadku niepowodzenia lub -2 jeśli sygnał nie jest obsługiwany.
     */
    private int proceedPIN(int signal)
    {
        code = 0;
        if(signal>-1 && signal < 10)
        {
            if(pinIndex<4)
            {
                pinCode[pinIndex] = signal;
                System.out.print(signal);
                pinIndex++;
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else if(signal == -7)
        {
            if(pinIndex == 4 && !user.isCardLocked())
            {

                for(int i=0;i<4;i++)
                {
                    code*=10;
                    code += pinCode[i];
                }
                System.out.println("\nYou entered: "+ code);
                if(user.checkCard(code))
                {

                    System.out.println("Success\nPlease select operation.");
                    resetCard();
                    changeState(states[4]);
                    return 0;
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
                    return -1;

                }
            }
            //Pin verify here
            else
            {
                if(user.isCardLocked())
                {
                    System.out.println("Card locked!");
                }
                return -1;
            }
        }
        else if(signal == -8)
        {
            if(pinIndex>0)
            {
                pinIndex--;
                return 0;
            }
            else return -1;
        }
        else if (signal == -9)
        {
            pinIndex = 0;
            return 0;
        }
        else if(signal == -10)
        {
            System.out.println("Operation Cancelled! Going back to IDLE");
            changeState(states[0]);
            this.returnCard();
            return 0;
        }
        return -2;
    }

    /**
     * Metoda drukująca potwierdzenie do pliku.
     */
    private void printConfirm()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_HHmmss"), full = new SimpleDateFormat("dd MM yyyy - HH:mm:ss");
        try {
            FileWriter writer = new FileWriter("userdata/" + formatter.format(new Date())+".html");
            writer.write("<!DOCTYPE HTML>\n<html>\n<head>\n<title>Summary</title><meta charset=\"UTF-8\">\n</head>\n<body style=\"font-family:Arial;left-margin:1em;\">");
            writer.write("Date signature: <b>"+full.format(new Date())+"</b><br>");
            writer.write("Name: <b>"+user.Name+"</b><br>Surname: <b>"+user.Surname+"</b><br>PIN code compliant<br>");
            if(lastState.equals("OUTPUT"))
            {
            writer.write("Withdraw value: <b>"+ moneyToBurn+"</b> "+Settings.currency+"<br>");
            }
            else if(lastState.equals("CHANGE"))
            {
                writer.write("PIN code successfully changed.<br>");
            }
            else if(lastState.equals("INPUT"))
            {
                writer.write("Deposit value: <b>"+ moneyToBurn+"</b> "+Settings.currency+"<br>");
            }
            writer.write("Credit remaining after operation: <b>"+user.getCard().checkCredit()+"</b> "+ Settings.currency);
            writer.write("</body>\n</html>");
            writer.close();
        }
        catch(NullPointerException | IOException exception)
        {
            System.out.println("Cannot open file!");
        }
    }
    /**
     * Najdłuższa i najbardziej skomplikowana metoda z tej klasy.<br>
     * Odpowiada ona za reakcję na sygnały wysyłane przez klasę <i>Window</i>.
     * @param signal <b style="color:#B45700;">int</b> - Sygnał wysyłany przez okno:<br>
     * <div style="padding-left:4em;">0 do 9 cyfry z numpada<br>
     * 10  000 z numpada<br>
     * -1 do -6 boczne przyciski przy ekranie<br>
     * -7 do -10 przyciski <b style="font-family:'Calibri';color:blue;">ENTER DELETE CLEAR CANCEL</b><br></div>
     * @param base <b style="color:#541704;">Wallet</b> - Portfel używany do wykonywania operacji przez bankomat - odpowiednik szczeliny na pieniądze w prawdziwym bankomacie.
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia operacji, -1 gdy operacja się nie powiedzie lub -2 jeśli sygnał nie jest rozpoznawany w danym momencie.
     */
    public int sendSignal(int signal, Wallet base)
    {
        int returnCode = -2;
        stateChanged = false;
        switch (currentState)
        {
            case "IDLE":
                break;
            case "OP_SEL":
                returnCode = proceedOp(signal);
                break;
            case "CHANGE":
               returnCode = proceedChange(signal);
                break;
            case "CREDIT":
                returnCode = proceedCredit(signal);
                break;
            case "INPUT":
                if(signal==-7)
                {
                    moneyToBurn = base.getAll();
                    if(deposit(base))
                    {
                        returnCode = 0;
                        changeState(states[5]);
                        this.returnCard();
                    }
                    else
                    {
                        System.out.println("Deposit failed!");
                        returnCode = -1;
                    }
                }
                break;
            case "OUTPUT":
                returnCode = proceedWithdraw(signal);
                break;
            case "PIN":
               returnCode = proceedPIN(signal);
                break;
            case "SUMMARY":
                 //tutaj operacja wyboru drukowania potwierdzenia
                if(signal == -4) {
                    System.out.println("Printing confirmation...");
                    printConfirm();
                    System.out.println("Actual account credit is " + user.getCard().checkCredit() + " "+currency);
                    System.out.println("Thank you!");
                    returnCode = 0;
                    changeState(states[0]);
                }
                else if(signal == -6)
                {
                    System.out.println("Thank you!");
                    returnCode = 0;
                    changeState(states[0]);
                }
                break;
        }
        if(returnCode == -2)System.out.println("Unrecognized operation!");
        updateVisible();
        return returnCode;
    }
}