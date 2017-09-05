package me.Demonly.osrs.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class HashChecker {
	
    public int getLocalHash(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            int i = jarFile.getManifest().hashCode();
            jarFile.close();
            return i;
        } catch (IOException e) {
            return -1;
        }
    }

    public int getRemoteHash(URL jar) {
        try(JarInputStream stream = new JarInputStream(jar.openStream())){
            return stream.getManifest().hashCode();
        } catch(IOException e){
        	e.printStackTrace();
            return -1;
        }
    }
    
    public boolean outdated(File file, URL jar) {
        if(!file.exists()){
            return true;
        }

        int localHash = getLocalHash(file);
        int remoteHash = getRemoteHash(jar);
        System.out.println("Local hash: " + localHash + "\nRemote hash: " + remoteHash);
        return localHash == -1 || localHash != remoteHash;
    }

}
