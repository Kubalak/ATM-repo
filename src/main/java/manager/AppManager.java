package manager;
import settings.Settings;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppManager extends JPanel implements ActionListener, Manager
{

    private JTextField xField,yField,currencyField;
    private JComboBox<Integer> selectedUsr;
    private JButton apply,blank,preview;
    public AppManager()
    {
        this.setLayout(null);
        JLabel X,Y,currency,selectUsr;
        X = new JLabel("Position X: ");
        Y = new JLabel("Position Y: ");
        currency  = new JLabel("Currency: ");
        selectUsr = new JLabel("Startup user: ");
        apply = new JButton("Apply");
        blank = new JButton("Clear");
        preview = new JButton("Preview");

        apply.addActionListener(this);
        blank.addActionListener(this);
        preview.addActionListener(this);

        xField = new JTextField();
        yField = new JTextField();
        currencyField = new JTextField();
        selectedUsr = new JComboBox<>();
        selectedUsr.addActionListener(this);
        X.setFont(new Font("Arial",Font.PLAIN,12));
        Y.setFont(new Font("Arial",Font.PLAIN,12));
        currency.setFont(new Font("Arial",Font.PLAIN,12));
        selectUsr.setFont(new Font("Arial",Font.PLAIN,12));
        X.setHorizontalAlignment(SwingConstants.RIGHT);
        X.setHorizontalTextPosition(SwingConstants.RIGHT);
        Y.setHorizontalAlignment(SwingConstants.RIGHT);
        Y.setHorizontalTextPosition(SwingConstants.RIGHT);
        currency.setHorizontalAlignment(SwingConstants.RIGHT);
        currency.setHorizontalTextPosition(SwingConstants.RIGHT);
        selectUsr.setHorizontalAlignment(SwingConstants.RIGHT);
        selectUsr.setHorizontalTextPosition(SwingConstants.RIGHT);
        X.setBounds(85,25,65,40);
        xField.setBounds(X.getX()+X.getWidth()+5,X.getY(),100,40);
        Y.setBounds(X.getX(),X.getY()+X.getHeight()+5,65,40);
        yField.setBounds(X.getX()+X.getWidth()+5,Y.getY(),100,40);
        currency.setBounds(X.getX(),Y.getY()+Y.getHeight()+5,65,40);
        currencyField.setBounds(X.getX()+X.getWidth()+5, currency.getY(),100,40);
        selectUsr.setBounds(X.getX() - 40,currency.getY()+currency.getHeight()+5,105,40);
        selectedUsr.setBounds(X.getX()+X.getWidth()+5, selectUsr.getY(),100,40);
        selectedUsr.setEditable(false);
        selectedUsr.setFocusable(false);
        apply.setFocusable(false);
        blank.setFocusable(false);
        preview.setFocusable(false);
        apply.setBounds(X.getX()+5,selectedUsr.getY()+selectedUsr.getHeight()+5,80,30);
        blank.setBounds(apply.getX()+apply.getWidth()+5,selectedUsr.getY()+selectedUsr.getHeight() +5,80,30);
        preview.setBounds(apply.getX(),blank.getY()+blank.getHeight() +5,165,30);
        if(Settings.users.isEmpty())
        {
            xField.setEnabled(false);
            yField.setEnabled(false);
            selectedUsr.setEnabled(false);
            currencyField.setEnabled(false);
            apply.setEnabled(false);
            preview.setEnabled(false);
        }
        this.add(X);
        this.add(xField);
        this.add(Y);
        this.add(yField);
        this.add(currency);
        this.add(currencyField);
        this.add(selectUsr);
        this.add(selectedUsr);
        this.add(apply);
        this.add(blank);
        this.add(preview);

    }

    @Override
    public void updateFields()
    {
        selectedUsr.removeAllItems();
        if(!Settings.users.isEmpty())
        {
        xField.setText(String.valueOf(Settings.posX));
        yField.setText(String.valueOf(Settings.posY));
        currencyField.setText(String.valueOf(Settings.currency));
        xField.setEnabled(true);
        yField.setEnabled(true);
        currencyField.setEnabled(true);
        apply.setEnabled(true);
        selectedUsr.setEnabled(true);
        preview.setEnabled(true);

            for (int i = 0; i < Settings.users.size(); i++) {
                selectedUsr.addItem(i);
            }
            selectedUsr.setSelectedIndex(Settings.currentUser());
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == apply)
        {
            System.out.println("You have applied somethings.");
            try
            {
                Settings.posX = Integer.parseInt(xField.getText());
                Settings.posY = Integer.parseInt(yField.getText());
                Settings.currency = currencyField.getText();
                Settings.setCurrentUser(selectedUsr.getSelectedIndex());
            }
            catch(Exception exception)
            {
                JOptionPane.showMessageDialog(this,"Invalid arguments!\nException: "+exception.getMessage(),"Input error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == blank)
        {
            xField.setEnabled(true);
            yField.setEnabled(true);
            selectedUsr.setEnabled(true);
            currencyField.setEnabled(true);
            apply.setEnabled(true);
            preview.setEnabled(true);
            xField.setText("0");
            yField.setText("0");
            currencyField.setText("-");
            selectedUsr.removeAllItems();
            if(!Settings.users.isEmpty()) {
                for (int i = 0; i < Settings.users.size(); i++) {
                    selectedUsr.addItem(i);
                }
                selectedUsr.setSelectedIndex(Settings.currentUser());
            }
        }
        else if(e.getSource() == preview)
        {
            JFrame tmp = new JFrame("Sample - currency: " + currencyField.getText());
            tmp.setBounds(Settings.posX,Settings.posY,800,900);
            tmp.setVisible(true);
        }

    }
}
