import java.util.ArrayList;

public class Assignment implements Cloneable{
    private Main.DayOfWeek day;
    private Main.Courses course;
    private Main.Category category;
    private Integer priority;

    public Assignment(Main.DayOfWeek day, Main.Courses course, Main.Category category, Integer priority) {
        this.day = day;
        this.course = course;
        this.category =  category;
        this.priority = priority;
    }

    public Main.DayOfWeek getDay() {
        return day;
    }

    public void setDay(Main.DayOfWeek day) {
        this.day = day;
    }

    public Main.Courses getCourse() {
        return course;
    }

    public void setCourse(Main.Courses course) {
        this.course = course;
    }

    public Main.Category getCategory() {
        return category;
    }

    public void setCategory(Main.Category category) {
        this.category = category;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Assignment{" + day +
                ", " + course +
                ", " + category +
                ", priority: " + priority +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
