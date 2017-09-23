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
		
		try{
			// Cria uma buffer que irá armazenar as informações enviadas pelo cliente
			BufferedReader entrada = new BufferedReader(new InputStreamReader(aceitar.getInputStream()));
			
            /* Faz a leitura das informações enviadas pelo cliente as amazenam na variável "cypher"
             * e decifra a informação, exibindo no console
            */
            String cypher = entrada.readLine();

            int aux = cypher.length();
            cypher = cypher.substring(7, aux);
            System.out.println("Servidor- Informação original: "+ cypher);	
          //  System.out.println("Servidor- Decrypt: "+ Decrypt.decrypt(cypher, key)); 	
            	
            	
            	
//            	// Cria uma stream de sáida para retorno das informações ao cliente
//            	ObjectOutputStream saida = new ObjectOutputStream(aceitar.getOutputStream());
//            	((ObjectOutput) aceitar).flush();
//            	saida.writeObject("transmissão ok");
  //
            
		}catch(IOException e){
			System.out.println("Servidor- IOException "+e.getMessage());
		}
	}

}