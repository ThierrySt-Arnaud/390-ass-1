//  mainActivity.java
//  Created by: Thierry St-Arnaud, ID 27649460
//
//  Initial activity to welcome user in the app
//  Simply displays a button that leads to gradeActivity

package ca.concordia.thierry.a390_ass_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class mainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";
    protected Button showGradesButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();

        Log.d(TAG, "OnCreate() called.");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "OnDestroy() called.");
    }

    // Sets up most of the UI elements of the activity
    protected void setUpUI() {
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        showGradesButton = findViewById(R.id.show_grades_button);
        showGradesButton.setOnClickListener(onClickShowGradesButton);

    }

    // When show_grades_button is pressed, go to gradeActivity
    private Button.OnClickListener onClickShowGradesButton = new Button.OnClickListener(){
        @Override
        public void onClick(View V){
            Intent GradeIntent = new Intent(getApplicationContext(), gradeActivity.class);
            startActivity(GradeIntent);
        }
    };

}
