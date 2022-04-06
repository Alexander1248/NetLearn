package com.samsung.nnlp.services;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.Layer;

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
    public void layerDraw(Canvas canvas, Layer layer, int layerNum, int maxNeurons) {
        float shift = (float) (maxNeurons - layer.getNeurons().length) / 2;
        float prevShift;
        if (layer.isFirstLayer()) {
            prevShift = (float) (maxNeurons - layer.getInput().length) / 2;
            for (int i = 0; i < layer.getInput().length; i++)
                canvas.drawCircle(15 + radius, 15 + (i + prevShift) * vDst + radius, radius, neuron);
            canvas.drawCircle(50 + radius, 15 + (layer.getInput().length + prevShift) * vDst + radius, radius, neuron);

            for (int i = 0; i < layer.getNeurons().length; i++) {
                float iy = 15 + (i + shift) * vDst + radius;
                canvas.drawCircle(15 + hDst + radius, iy, radius, neuron);

                for (int j = 0; j < layer.getInput().length; j++) {
                    float g = (float) Math.max(0, Math.min(1,(layer.getNeurons()[i].getWeights()[j] + 1) / 2));
                    link.setColor(Color.rgb(1 - g, g, 0f));
                    canvas.drawLine(15 + radius * 2, 15 + (j + prevShift) * vDst + radius, 15 + hDst, iy, link);
                }

                float g = (float) Math.max(0, Math.min(1, (layer.getNeurons()[i].getBiasWeight() + 1) / 2));
                link.setColor(Color.rgb(1 - g, g, 0f));
                canvas.drawLine(50 + radius * 2, 15 + (layer.getInput().length + prevShift) * vDst + radius, 15 + hDst, iy, link);
            }
        } else {
            prevShift = (float) (maxNeurons - layer.getPrevLayer().getNeurons().length) / 2;

            for (int i = 0; i < layer.getNeurons().length; i++) {
                float iy = 15 + (i + shift) * vDst + radius;

                canvas.drawCircle(15 + hDst * (layerNum + 1) + radius, iy, radius, neuron);

                for (int j = 0; j < layer.getPrevLayer().getNeurons().length; j++) {
                    float g = (float) Math.max(0, Math.min(1, (layer.getNeurons()[i].getWeights()[j] + 1) / 2));
                    link.setColor(Color.rgb(1 - g, g, 0f));
                    canvas.drawLine(15 + hDst * layerNum + radius * 2, 15 + (j + prevShift) * vDst + radius, 15 + hDst * (layerNum + 1), iy, link);
                }

                float g = (float) Math.max(0, Math.min(1, (layer.getNeurons()[i].getBiasWeight() + 1) / 2));
                link.setColor(Color.rgb(1 - g, g, 0f));
                canvas.drawLine(50 + hDst * layerNum + radius * 2, 15 + (layer.getPrevLayer().getNeurons().length + prevShift) * vDst + radius, 15 + hDst * (layerNum + 1), iy, link);
            }

            shift = (float) (maxNeurons - layer.getPrevLayer().getNeurons().length) / 2;
            canvas.drawCircle(50 + hDst * layerNum + radius, 15 + (layer.getPrevLayer().getNeurons().length + shift) * vDst + radius, radius, neuron);
        }


    }

}
