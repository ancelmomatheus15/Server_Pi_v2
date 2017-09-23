package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.Encrypt;

public class Cliente {

	public static void main(String[] args) {
		
		String key = "1234567891234567";
		
		try{
			while(true){
				//Cria o socket com o nome do servidor e a porta
				Socket client = new Socket("Ferretti-PC", 10010);	
				System.out.println("Cliente- CONECTADO");
				
				//Captura input do teclado
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Insira o texto a ser enviado: ");
				String text = in.readLine();
			
				//Encripta a leitura
				text = Encrypt.encrypt(text, key);
				System.out.println("Cliente- Cypher: "+text);
			
				//Envia para o servidor
				ObjectOutputStream envio = new ObjectOutputStream(client.getOutputStream());
				envio.writeObject(text);
			
				//Encerra o processo
				envio.close();
				System.out.println("Cliente- Conexão encerrada");
			}
		}
		catch(Exception e){
			System.out.println("Cliente: ERRO!" +e.getMessage());			
		}
	}
}

