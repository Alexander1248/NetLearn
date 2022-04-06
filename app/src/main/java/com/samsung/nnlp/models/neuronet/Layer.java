package com.samsung.nnlp.models.neuronet;

import java.io.Serializable;

public class Layer implements Serializable {
    private Layer prevLayer;
    private double[] input;
    private Neuron[] neurons;
    boolean firstLayer;

    public Layer(Layer prevLayer, AFunction function, int size) {
        firstLayer = false;
        this.prevLayer = prevLayer;
        neurons = new Neuron[size];
        for (int i = 0; i < size; i++) neurons[i] = new Neuron(function, prevLayer.neurons.length);
    }
    public Layer(int inputSize,AFunction function, int size) {
        firstLayer = true;
        input = new double[inputSize];
        neurons = new Neuron[size];
        for (int i = 0; i < size; i++) neurons[i] = new Neuron(function, inputSize);
    }

    public void setInput(int i, double input) {
        if (firstLayer && i >= 0 && i < this.input.length) this.input[i] = input;
    }

    public double getOutput(int i) {
        if (i >= 0 && i < this.neurons.length) return neurons[i].getOutput();
        return Double.MIN_EXPONENT;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public Layer getPrevLayer() {
       if(!firstLayer) return prevLayer;
       else return null;
    }


    public  void calculateLayer() {
        if (firstLayer) {
            for (int i = 0; i < neurons.length; i++) {
                for (int j = 0; j < input.length; j++) {
                    neurons[i].setInput(j, input[j]);
                }
                neurons[i].calculateNeuron();
            }
        } else {
            for (int i = 0; i < neurons.length; i++) {
                for (int j = 0; j < prevLayer.neurons.length; j++) {
                    neurons[i].setInput(j, prevLayer.neurons[j].getOutput());
                }
                neurons[i].calculateNeuron();
            }
        }
    }

    public void calculateOutLayerError(double[] rightResults) {
        for (int i = 0; i < neurons.length; i++) neurons[i].setError((rightResults[i] - neurons[i].getOutput()) * ActivationFunction.GetDerivative(neurons[i].getFunction(),neurons[i].getWeightedSum()));
    }
    public void calculateInOrHiddenLayerError(Layer postLayer) {
        for (int i = 0; i < neurons.length; i++) {
            double error = 0;
            for (int j = 0; j < postLayer.neurons.length; j++) {
                error += postLayer.neurons[j].weights[i] * postLayer.neurons[j].getError();
            }
            neurons[i].setError(error * ActivationFunction.GetDerivative(neurons[i].getFunction(),neurons[i].getWeightedSum()));
        }
    }

    public void calculateNewWeights(double trainSpeed, double momentumCoef) {
        if (firstLayer) {
            for (int i = 0; i < neurons.length; i++) {
                for (int j = 0; j < prevLayer.neurons.length; j++) {
                    neurons[i].acceleration[j] *= momentumCoef;
                    neurons[i].acceleration[j] += (1 - momentumCoef) * neurons[i].getError() * input[j] * trainSpeed;
                    neurons[i].weights[j] += neurons[i].acceleration[j];
                }
                neurons[i].biasWeight += neurons[i].getError() * trainSpeed;
            }
        }
        else {
            for (int i = 0; i < neurons.length; i++) {
                for (int j = 0; j < prevLayer.neurons.length; j++) {
                    neurons[i].acceleration[j] *= momentumCoef;
                    neurons[i].acceleration[j] += (1 - momentumCoef) * neurons[i].getError() * prevLayer.neurons[j].getOutput() * trainSpeed;
                    neurons[i].weights[j] += neurons[i].acceleration[j];
                }
                neurons[i].biasWeight += neurons[i].getError() * trainSpeed;
            }
        }
    }
    public double[] getInput() {
        return input;
    }

    public boolean isFirstLayer() {
        return firstLayer;
    }
}
