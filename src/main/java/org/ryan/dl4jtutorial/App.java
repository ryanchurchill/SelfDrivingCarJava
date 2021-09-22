package org.ryan.dl4jtutorial;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
//import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.lossfunctions.LossFunctions;

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

        // set up recordReader
        try (RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(
                    new FileSplit(
                            new File(getClass().getResource("iris.txt").toURI())));

            // read the file into allData, shuffle
            DataSetIterator iterator = new RecordReaderDataSetIterator(
                    recordReader, 150, 4, 3);
            DataSet allData = iterator.next();
            allData.shuffle();

            // normalize
            DataNormalization normalizer = new NormalizerStandardize();
            normalizer.fit(allData);
            normalizer.transform(allData);

            // split into Test and Train
            SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
            DataSet trainingData = testAndTrain.getTrain();
            DataSet testData = testAndTrain.getTest();

            // neural network config
            MultiLayerConfiguration configuration
                    = new NeuralNetConfiguration.Builder()
                    .iterations(1000)
                    .activation(Activation.TANH)
                    .weightInit(WeightInit.XAVIER)
                    .learningRate(0.1)
                    .regularization(true).l2(0.0001)
                    .list()
                    .layer(0, new DenseLayer.Builder().nIn(4).nOut(3).build())
                    .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
                    .layer(2, new OutputLayer.Builder(
                            LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                            .activation(Activation.SOFTMAX)
                            .nIn(3).nOut(3).build())
                    .backprop(true).pretrain(false)
                    .build();

            // create and train the network
            System.out.println("Training beginning...");
            MultiLayerNetwork model = new MultiLayerNetwork(configuration);
            model.init();
            model.fit(trainingData);
            System.out.println("Training done");

            // test the network
            INDArray output = model.output(testData.getFeatureMatrix());
            Evaluation eval = new Evaluation(3);
            eval.eval(testData.getLabels(), output);

            System.out.println(eval.stats());
        }
        catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.toString());
            }
        }
}
