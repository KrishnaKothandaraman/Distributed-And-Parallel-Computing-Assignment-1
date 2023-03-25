import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import utils.AuthenticatorResult;

public class TextFileAuthenticator implements AuthInterface {

    private String userInfoFilePath = "/Users/krishnakothandaraman/eclipse-workspace/Comp3258_Assignment1_Server/src/authfiles/UserInfo.txt";
    private String onlineUserFilePath = "/Users/krishnakothandaraman/eclipse-workspace/Comp3258_Assignment1_Server/src/authfiles/OnlineUser.txt";

    
    public static void clearFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(new File(filePath));
            writer.write("");
            writer.close();
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        }
    }
    
	private void writeToFile(String string, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(string);
            writer.newLine();
            writer.close();
            System.out.println("Successfully wrote " + string + " to file.");

        } catch (IOException e) {
            System.out.println("Error writing username to file: " + e.getMessage());
        }
    }
	
    private boolean checkIfOnlineAlready(String username) {
    	
    	try {
            // create a File object
            File file = new File(onlineUserFilePath);

            // create a Scanner object to read the file
            Scanner scanner = new Scanner(file);

            // read the file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (username.equals(line.trim())) {
                	System.out.println(username + " already online");
                	return true;
                }
                
            }
            // close the scanner
            scanner.close();
    	}
	    catch (FileNotFoundException e) {
	        System.out.println("File not found: " + e.getMessage());
	    }
    	System.out.println(username + " not online!");
    	return false;
	}
    
    
    private String checkifUserAlreadyExistsAndGetPassword(String username) {
    	try {
            // create a File object
            File file = new File(userInfoFilePath);

            // create a Scanner object to read the file
            Scanner scanner = new Scanner(file);

            // read the file line by line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length != 2) {
                	System.out.println("Skipping since error in part " + String.join(",", parts));
                }
                if (username.equals(parts[0])) {
                	return parts[1];
                }
                
            }
            // close the scanner
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    	
    	return "";
    }
    
	@Override
	public AuthenticatorResult performLogin(String username, String password) {

        
		String registeredPassword = this.checkifUserAlreadyExistsAndGetPassword(username);
		
		if (!registeredPassword.equals("") && registeredPassword.equals(password))
		{
			if (!this.checkIfOnlineAlready(username)) {
				this.writeToFile(username, this.onlineUserFilePath);
				return new AuthenticatorResult(true, "User logged in successfully!");
			}
			else {
				return new AuthenticatorResult(false, "User already online! Logout of your other sessions");

			}
		}
		return new AuthenticatorResult(false, "Incorrect username or password");
	}

	@Override
	public AuthenticatorResult performRegistration(String username, String password) {
		if (this.checkifUserAlreadyExistsAndGetPassword(username).equals("")) {
			this.writeToFile(username + "," + password, this.userInfoFilePath);
			return new AuthenticatorResult(true, username + " registered successfully!");
		}
		return new AuthenticatorResult(false, username + " already exists. Please login instead.");
	}

	@Override
	public void performLogout(String username) {
        try {
            // Create a temporary file to hold the modified contents
            String tempFilePath = "/Users/krishnakothandaraman/eclipse-workspace/Comp3258_Assignment1_Server/src/authfiles/temp.txt";
            FileWriter tempFileWriter = new FileWriter(tempFilePath);
            BufferedWriter tempWriter = new BufferedWriter(tempFileWriter);

            FileReader fileReader = new FileReader(this.onlineUserFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains(username)) {
                    tempWriter.write(line);
                    tempWriter.newLine();
                }
            }
            bufferedReader.close();
            tempWriter.close();
            File originalFile = new File(this.onlineUserFilePath);
            originalFile.delete();
            File tempFile = new File(tempFilePath);
            tempFile.renameTo(originalFile);
            System.out.println(username + "logged out successfully.");
        } catch (IOException e) {
            System.out.println("Error in logging out " + username + " : " + e.getMessage());
        }
    }
	
	public TextFileAuthenticator() {
		TextFileAuthenticator.clearFile(onlineUserFilePath);
	}
	
//	public static void main(String[] args) {
//		System.out.println("Hello from authenticator");
//		TextFileAuthenticator auth = new TextFileAuthenticator();
////		System.out.println(auth.performLogin("username1", "password1"));
////		System.out.println(auth.performRegistration("username1", "password1"));
////		System.out.println(auth.performRegistration("username1", "password1"));
////		System.out.println(auth.performLogin("username1", "password1"));
////		System.out.println(auth.performLogin("username1", "password1"));
////		auth.performLogout("username1");
////		System.out.println(auth.performLogin("username1", "password1"));
//	}
		
}
