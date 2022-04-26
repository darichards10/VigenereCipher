package itec220.fxlabs;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {
	@FXML
	private TextField key;
	@FXML
	private TextField string;
    @FXML
    private Button primaryButton;
    @FXML
    private ChoiceBox<String> list;
    
    private String[] choices = {"Encrypt", "Decrypt"};
    
    public void submit(ActionEvent e) {
    	String cipherKey = key.getText();
    	String cipher = string.getText();
    	String choice = list.getValue();
    	String result;
    	if(choice.equals(choices[0])) {
    		//encrypt
    		System.out.println("Encrypting.....");
    		result = encrypt(cipherKey, cipher);
    		
    	} else {
    		//decrypt
    		System.out.println("Decrypting.....");
    		result = decrypt(cipherKey, cipher);
    		
    	}
    	
    	JFrame resultFrame = new JFrame("Result Frame");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JLabel resultLabel = new JLabel();
    	JButton closeButton = new JButton("Close");
    	JButton copyButton = new JButton("Copy");
    	
    	resultLabel.setText(result);
    	
    	copyButton.setBounds(100,100,100,100);
    	closeButton.setBounds(50,50,50,50);
    	
    	StringSelection stringSelection = new StringSelection(result);
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(stringSelection, null);

    	resultLabel.setVerticalAlignment(SwingConstants.CENTER);
    	
    	resultFrame.getContentPane().add(copyButton, BorderLayout.NORTH);
    	resultFrame.getContentPane().add(resultLabel, BorderLayout.CENTER);
    	resultFrame.getContentPane().add(closeButton, BorderLayout.SOUTH);
    	closeButton.addActionListener(event -> { resultFrame.dispose();
    	
    	});
    	resultFrame.pack();
    	resultFrame.setSize(250,250);
    	resultFrame.show(true);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.getItems().addAll(choices);
		
	}
	
	public String decrypt(String key, String cipher) {
		String decryptedString = "";
		int[] decimalKey = new int[key.length()];
		
		for(int i = 0; i < key.length(); i++) {
			decimalKey[i] = key.charAt(i);
		}
		for(int i = 0; i < cipher.length(); i++) {
			decryptedString += decryptLetter(cipher.charAt(i), decimalKey[i%decimalKey.length]);
		}
		
		return decryptedString;
	}
	public String encrypt(String key, String cipher) {
		String encryptedString = "";
		int[] decimalKey = new int[key.length()];
		
		for(int i = 0; i < key.length(); i++) {
			decimalKey[i] = key.charAt(i);
			System.out.println(decimalKey[i]);
		}
		
		for(int i = 0; i < cipher.length(); i++) {
			encryptedString += encryptLetter(cipher.charAt(i), decimalKey[i%decimalKey.length]);
		}
		
		return encryptedString;
	}
	
	public char encryptLetter(char letter, int index) {
		int distanceToShift = index - 32;
		if(distanceToShift + letter > 126) {
			return (char) (letter - (94 - distanceToShift));
		}
		return (char) (letter + distanceToShift);
	}
	
	public char decryptLetter(char letter, int index) {
		int distanceToShift = index - 32;
		System.out.println(distanceToShift + " before");
		if(letter - distanceToShift < 32) {
			distanceToShift -= letter - 32;
			System.out.println(126 - distanceToShift);
			return (char) (126 - distanceToShift);
		}
		return (char) (letter - distanceToShift);
	}
} 
