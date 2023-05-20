import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject; 
import utils.AuthenticatorResult;


public class Server extends UnicastRemoteObject implements ServerInterface{

	private TextFileAuthenticator authenticator;
	static String currentDirectory = System.getProperty("user.dir");

	private static String securityPolicyFilePath = currentDirectory + "/security.policy";
	public Server() throws RemoteException {
		super();
		this.authenticator = new TextFileAuthenticator();
	}

	public static void main(String[] args) {
		try {
		System.out.println(securityPolicyFilePath);
		System.setProperty("java.rmi.server.hostname", args[0]);
		System.setProperty("java.security.policy", securityPolicyFilePath);
		System.setSecurityManager(new SecurityManager());
		Server server = new Server();
		System.out.println("Binding!");
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

	@Override
	public AuthenticatorResult logout(String username) {
		return authenticator.performLogout(username);
	}
	

}
