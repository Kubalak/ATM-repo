package manager;

import settings.Settings;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManager extends JPanel implements ActionListener,Manager{
    private JTextField name,surname;
    public final JComboBox <Integer> selected, selectedCrd;
    public int selectedIndex = 0;
    private JButton apply, add, delete;

    private class UserInput extends JPanel
    {
        public JTextField name,surname;
        UserInput()
        {
            name = new JTextField();
            surname = new JTextField();
            this.setLayout(new GridLayout(0,1));
            this.add(new JLabel("Name: "));
            this.add(name);
            this.add(new JLabel("Surname: "));
            this.add(surname);
        }

    }

    UserManager()
    {
        this.setLayout(null);
        JLabel nameL,surnameL,selectedL,selectedCrdL;
        nameL = new JLabel("Name:");
        surnameL = new JLabel("Surname: ");
        selectedCrdL = new JLabel("Selected card: ");
        selectedL = new JLabel("Editing user: ");
        name = new JTextField();
        surname = new JTextField();
        selected = new JComboBox<>();
        selectedCrd = new JComboBox<>();
        apply = new JButton("Apply");
        add = new JButton("Add");
        delete = new JButton("Delete");
        selected.addActionListener(this);
        apply.addActionListener(this);
        delete.addActionListener(this);
        add.addActionListener(this);

        apply.setFocusable(false);
        add.setFocusable(false);
        delete.setFocusable(false);

        selected.setFocusable(false);
        selected.setEditable(false);
        selectedCrd.setFocusable(false);
        selectedCrd.setEditable(false);

        nameL.setHorizontalAlignment(SwingConstants.RIGHT);
        nameL.setHorizontalTextPosition(SwingConstants.RIGHT);
        surnameL.setHorizontalAlignment(SwingConstants.RIGHT);
        surnameL.setHorizontalTextPosition(SwingConstants.RIGHT);
        selectedL.setHorizontalAlignment(SwingConstants.RIGHT);
        selectedL.setHorizontalTextPosition(SwingConstants.RIGHT);
        selectedCrdL.setHorizontalAlignment(SwingConstants.RIGHT);
        selectedCrdL.setHorizontalTextPosition(SwingConstants.RIGHT);

        nameL.setBounds(85,20,75,40);
        name.setBounds(nameL.getX()+nameL.getWidth()+5,nameL.getY(),100,40);
        surnameL.setBounds(nameL.getX(),nameL.getY()+nameL.getHeight()+5,75,40);
        surname.setBounds(surnameL.getX()+surnameL.getWidth()+5,surnameL.getY(),100,40);
        selectedCrdL.setBounds(surnameL.getX(),surnameL.getY()+surnameL.getHeight()+5,75,40);
        selectedCrd.setBounds(selectedCrdL.getX()+selectedCrdL.getWidth()+5,selectedCrdL.getY(),100,40);
        selectedL.setBounds(selectedCrdL.getX(),selectedCrdL.getY()+selectedCrdL.getHeight()+10,75,40);
        selected.setBounds(selectedL.getX()+selectedL.getWidth()+5,selectedL.getY(),100,40);
        apply.setBounds(nameL.getX(),selected.getY()+selected.getHeight()+5,185,30);
        add.setBounds(apply.getX(),apply.getY()+apply.getHeight()+5,90,30);
        delete.setBounds(add.getX()+add.getWidth()+5,add.getY(),90,30);

        this.add(nameL);
        this.add(surnameL);
        this.add(selectedL);
        this.add(selectedCrdL);
        this.add(name);
        this.add(surname);
        this.add(selected);
        this.add(selectedCrd);
        this.add(apply);
        this.add(add);
        this.add(delete);
    }


    @Override
    public void updateFields()
    {
        selected.removeAllItems();
        selectedCrd.removeAllItems();
        if(!Settings.users.isEmpty()) {
            apply.setEnabled(true);
            delete.setEnabled(true);
            selected.setEnabled(true);
            selectedCrd.setEnabled(true);
            name.setEnabled(true);
            surname.setEnabled(true);
            for (int i = 0; i < Settings.users.size(); i++) {
                selected.addItem(i);
            }
            if (selectedIndex >= Settings.users.size() || selectedIndex < 0) selectedIndex = Settings.currentUser();

            selected.setSelectedIndex(selectedIndex);
            User tmp = Settings.users.get(selectedIndex);
            name.setText(tmp.Name);
            surname.setText(tmp.Surname);
            if(!tmp.getCards().isEmpty()) {
                for (int i = 0; i < tmp.getCards().size(); i++) {
                    selectedCrd.addItem(i);
                }
                selectedCrd.setSelectedIndex(tmp.getCardIndex());
            }
        }
        else {
            apply.setEnabled(false);
            delete.setEnabled(false);
            selected.setEnabled(false);
            selectedCrd.setEnabled(false);
            name.setEnabled(false);
            surname.setEnabled(false);
            name.setText("");
            surname.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == apply)
        {
            if(name.getText().isEmpty()||surname.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this,"You cannot pass empty user details!","Input error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!Settings.users.isEmpty()) {
                User tmp = Settings.users.get(selected.getSelectedIndex());
                selectedIndex = selected.getSelectedIndex();
                tmp.Name = name.getText();
                tmp.Surname = surname.getText();
                tmp.switchCard(selectedCrd.getSelectedIndex());
            }
        }
        else if(e.getSource() == add)
        {
            UserInput input = new UserInput();
            int result = JOptionPane.showConfirmDialog(this,input,"Create user",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.OK_OPTION)
            {
                boolean ok = true;
                if(input.name.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Cannot insert empty name to user!","User creation error!",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                }
                if(input.surname.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Cannot insert empty surname to user!","User creation error!",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                }
                if(ok)Settings.addUser(new User(input.name.getText(),input.surname.getText()));
                updateFields();
            }

        }
        else if(e.getSource() == delete)
        {
            Settings.users.remove(selected.getSelectedIndex());
            updateFields();
        }
        else if(e.getSource() == selected)
        {
            selectedIndex = selected.getSelectedIndex()!=-1?selected.getSelectedIndex():selectedIndex;
            System.out.println("Yo");
            updateFields();
        }


    }
}
