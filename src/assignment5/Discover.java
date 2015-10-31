    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Santoro <daniele.santoro@studenti.unitn.it>
 */
public class Discover {
    
    Object misteryO = null;
    Class misteryC = null;
    
    public void discoveryLoop(String jarPath) {
        ArrayList<Class> classes = getClassList(jarPath);
        ListIterator<Class> i = classes.listIterator();
        while (i.hasNext()) {
            System.out.println("\nDiscovering class: " + i.nextIndex());
            Class c = i.next();
            discovery(c);            
        }
        playWithMistery(this.misteryC);
    }

    public ArrayList<Class> getClassList(String pathToJar) {
        ArrayList<Class> classList = new ArrayList<>();
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
        String className = c.getName();
        System.out.println("This Class name is: " + className);
        String classSimpleName = c.getSimpleName();
        System.out.println("This Class simple name is: " + classSimpleName);
        String parent = c.getSuperclass().getCanonicalName();
        System.out.println("This Class inherit from: " + parent);

        if (!classSimpleName.equals("")) {
            this.misteryC = c;
            deepDiscovery(c);
        }

    }

    @SuppressWarnings("unchecked")
    private void deepDiscovery(Class c) {
        try {
            //instantiateClass(c);
            c.getMethods();
            System.out.println("This Class public methods are:");
            Method[] methods = c.getMethods();
            for (int i = 0; i < methods.length; i++) {
                System.out.println("Method " + i + " name is: " + methods[i].getName());
                System.out.println("Method " + i + " return type is: " + methods[i].getReturnType());

                if ((methods[i].getParameterCount()) == 0) {
                    System.out.println("Method " + i + " has no parameters");
                } else {
                    System.out.println("Method " + i + " parameters are:");
                    Parameter[] parameters = methods[i].getParameters();
                    for (int j = 0; j < parameters.length; j++) {
                        System.out.println("\tParameter " + j + " name is: " + parameters[j].getName());
                        System.out.println("\tParameter " + j + " type is: " + parameters[j].getType());
                    }
                }
            }
            System.out.println("Return type of 'create' method is the object represented by thi class. Trying to call it instantiate an object of the Mistery class.");
            Method m = c.getMethod("create");
            Object o = new Object();
            try {
                this.misteryO = m.invoke(o);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
            }
                     
            
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void instantiateClass(Class c) {
        try {
            Constructor constructor = null;
            try {
                constructor = c.getDeclaredConstructor(new Class[0]);
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
            }
            constructor.setAccessible(true);
            Object o = c.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playWithMistery(Class c) {
            try {
                Method m = c.getMethod("setCH", int.class);
                Method m1 = c.getMethod("setW", int.class);
                Method m2 = c.getMethod("setC", int.class);
                try {
                    for (int i=0;i<100;i++) {
                        m.invoke(this.misteryO,50+i);
                        m1.invoke(this.misteryO,250+(i*5));
                        m2.invoke(this.misteryO,-60);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {           
            Logger.getLogger(Discover.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }
}
