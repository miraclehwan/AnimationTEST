package daehwankim.com.animaitontest;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.scene_objects.GVRVideoSceneObject;
import org.gearvrf.scene_objects.GVRVideoSceneObjectPlayer;

import java.io.IOException;

public class MainActivity extends GVRActivity {



    TestMain main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            main = new TestMain();
            setMain(main);



    }

    @Override
    protected void onPause() {
        super.onPause();
        main.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        main.onResume();
    }
}
