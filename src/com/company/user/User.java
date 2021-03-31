package com.company.user;

import java.util.Arrays;

public class User
{
    private String Imie,Nazwisko;
    private Wallet wallet;
    private CreditCard[] cards;

    public User(String Imie, String Nazwisko)
    {
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        wallet = new Wallet();
    }
    public void addCards(CreditCard[] creditCards)
    {
        cards = new CreditCard[creditCards.length];
        System.arraycopy(creditCards, 0, cards, 0, cards.length);
    }
    public Wallet getWallet()
    {
        return wallet;
    }
    public void setWallet(Wallet newWallet)
    {
        this.wallet = newWallet;
    }
    public boolean hasCard(int CardNo)
    {
        return CardNo < cards.length;
    }
    public boolean checkCard(int CardNo, int PIN)
    {
        if(CardNo >= cards.length)return false;
        return cards[CardNo].verifyPIN(PIN);
    }
    public boolean setBlockedCard(int CardNo)
    {
        if(CardNo>=cards.length)return false;
        cards[CardNo].lock();
        return true;
    }
    public boolean setUnblockedCard(int CardNo)
    {
        if(CardNo>=cards.length)return false;
        cards[CardNo].unlock();
        return true;
    }
    public boolean withdraw(int CardNo,double value)
    {
        if(CardNo>=cards.length || value < 0.0)return false;
        return cards[CardNo].changeCredit(-value);

    }
    public CreditCard getCard(int CardNo)
    {
        if(CardNo>=cards.length )return null;
        return cards[CardNo];
    }

}
