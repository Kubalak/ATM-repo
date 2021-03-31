package com.company.user;

class Account
{
    private double credit;
    public Account(double credit)
    {
        this.credit = credit;
    }
    public double checkCredit(){return credit;}
    public boolean changeCredit(double gap)
    {
        if(gap < 0.0 && credit + gap < 0.0)return false;
       credit += gap;
       return true;
    }

}
