package user;

import java.text.NumberFormat;
import java.util.Locale;

public class CreditCard extends Account
{

    private int PINNo;
    private boolean locked;
    public CreditCard(int PINNo, double value)
    {
        super(value);
        this.PINNo = PINNo;
    }
    public CreditCard(int PINNo)
    {
        super(0.0);
        this.PINNo = PINNo;
        locked = false;
    }
    public boolean verifyPIN(int PIN)
    {
        return PINNo == PIN;
    }
    public int changePIN(int oldPIN, int newPIN)
    {
        if(oldPIN==PINNo)
        {
            if(newPIN>=0) {
                PINNo = newPIN;
                return 0;
            }
            else return 1;
        }
        return 2;
    }

    public boolean changeCredit(int PINNo, double gap)
    {
        if(PINNo == this.PINNo)return super.changeCredit(gap);
        return false;
    }
    public void lock(){locked = true;}
    public void unlock(){locked = false;}
    public boolean isLocked(){return locked;}
    public static String getData(String input, String startingBy, String endingBy)
    {
        if(input == null)return null;
        String result;
        int begin,end;
        begin = input.indexOf(startingBy);
        end = input.indexOf(endingBy);
        if(begin>-1 && end>-1)result = input.substring(begin + startingBy.length(),end);
        else result = null;
        System.out.println(result);
        return result;
    }
    public static int countOccurrence(String data, String substring)
    {
        if(data == null)return -1;
        if(data.length()==0 || substring.length()==0)return 0;
        int index = 0, count = 0;
        while (true) {
            index = data.indexOf(substring, index);
            if (index != -1) {
                count ++;
                index += substring.length();
            } else break;
        }
        return count;
    }
    public String toXML(String margin, String spacer)
    {
        String result = margin+"<card>\n";
        result+=margin+spacer+"<pin>"+String.format("%04d",PINNo)+"</pin>\n";
        result+=margin+spacer+"<locked>"+ String.format("%s",locked?"YES":"NO")+"</locked>\n";
        result+=margin+spacer+"<credit>"+String.format("%.2f",super.checkCredit()).replace(',','.')+"</credit>\n";
        result+=margin+"</card>\n";
        return result;
    }
    public static CreditCard getFromXML(String data)
    {
        String PIN = getData(data,"<pin>","</pin>");
        String locked = getData(data,"<locked>","</locked>");
        String credit = getData(data,"<credit>","</credit>");
        try
        {
            CreditCard result = new CreditCard(Integer.parseInt(PIN),Double.parseDouble(credit));
            result.locked = locked.equals("YES");
            return result;
        }
        catch(NumberFormatException exception)
        {
            System.out.println("Data is corrupted!");
        }
        return null;
    }
    public static int firstIndexOf(String data, String identifiedBy)
    {
        int index = data.indexOf(identifiedBy);
        if(index < 0)return -1;
        return data.indexOf(identifiedBy)+identifiedBy.length();
    }

    public static String moveToNext(String data)
    {
        int index = firstIndexOf(data,"</card>");
        if(index < 0)return null;
        return data.substring(index);
    }
}
