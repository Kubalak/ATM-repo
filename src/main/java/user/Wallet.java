package user;
import xml.XMLTools;

/**
 * Klasa odpowiadająca za portfel.
 * <b><i style="color:orange;"><br>Uwaga:</i></b>
 * Klasa ta została źle zoptymalizowana pod kontem redundancji kodu.<br>Wymagana jest operacja zamiany wewnętrznej struktury klasy.
 * @author Jakub Jach
 * @version 1.0
 * @since 2021-01-10
 */

public class Wallet
{
    /**
     * Zmienne reprezentujące ilość każdego z banknotów.
     */
    private int tens,twenties,fifties,hundreds,two_hundreds,five_hundreds;

    /**
     * Jeden z dwóch konstruktorów klasy.
     * @param empty <b style="color:#B45700;">boolean</b> - Określa czy poertfel będzie pusty. <br>Jeśli zostanie przekazana wartość <i style="color:#B45700;">true</i>, wówczas w portfelu znajdzie się po 1 banknocie każdego rodzaju.
     */
    public Wallet(boolean empty)
    {
        if(empty) tens = twenties = fifties = hundreds = two_hundreds = five_hundreds = 0;
        else tens = twenties = fifties = hundreds = two_hundreds = five_hundreds = 1;
    }

    /**
     * Drugi konstruktor klasy.
     * @param cash <b style="color:#B45700;">int</b> - Ilość gotówki w portfelu (automatycznie dzielona na banknoty algorytmem zachłannym).
     */
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

    /**
     * Metoda służąca do wpłacania gotówki według nominałów.
     * @param what <b style="color:#0B5E03;">String</b> - Nazwa banknotu, którego ilość ma być wpłacona.
     * @param amount <b style="color:#B45700;">int</b> - Ilość sztuk danego banknotu.
     */
    public boolean cashIn(String what, int amount)
    {
        if(amount<0||what==null)
        {
            System.out.println("Operation not permitted!");
            return  false;
        }
        else if(what.toLowerCase().equals("10"))tens+=amount;
        else if(what.toLowerCase().equals("20"))twenties+=amount;
        else if(what.toLowerCase().equals("50"))fifties+=amount;
        else if(what.toLowerCase().equals("100"))hundreds+=amount;
        else if(what.toLowerCase().equals("200"))two_hundreds+=amount;
        else if(what.toLowerCase().equals("500"))five_hundreds+=amount;
        else
        {
            System.out.println("No banknote available!");
            return false;
        }
        return true;
    }

    /**
     * Metoda służąca do czyszczenia zawartości portfela.
     * @return <b style="color:#B45700;">int</b> - Zwracana jest suma zawartości portfela (wartość * ilość).
     */
    public int clear()
    {
        int returnVal = this.getAll();
        tens=twenties=fifties=hundreds=two_hundreds=0;
        return returnVal;
    }

    /**
     * Realizuje tą samą operację co drugi z konstruktorów,
     * @param cash <b style="color:#B45700;">int</b> - Wpłacana gotówka.
     */
    public boolean cashIn(int cash)
    {
        if(cash<0) {
            System.out.println("Cannot insert value lower than 0!");
            return false;
        }
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
        return true;
    }

    /**
     * Metoda służąca do wypłacania gotówki.
     * @param cash <b style="color:#B45700;">int</b> - Wartość do wypłacenia
     * @return <b style="color:#B45700;">int</b> - Zwraca 0 w przypadku powodzenia lub -1 w przypadku niepowodzenia (do poprawy).
     */
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

    /**
     * Działa odwrotnie do metody <i style="font-family:Consolas;"><b style="color:#B45700;">public boolean</b> <b style="color:grey;">cashIn</b>(<b style="color:#0B5E03;">String</b> what<b style="color:#B45700;">, int</b> <b>amount</b>)</i>
     * @param what <b style="color:#0B5E03;">String</b> - Nazwa banknotu, który ma zostać wypłacony.
     * @param amount <b style="color:#B45700;">int</b> - Ilość banknotu do wypłaty.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#0B5E03;">true</i> w przypadku powodzenia lub <i style="color:#0B5E03;">false</i> w przypadku niepowodzenia.
     */
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

    /**
     * Metoda zwraca ilość banknotów o nominale określonym w parametrze.
     * @param what <b style="color:#0B5E03;">String</b> - Nominał banknotu.
     * @return <b style="color:#B45700;">int</b> - Ilość banknotu.
     */
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

    /**
     * Zwraca wartość poertfela w postaci (ilość * wartość)
     * @return <b style="color:#B45700;">int</b> - Suma wartości banknotów z portfela.
     */
    public int getAll()
    {
        return tens * 10 + twenties * 20 + fifties * 50 + hundreds * 100 + two_hundreds * 200 + five_hundreds * 500;
    }

    /**
     * Metoda pozwalająca na transfer pieniędzy - konkretnych banknotów między portfelami (po jednym banknocie).
     * @param what <b style="color:#0B5E03;">String</b> - Nominał banknotu.
     * @param from <b style="color:#541704;">Wallet</b> - Portfel, z którego pobieramy banknoty.
     * @return <b style="color:#B45700;">int</b> -
     */
    public boolean transfer(String what, Wallet from)
    {
        switch(what)
        {
            case "10":
                if(from.tens > 0){
                    tens++;
                    from.tens--;
                    return true;
                }
                break;
            case "20":
                if(from.twenties > 0){
                    twenties++;
                    from.twenties--;
                    return true;
                }
                break;
            case "50":
                if(from.fifties > 0){
                    fifties++;
                   from.fifties--;
                    return true;
                }
                break;
            case "100":
                if(from.hundreds > 0){
                    hundreds++;
                   from.hundreds--;
                    return true;
                }
                break;
            case "200":
                if(from.two_hundreds > 0){
                    two_hundreds++;
                    from.two_hundreds--;
                    return true;
                }
                break;
            case "500":
                if(from.five_hundreds > 0){
                    five_hundreds++;
                    from.five_hundreds--;
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Metoda sprawdzająca czy możliwe jest pobranie z portfela <i style="color:#B45700;">this</i> portfela <i style="color:#541704;">outcome</i>.
     * @param outcome <b style="color:#541704;">Wallet</b> - Portfel, który sprawdzamy
     * @return <b style="color:#B45700;">boolean</b> - Zwraca <i style="color:#B45700;">true</i>, gdy transfer jest możliwy lub <i style="color:#B45700;">false</i>, w orzeciwnym wypadku.
     */
    public boolean letTake(Wallet outcome)
    {
        if(outcome.tens < 0|| outcome.twenties < 0||outcome.fifties < 0|| outcome.hundreds < 0 || outcome.two_hundreds < 0 || outcome.five_hundreds < 0) {
            System.out.println("Money amount incorrect!");
            return false;
        }
       return tens >= outcome.tens && twenties >= outcome.twenties && fifties >= outcome.fifties && hundreds >= outcome.hundreds && two_hundreds >= outcome.two_hundreds && five_hundreds >= outcome.five_hundreds;
    }

    /**
     * Metoda służy do pobierania z portfela <i style="color:#B45700;">this</i> portfela <i style="color:#541704;">outcome</i>.
     * @param outcome <b style="color:#541704;">Wallet</b> - Portfel, który będziemy odejmować.
     * @return <b style="color:#B45700;">int</b> - Zwracana jest ilośc gotówki, która została pobrana lub -1 w przypadku braku możliwości pobrania zawartości portfela.
     */
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
    /**
     * Metoda zwracająca zawartość obiektu w postaci XML.
     * @param margin <b style="color:#0B5E03;">String</b> - Margines - odstęp przed sekcją użytkownika.
     * @param spacer <b style="color:#0B5E03;">String</b> - Wcięcia - służą do tworzenia wcięć dla przechowywanych obiektów.
     * @return <b style="color:#0B5E03;">String</b> - Gotowy do zapisania w pliku łańcuch znaków w formacie XML.
     */
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
    /**
     * Statyczna metoda służąca do odczytu danych z postaci XML i zwrócenia ich jako obiekt klasy <i>User</i>.
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @return <b style="color:#541704;">Wallet</b> - Nowy obiekt klasy <i>Wallet</i> zawierający dane wczytane z pliku XML.
     */
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
            System.out.println(exception.getMessage());
            System.out.println("Data is corrupted!");
        }
        return null;
    }

    /**
     * Metoda zwracająca zawartość portfela w postaci ciągu znaków.
     * @return <b style="color:#0B5E03;">String</b> - Zwracany ciąg znaków.
     */
    @Override
    public String toString()
    {
        return "Tens: "+tens+"\nTwenties: "+twenties+"\nFifties: "+fifties+"\nHundreds: "+hundreds+"\nTwo hundreds: "+two_hundreds+"\nFive hundreds: "+five_hundreds+"\n";
    }

    /**
     * Metoda tworząca kopię obiektu, który ją wywołał (nie korzysta z clone() ze względu na problemy wyjątku <i>CloneNotSupportedException</i>.
     * @return <b style="color:#541704;">Wallet</b> - Zwracany nowy obiekt - kopia bieżącego obiektu.
     */
    public Wallet copy()  {
        Wallet clone = new Wallet(true);
        clone.tens = tens;
        clone.twenties = twenties;
        clone.fifties = fifties;
        clone.hundreds = hundreds;
        clone.two_hundreds = two_hundreds;
        clone.five_hundreds = five_hundreds;
        return clone;
    }

    /**
     * Metoda ta kopiuje dane z portfela <b style="color:#541704;">from</b> do bieżącego portfela.
     * @param from <b style="color:#541704;">Wallet</b> - Portfel, z którego kopiowana będzie zawartość.
     */
    public void copy(Wallet from)
    {
        tens = from.tens;
        twenties = from.twenties;
        fifties = from.fifties;
        hundreds = from.hundreds;
        two_hundreds = from.two_hundreds;
        five_hundreds = from.five_hundreds;
    }
}
