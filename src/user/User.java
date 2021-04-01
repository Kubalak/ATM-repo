package user;

import java.util.Arrays;

public class User
{
    private String Name,Surname;
    private Wallet wallet;
    private CreditCard[] cards;

    public User(String Name, String Surname)
    {
        this.Name = Name;
        this.Surname = Surname;
        cards = null;
        wallet = new Wallet();
    }
    public void addCards(CreditCard[] creditCards)
    {
        cards = new CreditCard[creditCards.length];
        System.arraycopy(creditCards, 0, cards, 0, cards.length);
    }
    public Wallet getWallet()
    {
        return wallet;
    }
    public void setWallet(Wallet newWallet)
    {
        this.wallet = newWallet;
    }
    public boolean hasCard(int CardNo)
    {
        return CardNo < cards.length;
    }
    public boolean checkCard(int CardNo, int PIN)
    {
        if(CardNo >= cards.length)return false;
        return cards[CardNo].verifyPIN(PIN);
    }
    public boolean setBlockedCard(int CardNo)
    {
        if(CardNo>=cards.length)return false;
        cards[CardNo].lock();
        return true;
    }
    public boolean setUnblockedCard(int CardNo)
    {
        if(CardNo>=cards.length)return false;
        cards[CardNo].unlock();
        return true;
    }
    public boolean withdraw(int CardNo,double value)
    {
        if(CardNo>=cards.length || value < 0.0)return false;
        return cards[CardNo].changeCredit(-value);

    }
    public CreditCard getCard(int CardNo)
    {
        if(CardNo>=cards.length )return null;
        return cards[CardNo];
    }
    public boolean isCardLocked(int CardNo)
    {
        if(CardNo>=cards.length)return true;
         return cards[CardNo].isLocked();
    }
    public String toXML(String margin, String spacer)
    {
        String result = margin+"<user>\n";
        result+=margin+spacer+"<name>"+Name+"</name>\n";
        result+=margin+spacer+"<surname>"+Surname+"</surname>\n";
        result+=wallet.toXML(margin + spacer, spacer);
        if(cards!=null) {
            result += margin + spacer + "<cards>\n";
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != null) result += cards[i].toXML(margin + spacer + spacer, spacer);
            }
            result += margin + spacer + "</cards>\n";
        }
        else result+=margin + spacer +"<cards>null</cards>\n";
        result+=margin+"</user>\n";
        return result;
    }
    public static String alter(String rawData)
    {
        if(rawData == null)return null;
        rawData = rawData.replaceAll("\t","");
        rawData = rawData.replaceAll("\n","");
        rawData = rawData.replaceAll(" ","");
        return rawData;
    }
    public static User getFromXML(String data)
    {
        User result;
        String name,surname,wallet,cards;
        int ANumberOfCards;
        name = CreditCard.getData(data,"<name>","</name>");
        surname = CreditCard.getData(data,"<surname>","</surname>");
        wallet = CreditCard.getData(data,"<wallet>","</wallet>");
        cards = CreditCard.getData(data,"<cards>","</cards>");
        result = new User(name,surname);
        ANumberOfCards = CreditCard.countOccurrence(cards,"<card>");
        result.cards = new CreditCard[ANumberOfCards];
        for(int i = 0;i < ANumberOfCards;i++)
        {
            result.cards[i] = CreditCard.getFromXML(cards);
            cards = CreditCard.moveToNext(cards);
        }
        result.wallet = Wallet.getFromXML(wallet);
        return result;
    }
    public static String moveToNext(String data)
    {
        int index = CreditCard.firstIndexOf(data,"</user>");
        if (index < 0)return null;
        return data.substring(index);
    }

}
