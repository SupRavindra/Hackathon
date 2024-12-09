package com.example.demo;

import org.deeplearning4j.datasets.iterator.file.FileDataSetIterator;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
//import org.nd4j.linalg.dataset.iterator.impl.FileDataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.dataset.DataSet;

import java.io.File;
import java.io.IOException;

public class NeuralNetworkTraining {
    private static MultiLayerNetwork model;
     public MultiLayerNetwork createModel ()throws Exception {
        // Parameters
        int height = 128; // Image height
        int width = 128; // Image width
        int channels = 1; // Grayscale images
        int outputNum = 2; // Number of classes (e.g., forgery vs. authentic)
        int batchSize = 64; // Batch size
        int epochs = 10; // Number of training epochs

        // Load preprocessed dataset
        File trainingData = new File("path/to/training/data");
        File testingData = new File("path/to/testing/data");
        DataSetIterator trainIter = new FileDataSetIterator(trainingData, batchSize);
        DataSetIterator testIter = new FileDataSetIterator(testingData, batchSize);


        // Define the neural network architecture
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(123)
                .updater(new Adam(0.001)) // Learning rate
                .l2(1e-4) // L2 regularization
                .list()
                .layer(new ConvolutionLayer.Builder(5, 5) // Kernel size
                        .nIn(channels)
                        .nOut(32)
                        .stride(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nOut(64)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutional(height, width, channels))
                .build();

        model = new MultiLayerNetwork(config);

        model.init();

        // Print the score every iteration
        model.setListeners(new ScoreIterationListener(10));

        // Train the model
        for (int i = 0; i < epochs; i++) {
            model.fit(trainIter);
        }

        // Evaluate the model
        System.out.println("Evaluating the model...");
        Evaluation eval = model.evaluate(testIter);
        System.out.println(eval.stats());

        // Save the model
        File modelFile = new File("trained_model.zip");
        ModelSerializer.writeModel(model, modelFile, true);
        System.out.println("Model saved at: " + modelFile.getAbsolutePath());
        return model;
    }
    public void trainModel(File file) throws Exception {
        MultiLayerNetwork model = createModel();
        // Train the model using the preprocessed data
        DataSetIterator trainIterator = new FileDataSetIterator(file, 64);
        model.fit(trainIterator);
    }

    public static void main(String[] args) throws IOException {
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork("path_to_model.zip");
    }
}
