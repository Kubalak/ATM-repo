package com.company.user;

public class CreditCard extends Account
{

    private int PINNo;
    private boolean locked;
    public CreditCard(int PINNo, double value)
    {
        super(value);
        this.PINNo = PINNo;
    }
    public CreditCard(int PINNo)
    {
        super(0.0);
        this.PINNo = PINNo;
        locked = false;
    }
    public boolean verifyPIN(int PIN)
    {
        return PINNo == PIN;
    }
    public int changePIN(int oldPIN, int newPIN)
    {
        if(oldPIN==PINNo)
        {
            if(newPIN>=0) {
                PINNo = newPIN;
                return 0;
            }
            else return 1;
        }
        return 2;
    }

    public boolean changeCredit(int PINNo, double gap)
    {
        if(PINNo == this.PINNo)return super.changeCredit(gap);
        return false;
    }
    public void lock(){locked = true;}
    public void unlock(){locked = false;}
}
