package control;

public class MainBot {


	public static void main(String[] args) {
		String host = args[0];

		Initializer initializer = new Initializer(host, 1337);
		initializer.initializeCommunicationObjects();
		initializer.initializeMVC();
		initializer.establishDependencies();	
		
		initializer.startListeningToServer();
		
		initializer.logInPrompt();
	}

}
