package net.block.detours_inject.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import net.block.detours_inject.callback.NativeCallBack;
import net.block.detours_inject.core.floatWindow.lib.WindowLib;
import net.block.detours_inject.debug.Logger;

/*
* By SemiLuo on JavaScript.
*/
public class ToolRockerView  {
    public Activity activity;
    public int deviation;
    public RockerEvent rockerEvent;
    public int dx,dy,rmaxy;
    public static boolean isUseRocker;


    public static boolean rockerOP = true;

    public static int windowX,windowY,windowXs,windowYs;

    public static int RAJt=0,
            rdy=0,
            rsy=0,
            rxxx=0,
            ryyy=0,
            rdist=0,
            rcr=0,
            rss=0,
            rpy=0,
            rxs=0,
            rzs=0;
    public interface RockerEvent{
        void onRockerUpListener(int windowX,int windowY);
        void onRockerDownListener();
        void onRockerMoveListener(int windowX,int windowY,int windowXs,int windowYs);
    }
    public ToolRockerView(Activity activity,int deviation,RockerEvent rockerEvent){
        this.activity = activity;
        this.deviation = deviation;
        this.rockerEvent = rockerEvent;

        this.windowX =  (int) (deviation + (H()/4 -  H()*0.05));
        this.windowY = (int) (H() - deviation -(H()/4 + H()*0.05));
        this.rmaxy= (int) ((H()-deviation-H()*0.1)-(H()-deviation-(H()/4+H()*0.05)));
    }

    public void createRocker(){
        LinearLayout hostLayout = new LinearLayout(activity);
        hostLayout.setOrientation(0);
        hostLayout.setGravity(Gravity.CENTER);
        hostLayout.setLayoutParams(new LinearLayout.LayoutParams(new Double(H()*0.5).intValue(),new Double(H()*0.5).intValue()));
        WindowLib.setDrawable("#31000000",hostLayout,20);

        LinearLayout testLayout = new LinearLayout(activity);
        testLayout.setOrientation(0);
        testLayout.setGravity(Gravity.CENTER);
        testLayout.setLayoutParams(new LinearLayout.LayoutParams(new Double(H()*0.1).intValue(),new Double(H()*0.1).intValue()));
        WindowLib.setDrawable("#9198e5",testLayout,20);

        LinearLayout mainLayout = new LinearLayout(activity);
        mainLayout.setOrientation(0);
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(new Double(H()*0.1).intValue(),new Double(H()*0.1).intValue()));
        WindowLib.setDrawable("#ffffff",mainLayout,20);

        PopupWindow rockerMainWindow = new PopupWindow();
        rockerMainWindow.setContentView(hostLayout);
        rockerMainWindow.setAnimationStyle(WindowLib.getPopupWindowAnimation(2));
        rockerMainWindow.setWidth((int)((int)H()*0.5));
        rockerMainWindow.setHeight((int)((int)H()*0.5));
        rockerMainWindow.setTouchable(true);
        rockerMainWindow.setClippingEnabled(true);

        PopupWindow rockerTestWindow = new PopupWindow();
        rockerTestWindow.setContentView(testLayout);
        rockerTestWindow.setAnimationStyle(WindowLib.getPopupWindowAnimation(2));
        rockerTestWindow.setWidth((int) ((int)H()*0.1));
        rockerTestWindow.setHeight((int) ((int)H()*0.1));
        rockerTestWindow.setTouchable(true);
        rockerTestWindow.setClippingEnabled(true);

        PopupWindow rockerWindow = new PopupWindow();
        rockerWindow.setContentView(mainLayout);
        rockerWindow.setAnimationStyle(WindowLib.getPopupWindowAnimation(2));
        rockerWindow.setWidth((int) ((int)H()*0.1));
        rockerWindow.setHeight((int) ((int)H()*0.1));
        rockerWindow.setTouchable(false);
        rockerWindow.setClippingEnabled(true);

        hostLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dx = (int) (windowX - motionEvent.getRawX());
                        dy = (int) (windowY - motionEvent.getRawY());
                        isUseRocker = true;
                        rockerEvent.onRockerDownListener();
                       break;
                       case MotionEvent.ACTION_MOVE:
                           windowX = (int) (motionEvent.getRawX() + dx);
                           windowY = (int) (motionEvent.getRawY() + dy);
                           if(windowX<=deviation + (H()/2 - H()*0.1) && windowX>=deviation){
                               windowXs = windowX;
                           }else{
                               if(windowX>=deviation
                                       +(H()/2-H()*0.1)) {
                                   windowXs = (int) (deviation + (H()/2-H()*0.1));
                               }
                               if(windowX<=deviation){
                                   windowXs = deviation;
                                   //
                               }
                           }
                           if(windowY>=H()-(H()/2+deviation)&&windowY<=H()-deviation-H()*0.1){
                               windowYs=windowY;
                           }else{
                               if(windowY>=H()-(H()/2+deviation)){
                                   windowYs= (int) (H()-deviation-H()*0.1);
                               };
                               if(windowY<=H()-deviation-H()*0.1){
                                   windowYs= (int) (H()-(H()/2+deviation));
                               };
                           };
                           rockerEvent.onRockerMoveListener(windowX,windowY,windowXs,windowYs);
                           rockerTestWindow.update(windowX,windowY, -1, -1);
                           rockerWindow.update(windowXs,windowYs,-1,-1);
                           break;
                           case MotionEvent.ACTION_UP:
                               NativeCallBack.setLocalPlayerMotion(0,0,0);
                               windowX= (int) (deviation+(H()/4-H()*0.05));
                               windowY= (int) (H()-deviation-(H()/4+H()*0.02));
                               windowXs= (int) (deviation+(H()/4-H()*0.05));
                               windowYs= (int) (H()-deviation-(H()/4+H()*0.02));
                               rockerEvent.onRockerUpListener(windowX,windowY);
                               rockerTestWindow.update(windowX,windowY,-1,-1);
                               rockerWindow.update(windowX,windowY,-1,-1);
                               isUseRocker = false;
                               break;
                }
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        if(rockerOP) {
                            if(isUseRocker) {
                                rxxx = (int) (windowYs - (H() - deviation - (H() / 4 + H() * 0.05)));
                              //  Log.d(Logger.Inject_Debug_Log, "rxxx:" + rxxx);
                                ryyy = (int) -(windowXs - (deviation + (H() / 4 - H() * 0.05)));
                              //  Log.d(Logger.Inject_Debug_Log, "ryyy:" + ryyy);
                                rdist = (int) Math.sqrt(Math.pow(rxxx, 2) + Math.pow(ryyy, 2));
                              //  Log.d(Logger.Inject_Debug_Log, "rdist:" + rdist);
                                rcr = (int) (-Math.atan2(rxxx, ryyy) * (180 / Math.PI));
                              //  Log.d(Logger.Inject_Debug_Log, "rcr:" + rcr);
                                rss = rdist / rmaxy * 1;
                              //  Log.d(Logger.Inject_Debug_Log, "rss:" + rss);
                                rpy = (int) (NativeCallBack.getYaw() + rcr);
                             //   Log.d(Logger.Inject_Debug_Log, "rpy:" + rpy);
                                rxs = (int) Math.cos(rpy * (Math.PI / 180));
                            //    Log.d(Logger.Inject_Debug_Log, "rxxx:" + rxs);
                                rzs = (int) Math.sin(rpy * (Math.PI / 180));
                             //   Log.d(Logger.Inject_Debug_Log, "rxxx:" + rzs);
                             //   Log.d(Logger.Inject_Debug_Log, "x:" + rxs * rss + ",y:" + rzs * rss);
                                NativeCallBack.setLocalPlayerMotion(rxs * rss, 0, rzs * rss);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        rockerMainWindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.LEFT|Gravity.BOTTOM,deviation,deviation);
        rockerTestWindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.LEFT|Gravity.TOP, windowX, windowY);
        rockerWindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.LEFT|Gravity.TOP, windowX,windowY);
    }

    private float H() {
        return new WindowLib(activity).H();
    }
}
