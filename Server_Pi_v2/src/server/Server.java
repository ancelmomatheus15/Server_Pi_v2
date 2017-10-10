package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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
		String MAC="";
		if(read1 ==false){
			
			try{
				// Cria uma buffer que irá armazenar as informações enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
			
				/* Faz a leitura das informações enviadas pelo cliente as amazenam na variável "cypher"
				* e decifra a informação, exibindo no console*/
				MAC = entrada.readLine();
				int aux=MAC.length();
				MAC = MAC.substring(7, aux);
				read1= true;
            
			}catch(IOException e){
				System.out.println("Servidor- IOException "+e.getMessage());
			}
			
		}
		
		if(Handshake.buscaMac(MAC)==true){
			System.out.println("HANDSHAKE: OK");
			int packCRC;
			String packChecksum;
			
			try{
				// Cria uma buffer que irá armazenar as informações enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
				
	            /* Faz a leitura das informações enviadas pelo cliente as amazenam na variável "cypher"
	             * e decifra a informação, exibindo no console
	            */
				
				String cypher = null;
				while(cypher == null){
					cypher = entrada.readLine();
				}

	            int aux = cypher.length();            
	            packCRC = Integer.parseInt(cypher.substring(7, 11));
	            packChecksum = cypher.substring(12, 28);
	            cypher = cypher.substring(29, aux);
	            
	            //validar se a mensagem foi corrompida
	            if (CRC.calcCRC(cypher) == packCRC){
	            	System.out.println("CRC: OK");
	            	
	            	if(packChecksum == "h"){
	            		System.out.println("CHECKSUM: OK");
	            		
	            		System.out.println("Servidor- Informação original: "+ cypher);	
	            		System.out.println("Servidor- Decrypt: "+ Decrypt.decrypt(cypher, key));
	            	}	            	 
	            }
	            else{
	            	System.out.println("ERRO: Mensagem corrompida");
	            }  	

			}catch(IOException e){
				System.out.println("Servidor- IOException "+e.getMessage());
			}
		}else{
			System.out.println("SERVIDOR - ERRO DE VALIDAÇÂO MAC ADRESS");
		}
		
	}

}