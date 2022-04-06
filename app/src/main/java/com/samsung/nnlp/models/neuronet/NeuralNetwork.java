package com.samsung.nnlp.models.neuronet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable {
    private final List<Layer> layers;

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
    public void calculateNewWeights(double trainSpeed, double momentumCoef) {
        for (int i = 1; i < layers.size(); i++) layers.get(i).calculateNewWeights(trainSpeed,momentumCoef);
    }

    public List<Layer> getLayers() {
        return layers;
    }
}
