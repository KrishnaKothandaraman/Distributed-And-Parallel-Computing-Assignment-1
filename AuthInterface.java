import utils.AuthenticatorResult;

public interface AuthInterface {
	AuthenticatorResult performLogin(String username, String password);
	AuthenticatorResult performRegistration(String username, String password);
	void performLogout(String username);
}
