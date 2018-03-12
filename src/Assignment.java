import java.time.LocalDateTime;
import java.util.ArrayList;

public class Assignment implements Cloneable{
    private LocalDateTime date;
    private Main.Courses course;
    private Main.Category category;
    private Integer priority;

    public Assignment(LocalDateTime date, Main.Courses course, Main.Category category, Integer priority) {
        this.date = date;
        this.course = course;
        this.category =  category;
        this.priority = priority;
    }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) { this.date = date; }

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
        return "Assignment{" + date +
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
