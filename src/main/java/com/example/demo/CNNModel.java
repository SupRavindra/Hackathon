package com.example.demo;


import org.deeplearning4j.datasets.iterator.file.FileDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.Convolution2D;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;

public class CNNModel {

    public static MultiLayerNetwork createModel() {
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .list()
                .layer(0, new Convolution2D.Builder(3, 3)
                        .nIn(1) // Input channels: 1 for grayscale images
                        .nOut(32) // Number of filters
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .build())
                .layer(2, new Convolution2D.Builder(3, 3)
                        .nOut(64)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .build())
                .layer(4, new DenseLayer.Builder().nOut(128).activation(Activation.RELU).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.SPARSE_MCXENT)
                        .nOut(2) // Binary classification: 2 output classes
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutional(128, 128, 1)) // Input shape: 128x128 grayscale images
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(config);
        model.init();

        return model;
    }
    public void trainModel(File file) throws Exception {
        MultiLayerNetwork model = createModel();
        // Train the model using the preprocessed data
        DataSetIterator trainIterator = new FileDataSetIterator(new File("C:\\Users\\Dell\\Desktop\\photos"), 64);
        model.fit(trainIterator);
    }





}
