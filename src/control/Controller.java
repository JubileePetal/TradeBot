package control;

import models.DataHolder;
import models.Order;
import communication.Sender;


public class Controller {

	private Sender sender;
	private DataHolder dataHolder;
	
	public Controller() {
	
	}

	public void addDataHolder(DataHolder dataHolder) {
		this.dataHolder = dataHolder;
	}
	
	public void addSender(Sender sender) {
		this.sender = sender;
	}
	
	public void sendOrder(Order order) {
		sender.sendOrder(order);
	}
	
	public boolean attemptLogIn(String nickname, int userType) {
		
		return sender.logIn(nickname, userType);
	}


}
