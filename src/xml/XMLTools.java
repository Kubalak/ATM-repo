package xml;
public class XMLTools
{
    private StringBuilder XML;
    XMLTools()
    {
        XML = new StringBuilder("<?xml version=\"1.0\"?>\n");
    }
    XMLTools(String data)
    {
        XML = new StringBuilder("<?xml version=\"1.0\"?>\n" +data);
    }
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
    public static int firstIndexOf(String data, String identifiedBy)
    {
        int index = data.indexOf(identifiedBy);
        if(index < 0)return -1;
        return data.indexOf(identifiedBy)+identifiedBy.length();
    }

    public static String moveToNext(String data, String identify)
    {
        int index = firstIndexOf(data,identify);
        if(index < 0)return null;
        return data.substring(index);
    }
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
    public static String toXML(int data, String sectionName)
    {
        return "<"+sectionName+">"+data+"</"+sectionName+">";
    }
    public static String toXML(double data, String sectionName)
    {
        return "<"+sectionName+">"+data+"</"+sectionName+">";
    }
    public static String toXML(String data, String sectionName)
    {
        return "<"+sectionName+">"+data+"</"+sectionName+">";
    }
    public static String insertSection(String to, String what, String sectionName)
    {
        StringBuilder builder = new StringBuilder(to);
        builder.insert(firstIndexOf(to,sectionName),what);
        return builder.toString();
    }
}
