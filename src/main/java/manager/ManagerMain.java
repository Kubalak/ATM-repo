package manager;

import user.CreditCard;

import javax.swing.*;

/**
 * Klasa <i>ManagerMain</i> będąca główną klasą dla menadżera zapisów bankomatu.
 */
public class ManagerMain {
    /**
     * Główna metoda programu.
     * @param args <b style="color:#0B5E03;">String</b> - Argumenty wiersza polecenia dla programu.
     */
    public static void main(String []args)
    {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        new ManagerWindow();
    }

}
