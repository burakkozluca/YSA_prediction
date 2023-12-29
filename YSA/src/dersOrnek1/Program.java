package dersOrnek1;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {
		YSA ysa = null;
		ysa = new YSA(20,0.4,0.1,0.0001,10000);
		HataGrafik hataGrafik = new HataGrafik("Eğitim Hata Grafiği");
        hataGrafik.showChart();
		ysa.egit();
		double egitimHata = ysa.egitimHata();
		hataGrafik.addErrorData(10000, egitimHata);
		System.out.println("Egitim Hata: " + ysa.egitimHata());
		System.out.println("Test Hata: " + ysa.test());
	}

}
