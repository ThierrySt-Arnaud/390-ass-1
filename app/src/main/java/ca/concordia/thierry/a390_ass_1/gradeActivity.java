package ca.concordia.thierry.a390_ass_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static ca.concordia.thierry.a390_ass_1.Course.generateRandomCourse;

public class gradeActivity extends AppCompatActivity {

    private ArrayList<Course> courseList;
    private ListView gradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        gradeList = (ListView) findViewById(R.id.gradeList);

    }

    @Override
    protected void onStart(){
        super.onStart();
        courseList = new ArrayList<Course>();
        for (int i = 0; i < 5; i++) {
            courseList.add(generateRandomCourse());
        }

        ArrayAdapter<Course> gradeListAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseList);

        gradeList.setAdapter(gradeListAdapter);
    }
}
