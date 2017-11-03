/**
 * Classe: Server
 * Projeto: Server_Pi_v2
 * 
 * git: https://github.com/ancelmomatheus15/Server_Pi_v2.git
 * @author Matheus Ancelmo & Rafael Ferretti
 * 
 * Projeto de conclus�o de curso para An�lise e Desenvolvimento de Sistemas
 * FATEC da Zona Leste
 * 
 * Outubro/2017
 * 
 */

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import client.CRC;



public class Server extends Thread{
	
	static ServerSocket conexao = null;
	static Socket aceitar = null;
	
	/**
	 * @param Socket aux
	 */
	public Server(Socket aux){ 
		aceitar = aux;
	}
	
	/**
	 * @param String args[]
	 */
	public static void main(String args[]){
		
		//tentando criar uma conexao
		try{
			//Cria um SocketServer com porta 10010
			conexao = new ServerSocket(10010);
			System.out.println("Servidor- OUVINDO PORTA "+conexao.getLocalPort());
			
			//cria um loop para esperar conex�es e criar threads individuais para cada uma 
			while(true){
				
				aceitar = conexao.accept();	
				System.out.println("Servidor- Cliente Conectado");
				//cria uma thread que envia a conexao
				Thread t = new Server(aceitar);
				//inicia a thread t
				t.start();
				 
			}
		}catch(IOException e){
			System.out.println("Servidor- IOException "+e.getMessage());
		}
	}
	
	/**
	 * M�todo para tratar cada thread individualmente
	 * 
	 * executa as checagens de seguran�a individualmente at� decifrar a informa��o
	 * 1-Handshake
	 * 2-CRC
	 * 3-Checksum
	 * 4-Decrypt
	 */
	public void start(){
		
		String key = "1234567891234567";
		boolean read1 = false;
		
		String packMac = "";
		String packCRC = "";
		String packChecksum = "";
		String rawData = "";
		
		//Valida se a thread ja esta conectada
		if(read1 ==false){
			
			try{
				// Cria uma buffer que ir� armazenar as informa��es enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
			
				/* Faz a leitura das informa��es enviadas pelo cliente as amazenam na vari�vel "cypher"
				* e decifra a informa��o, exibindo no console*/
				rawData = entrada.readLine();
				
				//separa as informa��es recebidas em partes para cada verifica��o individual
				int aux = rawData.length();
	            packMac = rawData.substring(7,24);
	            packCRC = rawData.substring(24, 28);
	            packChecksum = rawData.substring(28, 60);
	            rawData = rawData.substring(60, aux);
	            
				read1= true;
            
			}catch(IOException e){
				System.out.println("Servidor- IOException "+e.getMessage());
			}
			
		}
		
		//valida se o dispositivo tem autoriza��o
		if(Handshake.buscaMac(packMac)==true){
			System.out.println(Thread.currentThread().getId() + "- HANDSHAKE: OK");
			
			//validar se a mensagem foi corrompida
	        if (testeCRC.calcCRCS(rawData).equals(packCRC)){
	            System.out.println(Thread.currentThread().getId() + "- CRC: OK");
	            	
	            //valida o checksum da mensagem
	            if(Checksum.md5(rawData).equals(packChecksum)){
	            	System.out.println(Thread.currentThread().getId() + "- CHECKSUM: OK");
	            		
	            		//se todas as checagens ocorrerem com sucesso, a mensagem � decifrada e exibida
	            		System.out.println(Thread.currentThread().getId() + "- Servidor- Informa��o original: "+ rawData);	
	            		System.out.println(Thread.currentThread().getId() + "- Servidor- Decrypt: "+ Decrypt.decrypt(rawData, key));
	          	}else{
	          		System.out.println("ERRO! falha no Checksum");
	          	}
		
	        }else{
	        	System.out.println("ERRO! falha no CRC");
	        }

       }else{
	        System.out.println("ERRO! falha no handshake");
       }
	}
}