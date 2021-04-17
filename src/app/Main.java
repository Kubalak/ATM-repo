package app;
/**
 * Główna klasa programu wraz ze statyczną metodą <i>main</i>.
 *
 * @author Jakub Jach
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
        Window okno = new Window((args.length > 0) && args[args.length-1].equals("default"));
    }
}
