package ca.concordia.thierry.a390_ass_1;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static ca.concordia.thierry.a390_ass_1.Course.generateRandomCourse;

public class gradeActivity extends AppCompatActivity {

    private ArrayList<Course> courseList;
    private ExpandableListView gradeList;
    private Random rng = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        gradeList = findViewById(R.id.gradeList);

    }

    @Override
    protected void onStart(){
        super.onStart();

        int numberOfCourse = rng.nextInt(5) + 1;
        courseList = new ArrayList<Course>();
        for (int i = 0; i < numberOfCourse; i++) {
            courseList.add(Course.generateRandomCourse());
        }

        CourseListAdapter gradeListAdapter = new CourseListAdapter(this, courseList);

        gradeList.setAdapter(gradeListAdapter);
    }
}

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
        Course course = (Course) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_items, null);
        }
        String courseInfo = null;
        ArrayList<Assignment> assignments = course.getAssignments();
        if (assignments.isEmpty())
            courseInfo = course.getCourseTitle().trim();
        else{
            int avg = 0;
            for (Assignment assignment: assignments ) {
                avg += assignment.getAssignmentGrade();
            }
            avg /= assignments.size();
            courseInfo = course.getCourseTitle().trim() + "     " + avg;
        }

        TextView heading = convertView.findViewById(R.id.heading);
        heading.setText(courseInfo);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Assignment courseAssignment = (Assignment) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_items, null);
        }
        TextView childItem = convertView.findViewById(R.id.childItem);
        childItem.setText(courseAssignment.getAssignmentTitle().trim());

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
}