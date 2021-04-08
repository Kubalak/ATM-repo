package user;

import xml.XMLTools;

import java.util.Locale;
import java.util.Random;

public class Wallet
{
    private int tens,twenties,fifties,hundreds,two_hundreds,five_hundreds;
    public Wallet(boolean empty)
    {
        if(empty) tens = twenties = fifties = hundreds = two_hundreds = five_hundreds = 0;
        else tens = twenties = fifties = hundreds = two_hundreds = five_hundreds = 1;
    }
    public Wallet(int cash)
    {
        if(cash<0)throw new SecurityException("Cannot insert < 0 amount of money!");
        five_hundreds = cash / 500;
        cash %= 500;
        two_hundreds = cash / 200;
        cash %= 200;
        hundreds = cash / 100;
        cash %= 100;
        fifties = cash / 50;
        cash %= 50;
        twenties = cash / 20;
        cash %= 20;
        tens = cash / 10;

    }
    public void cashIn(String what, int amount)
    {
        if(amount<0||what==null)System.out.println("Operation not permitted!");
        else if(what.toLowerCase().equals("10"))tens+=amount;
        else if(what.toLowerCase().equals("20"))twenties+=amount;
        else if(what.toLowerCase().equals("50"))fifties+=amount;
        else if(what.toLowerCase().equals("100"))hundreds+=amount;
        else if(what.toLowerCase().equals("200"))two_hundreds+=amount;
        else if(what.toLowerCase().equals("500"))five_hundreds+=amount;
        else System.out.println("No banknote available!");
    }


    public void cashIn(int cash)
    {
        if(cash<0) System.out.println("Cannot insert value lower than 0!");
        else {
             five_hundreds += cash / 500;
             cash %= 500;
             two_hundreds += cash / 200;
             cash %= 200;
             hundreds += cash / 100;
             cash %= 100;
             fifties += cash / 50;
             cash %= 50;
             twenties += cash / 20;
             cash %= 20;
             tens += cash / 10;

        }
    }
    public int cashOut(int cash)
    {
        if(cash<0) {
            System.out.println("Cannot withdraw value lower than 0!");
            return -1;
        }
        if( cash / 500 > five_hundreds) {
            System.out.println("Not enough money!");
            return -1;
        }
        five_hundreds -= cash / 500;
        cash %= 500;
        if( cash / 200 > two_hundreds) {
            System.out.println("Not enough money!");
            return -1;
        }
        two_hundreds -= cash / 200;
        cash %= 200;
        if( cash / 100 > hundreds) {
            System.out.println("Not enough money!");
            return -1;
        }
        hundreds -= cash / 100;
        cash %= 100;
        if( cash / 50 > fifties) {
            System.out.println("Not enough money!");
            return -1;
        }
        fifties -= cash / 50;
        cash %= 50;
        if( cash / 20 > twenties) {
            System.out.println("Not enough money!");
            return -1;
        }
        twenties -= cash / 20;
        cash %= 20;
        if( cash / 10 > tens) {
            System.out.println("Not enough money!");
            return -1;
        }
        tens -= cash / 10;
        return 0;
    }

    public boolean cashOut(String what, int amount)
    {
        if(amount<0||what==null){
            System.out.println("Operation not permitted!");
            return false;
        }
        else if(what.toLowerCase().equals("10"))
        {
            if(amount>tens)
            {
                System.out.println("Not enough money!");
                return false;
            }
            tens-=amount;
        }
        else if(what.toLowerCase().equals("20")) {
            if(amount>twenties)
            {
                System.out.println("Not enough money!");
                return false;
            }
            twenties -= amount;
        }
        else if(what.toLowerCase().equals("50")){
            if(amount>fifties)
            {
                System.out.println("Not enough money!");
                return false;
            }
            fifties-=amount;
        }
        else if(what.toLowerCase().equals("100")){
            if(amount>hundreds)
            {
                System.out.println("Not enough money!");
                return false;
            }
            hundreds-=amount;
        }
        else if(what.toLowerCase().equals("200")){
            if(amount>two_hundreds)
            {
                System.out.println("Not enough money!");
                return false;
            }
            two_hundreds-=amount;
        }
        else if(what.toLowerCase().equals("500")){
            if(amount>five_hundreds)
            {
                System.out.println("Not enough money!");
                return false;
            }
            five_hundreds-=amount;
        }
        else {
            System.out.println("No banknote available!");
            return false;
        }
        return true;
    }
    public int getAmount(String what)
    {
         if(what.toLowerCase().equals("10"))  return tens;
        else if(what.toLowerCase().equals("20"))return twenties;
        else if(what.toLowerCase().equals("50"))return fifties;
        else if(what.toLowerCase().equals("100"))return hundreds;
        else if(what.toLowerCase().equals("200"))return  two_hundreds;
        else if(what.toLowerCase().equals("500"))return five_hundreds;
        System.out.println("No banknote available!");
        return -1;
    }
    public int getAll()
    {
        return tens * 10 + twenties * 20 + fifties * 50 + hundreds * 100 + two_hundreds * 200 + five_hundreds * 500;
    }
    public boolean letTake(Wallet outcome)
    {
        return outcome.tens > tens || outcome.twenties > twenties || outcome.fifties > fifties || outcome.hundreds > hundreds || outcome.two_hundreds > two_hundreds || outcome.five_hundreds > five_hundreds;
    }
    public int take(Wallet outcome)
    {
        if(!this.letTake(outcome))return -1;
        tens -= outcome.tens;
        twenties -= outcome.twenties;
        fifties -= outcome.fifties;
        hundreds -= outcome.hundreds;
        two_hundreds -= outcome.two_hundreds;
        five_hundreds -= outcome.five_hundreds;
        return outcome.getAll();
    }

    public String toXML(String margin, String spacer)
    {
        String result = margin+"<wallet>\n";
        result+=margin+spacer+"<tens>"+tens+"</tens>\n";
        result+=margin+spacer+"<twenties>"+twenties+"</twenties>\n";
        result+=margin+spacer+"<fifties>"+fifties+"</fifties>\n";
        result+=margin+spacer+"<hundreds>"+hundreds+"</hundreds>\n";
        result+=margin+spacer+"<twohundreds>"+two_hundreds+"</twohundreds>\n";
        result+=margin+spacer+"<fivehundreds>"+five_hundreds+"</fivehundreds>\n";
        result+=margin+"</wallet>\n";
        return result;
    }
    public static Wallet getFromXML(String data)
    {
        String tens,twenties,fifties,hundreds,two_hundreds,five_hundreds;
        tens = XMLTools.getData(data,"<tens>","</tens>");
        twenties = XMLTools.getData(data,"<twenties>","</twenties>");
        fifties = XMLTools.getData(data,"<fifties>","</fifties>");
        hundreds = XMLTools.getData(data,"<hundreds>","</hundreds>");
        two_hundreds = XMLTools.getData(data,"<twohundreds>","</twohundreds>");
        five_hundreds = XMLTools.getData(data,"<fivehundreds>","</fivehundreds>");
        try{
            Wallet result = new Wallet(true);
            result.tens = Integer.parseInt(tens);
            result.twenties = Integer.parseInt(twenties);
            result.fifties = Integer.parseInt(fifties);
            result.hundreds = Integer.parseInt(hundreds);
            result.two_hundreds = Integer.parseInt(two_hundreds);
            result.five_hundreds = Integer.parseInt(five_hundreds);

            return result;
        }
        catch(NumberFormatException exception)
        {
            System.out.println("Error while parsing"+data);
            System.out.println("Data is corrupted!");
        }
        return null;
    }
}
