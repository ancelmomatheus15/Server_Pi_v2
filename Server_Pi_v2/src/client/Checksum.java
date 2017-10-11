/**
 * Classe: Checksum
 * Projeto: Server_Pi_v2
 * 
 * https://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java
 * @author Bill Cruise
 * 
 * Projeto de conclusão de curso para Análise e Desenvolvimento de Sistemas
 * FATEC da Zona Leste
 * 
 * Outubro/2017
 * 
 */

package client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum {
	
	/**
	 * @param String senha
	 * @return String tratamento
	 */
	public static String md5(String senha){
		String tratamento = "";
		MessageDigest md = null;
		
		//realiza o digest do parametro
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		tratamento = hash.toString(16);			
		return tratamento;
	}
}
