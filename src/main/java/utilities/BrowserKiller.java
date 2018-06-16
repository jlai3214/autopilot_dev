package main.java.utilities;
import java.io.IOException;

public class BrowserKiller {

	public static void main(String[] args) throws IOException {

		String os = System.getProperty("os.name");

		if (os.contains("Windows")) {

			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
			Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");

			Runtime.getRuntime().exec("taskkill /F /IM safari.exe");

			Runtime.getRuntime().exec("taskkill /F /IM opera.exe");

		} else {

			// Assuming a non Windows OS will be some version of Unix, Linux, or Mac

			Runtime.getRuntime().exec("kill `ps -ef | grep -i firefox | grep -v grep | awk '{print $2}'`");

			Runtime.getRuntime().exec("kill `ps -ef | grep -i chrome | grep -v grep | awk '{print $2}'`");

			Runtime.getRuntime().exec("kill `ps -ef | grep -i safari | grep -v grep | awk '{print $2}'`");

		}

	}

}



