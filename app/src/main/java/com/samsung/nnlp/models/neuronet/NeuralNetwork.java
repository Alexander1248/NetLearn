package com.samsung.nnlp.models.neuronet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable {
    private final List<Layer> layers;

    private double learningRate = 0.1;
    private double momentum = 0;

    public NeuralNetwork() {
        layers = new ArrayList<>();
    }
    public NeuralNetwork(AFunction[] layersFunctions, int[] layersSizes) {
        layers = new ArrayList<>();
        initInLayer(layersFunctions[0], layersSizes[1], layersSizes[0]);
        for (int i = 1; i < layers.size(); i++) initHiddenOrOutLayer(layersFunctions[i], layersSizes[i + 1]);
    }

    public void initHiddenOrOutLayer(AFunction function, int size) {
        layers.add(new Layer(layers.get(layers.size() - 1), function, size));
    }
    public void initInLayer(AFunction function, int size, int inputSize){
        layers.add(new Layer(inputSize, function, size));
    }

    public void setInput(int i,double value) {
        layers.get(0).setInput(i,value);
    }
    public double getOutput(int i) {
        return layers.get(layers.size() - 1).getOutput(i);
    }
    public double getOutput(int layer,int i) {
        return layers.get(layer).getOutput(i);
    }

    public void calculateNet() {
        for (int i = 0; i < layers.size(); i++) layers.get(i).calculateLayer();
    }

    public void calculateError(double[] rightResults) {
        layers.get(layers.size() - 1).calculateOutLayerError(rightResults);
        for (int i = layers.size() - 2; i >= 0; i--) layers.get(i).calculateInOrHiddenLayerError(layers.get(i + 1));
    }
    public void calculateNewWeights() {
        for (int i = 1; i < layers.size(); i++) layers.get(i).calculateNewWeights(learningRate, momentum);
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getMomentum() {
        return momentum;
    }
}
