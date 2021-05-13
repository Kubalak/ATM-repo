package manager;

import settings.Settings;
import user.Wallet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WalletManager extends JPanel implements ActionListener,Manager{
    private JTextField[] banknotes;
    private JComboBox<Integer> userIndex;
    private JButton apply,clear;
    private static String[] values = {"10","20","50","100","200","500"};
    private class SpecialWallet extends Wallet
    {

        public SpecialWallet(boolean empty) {
            super(empty);
        }
        public SpecialWallet(Wallet wallet)
        {
            super(wallet);
        }
        public void adminSet(String what, int amount)
        {
            super.adminSet(what,amount);
        }
    }
    WalletManager(JComboBox<Integer> comboBox)
    {
        userIndex = comboBox;
        this.setLayout(null);
        apply = new JButton("Apply");
        clear = new JButton("Clear");
        banknotes = new JTextField[6];
        JLabel[] banknoteL = new JLabel[6];
        for(int i=0;i<6;i++)
        {
            banknotes[i] = new JTextField();
            banknoteL[i] = new JLabel(values[i]+":");
            banknotes[i].setEnabled(!Settings.users.isEmpty());
            banknoteL[i].setBounds(85,10+i*45,75,20);
            banknoteL[i].setHorizontalAlignment(SwingConstants.RIGHT);
            banknoteL[i].setHorizontalTextPosition(SwingConstants.RIGHT);
            banknotes[i].setBounds(banknoteL[i].getX()+banknoteL[i].getWidth()+5, banknoteL[i].getY(),100,20);
            this.add(banknoteL[i]);
            this.add(banknotes[i]);
        }
        apply.setFocusable(false);
        apply.setBounds(banknoteL[5].getX() + 30,banknoteL[5].getY()+banknoteL[5].getHeight()+10,80,30);
        apply.setEnabled(!Settings.users.isEmpty());
        clear.setFocusable(false);
        clear.setBounds(apply.getX()+apply.getWidth()+5,banknoteL[5].getY()+banknoteL[5].getHeight()+10,80,30);
        clear.setEnabled(!Settings.users.isEmpty());
        apply.addActionListener(this);
        clear.addActionListener(this);
        this.add(apply);
        this.add(clear);
    }


    @Override
    public void updateFields() {
        boolean enable = !Settings.users.isEmpty();

           for(int i=0;i<6;i++)
            {
                banknotes[i].setEnabled(enable);
                banknotes[i].setText(enable?String.valueOf(Settings.users.get(userIndex.getSelectedIndex()).getWallet().getAmount(values[i])):"");
            }
            apply.setEnabled(enable);
            clear.setEnabled(enable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == apply)
        {
            int[] tests = {0,0,0,0,0,0};
            try {
                for (int i = 0; i < 6; i++) {
                    tests[i] = Integer.parseInt(banknotes[i].getText());
                }
                SpecialWallet wallet = new SpecialWallet(Settings.users.get(userIndex.getSelectedIndex()).getWallet());
                for(int i = 0;i< 6;i++)
                    wallet.adminSet(values[i],tests[i]);
                Settings.users.get(userIndex.getSelectedIndex()).setWallet(wallet);
            }catch(Exception exception)
            {
                JOptionPane.showMessageDialog(this,"Invalid arguments!\nException: "+exception.getMessage(),"Input error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            for(int i=0;i<6;i++)
            {
                banknotes[i].setText("");
            }
        }

    }
}
