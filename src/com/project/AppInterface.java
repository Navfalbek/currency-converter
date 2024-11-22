package com.project;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;



/**
*
*
*  @author navfalbek.makhfuzullaev
*
 */



public class AppInterface extends JFrame {

    static CsvDecoding currency;
    static JFrame frame;
    static Dimension screenSize;
    static JPanel panelFor, panelTo;
    static ImageIcon fromIcon, toIcon;
    static JComboBox fromComboBox, toComboBox;
    static JLabel from, to, ratelabel, resultRateLabel;
    static Double[] rateResult;
    static Database database;


    private static void appGUI() {

        // this is to get all currency names
        currency = new CsvDecoding();


        // getting screen dimensions to run the app from the center of it
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        //separating height and width
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        int centerWidth = width / 2 - 500 / 2;
        int centerHeight = height / 2 - 300 / 2;

        //setting Frame
        frame = new JFrame("Currency exchange");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // stops executing java class on the IDE
        frame.setSize(500, 300);
        frame.setLocation(centerWidth, centerHeight);   // center of the screen
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.DARK_GRAY);


        // changing ui of OptionPane
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);


        //setting icon for Frame
        Image iconForFrame = Toolkit.getDefaultToolkit().getImage("src/resources/assets/main_icon.png");
        frame.setIconImage(iconForFrame);


        //setting MENU bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);

        JMenu menu = new JMenu("Settings"); // Settings menu to get info about the app
        menu.setForeground(Color.WHITE);

        menu.addMenuListener(new ClickedMenuListener());


        // main functionalities of the app and from Label
        panelFor = new JPanel();
        panelFor.setBounds(70,20,350,60);  // should change icon and height of the panel
        panelFor.setBackground(Color.DARK_GRAY);
        fromIcon = new ImageIcon("src/resources/assets/icon48.png");
        from = new JLabel("From:", fromIcon, SwingConstants.CENTER);
        //from.setLocation(10,10);
        from.setForeground(Color.WHITE);

        panelFor.add(from);


        // constructing comboBox for from
        fromComboBox = new JComboBox(currency.getCurrency());
        fromComboBox.setBounds(50, 50, 100, 40);
        fromComboBox.setBackground(Color.DARK_GRAY);
        fromComboBox.setForeground(Color.WHITE);
        panelFor.add(fromComboBox);


        // to
        panelTo = new JPanel();
        panelTo.setBounds(70, 90, 350, 60);
        panelTo.setBackground(Color.DARK_GRAY);
        toIcon = new ImageIcon("src/resources/assets/icon48.png");
        to = new JLabel("To:", toIcon, SwingConstants.CENTER);
        to.setForeground(Color.WHITE);
        panelTo.add(to);



        toComboBox = new JComboBox(currency.getCurrency());
        toComboBox.setBounds(50, 50, 100, 40);
        toComboBox.setBackground(Color.DARK_GRAY);
        toComboBox.setForeground(Color.WHITE);
        panelTo.add(toComboBox);



        frame.add(panelFor);
        frame.add(panelTo);


        // creating button for dark and light themes
        Icon themeButtonIcon = new ImageIcon("src/resources/assets/dark.png");
        JButton circularButtonTheme = new JButton(themeButtonIcon);
        circularButtonTheme.setBackground(Color.WHITE);
        circularButtonTheme.setBounds(445, 195, 30, 30);
        frame.add(circularButtonTheme);

        circularButtonTheme.addActionListener(new ClickedDarkTheme());


        // creating text field for amount
        JTextField textField = new JTextField();
        textField.setBounds(230, 160, 180, 25);
        textField.setFont(new Font("", Font.BOLD, 14));
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.GRAY);
        frame.add(textField);


        //making font bold
        Font fontConvert = new Font("Dialog", Font.BOLD, 12);


        // convert button to convert
        JButton convertButton = new JButton("Convert");
        convertButton.setFont(fontConvert);
        convertButton.setBounds(70,160,100,25);
        convertButton.setBackground(Color.GRAY);
        convertButton.setForeground(Color.WHITE);
        frame.add(convertButton);


        // Rate labels
        ratelabel = new JLabel();
        resultRateLabel = new JLabel();

        ratelabel.setFont(new Font("", Font.PLAIN, 12));
        ratelabel.setForeground(Color.WHITE);
        ratelabel.setBounds(70, 200, 120, 30);

        resultRateLabel.setFont(new Font("", Font.BOLD, 12));
        resultRateLabel.setForeground(Color.WHITE);
        resultRateLabel.setBounds(230, 200, 180, 30);


        // ActionListener for convert button to get text from TextField
        convertButton.addActionListener(event -> {
            if ( fromComboBox.getItemAt(fromComboBox.getSelectedIndex()) == toComboBox.getItemAt(toComboBox.getSelectedIndex()) ) {

                JOptionPane.showMessageDialog(frame,
                        "Selected same currency", "Error",
                        JOptionPane.ERROR_MESSAGE);

            }

            else if (textField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(frame,
                        "Type some amount", "Empty field",
                        JOptionPane.WARNING_MESSAGE);

            }

            else {
                try {
                    String convertText = textField.getText(); // getting text come back later
                    double amount = Double.parseDouble(convertText);

                    // I got confused about naming ComboBoxes that's why Strings are name vice versa
                    String to = currency.getCurrencyCode((String) fromComboBox.
                            getItemAt(fromComboBox.getSelectedIndex())); // getting currency code second

                    String from = currency.getCurrencyCode((String) toComboBox.
                            getItemAt(toComboBox.getSelectedIndex())); // getting currency coded first

                    System.out.println(from); // printing from
                    System.out.println(to);  // printing to
                    System.out.println(amount);  // printing amount

                    rateResult = SendingRequest.rateReturner(from, to, amount);

                    System.out.println(rateResult[0]);
                    System.out.println("some shit is happening");
                    System.out.println(rateResult[1]);

                    ratelabel.setText(from + " / " + to + " " + rateResult[1]); // total rate
                    resultRateLabel.setText(from + " / " + to + " " + rateResult[0]);   // just rate

                    database = new Database(from, to, amount, rateResult[1]);

                }
                catch (IndexOutOfBoundsException outOfBoundsException) {
                    outOfBoundsException.printStackTrace();
                    JOptionPane.showMessageDialog(frame,
                            "Something went wrong", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                    JOptionPane.showMessageDialog(frame,
                            "Amount should be in integer or float", "Wrong Input",
                            JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame,
                            "Amount should be in integer or float", "Wrong Input",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });



        frame.add(ratelabel);
        frame.add(resultRateLabel);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        appGUI();
    }

}


class ClickedMenuListener extends JFrame implements MenuListener {
    static JFrame settingsFrame;
    static ImageIcon versionIcon;
    static JLabel versionLabel, wVersionLabel;
    static JPanel versionPanel;
    @Override
    public void menuSelected(MenuEvent e) {
        settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(300,200);
        settingsFrame.setResizable(false);
        settingsFrame.setLocation(1030, 332);
        settingsFrame.getContentPane().setBackground(Color.DARK_GRAY);

        Image iconForFrame = Toolkit.getDefaultToolkit().getImage("src/resources/assets/settings_icon.png");
        settingsFrame.setIconImage(iconForFrame);

        versionPanel = new JPanel();
        versionPanel.setBounds(0,0,300,50);
        versionPanel.setBackground(Color.DARK_GRAY);

        versionIcon = new ImageIcon("src/resources/assets/version_icon.png");
        versionLabel = new JLabel("Converter", versionIcon, SwingConstants.CENTER);
        versionLabel.setForeground(Color.WHITE);

        wVersionLabel = new JLabel("                            v2.1.0");
        wVersionLabel.setForeground(Color.WHITE);

        versionPanel.setLayout(new GridLayout(1,2));
        versionPanel.add(versionLabel);
        versionPanel.add(wVersionLabel);


        System.out.println("menuSelected");

        settingsFrame.add(versionPanel);
        settingsFrame.setLayout(null);
        settingsFrame.setVisible(true);
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        System.out.println("menuDeselected");   // thinking about to remove this part
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        System.out.println("menuCanceled");
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        // Line2D line = new Line2D.Float();
    }
}


class ClickedDarkTheme extends AppInterface implements ActionListener {
    private static void lightThemeMode() {

        System.out.println("Private dark is working");
    }
    @Override
    public void actionPerformed(ActionEvent event) {

        System.out.println("Dark is working");

        lightThemeMode();
    }
}

