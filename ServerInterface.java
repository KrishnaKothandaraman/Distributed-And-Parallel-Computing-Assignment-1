
import java.rmi.Remote;
import java.rmi.RemoteException;

import utils.AuthenticatorResult;

public interface ServerInterface extends Remote {
    AuthenticatorResult login(String username, String password) throws RemoteException;
    AuthenticatorResult register(String username, String password) throws RemoteException;

}
