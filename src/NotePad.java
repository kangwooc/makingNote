
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Kangwoo on 2017-07-24.
 * Edited on 2019-01-15
 */

class NotePad extends JPanel {
    JFrame myFrame, about_noteplus;
    JMenu menu;
    JMenuBar menuBar;
    JTextField textField;
    JTextArea textArea;
    String file_name;
    GridBagConstraints c;
    private int width, height;

    public NotePad() {
        super(new GridBagLayout());
        c = new GridBagConstraints();
        this.width = 1024;
        this.height = 768;
        this.textArea = new JTextArea(width, height);
        this.textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        getNewNote();
    }

    private ActionListener new_Note() {
        ActionListener new_note = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getNewNote();
            }
        };
        return new_note;
    }

    private ActionListener open_File() {
        // TODO Auto-generated method stub
        ActionListener open_file = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                open();
            }
        };
        return open_file;
    }

    private ActionListener save_File() {
        ActionListener save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                save();
            }
        };
        return save;
    }

    private ActionListener exit_File() {
        ActionListener exit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Save the File?");
                if (choice == JOptionPane.YES_OPTION) {
                    save();
                    System.exit(0);
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                } else {
                    myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            }
        };
        return exit;
    }

    private ActionListener help() {
        // TODO Auto-generated method stub
        ActionListener help = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about_noteplus();
            }

        };
        return help;
    }

    private void createMenubar() {
        menuBar = new JMenuBar();// making a menu bar
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The menu in this program that can open and save the file");
        menuBar.add(menu); // a group of JMenuItems
        createMenuItem("This makes a new note", "New Note", KeyEvent.VK_N, ActionEvent.CTRL_MASK, menu);
        createMenuItem("This opens existed note","Open Note",KeyEvent.VK_O, ActionEvent.SHIFT_MASK, menu);
        createMenuItem("This saves the note","Save Note",KeyEvent.VK_S, ActionEvent.CTRL_MASK, menu);
        createMenuItem("Close the current note","Exit",KeyEvent.VK_Q, ActionEvent.SHIFT_MASK, menu);
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        JMenuItem menuItem4 = new JMenuItem("About Note+", KeyEvent.VK_H);
        menuItem4.getAccessibleContext().setAccessibleDescription("Help & Information");
        menuItem4.addActionListener(help());
        menu.add(menuItem4);
    }

    private void createMenuItem(String desc, String item, int key, int action, JMenu menu) {
        JMenuItem menuItem = new JMenuItem(item, KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(key, action));
        menuItem.getAccessibleContext().setAccessibleDescription(desc);
        switch (item) {
            case "New Note":
                menuItem.addActionListener(new_Note());
                break;
            case "Open Note":
                menuItem.addActionListener(open_File());
                break;
            case "Save Note":
                menuItem.addActionListener(save_File());
                break;
            case "Exit":
                menuItem.addActionListener(exit_File());
                break;
        }
        menu.add(menuItem);
    }

    public void getNewNote() {
        createMenubar();
        file_name = "New Note";
        myFrame = new JFrame(file_name);
        myFrame.setSize(width, height);
        myFrame.add(new JMenu());
        myFrame.setJMenuBar(menuBar);
        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myFrame.setVisible(true);
    }

    private void open() {
        // TODO Auto-generated method stub
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try {
                myFrame.setTitle(f.getName().replaceAll(".txt", ""));
                textArea.read(new FileReader(f.getAbsolutePath()), null);
            } catch (IOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getMessage());
            }
        }
    }

    private void save() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File f = chooser.getSelectedFile();
                FileWriter fw = new FileWriter(f.getPath()+".txt");
                fw.write(textArea.getText());
                fw.flush();
                fw.close();
                myFrame.setTitle(f.getName());
            } catch (IOException ex) {
                JOptionPane.showConfirmDialog(null, ex.getMessage());
            }
        }
    }

    private void about_noteplus() {
        // TODO Auto-generated method stub
        about_noteplus = new JFrame();
        Font f = new Font("Arial", Font.ITALIC, 15);
        String content = "Developed by Kangwoo Choi \n Thank you for Using this program!";
        about_noteplus.setTitle("About Note+");
        about_noteplus.setSize(width/2, height/2);
        about_noteplus.setLocation(width/4, height/4);
        about_noteplus.getContentPane().setBackground(Color.gray);
        textField = new JTextField(50);
        textField.setFont(f);
        textField.setText(content);
        textField.setEditable(false);
        about_noteplus.add(textField);
        about_noteplus.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        about_noteplus.setVisible(true);
    }
}
