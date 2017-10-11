/**
 * Classe: Checksum
 * Projeto: Server_Pi_v2
 * 
 * git: https://github.com/ancelmomatheus15/Server_Pi_v2.git
 * @author Matheus Ancelmo & Rafael Ferretti
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
