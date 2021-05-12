package app;
import javax.swing.*;
import settings.Settings;


/**
 * Główna klasa programu wraz ze statyczną metodą <i>main</i>.
 *
 * @author Jakub Jach, Patryk Jaworski
 * @version 1.0
 * @since 2021-01-10
 */

public class Main {

    /**
     * Statyczna metoda - główna funkcja programu.
     * @param args <b style="color:#0B5E03;">String</b> Argumenty wiersza polecenia dla programu.
     */
    public static void main(String[] args)
    {

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        Settings.loadSettings();
        try {
           new Window((args.length > 0) && args[args.length - 1].equals("default"));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Exception thrown \""+e.getMessage()+"\"","Critical fault!",JOptionPane.ERROR_MESSAGE);
        }
    }
}
