package org.ryan.dl4jtutorial;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run()
    {
        System.out.println("Hello World");

        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(
                    new FileSplit(
                            new File(getClass().getResource("iris.txt").toURI())));

            DataSetIterator iterator = new RecordReaderDataSetIterator(
                    recordReader, 150, 4, 3);
            DataSet allData = iterator.next();
            allData.shuffle();
        }
        catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.toString());
            }
        }
}
