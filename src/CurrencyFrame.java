import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyFrame extends JFrame implements ActionListener {

    JTextField amountTextField;
    JButton calculateButton;
    JComboBox fromComboBox;
    JComboBox toComboBox;
    String[][] country_code_list;
    String[] country_list;
    JLabel fromImageLabel;
    JLabel toImageLabel;
    JLabel exchangeRateLabel;
    DecimalFormat f = new DecimalFormat("##.00");

    CurrencyFrame()throws MalformedURLException, URISyntaxException, IOException{
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setTitle("Currency converter");

        try{
            String jsonString = new String(Files.readAllBytes(Paths.get("D:/Java/1/main/src/country_list.json")));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray keys = jsonObject.names();
            country_code_list = new String[keys.length()][2];
            country_list = new String[keys.length()];

            for (int i = 0; i < keys.length(); i++) {
                String key = keys.getString(i);
                String value = jsonObject.getString(key);
                country_code_list[i][0] = key;
                country_code_list[i][1] = value;
                country_list[i] = key;
            }
            Arrays.sort(country_list);
            // // System.out.println(country_list.length);
            // for (String entry : country_list) {
            //     System.out.println("{" + entry +"}");
            // }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        JLabel captionLabel = new JLabel("Currency converter");
        captionLabel.setBounds(0, 0, 500, 25);
        captionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel amountLabel = new JLabel("Enter amount:");
        amountLabel.setBounds(20, 50, 150, 25);
        amountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        amountTextField = new JTextField();
        amountTextField.setPreferredSize(new Dimension(200, 25));
        amountTextField.setBounds(170, 50, 200, 25);
        amountTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
            String value = amountTextField.getText();
            int l = value.length();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
               amountTextField.setEditable(true);
            } else {
               amountTextField.setEditable(false);
            }
            if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                if (amountTextField.getText().length() != 0){
                ke.consume();
                amountTextField.setText(""+amountTextField.getText().substring(0, amountTextField.getText().length() - 1));
                } else{
                    ;
                }
            }
         }
        });

        JLabel fromLabel = new JLabel("From");
        fromLabel.setBounds(20, 100, 150, 25);
        
        JLabel toLabel = new JLabel("To");
        toLabel.setBounds(170, 100, 100, 25);

        fromImageLabel = new JLabel();
        ImageIcon fromImageIcon = Additional.getImage("US");
        fromImageLabel.setIcon(fromImageIcon);
        fromImageLabel.setBounds(20, 125, 25, 25);

        fromComboBox = new JComboBox(country_list);
        fromComboBox.setBounds(50, 125, 60, 25);
        fromComboBox.addActionListener(this);
        fromComboBox.setSelectedIndex(144);

        toImageLabel = new JLabel();
        ImageIcon toImageIcon = Additional.getImage("UA");
        toImageLabel.setIcon(toImageIcon);
        toImageLabel.setBounds(170, 125, 25, 25);

        toComboBox = new JComboBox(country_list);
        toComboBox.setBounds(200, 125, 60, 25);
        toComboBox.addActionListener(this);
        toComboBox.setSelectedIndex(142);

        exchangeRateLabel = new JLabel("Exchange rate: ");
        exchangeRateLabel.setBounds(20, 175, 500, 25);

        calculateButton = new JButton("Get exchange rate");
        calculateButton.setBounds(20, 200, 200, 25);
        calculateButton.addActionListener(this);

        this.add(amountLabel);
        this.add(captionLabel);
        this.add(amountTextField);
        this.add(fromLabel);
        this.add(toLabel);
        this.add(fromComboBox);
        this.add(toComboBox);
        this.add(fromImageLabel);
        this.add(toImageLabel);
        this.add(exchangeRateLabel);
        this.add(calculateButton);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fromComboBox){
            String fromCode = Additional.getCode(country_code_list, fromComboBox.getSelectedItem().toString());
            try {
                fromImageLabel.setIcon(Additional.getImage(fromCode.toString()));
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==toComboBox){
            String toCode = Additional.getCode(country_code_list, toComboBox.getSelectedItem().toString());
            try {
                toImageLabel.setIcon(Additional.getImage(toCode.toString()));
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==calculateButton){
            try {
                if (!amountTextField.getText().isEmpty()){
                    double rate = Additional.getExchangeRate(fromComboBox.getSelectedItem().toString(), toComboBox.getSelectedItem().toString());
                    Integer amt = Integer.parseInt(amountTextField.getText());
                    Double mult = rate * amt;
                    exchangeRateLabel.setText("Exchange rate: "+ amt + fromComboBox.getSelectedItem().toString() + " = " 
                    + f.format(mult) + toComboBox.getSelectedItem().toString());
                } else {
                    amountTextField.setText("1");
                    double rate = Additional.getExchangeRate(fromComboBox.getSelectedItem().toString(), toComboBox.getSelectedItem().toString());
                    exchangeRateLabel.setText("Exchange rate: 1" + fromComboBox.getSelectedItem().toString() + " = " 
                    + f.format(rate) + toComboBox.getSelectedItem().toString());
                }
            } catch (JSONException | IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }
    
}
