package dersOrnek1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

public class YSA {
	private static final File egitimDosya = new File(YSA.class.getResource("Begitim.txt").getPath());
	private static final File testDosya = new File(YSA.class.getResource("Btest.txt").getPath());
	
	private double[] minimumlar;
	private double[] maksimumlar;
	//this.araKatmanNoronSayisi = araKatmanNoronSayisi();
	
	private int araKatmanNoronSayisi;
	private MomentumBackpropagation mbp;
	private DataSet egitimVeriSeti;
	private DataSet testVeriSeti;
	
	
	public YSA(int araKatmanNoronSayisi, double momentum, double ogrenmeKatsayisi, double maxHata, int epoch) throws FileNotFoundException {
		
		minimumlar = new double[4];
		maksimumlar = new double[4];
		
		//double en büyük değeri mine ata, double en küçük değeri maxa ata ve karşılaştır sayıları
		
		for(int i = 0; i<3; i++) {
			minimumlar[i] = Double.MAX_VALUE; //Double sınıfını kullanıyoruz
			maksimumlar[i] = Double.MIN_VALUE;
		}
		MinimumveMaksimumlar(egitimDosya);
		MinimumveMaksimumlar(testDosya);
		egitimVeriSeti = veriSetiOku(egitimDosya);
		testVeriSeti = veriSetiOku(testDosya);
		
		mbp = new MomentumBackpropagation();
		mbp.setMomentum(momentum);
		mbp.setLearningRate(ogrenmeKatsayisi);
		mbp.setMaxError(maxHata);
		mbp.setMaxIterations(epoch);
	}
	public void egit() {
		MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,3,araKatmanNoronSayisi,1);
		sinirselAg.setLearningRule(mbp);
		sinirselAg.learn(egitimVeriSeti);
		sinirselAg.save("ysa.nnet");
		System.out.println("Egitim tamamlandı. ");
	}
	public double test() {
		NeuralNetwork sinirselAg = NeuralNetwork.createFromFile("ysa.nnet");
		double toplamHata = 0;
		var satirlar = testVeriSeti.getRows();
		for(DataSetRow satir : satirlar)
		{
			sinirselAg.setInput(satir.getInput());
			sinirselAg.calculate();//ileri besleme
			toplamHata += mse(satir.getDesiredOutput(),sinirselAg.getOutput());
			
		}
		return toplamHata/testVeriSeti.size();
	}
	public String birVeriTest(double[] inputs) { //minmax yerleşmemiş hali gelir dışarıdan
		double[] minMaxInputs = new double[3];
		for(int i = 0; i<3;i++)
		{
			minMaxInputs[i] = minmaxDeger(inputs[i],minimumlar[i],maksimumlar[i]);
		}
		NeuralNetwork sinirselAg = NeuralNetwork.createFromFile("ysa.nnet");
		sinirselAg.setInput(minMaxInputs);
		sinirselAg.calculate();
		return gercekCikti(sinirselAg.getOutput());
		
	}
	private String gercekCikti(double[] outputs)
	{
		double max = Double.MIN_VALUE;
		int index = 0;
		for(int i = 0;i<1;i++)
		{
			if(outputs[i]>max) {
				max = outputs[i];
				index = i;
			}
		}
		switch(index) {
		case 0:
			return "Kötü";
		case 1:
			return "Normal";
		case 2:
			return "İyi";
			default:
				return "Tanımlanmadı";
			
		}
		
	}
	public double egitimHata() {
		return mbp.getTotalNetworkError();
		
	}
	private double mse(double[] beklenen, double[] uretilen) {
		double birVeridekiHata = 0;
		for(int i = 0; i<beklenen.length; i++)
		{
			birVeridekiHata += Math.pow(beklenen[i] - uretilen[i],2);
		}
		return birVeridekiHata/beklenen.length;
	}
	private void MinimumveMaksimumlar(File dosya) throws FileNotFoundException {
		Scanner in = new Scanner(dosya);
		DataSet ds = new DataSet(3, 1); //3 input 1 output
		while(in.hasNextDouble())
		{
			for(int i = 0; i<3;i++) {
				double d = in.nextDouble();
				if(d > maksimumlar[i]) maksimumlar[i] = d;
				if(d < minimumlar[i]) minimumlar[i] = d;
			}
			
			for(int i = 0; i < 1 ; i++) {
				in.nextDouble(); 
			}	
		}
		in.close();
	}
	private DataSet veriSetiOku(File dosya) throws FileNotFoundException {
		Scanner in = new Scanner(dosya);
		DataSet ds = new DataSet(3, 1); //3 input 1 output
		while(in.hasNextDouble())
		{
	
			double[] inputs = new double[3];
			for(int i = 0; i<3;i++) {
				double d = in.nextDouble();
				inputs[i] = minmaxDeger(d, minimumlar[i],maksimumlar[i]);
			}
			double[] outputs = new double[1];
			for(int i = 0; i < 1 ; i++) {
				double f = in.nextDouble(); 
				outputs[i] = minmaxDeger(f, minimumlar[i], maksimumlar[i]);
			}
			DataSetRow satir = new DataSetRow(inputs,outputs);
			ds.add(satir);
		}
		in.close();
		return ds;
	}
	
	private double minmaxDeger(double d, double min, double max) {
		return(d-min)/(max-min);
	}
	
}
