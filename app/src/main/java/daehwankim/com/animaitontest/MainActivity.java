package daehwankim.com.animaitontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.gearvrf.GVRActivity;

public class MainActivity extends GVRActivity {

    Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = new Main();
        setMain(main);
    }
}
