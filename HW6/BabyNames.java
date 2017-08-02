// CS210 Assignment #6 "baby names"
// David Johnson
//This program takes input from a user in the form of a baby name
// and returns the populatrity of that name since 1920.
// The statistics will also be written to a file
import java.io.*;   // For file I/O
import java.util.*; // For scanners
import java.awt.*;  // For drawing

public class BabyNames {
    public static final int SCALINGFACTOR = 15;
    public static final int WIDTH = 50; // Width of the data bars in the graph
    public static final int HEIGHT = 20; // Height of the data bard in the graph

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        Scanner names = new Scanner(new File("names.txt"));
        // Make sure the file is there
        
        
        System.out.println("** Popularity of a baby name since year 1920 **");
        // Get user input
        String name = getName(console);
        // Searh for the input name
        findName(name, names);
    }
    
    public static void findName(String inputName, Scanner file) throws FileNotFoundException {
        // Attempts to find the user input name in the data from the file
        // provided.  If found, it will get the popularity data
        Boolean found = false;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            Scanner nameData = new Scanner(line);
            String lineName = nameData.next();
            // Interesting note here:  inputName == lineName never returned
            // true, so I switched it to inputName.equals()
            // Why?
            if (inputName.equalsIgnoreCase(lineName)) {
                found = true;
                getPopularity(lineName, nameData);
            } 
        }
        if (!found) {
            System.out.println("name not found.");
        }
    }
    
    public static void getPopularity(String name, Scanner data) throws FileNotFoundException {
        // This function is sent a line of data matching the user input name
        // It will then print out the results
        DrawingPanel panel = new DrawingPanel(500,400);
        panel.setBackground(Color.GRAY);
        Graphics g = panel.getGraphics();
        g.setColor(Color.WHITE);
        drawGraphBackground(g);
        g.drawString(name, 450 / 2 - name.length(), 15);
        g.drawLine(0, 20, 500, 20);
        PrintStream outFile = new PrintStream(new File(name + ".txt"));
        if (data.hasNext()) {
            int year = 1920;
            int yearCount = 0;
            outFile.println(name + ",");
            // fencepost
            int popularity = data.nextInt();
            System.out.println(year + ": " + popularity);
            outFile.print(year + ": " + popularity);
            while (data.hasNextInt()) {
                outFile.println(",");
                year += 10;
                popularity = data.nextInt();
                System.out.println(year + ": " + popularity);
                outFile.print(year + ": " + popularity);
                g.fillRect(yearCount * WIDTH, (popularity / 2) + HEIGHT, WIDTH / 2, 400 - (popularity / 2) - HEIGHT);
                yearCount++;
            }
        }
    }
    
    public static void drawGraphBackground(Graphics g) {
        // Draws the background for the graph
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 500, HEIGHT);
        g.fillRect(0, 400 - HEIGHT, 500, 400);
        g.setColor(Color.BLACK);
        g.drawLine(0, HEIGHT, 500, HEIGHT);
        g.drawLine(0, 400 - HEIGHT, 500, 400 - HEIGHT);
        g.drawRect(0, 0, 499, 399);
    }
    
    public static String getName(Scanner input) {
        // Gets user input name and returns it to caller
        System.out.print("name? ");
        return input.next();
    }
}

