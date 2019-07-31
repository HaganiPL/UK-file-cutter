//Here you can find the final version of program in which I cut input files 
//regarding to data.txt


import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {


	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame("UK STORES");

		File[] wybranePliki_tablica = null;

		JFileChooser fc = new JFileChooser();

		fc.setMultiSelectionEnabled(true);
		fc.setFileFilter(new FileNameExtensionFilter("Choose just .csv", "csv"));
		fc.getCurrentDirectory();

		int wybranePliki = fc.showOpenDialog(null);

		if (wybranePliki == JFileChooser.APPROVE_OPTION)
			wybranePliki_tablica = fc.getSelectedFiles();
			
		for(int i = 0; i < wybranePliki_tablica.length; i++)
			zaladujWartosci(wybranePliki_tablica[i]);
		
		Main.DisplayImage();
		System.out.println("Press button to leave");
		System.in.read();
	}

	public static void zaladujWartosci(File file) throws IOException {

		String[][] aa = null;
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			String[] linee = null;
			int i = 0;
			int q = 0;

			int ilosclinii = 0;
			int tmp = 0;
			String ilosc_wartosci;

			String[] iloscwierszy;

			while ((ilosc_wartosci = br.readLine()) != null) {
				if(ilosclinii == 0)
					tmp = ilosc_wartosci.split(";").length;

				ilosclinii++;
			}

			String[][] lineee = new String[ilosclinii][tmp];
			
			br.close();

			while((line = bf.readLine()) != null){
				linee = line.split(";");

				for(int w = 0; w < linee.length; w++){
					linee[w] = linee[w];
				}

				lineee[q] = linee;
				q++;
			}

			System.out.println("\n\n\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
			System.out.println("Choosed file: " + file.getName() + "\n");

			System.out.println("\nLines in file: ");
			for(int k = 0; k < lineee.length; k++){
				for(int h = 0; h < tmp; h++)
					System.out.print(lineee[k][h] + "  ");

				System.out.println("  ");
			}
			System.out.println(" ");

			BufferedReader dane = new BufferedReader(new FileReader("dane.txt"));

			String tmp2;
			String[] nazwyPlikow;
			int[] kolumnyDoUsuniecia = null;

			while((tmp2 = dane.readLine()) != null){

				nazwyPlikow = tmp2.split(",");

				if(file.getName().contains(nazwyPlikow[0])){
					kolumnyDoUsuniecia = OdczytajLiczbyKolumn(nazwyPlikow);
					break;
				}
			}

			int ostatnirekord = 0;
			int pop = 0;
			for(int w = 0; w < linee.length; w++){

				if((linee.length - pop) == kolumnyDoUsuniecia[kolumnyDoUsuniecia.length - (pop + 2)]){

					ostatnirekord = kolumnyDoUsuniecia[kolumnyDoUsuniecia.length - (pop + 2)];
					pop++;
				}else{
					break;
				}

			}

			System.out.println("-----------------------------------------------------------------------------" );
			System.out.print("\nColumns to delete: " );

			for(int is : kolumnyDoUsuniecia){
				System.out.print(is + ",");
			}
			
			System.out.println(" ");

			PrintWriter pw = new PrintWriter(file.getName().replace(".csv", "") + "_" + java.time.LocalDate.now() +  ".txt");

			int licznikKolumndoUsuniecia = 0;
			int temporary = 0;

			for(int l = 0; l < ilosclinii; l++){
				for(int p = 0; p < tmp; p++){

					if(p == (kolumnyDoUsuniecia[licznikKolumndoUsuniecia] - 1)){

						licznikKolumndoUsuniecia++;
						temporary++;
					}else{
						
					pw.print(lineee[l][p].replaceAll("\"", ""));
					

					if(p != tmp-1 && p != ostatnirekord - 2)
						pw.print("\t");
					
					temporary++;
					}

				}
				licznikKolumndoUsuniecia = 0;

				if(l != ilosclinii-1)
					pw.println("");
					
			}
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int[] OdczytajLiczbyKolumn(String[] nazwyPlikow){

		int i = 0;
		int tmp = 0;
		int[] values = new int[nazwyPlikow.length];

		for(String s : nazwyPlikow){
			
			if(tmp == 0){
				tmp++;
			}else{
				values[i] = Integer.parseInt(s);
				i++;
			}
		}
		return values;
	}

	public static void DisplayImage() throws IOException
    {
        BufferedImage img=ImageIO.read(new File("Columns_Sirval.jpg"));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
		frame.add(lbl);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
