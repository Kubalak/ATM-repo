package user;

import xml.XMLTools;

import java.util.Arrays;

public class User
{
    public final String Name,Surname;
    private Wallet wallet;
    private int currentCard,ANumberOfCards;
    private CreditCard[] cards;

    public User(String Name, String Surname)
    {
        this.Name = Name;
        this.Surname = Surname;
        cards = null;
        wallet = new Wallet(false);
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
    public boolean switchCard(int CardNO)
    {
        if(CardNO>=ANumberOfCards )return false;
        currentCard = CardNO;
        return true;
    }
    public boolean checkCard(int PIN)
    {
        return cards[currentCard].verifyPIN(PIN);
    }
    public void blockCard()
    {
        cards[currentCard].lock();
    }
    public void unlockCard()
    {
        cards[currentCard].unlock();
    }
    public boolean withdraw(double value)
    {
        if(value < 0.0)return false;
        boolean OK = cards[currentCard].changeCredit(-value);
        if(OK)  wallet.cashIn((int)value);
        return OK;
    }
    public boolean deposit (Wallet cash)
    {
        if(!wallet.letTake(cash))return false;
        cards[currentCard].changeCredit(wallet.take(cash));
        return true;
    }
    public CreditCard getCard()
    {
        return cards[currentCard];
    }
    public boolean isCardLocked()
    {
         return cards[currentCard].isLocked();
    }
    public String toXML(String margin, String spacer)
    {
        String result = margin+"<user>\n";
        result+=margin+spacer+"<name>"+Name+"</name>\n";
        result+=margin+spacer+"<surname>"+Surname+"</surname>\n";
        result+=wallet.toXML(margin + spacer, spacer);
        if(cards!=null) {
            result += margin + spacer + "<cards>\n";
            result+=margin+spacer+"<current>"+currentCard+"</current>\n";
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != null) result += cards[i].toXML(margin + spacer + spacer, spacer);
            }
            result += margin + spacer + "</cards>\n";
        }
        else result+=margin + spacer +"<cards>null</cards>\n";
        result+=margin+"</user>\n";
        return result;
    }
    public static User getFromXML(String data)
    {
        User result;
        String name,surname,wallet,cards;
        name = XMLTools.getData(data,"<name>","</name>");
        surname = XMLTools.getData(data,"<surname>","</surname>");
        wallet = XMLTools.getData(data,"<wallet>","</wallet>");
        cards = XMLTools.getData(data,"<cards>","</cards>");
        result = new User(name,surname);
        result.ANumberOfCards = XMLTools.countOccurrence(cards,"<card>");
        try {
            result.currentCard = Integer.parseInt(XMLTools.getData(cards, "<current>", "</current>"));
        }
        catch(NullPointerException | NumberFormatException exception)
        {
            System.out.println("Error on getting current card!\nUsing defaults...");
            result.currentCard = 0;
        }
        result.cards = new CreditCard[result.ANumberOfCards];
        for(int i = 0;i < result.ANumberOfCards;i++)
        {
            result.cards[i] = CreditCard.getFromXML(cards);
            cards = XMLTools.moveToNext(cards,"</card>");
        }
        result.wallet = Wallet.getFromXML(wallet);
        return result;
    }
    public static String moveToNext(String data)
    {
        int index = XMLTools.firstIndexOf(data,"</user>");
        if (index < 0)return null;
        return data.substring(index);
    }

}
