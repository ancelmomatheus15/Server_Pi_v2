package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



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
				// Cria uma buffer que ir� armazenar as informa��es enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
			
				/* Faz a leitura das informa��es enviadas pelo cliente as amazenam na vari�vel "cypher"
				* e decifra a informa��o, exibindo no console*/
				MAC = entrada.readLine();
				int aux=MAC.length();
				MAC = MAC.substring(7, aux);
				read1= true;
				System.out.println("MAC ACEITO");
            
			}catch(IOException e){
				System.out.println("Servidor- IOException "+e.getMessage());
			}
			
		}
		
		if(Handshake.buscaMac(MAC)==true){
			try{
				// Cria uma buffer que ir� armazenar as informa��es enviadas pelo cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
				
	            /* Faz a leitura das informa��es enviadas pelo cliente as amazenam na vari�vel "cypher"
	             * e decifra a informa��o, exibindo no console
	            */
	            String cypher = entrada.readLine();

	            int aux = cypher.length();
	            cypher = cypher.substring(7, aux);
	            System.out.println("Servidor- Informa��o original: "+ cypher);	
	            System.out.println("Servidor- Decrypt: "+ Decrypt.decrypt(cypher, key)); 	
	            	
	            	
	  
	  
	            
			}catch(IOException e){
				System.out.println("Servidor- IOException "+e.getMessage());
			}
		}else{
			System.out.println("SERVIDOR - ERRO DE VALIDA��O MAC ADRESS");
		}
		
	}

}