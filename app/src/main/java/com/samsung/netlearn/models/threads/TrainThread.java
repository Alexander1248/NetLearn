package com.samsung.netlearn.models.threads;

import android.annotation.SuppressLint;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import java.io.Serializable;
import java.util.List;

public class TrainThread extends Thread implements Serializable {
    private final MultiLayerPerceptron network;
    private final DataSet dataset;
    private boolean isTrain = true;
    private ProgressBar progressBar;
    private TextView progressText;

    public TrainThread(MultiLayerPerceptron network, DataSet dataset) {
        this.network = network;
        this.dataset = dataset;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void run() {
        long t = System.currentTimeMillis();
        double error;
        BackPropagation rule = network.getLearningRule();

        rule.doOneLearningIteration(dataset);
        double startError = rule.getTotalNetworkError();
        while (isTrain) {
            rule.doOneLearningIteration(dataset);
            error = rule.getTotalNetworkError() / startError * 100;
            if (System.currentTimeMillis() - t >= 100) {
                progressBar.setProgress((int) (error));
                progressText.setText("Error rate: " + String.format("%.2f", error));
                t = System.currentTimeMillis();
            }
            try {
                sleep(1);
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
