/**
 * Classe: Cliente
 * Projeto: Server_Pi_v2
 * 
 * git: https://github.com/alexandrage/PHP-Java-AES-Encrypt-master
 * @author alexandrage
 * 
 * Projeto de conclusão de curso para Análise e Desenvolvimento de Sistemas
 * FATEC da Zona Leste
 * 
 * Outubro/2017
 * 
 */

package client;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encrypt {
	
	/**
	 * @param String input
	 * @param String key
	 * @return String crypted
	 */
	public static String encrypt(String input, String key){
		  byte[] crypted = null;
		  
		  try{
		    SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
		      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		      cipher.init(Cipher.ENCRYPT_MODE, skey);
		      crypted = cipher.doFinal(input.getBytes());
		    }catch(Exception e){
		    	System.out.println(e.toString());
		    }
		    return new String(Base64.encodeBase64(crypted));
		}
}