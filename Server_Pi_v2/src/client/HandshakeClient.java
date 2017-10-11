/**
 * Classe: HandshakeClient
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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class HandshakeClient {
	
	/** 
	 * @return String macAdress
	 */
	
	public static String HandShake(){
		
		// ip necessario para verificar ip e mac
		InetAddress ip;
		
		//tentará alcançar o ip e o mac da maquina
		try {
			//pega o ip e mostra no console
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			
			//cria a interface de rede para conexão com a maquina a partir do ip adquirido
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			
			//pega o endereço mac byte por byte
			byte[] mac = network.getHardwareAddress();
			
			//formata o mac para o padrão de escrita
			System.out.print("Current MAC address : ");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			
			String macAdress = sb.toString();
			System.out.println(macAdress);
			return macAdress;

		} catch (UnknownHostException e) {

			e.printStackTrace();

		}
		catch (SocketException e){

			e.printStackTrace();
		}
		return "";		
	}
}