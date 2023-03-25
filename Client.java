import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

import utils.AuthenticatorResult;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {
	
	private ServerInterface server;
    private JFrame mainFrame;
    private JDialog loginDialog;
    private JDialog registerDialog;
    
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    
    private JLabel loginUsernameLabel;
    private JTextField loginUsernameField;
    private JLabel loginPasswordLabel;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    
    
    private JPanel userProfilePanel, playGamePanel, leaderboardPanel, logoutPanel;
    private JTabbedPane tabbedPane;

	
	public Client(String host){
		try{
			
			System.out.println("Connecting to: " + host);
			System.setProperty("java.security.policy","/Users/krishnakothandaraman/eclipse-workspace/Comp3258_Assignment1_Server/src/security.policy");
			Registry registry = LocateRegistry.getRegistry(host);
			server = (ServerInterface) registry.lookup("24Server");

			mainFrame = new JFrame("24 Game Login");
			
			// Create four panels for each tab
	        userProfilePanel = new JPanel();
	        playGamePanel = new JPanel();
	        leaderboardPanel = new JPanel();
	        logoutPanel = new JPanel();

	        userProfilePanel.add(this.getUserProfilePanel());
	        playGamePanel.add(this.getPlayGamePanel());
	        leaderboardPanel.add(this.getLeaderBoardPanel());
	        logoutPanel.add(this.getLeaderboardPanel());

	        // Create a JTabbedPane and add each panel to it
	        tabbedPane = new JTabbedPane();
	        tabbedPane.addTab("User Profile", userProfilePanel);
	        tabbedPane.addTab("Play Game", playGamePanel);
	        tabbedPane.addTab("Leaderboard", leaderboardPanel);
	        tabbedPane.addTab("Log out", logoutPanel);

	        mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

	        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainFrame.setSize(600, 400);

			// Login dialog
			loginDialog = new JDialog(mainFrame, "Login", true);
			loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			loginUsernameLabel = new JLabel("Username:");
			loginUsernameField = new JTextField(20);
			loginPasswordLabel = new JLabel("Password:");
			loginPasswordField = new JPasswordField(20);
			loginButton = new JButton("Login");
			registerButton = new JButton("Register");

			JPanel loginPanel = new JPanel(new GridLayout(3, 2));
			loginPanel.add(loginUsernameLabel);
			loginPanel.add(loginUsernameField);
			loginPanel.add(loginPasswordLabel);
			loginPanel.add(loginPasswordField);
			loginPanel.add(loginButton);
			loginPanel.add(registerButton);
			loginDialog.getContentPane().add(loginPanel);
			loginDialog.pack();
	        
	        // Register dialog
	        registerDialog = new JDialog(mainFrame, "Register", true);
	        registerDialog.setVisible(false);
	        JPanel registerPanel = new JPanel(new GridLayout(4, 2));
	        JLabel newUsernameLabel = new JLabel("New Username:");
	        JLabel newPasswordLabel = new JLabel("New Password:");
	        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
	        JButton registerSubmitButton = new JButton("Register");
	        JButton registerCancelButton = new JButton("Cancel");
	        newUsernameField = new JTextField();
	        newPasswordField = new JPasswordField();
	        confirmPasswordField = new JPasswordField();
	        registerPanel.add(newUsernameLabel);
	        registerPanel.add(newUsernameField);
	        registerPanel.add(newPasswordLabel);
	        registerPanel.add(newPasswordField);
	        registerPanel.add(confirmPasswordLabel);
	        registerPanel.add(confirmPasswordField);
	        registerPanel.add(registerSubmitButton);
	        registerPanel.add(registerCancelButton);
	        registerDialog.getContentPane().add(registerPanel);
	        registerDialog.pack();
	        

	        loginButton.addActionListener(loginListener);
	        registerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	loginDialog.setVisible(false);
	                newUsernameField.setText("");
	                newPasswordField.setText("");
	                confirmPasswordField.setText("");
	                // Show register dialog
	                registerDialog.setVisible(true);
	            }
	        });
	        
	        registerSubmitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
		            String username = newUsernameField.getText();
		            String password = new String(newPasswordField.getPassword());
		            String confirmPassword = new String(confirmPasswordField.getPassword());
		            System.out.println(password + "," + confirmPassword);
		            AuthenticatorResult registerResult = null;
		            
		            if (!username.isEmpty() && !password.isEmpty()) {
		            	if (password.equals(confirmPassword)) {
							try {
								registerResult = server.register(username, password);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
			                if (registerResult.isSuccess()) {
			                    JOptionPane.showMessageDialog(loginDialog, registerResult.getMessage());
			                    registerDialog.dispose();
			                    mainFrame.setVisible(true);
			                } else {
			                    JOptionPane.showMessageDialog(loginDialog, registerResult.getMessage());
			                }
			            } else {
			                JOptionPane.showMessageDialog(loginDialog, "Password does not match confirm password!");
			            }
		            }
		            else {
		                JOptionPane.showMessageDialog(loginDialog, "Username and password cannot be empty");

		            }
				}
	        });
	        
	        registerCancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					registerDialog.setVisible(false);
					loginUsernameField.setText("");
			        loginPasswordField.setText("");
			        // Show register dialog
			        loginDialog.setVisible(true);
				}
	        });


	        registerDialog.setVisible(false);
	        mainFrame.setVisible(false);
	        mainFrame.setLocationRelativeTo(null);
	        loginDialog.setVisible(true);
	        loginDialog.setLocationRelativeTo(null);
	        registerDialog.setLocationRelativeTo(null);

	        
	        
		} catch (Exception e){
			System.err.println("Failed in RMI access!" + e);
		}
	}
    
	private JLabel getUserProfilePanel() {
		return new JLabel("User Profile Content");
	}
	private JLabel getPlayGamePanel() {
		return new JLabel("Play Game Content");
	}
	private JLabel getLeaderBoardPanel() {
		return new JLabel("Leaderboard Content");
	}
	private JLabel getLeaderboardPanel() {
		return new JLabel("Logout Content");
	}
	
    ActionListener loginListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());
            AuthenticatorResult loginResult = null;

            if (!username.isEmpty() && !password.isEmpty()) {
				try {
					loginResult = server.login(username, password);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

                if (loginResult.isSuccess()) {
                    JOptionPane.showMessageDialog(loginDialog, loginResult.getMessage());
                    loginDialog.dispose();
                    mainFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginDialog, loginResult.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Username and password cannot be empty!");
            }
        }
    };
	public static void main(String[] args) throws RemoteException {
		Client client = new Client("10.70.193.78");

	}
	

}
