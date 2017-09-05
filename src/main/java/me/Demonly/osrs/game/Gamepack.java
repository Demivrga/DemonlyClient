package me.Demonly.osrs.game;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.Demonly.osrs.config.Config;
import me.Demonly.osrs.util.Store;

public class Gamepack {

	public static final int startWorld;
	public static final String urlBase;
	public static final Pattern paramPattern;
	public static final Pattern gamepackPattern;
	public static final Path localGamepackPath;

	private static Store store = new Store();
	private static Config config = new Config();

	static {
		config.load();
		startWorld = config.getInt("World");
		urlBase = String.format("http://oldschool%d.runescape.com/", startWorld);
		paramPattern = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");
		gamepackPattern = Pattern.compile("gamep\\w+");
		localGamepackPath = store.jar.toPath();
	}

	public String getPageSource(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		DataInputStream in = new DataInputStream(connection.getInputStream());
		byte[] temp = new byte[connection.getContentLength()];
		in.readFully(temp);
		return new String(temp);
	}

	public URL getRemoteGamepackURL(String pageSource) {
		Matcher matcher = gamepackPattern.matcher(pageSource);
		if (matcher.find()) {
			try {
				return new URL(urlBase + matcher.group(0) + ".jar");
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean downloadGamepack(URL gamepackURL) throws IOException {
		URLConnection connection = gamepackURL.openConnection();
		System.out.printf("Downloading %s%n", connection.getURL());
		try (final InputStream in = connection.getInputStream()) {
			Files.deleteIfExists(localGamepackPath);
			Files.copy(in, localGamepackPath);
		} catch (IOException e) {
			Files.deleteIfExists(localGamepackPath);
			throw e;
		}
		return true;
	}

}
