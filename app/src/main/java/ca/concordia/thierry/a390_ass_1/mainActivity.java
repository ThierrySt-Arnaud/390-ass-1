package ca.concordia.thierry.a390_ass_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";
    //public static final String EXTRA_MESSAGE = "ca.concordia.thierry.a390_ass_1";

    protected Button showGradesButton = null;
    protected TextView buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();

        Log.d(TAG, "OnCreate() called.");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "OnDestroy() called.");
    }

    protected void setUpUI() {
        showGradesButton = (Button) findViewById(R.id.showGradesButton);
        buttonTest = (TextView) findViewById(R.id.buttonTest);

        showGradesButton.setOnClickListener(onClickShowGradesButton);
    }

    private Button.OnClickListener onClickShowGradesButton = new Button.OnClickListener(){
        @Override
        public void onClick(View V){
            /*if (buttonTest.isShown())
                buttonTest.setVisibility(View.INVISIBLE);
            else
                buttonTest.setVisibility(View.VISIBLE);*/
            Intent GradeIntent = new Intent(getApplicationContext(), gradeActivity.class);
            startActivity(GradeIntent);
        }
    };

}
