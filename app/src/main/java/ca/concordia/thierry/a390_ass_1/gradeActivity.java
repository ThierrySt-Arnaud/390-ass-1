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

import static ca.concordia.thierry.a390_ass_1.Course.generateRandomCourse;

public class gradeActivity extends AppCompatActivity {

    private ArrayList<Course> courseList;
    private ExpandableListView gradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        gradeList = findViewById(R.id.gradeList);

    }

    @Override
    protected void onStart(){
        super.onStart();
        courseList = new ArrayList<Course>();
        for (int i = 0; i < 5; i++) {
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
        Course courseInfo = (Course) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_items, null);
        }

        TextView heading = convertView.findViewById(R.id.heading);
        heading.setText(courseInfo.getCourseTitle().trim());
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