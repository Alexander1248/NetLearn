package com.samsung.nnlp.models.netview;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.samsung.nnlp.models.neuronet.NeuralNetwork;

public class NetView extends SurfaceView implements SurfaceHolder.Callback {

    private NeuralNetwork net;
    private DrawThread thread;

    public NetView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new DrawThread(getContext(), holder, net);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        holder.setFormat(format);
        holder.setFixedSize(width, height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        thread.requestStop();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setNeuralNetwork(NeuralNetwork net) {
        this.net = net;
    }
}
