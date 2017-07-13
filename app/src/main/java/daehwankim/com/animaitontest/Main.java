package daehwankim.com.animaitontest;

import android.util.Log;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.ZipLoader;
import org.gearvrf.animation.GVRAnimation;
import org.gearvrf.animation.GVRRepeatMode;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by daehwan.kim on 2017-07-13.
 */

public class Main extends GVRMain {

    public Main(){

    }

    GVRContext mGVRContext;

    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {
        super.onInit(gvrContext);

        mGVRContext = gvrContext;


        try {
            List<Future<GVRTexture>> loaderTextures = ZipLoader.load(mGVRContext,
                    "loading.zip", new ZipLoader.ZipEntryProcessor<Future<GVRTexture>>() {
                        @Override
                        public Future<GVRTexture> getItem(GVRContext context, GVRAndroidResource
                                resource) {
                            return context.loadFutureTexture(resource);
                        }
                    });

            GVRSceneObject loadingObject = new GVRSceneObject(mGVRContext, 5.0f, 5.0f);

            GVRRenderData renderData = loadingObject.getRenderData();
            GVRMaterial loadingMaterial = new GVRMaterial(mGVRContext);
            renderData.setMaterial(loadingMaterial);
            loadingMaterial.setMainTexture(loaderTextures.get(0));
            GVRAnimation animation = new GVRImageFrameAnimation(loadingMaterial, 3.0f,
                    loaderTextures);
            animation.setRepeatMode(GVRRepeatMode.PINGPONG);
            animation.setRepeatCount(-1);
            animation.start(mGVRContext.getAnimationEngine());

            loadingObject.getTransform().setPosition(0.0f, 0.0f, -4.0f);
            mGVRContext.getMainScene().addSceneObject(loadingObject);
        } catch (IOException e) {
            Log.e("Error loading animation", String.valueOf(e));
        }


    }
}
