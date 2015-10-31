/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

/**
 *
 * @author Daniele Santoro <daniele.santoro@studenti.unitn.it>
 */
public class Assignment5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String jarPath = "Black.jar";
        if (args.length == 1) {
            jarPath = args[0];
        }
        Discover  d = new Discover();        
        d.discoveryLoop(jarPath);
    }
    
}
