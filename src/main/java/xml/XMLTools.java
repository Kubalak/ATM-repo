package xml;

/**
 * Ta klasa odpowiedzialna jest za obsługę danych w formacie XML(uproszczona).<br>
 * Jest to klasa abstrakcyjna posiadająca jedynie metody statyczne.
 * @author Jakub Jach
 * @version 1.0
 * @since 2021-01-10
 *
*/

public abstract class XMLTools
{
    /**
     *
     * @param input <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param sectionName <b style="color:#0B5E03;">String</b> - Nazwa sekcji.
     * @return <b style="color:#0B5E03;">String</b> - Zwraca zawartość spomiędzy znaczników sekcji.
     */
    public static String getData(String input, String sectionName)
    {
        if(input == null)return null;
        String result;
        int begin,end;
        begin = input.indexOf("<"+sectionName+">");
        end = input.indexOf("</"+sectionName+">");
        if(begin>-1 && end>-1)result = input.substring(begin + ("<"+sectionName+">").length(),end);
        else result = null;
        return result;
    }

    /**
     *
     * @param input <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param startingBy <b style="color:#0B5E03;">String</b> - Początek nazwy sekcji.
     * @param endingBy <b style="color:#0B5E03;">String</b> - Koniec nazwy sekcji.
     * @return <b style="color:#0B5E03;">String</b> - Zwraca zawartość spomiędzy znaczników sekcji.
     */
    public static String getData(String input, String startingBy, String endingBy)
    {
        if(input == null)return null;
        String result;
        int begin,end;
        begin = input.indexOf(startingBy);
        end = input.indexOf(endingBy);
        if(begin>-1 && end>-1)result = input.substring(begin + startingBy.length(),end);
        else result = null;
        return result;
    }

    /**
     *
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param identifiedBy <b style="color:#0B5E03;">String</b> - Szukany  identyfikator.
     * @return <b style="color:#B45700;">int</b> - Pierwszy indeks <b>ZA</b> danym elementem.
     */
    public static int firstIndexOf(String data, String identifiedBy)
    {
        int index = data.indexOf(identifiedBy);
        if(index < 0)return -1;
        return data.indexOf(identifiedBy)+identifiedBy.length();
    }

    /**
     *
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param identify <b style="color:#0B5E03;">String</b> - Szukany  identyfikator.
     * @return <b style="color:#0B5E03;">String</b> - Zwraca ciąg od miejsca w którym po raz pierwszy pojawił się dany identyfikator.
     */
    public static String moveToNext(String data, String identify)
    {
        int index = firstIndexOf(data,identify);
        if(index < 0)return null;
        return data.substring(index);
    }

    /**
     *
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param substring <b style="color:#0B5E03;">String</b> - Podciąg, którego ilośc wystapień chcemy policzyć.
     * @return <b style="color:#B45700;">int</b> - Liczba wystapień wzorca.
     */
    public static int countOccurrence(String data, String substring)
    {
        if(data == null)return -1;
        if(data.length()==0 || substring.length()==0)return 0;
        int index = 0, count = 0;
        while (true) {
            index = data.indexOf(substring, index);
            if (index != -1) {
                count ++;
                index += substring.length();
            } else break;
        }
        return count;
    }

    /**
     *
     * @param data <b style="color:#B45700;">int</b> - Dane wejściowe.
     * @param sectionName <b style="color:#0B5E03;">String</b> - Nazwa sekcji.
     * @return <b style="color:#0B5E03;">String</b> - Dane wejściowe opakowane w znaczniki XML.
     */
    public static String toXML(int data, String sectionName)
    {
        return "<"+sectionName+">"+data+"</"+sectionName+">";
    }

    /**
     *
     * @param data <b style="color:#B45700;">double</b> - Dane wejściowe.
     * @param sectionName <b style="color:#0B5E03;">String</b> - Nazwa sekcji.
     * @return <b style="color:#0B5E03;">String</b> - Dane wejściowe opakowane w znaczniki XML.
     */
    public static String toXML(double data, String sectionName){return "<"+sectionName+">"+data+"</"+sectionName+">";}

    /**
     *
     * @param data <b style="color:#0B5E03;">String</b> - Dane wejściowe.
     * @param sectionName <b style="color:#0B5E03;">String</b> - Nazwa sekcji.
     * @return <b style="color:#0B5E03;">String</b> - Dane wejściowe opakowane w znaczniki XML.
     */
    public static String toXML(String data, String sectionName){return "<"+sectionName+">"+data+"</"+sectionName+">";}
}
