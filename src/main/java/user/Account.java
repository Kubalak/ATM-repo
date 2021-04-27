package user;

/**
 * Klasa ta jest odpowiedzialna za konto bankowe użytkownika.<br>
 * Przechowywaną wartością jest jedynie stan konta.<br>
 * Dziedziczy po niej klasa <i><b style="color:#541704;">CreditCard.</b></i>
 * @author Jakub Jach
 * @version 1.0
 * @since 2021-01-10
 */
class Account
{
    /**
     * Stan konta.
     */
    private double credit;

    /**
     * Jedyny konstruktor klasy.
     * @param credit <b style="color:#B45700;">double</b> - Nadaje stan konta przy jego tworzeniu.
     */
    public Account(double credit)
    {
        this.credit = credit;
    }

    /**
     * Metoda zwracająca stan konta.
     * @return <b style="color:#B45700;">double</b> - Zwraca stan konta.
     */
    public double checkCredit(){return credit;}

    /**
     * Metoda służąca do zmiany stanu konta.
     * Zwraca <i>true</i> jeśli opercja się powiedzie lub <i>false</i> w przypadku niepowodzenia np. próby podjęcia więcej gotówki niż jest to możliwe.
     * @param gap <b style="color:#B45700;">double</b> - Służy do przekazania różnicy w stanie konta.
     * @return <b style="color:#B45700;">boolean</b> - Zwraca czy operacja zakończyła się powodzeniem zwraca <i style="color:#B45700;">true</i> w przypadku powodzenia lub <i style="color:#B45700;">false</i> w przypadku porażki.
     */
    public boolean changeCredit(double gap)
    {
        if(gap < 0.0 && credit + gap < 0.0)return false;
       credit += gap;
       return true;
    }

    /**
     * Metoda do administracyjnego ustawienia stanu konta.
     * @param credit <b style="color:#B45700;">double</b>  - Nowy stan konta.
     */
    protected void adminSetCredit(double credit)
    {
        this.credit = credit;
    }
}