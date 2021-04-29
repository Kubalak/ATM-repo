package manager;
import settings.Settings;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ManagerWindow extends JFrame implements ActionListener
{
    JMenu MFile;
    JMenuItem Open,Save,Exit;
    JTabbedPane Pane;
    JPanel App,Usr,Crd,Wlt;
    JFileChooser chooser;
    ManagerWindow()
    {
        this.setTitle("Settings manager: ");
        this.setSize(400,400);
        this.setResizable(false);
        this.setLocation(Settings.posX,Settings.posY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Folder to open");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        MFile = new JMenu("File");
        Open = new JMenuItem("Open");
        Open.addActionListener(this);
        Save = new JMenuItem("Save");
        Save.addActionListener(this);
        Exit = new JMenuItem("Exit");
        Exit.addActionListener(this);
        JMenuBar tmp = new JMenuBar();
        MFile.add(Open);
        MFile.add(Save);
        MFile.add(Exit);
        tmp.add(MFile);
        this.setJMenuBar(tmp);

        Pane = new JTabbedPane();
        App = new AppManager();
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
        if(e.getSource()==Open)
        {
            if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
            {
                Settings.path = chooser.getSelectedFile().getAbsolutePath();
                Settings.path+=Settings.path.contains("\\")?"\\":"/";
                System.out.println(Settings.path);
            }
        }

    }
}
