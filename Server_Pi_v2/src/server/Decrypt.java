/**
 * Classe: Decrypt
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

package server;

import javax.crypto.Cipher; 
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Decrypt {
	
	/**
	 * @param String input
	 * @param String key
	 * @return String output
	 */
	
	public static String decrypt(String input, String key){
	    byte[] output = null;
	    
	    //processo que utiliza as bibliotecas do java para decifrar a mensagem
	    try{
	      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      output = cipher.doFinal(Base64.decodeBase64(input));
	    }catch(Exception e){
	      System.out.println(e.toString());
	    }
	    return new String(output);
	}

}
