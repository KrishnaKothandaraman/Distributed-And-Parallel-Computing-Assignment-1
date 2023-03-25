import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WordCounter extends UnicastRemoteObject implements WordCount {

	public static void main(String[] args) {
		try {
			System.setProperty("java.rmi.server.hostname", "10.68.41.108");
			System.setProperty("java.security.policy","/Users/krishnakothandaraman/IdeaProjects/Tutorial4/security.policy");
			System.setSecurityManager(new SecurityManager());
			WordCounter app = new WordCounter();
			Naming.bind("WordCounter", app);
			System.out.println("Service registered!");
		} catch(Exception e) {
			System.err.println("Exception thrown: "+e);
		}
	}
	
	public WordCounter() throws RemoteException {
		super();
	}
	public int count(String message) throws RemoteException{
		return message.split(" +").length;
	}

}
