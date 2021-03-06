import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static Random rand = new Random();
    static Scanner sc = new Scanner(System.in);

    public enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

        private static final List<DayOfWeek> DayOfWeekList =
                Collections.unmodifiableList(Arrays.asList(values()));

        public static DayOfWeek randomDay() {
            return DayOfWeekList.get(rand.nextInt(DayOfWeekList.size()));
        }
    }

    public enum Courses {
        PHED1040, CPSC2730, MATH2030, FRSM1100, CMST1080, ENGL2010;

        private static final List<Courses> CourseList =
                Collections.unmodifiableList(Arrays.asList(values()));

        public static Courses randomCourse() {
            return CourseList.get(rand.nextInt(CourseList.size()));
        }
    }

    public enum Category {
        HOMEWORK, QUIZ, TEST, PRESENTATION, FINAL_EXAM;

        private static final List<Category> CategoryList =
                Collections.unmodifiableList(Arrays.asList(values()));

        public static Category randomCategory() {
            return CategoryList.get(rand.nextInt(CategoryList.size()));
        }
    }

    public static void main(String[] args) {
        System.out.println("\n\nHello, AssignmentsApp!\n");

        //Output the current date-time.
        LocalDateTime today = LocalDateTime.now();
        System.out.println("\nThe current date-time is " + today);

        //Output tomorrow's date using a formatter.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        String formatDateTime = today.format(formatter);
        System.out.println("Tomorrow's formatted date is " + formatDateTime);

        //Add 5 weeks to today's LocalDateTime.
        LocalDateTime fiveWeeksLater = today.plusWeeks(5);
        System.out.println("The five weeks, the date will be " + fiveWeeksLater);

        //Initialize a LocalDateTime object to your birth date and the time 12:35 PM.
        LocalDateTime birthDate = LocalDateTime.of(1999, 2, 7, 12, 35);
        System.out.println("Your birth date is " + birthDate);

        //Output the day of the week (Sunday-Saturday) that you were born.
        System.out.println("The day of the week of your birth date was " + birthDate.getDayOfWeek());

        //Output the number of days you've been alive.
        System.out.println("The number of days you have been alive is " + ChronoUnit.DAYS.between(birthDate, today) + " days.");

        //Output the number of days between two dates.
        LocalDateTime obamaInauguration = LocalDateTime.of(2009, 2, 20, 12, 0);
        System.out.println("The number of days between your birth date and Obama's inauguration is " + ChronoUnit.DAYS.between(birthDate, obamaInauguration) + " days.");

        //Given two dates, output the earlier.
        System.out.println("The earlier date is " + FindEarlierDate(today, obamaInauguration));

        //Create a file with 100 random "month/day/year  hour:minutes" (in that format) on each line.
        ArrayList<LocalDateTime> hundredRandomDates = randomDateArray(100);
        hundredRandomDates.forEach(d -> System.out.println("Date is " + d));

        //Output the number of stored dates in the year [Y].
        System.out.print("\nWhat is the year you want to find the dates of? ");
        ArrayList<LocalDateTime> datesOfUserYear = searchByYear(hundredRandomDates, sc.nextInt());
        System.out.println("The number of dates with that year is " + datesOfUserYear.size());

        //Count the number of stored dates in the current year.
        ArrayList<LocalDateTime> datesOfCurrentYear = searchByYear(hundredRandomDates, today.getYear());
        System.out.println("\nThe number of dates in the current year is " + datesOfCurrentYear.size());

        //Count the number of duplicates.
        ArrayList<LocalDateTime> duplicatedDates = seekDuplicates(hundredRandomDates);
        System.out.println("\nThere are " + duplicatedDates.size() + " duplicated dates.");

        // Sort the dates in chronological order.
        Collections.sort(hundredRandomDates);
        System.out.println("\nThe sorted dates are as followed: ");
        hundredRandomDates.forEach(System.out::println);

        //Count the number of duplicates in a sorted list without using a Java Set.
        System.out.println("\nWithout using a Set, the number of duplicated dates are " + countDuplicates(hundredRandomDates));

        //Count the number of evening (after 6pm) dates.
        ArrayList<LocalDateTime> eveningDates = searchDatesInTimeframe(hundredRandomDates,18, 24 );
        System.out.println("\nThe number of evening dates are " + eveningDates.size());

        //Count the number of dates in each of the individual 12 months without using a Java Map.
        System.out.print("\nWhat is the number of the month you are searching the dates for? ");
        int month = sc.nextInt();
        if (month > 12 || month < 1) System.out.println("That month value is not valid.");
        else {
            ArrayList<LocalDateTime> datesOfMonth = searchByMonth(hundredRandomDates, month);
            System.out.println("The number of dates in month " + month + " is " + datesOfMonth.size());
        }

        //Count the number of dates in each of the individual 12 months using a Java Map.
        if (month >= 1 && month <=12){
            System.out.println("Using a Java Map, the number of dates in month " + month + " is " + mapByMonthSearch(hundredRandomDates,month));
        }

        //Determine the index of the latest LocalDateTime.
        System.out.println("\nThe index of the latest LocalDateTime is " + indexLatestDate(hundredRandomDates));

        //Determine the indexes of the elements that have the earliest starting time, regardless of date.
        System.out.println("\nThe index of the date with the earliest starting time is " + indexEarliestTime(hundredRandomDates));

        //Output a date in the format "January 1st, 2018".
        System.out.print("\nWhat is the index of the date you want to be outputted in the format \"January 1st, 2018\"? ");
        System.out.println("The formatted date is " + formattedDate(hundredRandomDates.get(sc.nextInt())));

        //In the driver, generate 2 random assignments named assign1 and assign2.
        Assignment assign1 = randomAssignmentGenerator();
        Assignment assign2 = randomAssignmentGenerator();
        System.out.println("\nThe two random assignments are " + assign1 + " and " + assign2);

        //Copy assign1 to assign3.
        Assignment assign3 = new Assignment(assign1);
        System.out.println("\nThe copied assignment is " + assign3);

        //Override an Assignment.equals() method.
        System.out.println("\nIs the copied assignment equal to the original? " + assign3.equals(assign1));

        //Override an Assignment.compareTo() method.
        System.out.println("\nassign1 comes " + compareToResult(assign1, assign2) + ", compared to assign2.");

        //Which of assign1, assign2, or assign3 is the earliest?
        if (assign1.equals(earliestAssignment(assign1,assign2,assign3))) { System.out.println("assign1 is the earliest assignment."); }
        else if (assign2.equals(earliestAssignment(assign1,assign2,assign3))) { System.out.println("assign2 is the earliest assignment."); }
        else if (assign3.equals(earliestAssignment(assign1,assign2,assign3))) { System.out.println("assign3 is the earliest assignment."); }

        //Write [X] randomly generated assignments to the file 'input.dat'.
        System.out.print("\nHow many assignments do you want saved onto the file? ");
        generateAssignmentsFile("input.dat", sc.nextInt());

        //Read assignments from the file 'input.dat' and store them in an ArrayList object.
        ArrayList<Assignment>assignmentsArray = fileToArrayListAssignment("input.dat");
        assignmentsArray.forEach(assignment -> System.out.println("Assignment in array: " + assignment));

        //TODO Remove any duplicate assignments.
        ArrayList<Assignment> uniqueAssignments = removeDuplicateAssignments(assign1, assign2, assign3);
        System.out.println("The unique assignments are " + uniqueAssignments);
    }

    private static ArrayList<Assignment> removeDuplicateAssignments(Assignment ... assignments) {
        ArrayList<Assignment> assignmentsArray = new ArrayList<>();
        Collections.addAll(assignmentsArray, assignments);
        return removeDuplicateAssignments(assignmentsArray);
    }

    private static ArrayList<Assignment> removeDuplicateAssignments(ArrayList<Assignment> assignmentsArray){
        return new ArrayList<>(new HashSet<>(assignmentsArray));
    }

    private static ArrayList<Assignment> fileToArrayListAssignment(String fileName) {
        ArrayList<Assignment>returnArray = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(fileName)) ) {
            stream.forEach(line -> returnArray.add(Assignment.valueOf(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnArray;
    }

    private static void generateAssignmentsFile(String fileName, int numOfAssignments) {
        File outfile = new File(fileName);
        int count = numOfAssignments;
        try(PrintWriter pw = new PrintWriter(outfile))
        {
            while (count != 0){
                pw.println(randomAssignmentGenerator());
                count--;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Assignment randomAssignmentGenerator(){
        return new Assignment(randomDateGenerator(), Courses.randomCourse(), Category.randomCategory(), rand.nextInt(4));
    }

    private static Assignment earliestAssignment (Assignment ... assignments){
        Assignment earliest = new Assignment(assignments[0]);
        for (Assignment assignment: assignments) {
            if (earliest.getDate().compareTo(assignment.getDate()) > 0){ earliest = new Assignment(assignment); }
        }
        return earliest;
    }

    private static String compareToResult(Assignment assignment1, Assignment assignment2) {
        int value = assignment1.compareTo(assignment2);
        if (value < 0) { return "BEFORE"; }
        else if (value > 0) { return "AFTER"; }
        else { return "EQUALS"; }
    }

    private static String formattedDate(LocalDateTime date) {
        String newDate = "";
        newDate += date.format(DateTimeFormatter.ofPattern("MMMM "));
        newDate += intToOrdinal(date.getDayOfMonth());
        newDate += date.format(DateTimeFormatter.ofPattern(", yyyy"));
        return newDate;
    }

    private static String intToOrdinal(int num){
        String[] suffixes = new String[] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        if (num % 100 == 11 || num % 100 == 12 || num % 100 == 13) { return num + "th"; }
        else { return num + suffixes[num % 10]; }
    }

    private static int indexEarliestTime(ArrayList<LocalDateTime> dateList){
        LocalDateTime earliestDateTime = dateList.get(0);
        for (LocalDateTime date: dateList) {
            if (earliestDateTime.toLocalTime().isAfter(date.toLocalTime())) earliestDateTime = date;
        }
        return dateList.indexOf(earliestDateTime);
    }

    private static int indexLatestDate(ArrayList<LocalDateTime> dateList) {
        return dateList.indexOf(Collections.max(dateList));
    }

    private static int mapByMonthSearch (ArrayList<LocalDateTime> dateList, int month){
        return mapByMonth(dateList).get(month);
    }

    private static Map<Integer, Integer> mapByMonth(ArrayList<LocalDateTime> dateList) {
        Map<Integer,Integer> returnMap = new HashMap<>();
        for (LocalDateTime date : dateList) {
            Integer count = returnMap.get(date.getMonthValue());
            returnMap.put(date.getMonthValue(), (count == null) ? 1 : count + 1);
        }
        return returnMap;
    }

    private static ArrayList<LocalDateTime> searchByMonth(ArrayList<LocalDateTime> dateList, int month) {
        return (ArrayList) dateList.stream()
                .filter( date -> date.getMonthValue() == month)
                .collect(Collectors.toList());
    }

    private static ArrayList searchDatesInTimeframe (ArrayList<LocalDateTime> dateList, int startHour, int endHour){
        return (ArrayList) dateList.stream()
                .filter( date -> date.getHour() >= startHour && date.getHour() < endHour)
                .collect(Collectors.toList());
    }

    private static int countDuplicates(ArrayList<LocalDateTime> hundredRandomDates) {
        int count = 0;
        for (LocalDateTime date: hundredRandomDates) {
            if (Collections.frequency(hundredRandomDates, date) >= 2) count++;
        }
        return count;
    }


    private static ArrayList<LocalDateTime> seekDuplicates(ArrayList<LocalDateTime> userList) {
        ArrayList<LocalDateTime> returnArray = new ArrayList<>();
        Set<LocalDateTime> dateSet = new HashSet<>();
        for (LocalDateTime date : userList) {
            if (dateSet.contains(date)) returnArray.add(date);
            dateSet.add(date);
        }
        return returnArray;
    }

    private static ArrayList<LocalDateTime> searchByYear(ArrayList<LocalDateTime> listOfLocalDateTimes, int year) {
        return (ArrayList) listOfLocalDateTimes.stream()
                .filter( date -> date.getYear()==year)
                .collect(Collectors.toList());
    }

    private static ArrayList<LocalDateTime> randomDateArray (int NumElements){
        ArrayList<LocalDateTime> returnArray = new ArrayList<>();
        for (int i = 0; i < NumElements; i++) { returnArray.add(randomDateGenerator()); }
        return returnArray;
    }

    private static LocalDateTime randomDateGenerator (){
        long startOfTime = ChronoUnit.MINUTES.between(LocalDateTime.of(0,1,1,0,0),LocalDateTime.now());
        long minutes = rand.nextInt((int) startOfTime);
        return LocalDateTime.now().minusMinutes(minutes);
    }

    private static LocalDateTime FindEarlierDate(LocalDateTime date1, LocalDateTime date2) {
        LocalDateTime earlyDate = date1;
        if (date2.isBefore(date1)) { earlyDate = date2; }
        return earlyDate;
    }
}
