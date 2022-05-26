package com.samsung.netlearn.services;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.samsung.netlearn.R;

import org.neuroph.core.Neuron;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;

public class NetDrawer {
    private final float hDst;
    private final float vDst;
    private final float radius;

    private final Paint neuronPaint = new Paint();
    private final Paint linkPaint = new Paint();

    private double min = -1;
    private double max = 1;

    public NetDrawer(Context context, float hDst, float vDst, float radius) {
        this.hDst = hDst;
        this.vDst = vDst;
        this.radius = radius;

        neuronPaint.setColor(context.getResources().getColor(R.color.neuron));
        neuronPaint.setStyle(Paint.Style.STROKE);
        neuronPaint.setStrokeWidth(radius / 3);

        linkPaint.setStyle(Paint.Style.STROKE);
        linkPaint.setStrokeWidth((float) Math.sqrt(radius * 3));
    }

    public static final float marginX = 50;
    public static final float marginY = 50;

    private static final float dataRange = 10;

    public void layerDraw(Canvas canvas, MultiLayerPerceptron net, int layerNum, int maxNeurons) {
        double tempMin = min;
        double tempMax = max;
        double range = max - min;

        float shift = marginY + (maxNeurons - net.getLayerAt(layerNum).getNeuronsCount()) * vDst / 2;
        if (layerNum > 0) {
            for (int i = 0; i < net.getLayerAt(layerNum).getNeuronsCount(); i++) {
                float cy = i * vDst + radius;
                Neuron neuron = net.getLayerAt(layerNum).getNeuronAt(i);
                if (neuron instanceof BiasNeuron)
                    canvas.drawCircle(marginX + hDst * (layerNum + 0.2f), shift + cy, radius, neuronPaint);
                else {
                    canvas.drawCircle(marginX + hDst * layerNum, shift + cy, radius, neuronPaint);

                    for (int j = 0; j < net.getLayerAt(layerNum - 1).getNeuronsCount(); j++) {
                        float prevShift = marginY + (maxNeurons - net.getLayerAt(layerNum - 1).getNeuronsCount()) * vDst / 2;
                        float pcy = j * vDst + radius;
                        float c = (float) Math.max(0, Math.min(1, (neuron.getWeights()[j].value - min) / range));
                        linkPaint.setColor(Color.rgb(c, 1 - c, 0));

                        if (net.getLayerAt(layerNum - 1).getNeuronAt(j) instanceof BiasNeuron)
                            canvas.drawLine(marginX + hDst * (layerNum - 0.8f) + radius,prevShift + pcy,marginX + hDst * layerNum - radius, shift + cy, linkPaint);
                        else canvas.drawLine(marginX + hDst * (layerNum - 1) + radius,prevShift + pcy,marginX + hDst * layerNum - radius, shift + cy, linkPaint);

                        if (neuron.getWeights()[j].value > tempMax) tempMax = max;
                        else if (neuron.getWeights()[j].value < tempMin) tempMin = max;
                    }
                }
            }
        } else {
            for (int i = 0; i < net.getLayerAt(layerNum).getNeuronsCount(); i++) {
                Neuron neuron = net.getLayerAt(layerNum).getNeuronAt(i);
                if (neuron instanceof BiasNeuron)
                    canvas.drawCircle(marginX + hDst / 5, shift + i * vDst + radius, radius, neuronPaint);
                else canvas.drawCircle(marginX, shift + i * vDst + radius, radius, neuronPaint);
            }
        }

        min = tempMin;
        max = tempMax;
    }

}
