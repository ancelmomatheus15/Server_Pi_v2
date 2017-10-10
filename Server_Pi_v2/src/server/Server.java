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
	
	public Server(Socket aux){ 
		aceitar = aux;
	}
	
	public static void main(String args[]){
		
		try{//tentando criar uma conexao
		
			conexao = new ServerSocket(10010);//Cria um SocketServer com porta 11015
			System.out.println("Servidor- OUVINDO PORTA "+conexao.getLocalPort());
			
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

	public void start(){
		
		String key = "1234567891234567";
		boolean read1 = false;
		
		String packMac = "";
		String packCRC = "";
		String packChecksum = "";
		String rawData = "";
		
		if(read1 ==false){
			
			try{
				// Cria uma buffer que irá armazenar as informações enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
			
				/* Faz a leitura das informações enviadas pelo cliente as amazenam na variável "cypher"
				* e decifra a informação, exibindo no console*/
				rawData = entrada.readLine();
				
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
		
		if(Handshake.buscaMac(packMac)==true){
			System.out.println("HANDSHAKE: OK");
			
			//validar se a mensagem foi corrompida
	        if (testeCRC.calcCRCS(rawData).equals(packCRC)){
	            System.out.println("CRC: OK");
	            	
	            if(Checksum.md5(rawData).equals(packChecksum)){
	            	System.out.println("CHECKSUM: OK");
	            		
	            		System.out.println("Servidor- Informação original: "+ rawData);	
	            		System.out.println("Servidor- Decrypt: "+ Decrypt.decrypt(rawData, key));
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