
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.roe.Ruby;

public class MainActivity extends AppCompatActivity implements Citizen {
    private static final String TAG = "F3-MainActivity";


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        Button button = (Button) findViewById(R.id.rubyTestButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ruby ruby = new Ruby();
                ruby.birth();
                ruby.evaluate("puts \"Hello, world!\"");
                ruby.death();
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onResume() {
        super.onResume();
        birth();
    }

    @Override
    public void onPause() {
        death();
        super.onPause();
    }

    @Override
    public Family getFamily() {
        return Family.ACTIVITY;
    }

    @Override
    public void birth() {
        Governor.getInstance().register(this);
    }

    @Override
    public void death() {
        Governor.getInstance().unregister(this);
    }
}
