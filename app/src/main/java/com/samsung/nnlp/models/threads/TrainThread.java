package com.samsung.nnlp.models.threads;

import android.annotation.SuppressLint;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samsung.nnlp.models.neuronet.NeuralNetwork;

import java.io.Serializable;
import java.util.List;

public class TrainThread extends Thread implements Serializable {
    private final NeuralNetwork network;
    private final List<double[]> in;
    private final List<double[]> out;
    private boolean isTrain = true;
    private ProgressBar progressBar;
    private TextView progressText;

    public TrainThread(NeuralNetwork network, List<double[]> in, List<double[]> out) {
        this.network = network;
        this.in = in;
        this.out = out;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void run() {
        double error;
        while (isTrain) {
            error = 0;
            for (int i = 0; i < 100; i++) {
                int j = (int) (Math.random() * in.size());
                network.setInput(in.get(j));
                network.calculateNet();
                error += network.calculateError(out.get(j));
                network.calculateNewWeights();
            }

            progressBar.setProgress((int) (error));
            progressText.setText("Error rate: " + String.format("%.2f",error));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTraining() {
        isTrain = false;
    }

    public void setProgressBar(ProgressBar progressBar, TextView progressText) {

        this.progressBar = progressBar;
        progressBar.setMax(100);
        this.progressText = progressText;
    }
}
