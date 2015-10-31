    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Santoro <daniele.santoro@studenti.unitn.it>
 */
public class Discover {

    public void discoveryLoop() {
        ArrayList<Class> classes = getClassList("Black.jar");
        Iterator<Class> i = classes.iterator();
        while(i.hasNext()){
            Class c = i.next();
            discovery(c);
        }
    }

    public ArrayList<Class> getClassList(String pathToJar) {
        ArrayList<Class> classList = new ArrayList();
        JarFile jarFile;
        try {
            jarFile = new JarFile(pathToJar);
            Enumeration e = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = (JarEntry) e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of ".class"characters
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                System.out.println("Class found in Jar archive: " + className);
                Class c = cl.loadClass(className);
                classList.add(c);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classList;
    }

    private void discovery(Class c) {
        
    }
}
