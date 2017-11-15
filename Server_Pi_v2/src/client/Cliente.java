/**
 * Classe: Cliente
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Encrypt;

public class Cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String key = "1234567891234567";
					
			while(true){
				try{
					//Cria o socket com o nome do servidor e a porta
					Socket client = new Socket("raspberrypi", 10010);	
					System.out.println("Cliente- CONECTADO");
			
					//Pega IP e MAC
					String mac = HandshakeClient.HandShake();
								
					//Captura input do teclado
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Insira o texto a ser enviado: ");
					String text = in.readLine();
				
					//Encripta a leitura
					text = Encrypt.encrypt(text, key);
			
					//Calculo do CRC
					String CRCvalue = CRC.calcCRC(text);
				
					//Calculo do Checksum
					String checksumValue = Checksum.md5(text);
				
					//adicionando parametros de vericação ao String principal de texto
					String aux = text;
					text = mac.concat(CRCvalue); //MAC + CRC
					text = text.concat(checksumValue); //MAC + CRC + CHECKSUM
					text = text.concat(aux); //MAC + CRC + CHECKSUM + INFO		
				
					//Envia para o servidor
					ObjectOutputStream envio = new ObjectOutputStream(client.getOutputStream());
					envio.writeObject(text);
					envio.flush();
			
					//Encerra o processo
					envio.close();
					System.out.println("Cliente- Conexão encerrada");
				}
				catch(Exception e){
					e.printStackTrace();			
				}
		}
		
	}
}

