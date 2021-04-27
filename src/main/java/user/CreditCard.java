package user;
import xml.XMLTools;

/**
 * Klasa odpowiedzialna za przechowywanie informacji o kartach kredytowych.
 * Dziedziczy po klasie <i>Account</i>
 * @author Jakub Jach
 * @version 1.1
 * @since 2021-01-10
 */
public class CreditCard extends Account
{
    /**
     * Numer PIN do karty.
     */
    private int PINNo;
    /**
     * Informacja o tym czy karta jest zablokowana.
     */
    private boolean locked;

    /**
     * Jeden z dwóch konstruktorów klasy <i>CreditCard</i>.
     * @param PINNo <b style="color:#B45700;">int</b> - Numer PIN nadawany karcie w konstruktorze.
     * @param value <b style="color:#B45700;">double</b> - Stan konta przypisanego karty.
     */
    public CreditCard(int PINNo, double value)
    {
        super(value);
        this.PINNo = PINNo;
        locked = false;
    }

    /**
     * Drugi z konstruktorów klasy - tworzy nową kartę przypisaną do konta o saldzie 0.
     * @param PINNo <b style="color:#B45700;">int</b> - Numer PIN nowo stworzonej karty.
     */
    public CreditCard(int PINNo)
    {
        super(0.0);
        this.PINNo = PINNo;
        locked = false;
    }

    /**
     * Administracyjne ustawienie PINu - używane w programie do edycji zapisu.
     * @param PIN <b style="color:#B45700;">int</b> - Nowy kod PIN.
     */
    protected void adminPINSet(int PIN){PINNo = PIN;}

    /**
     * Administracyjne ustawienie stanu konta - do programu do edycji zapisów.
     * @param credit <b style="color:#B45700;">double</b> - Nowy stan konta.
     */
    protected void adminSetCredit(double credit){super.adminSetCredit(credit);}
    /**
     * Metoda sprawdza czy wpisany PIN jest poprawny.
     * @param PIN <b style="color:#B45700;">int</b> - Sprawdzany numer PIN.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i>true</i> jeśli numer PIN jest zgodny z tym przypisanym karcie.
     */
    public boolean verifyPIN(int PIN)
    {
        return PINNo == PIN;
    }

    /**
     * Metoda służąca do zmiany numeru PIN.
     * @param oldPIN <b style="color:#B45700;">int</b> - Stary numer PIN.
     * @param newPIN <b style="color:#B45700;">int</b> - Nowy numer PIN.
     * @return <b style="color:#B45700;">int</b> - Zwraca <i>0</i> w przypadku powodzenia <i>1</i>, gdy nowy numer PIN &lt; 0 lub <i>2</i> jeśli numer podany jako oldPIN nie zgadza się z tym przypisanym do karty.
     */
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

    /**
     * Funkcja służąca do zmiany stanu konta.
     * @param PINNo <b style="color:#B45700;">int</b> - Numer PIN.
     * @param gap <b style="color:#B45700;">double</b> - Różnica w stanie konta.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i>true</i> w przypadku powodzenia lub <i>false</i> w przypadku niepowodzenia.
     */
    public boolean changeCredit(int PINNo, double gap)
    {
        if(PINNo == this.PINNo)return super.changeCredit(gap);
        return false;
    }

    /**
     * Blokuje kartę.
     */
    public void lock(){locked = true;}

    /**
     * Odblokowywuje kartę.
     */
    public void unlock(){locked = false;}

    /**
     * Zwraca informację o tym, czy karta jest zablokowana.
     * @return <b style="color:#B45700;">boolean</b> - Informacja o stanie blokady <i>true</i> lub <i>false</i>.
     */
    public boolean isLocked(){return locked;}

    /**
     * Funkcja transformuje zawartość klasy do postaci XML.
     * @param margin <b style="color:#0B5E03;">String</b> - Początkowy odstęp - margines.
     * @param spacer <b style="color:#0B5E03;">String</b> - Znacznik odstępu - do wcięć.
     * @return <b style="color:#0B5E03;">String</b> - Dane w postaci XML.
     */
    public String toXML(String margin, String spacer)
    {
        String result = margin+"<card>\n";
        result+=margin+spacer+"<pin>"+String.format("%04d",PINNo)+"</pin>\n";
        result+=margin+spacer+"<locked>"+ String.format("%s",locked?"YES":"NO")+"</locked>\n";
        result+=margin+spacer+"<credit>"+String.format("%.2f",super.checkCredit()).replace(',','.')+"</credit>\n";
        result+=margin+"</card>\n";
        return result;
    }

    /**
     * Statyczna metoda wyciągająca dane z postaci XML do nowego obiektu klasy <i>CreditCard</i>.
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @return <b style="color:#541704;">CreditCard</b> - Zwracany nowy obiekt klasy <i>CreditCard</i>.
     */
    public static CreditCard getFromXML(String data)
    {
        String PIN = XMLTools.getData(data,"pin");
        String locked = XMLTools.getData(data,"locked");
        String credit = XMLTools.getData(data,"credit");
        String currency = XMLTools.getData(data,"currency");
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
