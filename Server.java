import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject; 
import utils.AuthenticatorResult;


public class Server extends UnicastRemoteObject implements ServerInterface{

	private TextFileAuthenticator authenticator;
	
	public Server() throws RemoteException {
		super();
		this.authenticator = new TextFileAuthenticator();
	}

	public static void main(String[] args) {
		try {
		System.setProperty("java.rmi.server.hostname", "10.70.193.78");
		System.setProperty("java.security.policy","/Users/krishnakothandaraman/eclipse-workspace/Comp3258_Assignment1_Server/src/security.policy");
		System.setSecurityManager(new SecurityManager());
		Server server = new Server();
		Naming.bind("24Server", server);
		System.out.println("Service registered!");
	} catch(Exception e) {
		System.err.println("Exception thrown: "+e);
	}
	}

	@Override
	public AuthenticatorResult login(String username, String password) throws RemoteException {
		return authenticator.performLogin(username, password);
	}

	@Override
	public AuthenticatorResult register(String username, String password) throws RemoteException {
		return authenticator.performRegistration(username, password);
	}

}
