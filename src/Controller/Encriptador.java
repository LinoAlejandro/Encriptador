
package Controller;

/**
 *
 * @author Limbo
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import Models.ListaCircular;
import Models.Nodo;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Encriptador {
	
	private ListaCircular listaLlave;
	private Nodo nodoLlave;
	
	public Encriptador(){
	} 
	
	public String encriptar(String llave, String mensaje) {
		this.listaLlave = new ListaCircular<Character>();
		this.poblarListaLlave(llave);
		this.inicializarNodoParaElRecorrido();
		String mensajeEncriptado = "";
		for(int c = 0; c < mensaje.length(); c++){
			mensajeEncriptado = mensajeEncriptado + this.obtenerDatoEncriptado(mensaje.charAt(c));
		}
		listaLlave = new ListaCircular<Character>();
		return mensajeEncriptado;
	}
	
	public void poblarListaLlave (String llave) {
		for(int c = 0; c < llave.length(); c++){
			listaLlave.insertNodeAntiClockwise(llave.charAt(c));
		}
	}
	
	public void poblarListaLlaveByte (String llave) {
		for(int c = 0; c < llave.length(); c++){
			listaLlave.insertNodeAntiClockwise((int)llave.charAt(c));
		}
	}
	
	public void inicializarNodoParaElRecorrido(){
		nodoLlave = listaLlave.getInicio();
	}
	
	public String obtenerDatoEncriptado(char caracter){
		caracter = (char)((int)caracter + (int)nodoLlave.getDato());
		String dato = Character.toString(caracter);
		nodoLlave = nodoLlave.getSiguiente();
		return dato;
	}
	
	public String desencriptar(String llave, String mensaje){
		this.listaLlave = new ListaCircular<Character>();
		this.poblarListaLlave(llave);
		this.inicializarNodoParaElRecorrido();
		String mensajeDesencriptado = "";
		for(int c = 0; c < mensaje.length(); c++){
			mensajeDesencriptado = mensajeDesencriptado + this.obtenerDatoDesencriptador(mensaje.charAt(c));
		}
		listaLlave = new ListaCircular<Character>();
		return mensajeDesencriptado;
	}
	
	public String obtenerDatoDesencriptador(char caracter){
		caracter = (char)((int)caracter - (int)nodoLlave.getDato());
		String dato = Character.toString(caracter);
		nodoLlave = nodoLlave.getSiguiente();
		return dato;
	}
	
	public void encriptarArchivo(String llave, Path path, JProgressBar progressBar){
		this.listaLlave = new ListaCircular<Byte>();
		this.poblarListaLlaveByte(llave);
		this.inicializarNodoParaElRecorrido();
		byte[] fileBytes = generateBytesFromFile(path);
		this.generateEncryptedBytes(fileBytes, progressBar);
		this.generateFile(path, fileBytes);
		this.listaLlave = new ListaCircular<Character>();
	}
	
	public void desencriptarArchivo(String llave, Path path, JProgressBar progressBar){
		this.listaLlave = new ListaCircular<Byte>();
		this.poblarListaLlaveByte(llave);
		this.inicializarNodoParaElRecorrido();
		byte[] fileBytes = generateBytesFromFile(path);
		this.generateDecryptedBytes(fileBytes, progressBar);
		this.generateFile(path, fileBytes);
		this.listaLlave = new ListaCircular<Character>();
	}
	
	public byte[] generateBytesFromFile(Path path){
		byte[] fileBytes = null;
		try {
			fileBytes =  Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileBytes;
	}
	
	private void generateEncryptedBytes(final byte[] fileBytes, final JProgressBar progressBar){
            Thread hilo = new Thread(){
                public void run(){
                    for(int c = 0; c < fileBytes.length; c++){
                        progressBar.setValue(((c+1)*100)/fileBytes.length);
                        int suma = (int)(fileBytes[c] + (int)nodoLlave.getDato()); 
                        fileBytes[c] = (byte) suma;
                    }
                    JOptionPane.showMessageDialog(null, "Proceso terminado, archivo sustituido");
                }
            };
            hilo.start();
            
	}
	
	private void generateDecryptedBytes(final byte[] fileBytes, final JProgressBar progressBar){
            Thread hilo = new Thread(){
                public void run(){
                    for(int c = 0; c < fileBytes.length; c++){
                        progressBar.setValue(((c+1)*100)/fileBytes.length);
                        int suma = (int)(fileBytes[c] - (int)nodoLlave.getDato()); 
                        fileBytes[c] = (byte) suma;
                    }
                    JOptionPane.showMessageDialog(null, "Proceso terminado, archivo sustituido");
                }
            };
            hilo.start();
	}
	
	private void generateFile(Path path, byte[] fileBytes){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path.toString());
			fos.write(fileBytes);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
