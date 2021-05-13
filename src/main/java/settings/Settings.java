package settings;
import user.CreditCard;
import user.User;
import user.Wallet;
import xml.XMLTools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

/**
 * Abstrakcyjna klasa zawierająca tylko pola i metody statyczne.<br>
 * Jej zadaniem jest przechowywanie ustawień, użytkowników oraz waluty.
 * @author Jakub Jach
 * @version 1.0
 * @since 2021-04-19
 */

public abstract class Settings
{
    /**
    * Pozycja okna X / Y.
    * Uzyskiwana jest z pliku <i style="background:rgba(92,92,92,0.5);border-radius:0.5em;">&nbsp;settings.xml&nbsp;</i> oraz zapisywana do niego podczas zamykania programu.
     */
    public static int posX = 0,posY = 0;
    /**
     * Zmienne opisujące bieżącego użytkownika jak i ilość użytkowników.
     */
    private static int currentUser = 0,ANumberOfUsers = 0;
    /**
     * Waluta używana w bankomacie.
     */
    public static String currency = "-",path = null;
    /**
     * Wektor przechowujący użytkowników.
     */
    public static final Vector<User> users = new Vector<>();
    /**
     * Metoda zapisuje stan maszyny do pliku <i style="background:rgba(92,92,92,0.5);border-radius:0.5em;">&nbsp;settings.xml&nbsp;</i> podczas zamykania programu.<br>
     * Jeśli zmienna <i>path</i> nie została ustawiona to domyślnie będzie to plik <i style="background:rgba(92,92,92,0.5);border-radius:0.5em;">&nbsp;userdata/settings.xml&nbsp;</i>
     */
    public static void saveState()
    {
        try
        {
            FileWriter writer = new FileWriter(path!=null?(path+"settings.xml"):"userdata/settings.xml");
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<users>\n"+ XMLTools.toXML(currency,"currency")+"\n");
            writer.write(XMLTools.toXML(posX,"posX")+"\n");
            writer.write(XMLTools.toXML(posY,"posY")+"\n");
            if(users.size() != 0){
                writer.write("  <current>"+currentUser+"</current>\n");
                for (User user : users) if (user != null) writer.write(user.toXML("   ", "   "));
            }
            else writer.write("null");
            writer.write("</users>");
            writer.close();
        }
        catch(IOException exception)
        {
            System.out.println("Save failed!");
        }
    }

    /**
     * Metoda ładuje dane z pliku o ścieżce podanej przez zmienną <i>path</i> lub jeśli nie jest ustawiona <i style="background:rgba(92,92,92,0.5);border-radius:0.5em;">&nbsp;userdata/settings.xml&nbsp;</i> do pól statycznych zawartych w klasie.<br>
     * Jeśli ładowanie z jakiegoś powodu się nie powiedzie to tworzony jest jeden użytkownik <i>John Trueman</i> z numerem PIN do karty <b>1111</b> z pustym kontem i portfelem, w którym znajduje się po jednym banknocie każdego rodzaju.<br>
     */
    public static boolean loadSettings()
    {
        boolean result = true;
        try {
            if(path==null)
            {
                if(!Files.isDirectory(Paths.get("userdata")))
                {
                    if(new File("userdata").mkdir())
                    {
                     System.out.println("Directory created");
                    }
                    else System.out.println("Directory creation failed");
                }
            }
            File input = new File(path==null?"userdata/settings.xml":(path+"settings.xml"));
            Scanner reader = new Scanner(input);
            StringBuilder data = new StringBuilder();
            String tmp;
            while(reader.hasNextLine())
            {
                data.append(reader.nextLine());
            }
            reader.close();
            tmp = XMLTools.getData(data.toString(),"users");
            try {
                posX = Integer.parseInt(XMLTools.getData(data.toString(), "posX"));
                posY = Integer.parseInt(XMLTools.getData(data.toString(),"posY"));
            }
            catch(NumberFormatException | NullPointerException exception)
            {
                System.out.println("Exception "+exception.getMessage());
                posX = -1;
                posY = -1;
            }
            currency = XMLTools.getData(data.toString(),"currency");
            currentUser = Integer.parseInt(XMLTools.getData(data.toString(),"current"));
            ANumberOfUsers = XMLTools.countOccurrence(data.toString(),"<user>");
            for(int i=0;i<ANumberOfUsers;i++)
            {
                users.add(User.getFromXML(tmp));
                tmp = User.moveToNext(tmp);
            }
        }
        catch(NullPointerException | FileNotFoundException exception)
        {
            System.out.println("Exception: "+exception.getMessage());
            users.add(new User("John","Trueman"));
            currency = "-";
            CreditCard[] tmp = new CreditCard[1];
            tmp[0] = new CreditCard(1111);
            users.get(0).addCards(tmp);
            users.get(0).setWallet(new Wallet(false));
            currentUser = 0;
            result = false;
        }
        return result;
    }

    /**
     * Zwraca bieżącego użytkownika.
     * @return <b style="color:#B45700;">int</b> - Zwraca bieżącego użytkownika.
     */
    public static int currentUser()
    {
        return currentUser;
    }

    /**
     * Metoda służąca do zmiany bieżącego użytkownika.
     * @param newCurrentUser <b style="color:#B45700;">int</b> - Nowy indeks użytkownika w wektorze użytkowników.
     * @throws IndexOutOfBoundsException <b style="color:#B45700;">new</b> <b>>IndexOutOfBoundsException</b> (<b style="color:#541704;">"Index out of range"</b>) - Wyrzuca wyjątek w momencie, gdy następuje próba zmiany użytkownika na nieistniejące w wektorze.
     */
    public static void setCurrentUser(int newCurrentUser)
            throws IndexOutOfBoundsException
    {
        if(newCurrentUser >= ANumberOfUsers)throw new IndexOutOfBoundsException("Index out of range");
        currentUser = newCurrentUser;
    }

    /**
     * Metoda służąca do pobrania ilości użytkowników.
     * @return <b style="color:#B45700;">int</b> - Liczba użytkowników.
     */
   public static int ANumberOfUsers(){return ANumberOfUsers;}

    /**
     * Metoda ta służy dodawaniu użytkownika do kolekcji.
     * @param newUser <b style="color:#541704;">User</b> - Nowy użytkownik, który zostanie dddany do kolekcji.
     */
   public static void addUser(User newUser)
    {
        users.add(newUser);
        ANumberOfUsers++;
    }
}
