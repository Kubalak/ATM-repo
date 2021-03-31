package com.company.user;

public class CreditCard extends Account
{

    private int PINNo;
    public CreditCard(int PINNo, double value)
    {
        super(value);
        this.PINNo = PINNo;
    }

}
