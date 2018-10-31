package ch.exgBot.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveSystem {

	private static final File FILE_OF_INC = new File("inc.nmb");

	public static long loadActualNumberOfInc() {
		FileReader fr = null;
		BufferedReader br = null;

		try{
			fr = new FileReader(FILE_OF_INC);
			br = new BufferedReader(fr);
			return Long.parseLong(br.readLine());
		}
		catch (Exception e){
			System.out.println(e.toString());
		}finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erreur pendant le chargement");
			}
		}
		return 0;
	}
	
	public static void saveActualNumberOfInc(long inc) {
		FileWriter ffw = null;
		try {
			ffw = new FileWriter(FILE_OF_INC);
			ffw.write(Long.toString(inc));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erreur dans la sauvegarde");
		}finally {
			try {
				ffw.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erreur de fermeture de fichier, sauvegarde");
			}
		}
	}
	
}
