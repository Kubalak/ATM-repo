package manager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerWindow extends JFrame implements ActionListener
{
    JMenu File;
    JMenuItem Open,Save,Exit;
    JTabbedPane Pane;
    JPanel App,Usr,Crd,Wlt;
    ManagerWindow()
    {
        this.setTitle("Settings manager: ");
        this.setSize(800,600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File = new JMenu("File");
        Open = new JMenuItem("Open");
        Open.addActionListener(this);
        Save = new JMenuItem("Save");
        Save.addActionListener(this);
        Exit = new JMenuItem("Exit");
        Exit.addActionListener(this);
        JMenuBar tmp = new JMenuBar();
        File.add(Open);
        File.add(Save);
        File.add(Exit);
        tmp.add(File);
        this.setJMenuBar(tmp);

        Pane = new JTabbedPane();
        App = new JPanel();
        Usr = new JPanel();
        Crd = new JPanel();
        Wlt = new JPanel();
        Pane.addTab("General",App);
        Pane.addTab("Users",Usr);
        Pane.addTab("Credit cards",Crd);
        Pane.addTab("Wallet",Wlt);
        this.add(Pane);


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
}
