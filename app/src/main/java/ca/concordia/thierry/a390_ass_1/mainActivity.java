package ca.concordia.thierry.a390_ass_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mainActivity extends AppCompatActivity {

    protected Button showGradesButton = null;
    protected TextView buttonTest;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUpUI();
    }

    protected void setUpUI() {
        showGradesButton = (Button) findViewById(R.id.showGradesButton);

        showGradesButton.setOnClickListener(onClickShowGradesButton);
    }

    //TODO
    // make the button show a test message
    private Button.OnClickListener onClickShowGradesButton = new Button.OnClickListener(){
        @Override
        public void onClick(View V){

        }
    };

}
