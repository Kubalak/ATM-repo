package manager;
import settings.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppManager extends JPanel implements ActionListener
{

    private JTextField XField,YField,CurrencyField;
    private JComboBox<Integer> selectedUsr;
    JButton Apply,Blank,Preview;
    public AppManager()
    {
        this.setLayout(null);
        JLabel X,Y,currency,selectUsr;
        X = new JLabel("Position X: ");
        Y = new JLabel("Position Y: ");
        currency  = new JLabel("Currency: ");
        selectUsr = new JLabel("Startup user: ");
        Apply = new JButton("Apply");
        Blank = new JButton("Clear");
        Preview = new JButton("Preview");
        Apply.addActionListener(this);
        Blank.addActionListener(this);
        Preview.addActionListener(this);

        XField = new JTextField();
        YField = new JTextField();
        CurrencyField = new JTextField();
        selectedUsr = new JComboBox<>();
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
        XField.setBounds(X.getX()+X.getWidth()+5,X.getY(),100,40);
        Y.setBounds(X.getX(),X.getY()+X.getHeight()+5,65,40);
        YField.setBounds(X.getX()+X.getWidth()+5,Y.getY(),100,40);
        currency.setBounds(X.getX(),Y.getY()+Y.getHeight()+5,65,40);
        CurrencyField.setBounds(X.getX()+X.getWidth()+5, currency.getY(),100,40);
        selectUsr.setBounds(X.getX() - 40,currency.getY()+currency.getHeight()+5,105,40);
        selectedUsr.setBounds(X.getX()+X.getWidth()+5, selectUsr.getY(),100,40);
        selectedUsr.setEditable(false);
        selectedUsr.setFocusable(false);
        Apply.setFocusable(false);
        Blank.setFocusable(false);
        Preview.setFocusable(false);
        Apply.setBounds(X.getX()+5,selectedUsr.getY()+selectedUsr.getHeight()+5,80,30);
        Blank.setBounds(Apply.getX()+Apply.getWidth()+5,selectedUsr.getY()+selectedUsr.getHeight() +5,80,30);
        Preview.setBounds(Apply.getX(),Blank.getY()+Blank.getHeight() +5,165,30);
        if(Settings.users.isEmpty())
        {
            XField.setEnabled(false);
            YField.setEnabled(false);
            selectedUsr.setEnabled(false);
            CurrencyField.setEnabled(false);
            Apply.setEnabled(false);
            Preview.setEnabled(false);
        }
        this.add(X);
        this.add(XField);
        this.add(Y);
        this.add(YField);
        this.add(currency);
        this.add(CurrencyField);
        this.add(selectUsr);
        this.add(selectedUsr);
        this.add(Apply);
        this.add(Blank);
        this.add(Preview);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Apply)
        {
            System.out.println("You have applied somethings.");
        }
        else if(e.getSource() == Blank)
        {
            System.out.println("You have blanked something.");
            XField.setEnabled(true);
            YField.setEnabled(true);
            selectedUsr.setEnabled(true);
            CurrencyField.setEnabled(true);
            Apply.setEnabled(true);
            XField.setText("0");
            YField.setText("0");
            CurrencyField.setText("-");
            if(selectedUsr.getItemCount()>0)selectedUsr.setSelectedIndex(0);
        }

    }
}
