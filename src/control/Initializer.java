package control;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import view.LogInPanel;
import models.DataHolder;

import communication.Receiver;
import communication.Sender;

public class Initializer {

	private Controller controller;
	private Socket socket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private Sender sender;
	private Receiver receiver;
	private DataHolder dataHolder;


	
	public Initializer(String host, int port) {
		
		System.out.println("Attempting to connect to " + host + " on port " + port);
		socket = initializeSocket(host, port);
	

	}

	public void startListeningToServer() {
		Thread t = new Thread(receiver);
		t.start();
	}

	public void establishDependencies() {
		
		sender.addOutputStream(outToServer);
		receiver.addBufferedReader(inFromServer);
		receiver.addController(controller); 
		receiver.addDataHolder(dataHolder);
		
		controller.addSender(sender);


	}
	
	public void initializeCommunicationObjects() {
		
		outToServer = createOutputStream(socket);
		inFromServer = createBufferedReader(socket);
		
		sender = new Sender();
		receiver = new Receiver();

	}
	
	public void initializeMVC() {
		
		controller = new Controller();
		dataHolder = new DataHolder();	

	}
	
	private Socket initializeSocket(String host, int port) {

		Socket socket = null;		
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host when trying to connect socket, shutting down.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO exception when trying to connect socket, shutting down.");
			System.exit(0);
		}
		return socket;		
	}
	
	private BufferedReader createBufferedReader(Socket socket) {
		
		BufferedReader inFromServer = null;
		try {
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("IO exception when trying to create inputstream from server, shutting down.");
			System.exit(0);
		}
		return inFromServer;
		
	}
	
	private DataOutputStream createOutputStream(Socket socket) {
		
		DataOutputStream outToServer = null;
		
		try {
			outToServer = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("IO exception when trying to create outputstream to server, shutting down.");
			System.exit(0);
		}
		return outToServer;	
	}
	
	public void logInPrompt() {

		String nick = "Default";
		int userType = 0;
		
		LogInPanel logInPanel = new LogInPanel();

		if(logInPanel.logInAttemptConfirmed()) {
			nick = logInPanel.getNickName();
			
			userType = logInPanel.getUserType();
			receiver.setUserType(userType);
			
			
		} else {
			System.exit(0);
		}

		if(!controller.attemptLogIn(nick, userType)) {
			logInPrompt();
		}
		dataHolder.setNickName("Bot"+ nick);
	
	}
}
