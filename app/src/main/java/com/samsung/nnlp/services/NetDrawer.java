package com.samsung.nnlp.services;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.samsung.nnlp.R;
import org.neuroph.nnet.MultiLayerPerceptron;

public class NetDrawer {
    private final float hDst;
    private final float vDst;
    private final float radius;

    private final Paint neuron = new Paint();
    private final Paint link = new Paint();

    public NetDrawer(Context context, float hDst, float vDst, float radius) {
        this.hDst = hDst;
        this.vDst = vDst;
        this.radius = radius;

        neuron.setColor(context.getResources().getColor(R.color.neuron));
        neuron.setStyle(Paint.Style.STROKE);
        neuron.setStrokeWidth(radius / 5);

        link.setStyle(Paint.Style.STROKE);
        link.setStrokeWidth(radius / 7);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void layerDraw(Canvas canvas, MultiLayerPerceptron net, int layerNum, int maxNeurons) {
        float shift = (float) (maxNeurons - net.getLayers().length) / 2;
        if (layerNum > 0) {
            for (int i = 0; i < net.getLayerAt(layerNum).getNeurons().length; i++) {
                float iy = 15 + (i + shift) * vDst + radius;
                canvas.drawCircle(15 + hDst + radius, iy, radius, neuron);
            }
        } else {

            float prevShift = (float) (maxNeurons - net.getLayerAt(layerNum - 1).getNeurons().length) / 2;

            for (int i = 0; i < net.getLayerAt(layerNum).getNeurons().length; i++) {
                float iy = 15 + (i + shift) * vDst + radius;

                canvas.drawCircle(15 + hDst * (layerNum + 1) + radius, iy, radius, neuron);

                for (int j = 0; j < net.getLayerAt(layerNum - 1).getNeurons().length; j++) {
                    float g = (float) Math.max(0, Math.min(1, (net.getLayerAt(layerNum).getNeurons()[i].getWeights()[j].value + 1) / 2));
                    link.setColor(Color.rgb(1 - g, g, 0f));
                    canvas.drawLine(15 + hDst * layerNum + radius * 2, 15 + (j + prevShift) * vDst + radius, 15 + hDst * (layerNum + 1), iy, link);
                }

                canvas.drawLine(15 + hDst / 3 + hDst * layerNum + radius * 2, 15 + (Math.max(net.getLayerAt(layerNum - 1).getNeurons().length + prevShift, net.getLayerAt(layerNum).getNeurons().length + shift)) * vDst + radius, 15 + hDst * (layerNum + 1), iy, link);
            }

            shift = (float) (maxNeurons - net.getLayerAt(layerNum - 1).getNeurons().length) / 2;
            canvas.drawCircle(15 + hDst / 3 + hDst * layerNum + radius, 15 + (Math.max(net.getLayerAt(layerNum - 1).getNeurons().length, net.getLayerAt(layerNum).getNeurons().length) + shift) * vDst + radius, radius, neuron);
        }
    }

}
