import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import user.Wallet;

public class WalletTest
{
    private static Wallet w1,w2;
    @BeforeClass
    public static void beforeRun()
    {
        w1 = new Wallet(true);
        w2 = new Wallet(false);
        w1.cashIn("10",2);
        w1.cashIn("20",3);
        w1.cashIn("50",5);
        w1.cashIn("100",1);
    }

    @Test
    @DisplayName("Test poprawnej wpłaty")
    public void tryGoodDeposit()
    {
        Assert.assertTrue(w1.cashIn("10",1));
    }
    @Test
    @DisplayName("Test niepoprawnej wpłaty")
    public void tryBadDeposit()
    {
        Assert.assertFalse(w1.cashIn("10",-1));
    }
    @Test
    @DisplayName("Test wpłaty niepoprawnego banknotu")
    public void tryBadDepositName()
    {
        Assert.assertFalse(w1.cashIn("1000",1));
    }
    @Test
    @DisplayName("Test poprawnego transferu")
    public void tryGoodTake()
    {
        Assert.assertTrue(w1.cashOut("10",1));
    }
    @Test
    @DisplayName("Test poprawnego transferu")
    public void tryBadTake()
    {
        Assert.assertFalse(w1.cashOut("10",10));
    }
    @Test
    @DisplayName("Test poprawnego transferu")
    public void tryGoodTransfer()
    {
        Assert.assertTrue(w1.transfer("10",w2));
    }
    @Test
    @DisplayName("Test poprawnego transferu")
    public void tryBadTransfer()
    {
        Assert.assertFalse(w1.transfer("10",w2));
    }
}
