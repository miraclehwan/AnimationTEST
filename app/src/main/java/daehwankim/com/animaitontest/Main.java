package daehwankim.com.animaitontest;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.GVRTextureParameters;
import org.gearvrf.ZipLoader;
import org.gearvrf.animation.GVRAnimation;
import org.gearvrf.animation.GVRRepeatMode;
import org.gearvrf.scene_objects.GVRSphereSceneObject;
import org.gearvrf.scene_objects.GVRVideoSceneObject;
import org.gearvrf.scene_objects.GVRVideoSceneObjectPlayer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by daehwan.kim on 2017-07-13.
 */

public class Main extends GVRMain {

    public Main(){

    }

    private GVRVideoSceneObjectPlayer<?> mPlayer;
    GVRContext mGVRContext;

    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {
        super.onInit(gvrContext);

        mGVRContext = gvrContext;
        GVRScene scene = gvrContext.getMainScene();


//        try {
//            List<Future<GVRTexture>> loaderTextures = ZipLoader.load(mGVRContext,
//                    "loading.zip", new ZipLoader.ZipEntryProcessor<Future<GVRTexture>>() {
//                        @Override
//                        public Future<GVRTexture> getItem(GVRContext context, GVRAndroidResource
//                                resource) {
//                            return context.loadFutureTexture(resource);
//                        }
//                    });
//
//            GVRSceneObject loadingObject = new GVRSceneObject(mGVRContext, 5.0f, 5.0f);
//
//            GVRRenderData renderData = loadingObject.getRenderData();
//            GVRMaterial loadingMaterial = new GVRMaterial(mGVRContext);
//            renderData.setMaterial(loadingMaterial);
//            loadingMaterial.setMainTexture(loaderTextures.get(0));
//            GVRAnimation animation = new GVRImageFrameAnimation(loadingMaterial, 3.0f,
//                    loaderTextures);
//            animation.setRepeatMode(GVRRepeatMode.PINGPONG);
//            animation.setRepeatCount(-1);
//            animation.start(mGVRContext.getAnimationEngine());
//
//            loadingObject.getTransform().setPosition(0.0f, 0.0f, -4.0f);
//            scene.addSceneObject(loadingObject);
//        } catch (IOException e) {
//            Log.e("Error loading animation", String.valueOf(e));
//        }


        videoSceneObjectPlayer = MakeMediaPlayer();
        mPlayer = videoSceneObjectPlayer;

        GVRSphereSceneObject sphere = new GVRSphereSceneObject(gvrContext, 72, 144, false);
        GVRMesh mesh = sphere.getRenderData().getMesh();
        final GVRVideoSceneObject video = new GVRVideoSceneObject( gvrContext, mesh, mPlayer, GVRVideoSceneObject.GVRVideoType.MONO );
        video.getTransform().setScale(21f, 21f, 21f);
        video.setName( "video" );
        float[] texCoords = mesh.getTexCoords();
        for(int i = 0; i < texCoords.length; i+=2){
            texCoords[i] = 2*texCoords[i];
        }

        mesh.setTexCoords(texCoords);
        video.getTransform().rotateByAxis(-180, 0, 1, 0);
        scene.addSceneObject(video);




    }

    private GVRVideoSceneObjectPlayer<?> videoSceneObjectPlayer;

    private GVRVideoSceneObjectPlayer<MediaPlayer> MakeMediaPlayer() {
        final MediaPlayer EnvironmentPlayer = new MediaPlayer();
        final AssetFileDescriptor afd;

        try {
            afd = mGVRContext.getContext().getAssets().openFd("dark.mp4");
            android.util.Log.d("Minimal360Video", "Assets was found.");
            EnvironmentPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            android.util.Log.d("Minimal360Video", "DataSource was set.");
            afd.close();
            EnvironmentPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    EnvironmentPlayer.start();
                }
            });
            EnvironmentPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            mGVRContext.getActivity().finish();
            Log.e("MainVideoVideoStatus", "URL were not loaded. Stopping application!");
            return null;
        }
        EnvironmentPlayer.setLooping(true);
        Log.d("MainVideoVideoStatus", "starting player.");
        return GVRVideoSceneObject.makePlayerInstance(EnvironmentPlayer);
    }
}
