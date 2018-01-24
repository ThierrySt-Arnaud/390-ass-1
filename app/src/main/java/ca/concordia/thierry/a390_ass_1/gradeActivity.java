//  gradeActivity.java
//  Created by: Thierry St-Arnaud, ID 27649460
//
//  Displays grades from randomly generated lists of courses and assignments

package ca.concordia.thierry.a390_ass_1;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

public class gradeActivity extends AppCompatActivity {

    protected ToggleButton toggleLetterButton = null;
    private ArrayList<Course> courseList;
    private ExpandableListView gradeList;
    static protected boolean letterGrade = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        setUpUI();

    }

    @Override
    protected void onStart(){
        super.onStart();

        generateCourseList();

        CourseListAdapter gradeListAdapter = new CourseListAdapter(this, courseList);

        configureToggleLetterButton();

        gradeList.setAdapter(gradeListAdapter);

    }

        //  Sets up most UI element, including the custom ActionBar with the toggle button
    private void setUpUI(){
        Toolbar gradeToolbar = findViewById(R.id.grade_toolbar);
        setSupportActionBar(gradeToolbar);

        ActionBar gradeActionBar = getSupportActionBar();

        gradeActionBar.setCustomView(R.layout.grade_toolbar_layout);

        gradeActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
        gradeActionBar.setDisplayHomeAsUpEnabled(true);

        gradeList = findViewById(R.id.grade_list);
    }

        //  Generate between 1 and 5 course to put in the list
    private void generateCourseList(){
        Random rng = new Random();
        int numberOfCourse = rng.nextInt(5) + 1;
        courseList = new ArrayList<>();
        for (int i = 0; i < numberOfCourse; i++) {
            courseList.add(Course.generateRandomCourse());
        }
    }

        //  Sets up the toggle_letter_button to change the state of letterGrade
        //  and refresh the ExpandableListView when required.
    private void configureToggleLetterButton(){
        toggleLetterButton =  findViewById(R.id.toggle_letter);
        letterGrade = false;
        toggleLetterButton.setChecked(false);
        toggleLetterButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                letterGrade = isChecked;
                gradeList.invalidateViews();
            }
        });
    }
}

    //  This inner class will act as the adapter for the grade_list ExpandableListView.
    //  It extracts information from the list and sublists containing course
    //  information and creates an appropriate view for the ExpandableListView
class CourseListAdapter implements ExpandableListAdapter {
    private Context context;
    private ArrayList<Course> courseArrayList;

    public CourseListAdapter(Context ctxt, ArrayList<Course> courseAList){
        this.context = ctxt;
        this.courseArrayList = courseAList;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return courseArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (courseArrayList.get(groupPosition).getAssignments().size() == 0)
            return 1;
        return courseArrayList.get(groupPosition).getAssignments().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return courseArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courseArrayList.get(groupPosition).getAssignments().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater courseInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = courseInflater.inflate(R.layout.group_items, parent, false);
        }

        Course course = (Course) getGroup(groupPosition);
        ArrayList<Assignment> assignments = course.getAssignments();

        TextView courseGrade = convertView.findViewById(R.id.course_grade);
        TextView courseTitle = convertView.findViewById(R.id.course_title);

        if (!assignments.isEmpty()){
            int avg = 0;
            for (Assignment assignment: assignments ) {
                avg += assignment.getAssignmentGrade();
            }
            avg /= assignments.size();
            if(gradeActivity.letterGrade)
                courseGrade.setText(convertToLetter(avg));
            else
                courseGrade.setText(Integer.toString(avg));
        }
        else
            courseGrade.setText("");

        courseTitle.setText(course.getCourseTitle().trim());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater assignmentInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = assignmentInflater.inflate(R.layout.child_items, parent, false);
        }
        TextView assignmentTitle = convertView.findViewById(R.id.assignment_title);
        TextView assignmentGrade = convertView.findViewById(R.id.assignment_grade);

        if (courseArrayList.get(groupPosition).getAssignments().size() < 1){
            assignmentTitle.setText("No assignments for " + courseArrayList.get(groupPosition).getCourseTitle() + "!");
            assignmentGrade.setText("");
            return convertView;
        }
        Assignment courseAssignment = (Assignment) getChild(groupPosition, childPosition);
        assignmentTitle.setText(courseAssignment.getAssignmentTitle().trim());
        if (gradeActivity.letterGrade)
            assignmentGrade.setText(convertToLetter(courseAssignment.getAssignmentGrade()));
        else
            assignmentGrade.setText(Integer.toString(courseAssignment.getAssignmentGrade()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return courseArrayList.isEmpty();
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

        //  Converts the received number grade to a letter grade using the table
        //  found at https://alcor.concordia.ca/~higgins/qth/concordia.grades.html
        //  for reference.
    private String convertToLetter(int numberGrade){
        String letterGrade = "F";
        if (numberGrade <= 100 && numberGrade >= 0){
            if (numberGrade > 89)
                letterGrade = "A+";
            else if (numberGrade > 84)
                letterGrade = "A";
            else if (numberGrade > 79)
                letterGrade = "A-";
            else if (numberGrade > 76)
                letterGrade = "B+";
            else if (numberGrade > 72)
                letterGrade = "B";
            else if (numberGrade > 69)
                letterGrade = "B-";
            else if (numberGrade > 66)
                letterGrade = "C+";
            else if (numberGrade > 62)
                letterGrade = "C";
            else if (numberGrade > 59)
                letterGrade = "C-";
            else if (numberGrade > 56)
                letterGrade = "D+";
            else if (numberGrade > 52)
                letterGrade = "D";
            else if (numberGrade > 49)
                letterGrade = "D-";
        }
        else
            letterGrade = "Invalid grade";
        return letterGrade;
    }
}