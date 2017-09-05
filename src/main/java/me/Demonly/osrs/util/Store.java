package me.Demonly.osrs.util;

import java.io.File;
import java.net.URL;

public class Store {

	public String appData = (System.getenv("APPDATA"));
	public File store = new File(appData + "/DemonlyClient");
	public File jar = new File(store + "/gamepack.jar");
	public File config = new File(store + "/config.yml");

	public String getOperatingSystem() {
		String os = System.getProperty("os.name");

		if (os.contains("Windows")) {
			return "Windows";
		}
		if (os.contains("Mac")) {
			return "Mac";
		}
		if (os.contains("Linux")) {
			return "Linux";
		}
		return "N/A";
	}

	public void storeClientData() {

		if (getOperatingSystem().equals("Windows")) {

			System.out.println("DEMONLY: Our system seems to be Windows. Setting functionality up for that.");

			if (!store.exists()) {
				System.out.println("DEMONLY: We could not find our directory, making it now!");
				store.mkdir();
			}

		}
	}

	public boolean checkSum(URL remoteURL, File localJar) {

		HashChecker hash = new HashChecker();

		return hash.outdated(localJar, remoteURL);
	}
}
