import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import user.CreditCard;

public class CreditCardTest
{
    private static CreditCard card;

    @BeforeClass
    public static void Before()
    {
        card = new CreditCard(1010,1000);
        System.out.println("Junit5 Test");
    }

    @Test
    @DisplayName("Test changing credit")
    public void changeGoodCredit()
    {
        Assert.assertTrue(card.changeCredit(-100));
    }
    @Test
    @DisplayName("Test changing credit")
    public void changeBadCredit()
    {
        Assert.assertFalse(card.changeCredit(-1000));
    }

    @Test
    @DisplayName("Trying bad PIN verification")
    public void tryBadPIN()
    {
        Assert.assertFalse(card.verifyPIN(1011));
    }
    @Test
    @DisplayName("Trying to change PIN to incorrect PIN")
    public void tryBadNewPIN()
    {
        Assert.assertEquals(card.changePIN(1010,-1000),1);
    }
}
