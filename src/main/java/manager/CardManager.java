package manager;

import settings.Settings;
import user.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardManager extends JPanel implements ActionListener,Manager{
    private JTextField pin,credit;
    private JButton apply, add,delete;
    private JCheckBox locked;
    private JComboBox<Integer> selected, userIndex;

    private static class InputDialog extends JPanel
    {
        JTextField pin,credit;
        JCheckBox locked;
        InputDialog()
        {
            JLabel pinL,creditL,lockedL;
            pinL = new JLabel("PIN:");
            creditL = new JLabel("Credit:");
            lockedL = new JLabel("Locked:");
            pin = new JTextField();
            credit = new JTextField();
            locked = new JCheckBox();
            this.setLayout(new GridLayout(0,1));
            this.add(pinL);
            this.add(pin);
            this.add(creditL);
            this.add(credit);
            this.add(lockedL);
            this.add(locked);

        }
    }

    private static class SpecialCard extends CreditCard
    {

        public SpecialCard(int PINNo, double value) {
            super(PINNo, value);
        }
        public SpecialCard(CreditCard card)
        {
            super(card);
        }
        public int getPIN()
        {
            return super.adminPINGet();
        }
    }

    CardManager(JComboBox<Integer> comboBox )
    {
        this.setLayout(null);

        userIndex = comboBox;
        JLabel pinL,creditL,selectedL,lockedL;
        pinL = new JLabel("PIN:");
        creditL = new JLabel("Credit: ");
        selectedL = new JLabel("Working on card: ");
        lockedL = new JLabel("Card locked:");
        pin = new JTextField();
        credit = new JTextField();
        selected = new JComboBox<>();
        apply = new JButton("Apply");
        add = new JButton("Add");
        delete = new JButton("Delete");
        locked = new JCheckBox();

        apply.addActionListener(this);
        add.addActionListener(this);
        delete.addActionListener(this);
        selected.addActionListener(this);

        apply.setFocusable(false);
        add.setFocusable(false);
        apply.setFocusable(false);
        selected.setFocusable(false);
        delete.setFocusable(false);
        locked.setFocusable(false);

        if(Settings.users.isEmpty() || Settings.users.get(comboBox.getSelectedIndex()).getCards().isEmpty())
        {
            apply.setEnabled(false);
            add.setEnabled(false);
            apply.setEnabled(false);
            selected.setEnabled(false);
            pin.setEnabled(false);
            credit.setEnabled(false);
            delete.setEnabled(false);
            locked.setEnabled(false);
        }

        pinL.setBounds(85,20,75,40);
        pin.setBounds(pinL.getX()+pinL.getWidth()+5,pinL.getY(),100,40);
        creditL.setBounds(pinL.getX(),pinL.getY()+pinL.getHeight()+5,75,40);
        credit.setBounds(creditL.getX()+creditL.getWidth()+5,creditL.getY(),100,40);
        lockedL.setBounds(pinL.getX() - 5,creditL.getY()+creditL.getHeight()+5,75,40);
        locked.setBounds(lockedL.getX()+lockedL.getWidth()+40,lockedL.getY(),30,30);
        selectedL.setBounds(creditL.getX() - 10,lockedL.getY()+lockedL.getHeight()+10,85,40);
        selected.setBounds(selectedL.getX()+selectedL.getWidth()+5,selectedL.getY(),100,40);
        apply.setBounds(selectedL.getX()+5,selected.getY()+selected.getHeight()+5,185,30);
        add.setBounds(apply.getX(),apply.getY()+apply.getHeight()+5,90,30);
        delete.setBounds(add.getX()+add.getWidth()+5,add.getY(),90,30);
        pinL.setHorizontalAlignment(SwingConstants.RIGHT);
        pinL.setHorizontalTextPosition(SwingConstants.RIGHT);
        creditL.setHorizontalAlignment(SwingConstants.RIGHT);
        creditL.setHorizontalTextPosition(SwingConstants.RIGHT);
        selectedL.setHorizontalAlignment(SwingConstants.RIGHT);
        selectedL.setHorizontalTextPosition(SwingConstants.RIGHT);
        lockedL.setHorizontalAlignment(SwingConstants.RIGHT);
        lockedL.setHorizontalTextPosition(SwingConstants.RIGHT);

        this.add(pin);
        this.add(credit);
        this.add(selected);
        this.add(apply);
        this.add(add);
        this.add(delete);
        this.add(pinL);
        this.add(creditL);
        this.add(selectedL);
        this.add(lockedL);
        this.add(locked);

    }
    @Override
    public void updateFields()
    {
        int index = userIndex.getSelectedIndex();
        if (index < 0)index = 0;
        if(!Settings.users.isEmpty()&&!Settings.users.get(index).getCards().isEmpty())
        {
            int tmpIndex = selected.getSelectedIndex();
            if(tmpIndex >= Settings.users.get(index).getCards().size() || tmpIndex < 0)tmpIndex = Settings.users.get(index).getCardIndex();
            apply.setEnabled(true);
            add.setEnabled(true);
            apply.setEnabled(true);
            selected.setEnabled(true);
            pin.setEnabled(true);
            credit.setEnabled(true);
            delete.setEnabled(true);
            locked.setEnabled(true);
            SpecialCard card  = new SpecialCard(Settings.users.get(index).getCards().get(tmpIndex));
            pin.setText(String.valueOf(card.getPIN()));
            credit.setText(String.valueOf(card.checkCredit()));
            locked.setSelected(card.isLocked());
            selected.removeAllItems();
            for(int i=0;i<Settings.users.get(index).getCards().size();i++)
            {
                selected.addItem(i);
            }
            selected.setSelectedIndex(tmpIndex);
        }
        else
        {
            apply.setEnabled(false);
            add.setEnabled(!Settings.users.isEmpty());
            apply.setEnabled(false);
            selected.setEnabled(false);
            pin.setEnabled(false);
            credit.setEnabled(false);
            delete.setEnabled(false);
            locked.setEnabled(false);
            pin.setText("");
            credit.setText("");
            locked.setSelected(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == apply)
        {
            try {
                if(pin.getText().length() > 4)throw new Exception("PIN length mismatch!");
                int pinNo = Integer.parseInt(pin.getText());
                double creditT = Double.parseDouble(credit.getText());
                if(pinNo < 0 || pinNo >= 10000)throw new Exception("Invalid PIN number!");
                if(creditT < 0.0)throw new Exception("Invalid credit!");
                CreditCard tmp = new CreditCard(pinNo,creditT);
                if(locked.isSelected())tmp.lock();
                Settings.users.get(userIndex.getSelectedIndex()).getCards().set(selected.getSelectedIndex(),tmp);

            }
            catch(Exception exception)
            {
                JOptionPane.showMessageDialog(this,"Invalid arguments!\nException: "+exception.getMessage(),"Input error",JOptionPane.ERROR_MESSAGE);
            }

        }
        else if(e.getSource() == add)
        {
            InputDialog dialog = new InputDialog();
            int result = JOptionPane.showConfirmDialog(this,dialog,"Add card",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.OK_OPTION)
            {
                try {
                    if(dialog.pin.getText().length() > 4)throw new Exception("PIN length mismatch!");
                    int pinTmp = Integer.parseInt(dialog.pin.getText());
                    double creditTmp = Double.parseDouble(dialog.credit.getText());
                    boolean lockedTmp = dialog.locked.isSelected();
                    if(pinTmp < 0 || pinTmp >= 10000)throw new Exception("Invalid PIN number!");
                    if(creditTmp < 0.0)throw new Exception("Invalid credit!");
                    CreditCard card = new CreditCard(pinTmp,creditTmp);
                    if(lockedTmp)card.lock();
                    Settings.users.get(userIndex.getSelectedIndex()).getCards().add(card);
                }
                catch(Exception exception)
                {
                    JOptionPane.showMessageDialog(this,"Invalid arguments!\nException: "+exception.getMessage(),"Input error",JOptionPane.ERROR_MESSAGE);
                }
                updateFields();
            }

        }
        else if(e.getSource() == delete)
        {
            Settings.users.get(userIndex.getSelectedIndex()).getCards().remove(selected.getSelectedIndex());
            updateFields();
        }
        else if(e.getSource() == selected)
        {
            updateFields();
        }

    }
}
