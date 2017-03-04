/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.nio.file.Path;
import javax.swing.JProgressBar;

/**
 *
 * @author Limbo
 */
public class Controller {
    Encriptador encriptador;
    
    public Controller(){
        encriptador = new Encriptador();
    }
    
    public void encriptarArchivo(String llave, File file, JProgressBar progressBar){
        Path path = file.toPath();
        encriptador.encriptarArchivo(llave, path, progressBar);
    }
    
    public void desencriptarArchivo(String llave, File file, JProgressBar progressBar){
        Path path = file.toPath();
        encriptador.desencriptarArchivo(llave, path, progressBar);
    }
}
