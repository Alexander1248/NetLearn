package com.samsung.netlearn.models.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.samsung.netlearn.services.NetDrawer;
import com.samsung.nnlp.R;

import org.neuroph.nnet.MultiLayerPerceptron;

public class DrawThread extends Thread {
    private final Context context;
    private SurfaceHolder surfaceHolder;

    private final MultiLayerPerceptron net;

    private boolean running = true;

    private boolean update = true;


    public DrawThread(Context context, SurfaceHolder surfaceHolder, MultiLayerPerceptron net) {
        this.context = context;
        this.surfaceHolder = surfaceHolder;
        this.net = net;
    }

    public void requestStop() {
        running = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(context.getResources().getColor(R.color.gray));

                if (net != null && net.getLayers().length > 0) {
                    int max = 0;
                    for (int i = 0; i < net.getLayers().length; i++)
                        if (net.getLayerAt(i).getNeurons().length > max)
                            max = net.getLayerAt(i).getNeurons().length;

                    float radius = (float) (canvas.getWidth() + canvas.getHeight()) / 50 / max;
                    //Calculating horizontal distance
                    float hDst = (canvas.getWidth() - radius - NetDrawer.marginX) / net.getLayers().length;

                    //Calculating vertical distance
                    float vDst = (canvas.getHeight() - radius - NetDrawer.marginY) / max;

                    //Rendering
                    NetDrawer drawer = new NetDrawer(context, hDst, vDst, radius);
                    for (int i = 0; i < net.getLayers().length; i++)
                        drawer.layerDraw(canvas, net, i, max);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void update() {
        this.update = true;
    }
}
