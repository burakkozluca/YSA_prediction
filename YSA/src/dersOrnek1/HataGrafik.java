package dersOrnek1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class HataGrafik extends JFrame {

    private XYSeries errorSeries;
    private XYSeries epochSeries;

    public HataGrafik(String title) {
        super(title);
        errorSeries = new XYSeries("Hata");
        epochSeries = new XYSeries("Epoch");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(errorSeries);
        dataset.addSeries(epochSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Eğitim Hata ve Epoch Grafiği",
                "Epoch",
                "Hata",
                dataset
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        // X eksenindeki değerlerin formatını belirleme
        xAxis.setNumberFormatOverride(java.text.NumberFormat.getIntegerInstance());

        // Y eksenindeki değerlerin formatını belirleme
        yAxis.setNumberFormatOverride(java.text.NumberFormat.getNumberInstance());

        // Hataları etiketleme stratejisi
        XYItemLabelGenerator labelGenerator = new StandardXYItemLabelGenerator("{2}", java.text.NumberFormat.getNumberInstance(), java.text.NumberFormat.getNumberInstance());
        plot.getRenderer().setBaseItemLabelGenerator(labelGenerator);
        plot.getRenderer().setBaseItemLabelsVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        getContentPane().add(chartPanel);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addErrorData(int epoch, double error) {
        errorSeries.add(epoch, error);
        epochSeries.add(epoch, 10); // Eğitim (Epoch) verisi ekleniyor, hata değeri 0 kabul edildi
    }

    public void showChart() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }
}
