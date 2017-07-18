package daehwankim.com.animaitontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.OrientationEventListener;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.scene_objects.GVRTextViewSceneObject;

/**
 * Created by daehwan.kim on 2017-07-18.
 */

public class TestMain extends GVRMain {

    GVRContext mGVRContext;
    OrientationEventListener orientationEventListener;
    GVRScene MainScene;

    public TestMain(){}

    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {
        super.onInit(gvrContext);
        mGVRContext = gvrContext;

        MainScene = mGVRContext.getMainScene();

        if (orientationEventListener != null){
            orientationEventListener.disable();
        }
        if (ContextCompat.checkSelfPermission(mGVRContext.getContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED){
            //Manifest.permission.READ_CALENDAR이 접근 승낙 상태 일때
        } else{
            //Manifest.permission.READ_CALENDAR이 접근 거절 상태 일때
            final GVRTextViewSceneObject AlertTextViewScene = new GVRTextViewSceneObject(mGVRContext, 10, 5, "권한이 필요합니다. 화면을 돌려주세요.");
            AlertTextViewScene.getTransform().setPosition(0, 0, -5.0f);
            AlertTextViewScene.setGravity(Gravity.CENTER);
            AlertTextViewScene.setName("PermissionAlertText");
            AlertTextViewScene.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.TRANSPARENT);
            AlertTextViewScene.setTextSize(5.0f);
            AlertTextViewScene.setTextColor(Color.WHITE);
            MainScene.addSceneObject(AlertTextViewScene);
            orientationEventListener = new OrientationEventListener(mGVRContext.getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
                @Override
                public void onOrientationChanged(int orientation) {
                    if (orientation < 5 && orientation >= 0){
                        orientationEventListener.disable();
                        Intent intent = new Intent(mGVRContext.getContext(), PermissionRequest.class);
                        MainScene.removeSceneObject(AlertTextViewScene);
                        mGVRContext.getContext().startActivity(intent);
                    }
                }
            };
            if (orientationEventListener.canDetectOrientation()) {
                orientationEventListener.enable();
            }
        }

    }

    public void onResume(){
        if (mGVRContext != null){
            if (orientationEventListener != null){
                orientationEventListener.disable();
            }
            if (ContextCompat.checkSelfPermission(mGVRContext.getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
//                final GVRTextViewSceneObject AlertTextViewScene = new GVRTextViewSceneObject(mGVRContext, 10, 5, "권한이 필요합니다. 화면을 돌려주세요.");
//                AlertTextViewScene.getTransform().setPosition(0, 0, -5.0f);
//                AlertTextViewScene.setGravity(Gravity.CENTER);
//                AlertTextViewScene.setName("PermissionAlertText");
//                AlertTextViewScene.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.TRANSPARENT);
//                AlertTextViewScene.setTextSize(5.0f);
//                AlertTextViewScene.setTextColor(Color.WHITE);
//                MainScene.addSceneObject(AlertTextViewScene);

                orientationEventListener = new OrientationEventListener(mGVRContext.getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
                    @Override
                    public void onOrientationChanged(int orientation) {
                        if (orientation < 5 && orientation >= 0){
                            orientationEventListener.disable();
                            Intent intent = new Intent(mGVRContext.getContext(), PermissionRequest.class);
//                            MainScene.removeSceneObject(AlertTextViewScene);
                            mGVRContext.getContext().startActivity(intent);
                        }
                    }
                };
                if (orientationEventListener.canDetectOrientation()) {
                    orientationEventListener.enable();
                }
            }
        }
    }

    public void onPause(){

    }



}
