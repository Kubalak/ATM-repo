package user;

import xml.XMLTools;

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
        locked = false;
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
        String PIN = XMLTools.getData(data,"<pin>","</pin>");
        String locked = XMLTools.getData(data,"<locked>","</locked>");
        String credit = XMLTools.getData(data,"<credit>","</credit>");
        String currency = XMLTools.getData(data,"<currency>","</currency>");
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
        catch (NullPointerException exception)
        {
            System.out.println("Null pointer error on CreditCard!");
            return new CreditCard(1111);
        }
        return null;
    }

}
