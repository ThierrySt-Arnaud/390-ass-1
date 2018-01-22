import java.util.Random;
/**
 * Created by Tawfiq on 1/13/2017. */
public class Assignment {
    private static int assID = 0;
    assignment created

    private String assignmentTitle; private int assignmentGrade;
    //private constructor. Increments ID.
    private Assignment(String title, int grade) {
        assignmentTitle = title; assignmentGrade = grade; assID++;
    }
    //returns an Assignment instance with random values
    static public Assignment generateRandomAssignment() {
        Random rnd = new Random();
        String tempTitle = "Assignment " + assID; int tempGrade = rnd.nextInt(100) + 1;
        return new Assignment(tempTitle, tempGrade); }
    //****get methods*****//
    public String getAssignmentTitle() {return assignmentTitle; }
    public int getAssignmentGrade() {return assignmentGrade;}
}
