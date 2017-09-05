package me.Demonly.osrs.client;

import java.applet.Applet;
import java.awt.Dimension;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;

import me.Demonly.osrs.config.Config;
import me.Demonly.osrs.game.Gamepack;
import me.Demonly.osrs.game.Gamestub;

public class Client {

	public void oldschool() {

		final URLClassLoader classLoader;
		Config config = new Config();
		config.load();
		Gamepack gamepack = new Gamepack();
		JFrame frame = new JFrame();
		Applet applet = null;
		Gamestub gameStub = null;

		try {
			System.out.println("DEMONLY: Loading the Demonly Client:");
			String pageSource = gamepack.getPageSource(new URL(Gamepack.urlBase));
			System.out.println("DEMONLY: pageSource loaded..");
			gameStub = new Gamestub(Gamepack.paramPattern, pageSource, Gamepack.urlBase);
			System.out.println("DEMONLY: Gamestub loaded..");
			classLoader = new URLClassLoader(new URL[] { Gamepack.localGamepackPath.toUri().toURL() });
			System.out.println("DEMONLY: Classloader built..");
			Class<?> appletClass = classLoader.loadClass("client");
			System.out.println("DEMONLY: Client class loaded..");
			applet = (Applet) appletClass.newInstance();
			System.out.println("DEMONLY: Applet instance built..");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		applet.setPreferredSize(new Dimension(800, 600));
		applet.setStub(gameStub);
		
		frame.add(applet);
		frame.setTitle(config.getString("Title"));
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		applet.init();
		applet.start();

	}

}
