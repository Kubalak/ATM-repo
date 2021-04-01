package user;

import java.util.Locale;

public class Wallet
{
    private int tens,twenties,fifties,hundreds,two_hundreds,five_hundreds;
    public Wallet()
    {
        tens = twenties = fifties = hundreds = two_hundreds = five_hundreds = 1;
    }
    public void cash_in(String what, int amount)
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


    public void cash_in(int cash)
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
    public int cash_out(int cash)
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

    public int cash_out(String what, int amount)
    {
        if(amount<0||what==null){
            System.out.println("Operation not permitted!");
            return -1;
        }
        else if(what.toLowerCase().equals("10"))
        {
            if(amount>tens)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            tens-=amount;
        }
        else if(what.toLowerCase().equals("20")) {
            if(amount>twenties)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            twenties -= amount;
        }
        else if(what.toLowerCase().equals("50")){
            if(amount>fifties)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            fifties-=amount;
        }
        else if(what.toLowerCase().equals("100")){
            if(amount>hundreds)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            hundreds-=amount;
        }
        else if(what.toLowerCase().equals("200")){
            if(amount>two_hundreds)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            two_hundreds-=amount;
        }
        else if(what.toLowerCase().equals("500")){
            if(amount>five_hundreds)
            {
                System.out.println("Not enough money!");
                return -1;
            }
            five_hundreds-=amount;
        }
        else {
            System.out.println("No banknote available!");
            return -1;
        }
        return 0;
    }
    public int getAll()
    {
        return tens * 10 + twenties * 20 + fifties * 50 + hundreds * 100 + two_hundreds * 200 + five_hundreds * 500;
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
        tens = CreditCard.getData(data,"<tens>","</tens>");
        twenties = CreditCard.getData(data,"<twenties>","</twenties>");
        fifties = CreditCard.getData(data,"<fifties>","</fifties>");
        hundreds = CreditCard.getData(data,"<hundreds>","</hundreds>");
        two_hundreds = CreditCard.getData(data,"<twohundreds>","</twohundreds>");
        five_hundreds = CreditCard.getData(data,"<fivehundreds>","</fivehundreds>");
        try{
            Wallet result = new Wallet();
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
            System.out.println("Data is corrupted!");
        }
        return null;
    }
}
