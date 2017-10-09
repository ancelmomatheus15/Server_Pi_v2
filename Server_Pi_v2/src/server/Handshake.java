package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Handshake {
	static boolean buscaMac(String buscaMacs) {
		List<String> macs = new ArrayList<String>();
		
		macs = leTxt();
		
		for (String string : macs) {
			if(string.equals(buscaMacs)) {
				return true;
			}
			else {
				System.out.println("MAC ACEITO");
				return false;
			}
		}
		
		return false;
	}
	
	
private static List leTxt() {
		
		File arquivo = new File("C:\\Users\\Matheus Ancelmo\\Desktop\\regMac.txt");
		List<String> enderecos = new ArrayList<String>();		
		
		try {
			FileReader leitorArquivo = new FileReader(arquivo); 
			BufferedReader leitor = new BufferedReader(leitorArquivo);
			String texto = null;
		
			while((texto = leitor.readLine()) != null){
				enderecos.add(texto);
			}
		
			leitorArquivo.close();
			leitor.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("ERRO! Arquivo não encontrado");
		}		
		catch(IOException e) {
			System.out.println("ERRO! Erro de leitura");
		}
		return enderecos;	
	}	

}
