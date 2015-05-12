package view;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.OpCodes;

/* 
 * note this class currently has hardcoded
 * user types and will need to be rewritten
 * in case customer wants to add more.
 */

public class LogInPanel extends JPanel {
	
	private JComboBox userTypesCombo;
	private JTextField nickField;
	
	public LogInPanel() {
		
		nickField = new JTextField(14);
		this.add(new JLabel("Nickname:"));
		this.add(nickField);
		
		String[] userTypes = {"Bot"};
		userTypesCombo = new JComboBox(userTypes);
		userTypesCombo.setSelectedIndex(0);
		this.add(new JLabel("User type:"));
		this.add(userTypesCombo);			

	}
	
	public boolean logInAttemptConfirmed() {
		
		int result = JOptionPane.showConfirmDialog(null,  this,
				"Please enter nickname and usertype", JOptionPane.OK_CANCEL_OPTION);
		return result == JOptionPane.OK_OPTION ? true : false;
	}
	
	public String getNickName() {
		return nickField.getText();
	}
	
	public int getUserType() {

		int userType = 0;
		switch (userTypesCombo.getSelectedIndex()) {
			case 0: userType = OpCodes.BOT; break;
		}
		return userType;
	}

}
