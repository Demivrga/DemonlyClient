package me.Demonly.osrs;

import java.io.IOException;
import java.net.URL;

import me.Demonly.osrs.client.Client;
import me.Demonly.osrs.config.Config;
import me.Demonly.osrs.game.Gamepack;
import me.Demonly.osrs.util.Store;

public class Loader {

	public static void main(String[] args) {

		// Configuration Utilities
		Config config = new Config();
		config.load();

		// Initializing our storage files
		Store store = new Store();

		if (!store.config.exists()) {
			System.out.println("DEMONLY: Could not find our config file, adding it in.");
			config.saveDefaultConfig();
		}

		// Initializing our game classes.
		Client client = new Client();
		Gamepack gamepack = new Gamepack();

		store.storeClientData();

		try {
			// Checking if our gamepack exists, if so, check to see if it's up to date.
			if (store.jar.exists()) {
				System.out.println("DEMONLY: Checking to see if our gamepack is up to date..");

				String pageSource = gamepack.getPageSource(new URL(Gamepack.urlBase));
				if (store.checkSum(gamepack.getRemoteGamepackURL(pageSource), store.jar) == true) {
					store.jar.delete();
					gamepack.downloadGamepack(gamepack.getRemoteGamepackURL(pageSource));
					System.out.println("DEMONLY: Gamepack is now up to date.");

				} else {
					System.out.println("DEMONLY: Gamepack is up to date.");
				}

				// If our gamepack doesn't exist, lets download it.
			} else if (!store.jar.exists()) {
				System.out.println("DEMONLY: It seems we're missing our gamepack, downloading now..");
				String pageSource = gamepack.getPageSource(new URL(Gamepack.urlBase));
				gamepack.downloadGamepack(gamepack.getRemoteGamepackURL(pageSource));
				System.out.println("DEMONLY: Gamepack is now downloaded.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		client.oldschool();

	}

}
