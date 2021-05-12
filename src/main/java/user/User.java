package user;
import xml.XMLTools;

import java.util.Arrays;
import java.util.Vector;

/**
 * Klasa odpowiadająca za poszczególnego użytkownika.
 * @author Jakub Jach
 * @version 1.2
 * @since 2021-01-10
 */


public class User
{
    /**
     * Imię oraz nazwisko użytkownika
     */
    public String Name,Surname;
    /**
     * Portfel użytkownika.
     */
    private Wallet wallet;
    /**
     * Zmienne dotyczące bieżącej karty oraz ich ilości.
     */
    private int currentCard,ANumberOfCards;
    /**
     * Tablica przechowująca wszystkie karty użytkownika.
     */
    private final Vector<CreditCard> cards;

    /**
     * Jedyny konstruktor dla klasy.
     * @param Name <b style="color:#541704;">String</b> - Imię użytkownika.
     * @param Surname <b style="color:#541704;">String</b> - Nazwisko użytkownika.
     */
    public User(String Name, String Surname)
    {
        this.Name = Name;
        this.Surname = Surname;
        cards = new Vector<CreditCard>();
        wallet = new Wallet(false);
    }

    /**
     * Metoda pozwalająca dodać tablicę z kartami.
     * @param creditCards <b style="color:#541704;">CreditCard[]</b> - Tablica z kartami.
     */
    public void addCards(CreditCard[] creditCards)
    {
        for(int i =0;i< creditCards.length;++i)cards.addAll(Arrays.asList(creditCards));
        currentCard = 0;
    }

    /**
     * Metoda pozwalająca dodać nową kartę do kolekcji.
     * @param card <b style="color:#541704;">CreditCard</b> - Dodawana karta.
     */
    public void addCard(CreditCard card)
    {
        cards.add(card);
        ANumberOfCards++;
    }
    /**
     * Zwraca portfel użytkownika.
     * @return <b style="color:#541704;">Wallet</b> -  Portfel.
     */
    public Wallet getWallet()
    {
        return wallet;
    }

    /**
     * Pozwala ustawić portfel użytkownika.
     * Użycie manualne <u>niezalecane</u> ze względu na możliwość utraty danych.
     * @param newWallet <b style="color:#541704;">Wallet</b> - Nowy portfel użytkownika.
     */
    public void setWallet(Wallet newWallet)
    {
        this.wallet.copy(newWallet);
    }

    /**
     * Metoda pozwala na zmianę karty.
     * @param CardNO <b style="color:#B45700;">int</b> - Numer karty, na którą chcemy się przełączyć.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i> jeśli zmiana karty się powiedzie lub <i style="color:#B45700;">false</i> jeśli nie.
     */
    public boolean switchCard(int CardNO)
    {
        if(CardNO>=ANumberOfCards )return false;
        currentCard = CardNO;
        return true;
    }

    /**
     * Służy do sprawdzenia karty - porównaniua numeru PIN bieżącej karty z wpisanym.
     * @param PIN <b style="color:#B45700;">int</b> - Numer PIN do sprawdzenia.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i> jeśli PINy są zgodne lub <i style="color:#B45700;">false</i> jeśli są niezgodne.
     */
    public boolean checkCard(int PIN){return cards.get(currentCard).verifyPIN(PIN);}

    /**
     * Blokuje bieżącą kartę.
     */
    public void blockCard(){cards.get(currentCard).lock();}

    /**
     * Odblokowuje bieżącą kartę.
     */
    public void unlockCard()
    {
        cards.get(currentCard).unlock();
    }

    /**
     * Wypłaca określoną kwotę z konta, którą przekazuje do portfela.
     * @param value <b style="color:#B45700;">int</b> - Kwota wypłacana z konta.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i> w przypadku powodzenia lub <i style="color:#B45700;">false</i> w przypadku niepowodzenia.
     */
    public boolean withdraw(int value)
    {
        if(value < 0 || value % 10 != 0)return false;
        boolean OK = cards.get(currentCard).changeCredit((double)-value);
        if(OK)  wallet.cashIn((int)value);
        return OK;
    }

    /**
     * Funkcja służąca do wpłacania pieniędzy na konto z portfela.
     * @param cash <b style="color:#541704;">Wallet</b> - Portfel, który będziemy wpłacać - odejmować od portfela użytkownika.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i> w przypadku powodzenia operacji lub <i style="color:#B45700;">false</i> w przypadku niepowodzenia.
     */
    public boolean deposit (Wallet cash)
    {
        System.out.println("Trying to deposit:\n"+ cash.toString()+"\nFrom:\n"+wallet.toString());
        if(!wallet.letTake(cash))return false;
        cards.get(currentCard).changeCredit(wallet.take(cash));
        return true;
    }

    /**
     * Metoda zwraca wektor z kartami.
     * @return <b style="color:#0B5E03;">Vector<CreditCard></b> - Wektor z kartami.
     */
    public Vector<CreditCard> getCards(){return cards;}
    /**
     * Zwraca bieżącą kartę użytkownika.
     * @return <b style="color:#541704;">CreditCard</b> - Bieżąca karta.
     */
    public CreditCard getCard(){return cards.get(currentCard);}

    /**
     * Zwraca informację o tym, czy bieżąca karta jesat zablokowana.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i> jeśli karta jest zablokowana lub <i style="color:#B45700;">false</i> jeśli nie jest.
     */
    public boolean isCardLocked()
    {
         return cards.get(currentCard).isLocked();
    }

    /**
     * Metoda zwracająca indeks karty aktywnego użytkownika.
     * @return <b style="color:#B45700;">int</b> - Zwraca indeks bieżącej karty użytkownika.
     */
    public int getCardIndex(){return currentCard;}

    /**
     * Metoda zwracająca ilość kart posiadanych przez użytkownika.
     * @return <b style="color:#B45700;">int</b> - Zwraca ilość kart, które posiada użytkownik.
     */
    public int getNumberOfCards(){return ANumberOfCards;}
    /**
     * Metoda zwracająca zawartość obiektu w postaci XML.
     * @param margin <b style="color:#0B5E03;">String</b> - Margines - odstęp przed sekcją użytkownika.
     * @param spacer <b style="color:#0B5E03;">String</b> - Wcięcia - służą do tworzenia wcięć dla przechowywanych obiektów.
     * @return <b style="color:#0B5E03;">String</b> - Gotowy do zapisania w pliku łańcuch znaków w formacie XML.
     */
    public String toXML(String margin, String spacer)
    {
        String result = margin+"<user>\n";
        result+=margin+spacer+"<name>"+Name+"</name>\n";
        result+=margin+spacer+"<surname>"+Surname+"</surname>\n";
        result+=wallet.toXML(margin + spacer, spacer);
        if(!cards.isEmpty()) {
            result += margin + spacer + "<cards>\n";
            result+=margin+spacer+"<current>"+currentCard+"</current>\n";
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i) != null) result += cards.get(i).toXML(margin + spacer + spacer, spacer);
            }
            result += margin + spacer + "</cards>\n";
        }
        else result+=margin + spacer +"<cards></cards>\n";
        result+=margin+"</user>\n";
        return result;
    }

    /**
     * Statyczna metoda służąca do odczytu danych z postaci XML i zwrócenia ich jako obiekt klasy <i>User</i>.
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @return <b style="color:#541704;">User</b> - Nowy obiekt klasy <i>User</i> zawierający dane wczytane z pliku XML.
     */
    public static User getFromXML(String data)
    {
        User result;
        String name,surname,wallet,cards;
        name = XMLTools.getData(data,"name");
        surname = XMLTools.getData(data,"surname");
        wallet = XMLTools.getData(data,"wallet");
        cards = XMLTools.getData(data,"cards");
        result = new User(name,surname);
        result.ANumberOfCards = XMLTools.countOccurrence(cards,"<card>");
        try {
            result.currentCard = Integer.parseInt(XMLTools.getData(cards, "current"));
        }
        catch(NullPointerException | NumberFormatException exception)
        {
            System.out.println("Error on getting current card!\nUsing defaults...");
            result.currentCard = 0;
        }
        for(int i = 0;i < result.ANumberOfCards;i++)
        {
            result.cards.add(CreditCard.getFromXML(cards));
            cards = XMLTools.moveToNext(cards,"</card>");
        }
        result.wallet = Wallet.getFromXML(wallet);
        return result;
    }

    /**
     * Metoda odpowiedzialna za przechodzenie za użytkownika, który został znaleziony jako pierwszy w danym ciagu.
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @return <b style="color:#0B5E03;">String</b> - Wejściowy łańcuch znaków bez pierwszego znalezionego użytkownika (włączając w to wszystkie informacje przed nim).
     */
    public static String moveToNext(String data)
    {
        int index = XMLTools.firstIndexOf(data,"</user>");
        if (index < 0)return null;
        return data.substring(index);
    }

}
