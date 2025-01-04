import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class fireworks {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String[] COLORS = {
        "\u001B[31m", // RED
        "\u001B[32m", // GREEN
        "\u001B[33m", // YELLOW
        "\u001B[34m", // BLUE
        "\u001B[35m", // PURPLE
        "\u001B[36m"  // CYAN
    };
    
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Show help if requested
        if (args.length > 0 && args[0].equals("--help")) {
            System.out.println();
            System.out.println("Play text art animations in the terminal\n");
            System.out.println("Usage: java Firew0rks [folder] [loops]");
            System.out.println("\t[folder]\tFolder containing text art frames (default: fireworks)");
            System.out.println("\t[loops]\t\tNumber of times to loop the animation or use -1 to loop until the user terminates the program (default: 20)");
            System.out.println();
            System.exit(0);
        }

        // Use default values if no arguments provided
        String folderName = args.length > 0 ? args[0] : "fireworks";
        int loops = args.length > 1 ? Integer.parseInt(args[1]) : 20;

        // Resolve folder path relative to package location
        Path folderPath = Paths.get(folderName);

        if (!Files.exists(folderPath)) {
            System.out.println(folderName + " could not be found");
            System.exit(0);
        }

        List<String> textFiles = new ArrayList<>();
        int numFound = 0;
        boolean filesExist = true;

        while (filesExist) {
            Path fileName = folderPath.resolve(numFound + ".txt");

            if (Files.exists(fileName)) {
                try {
                    textFiles.add(new String(Files.readAllBytes(fileName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                numFound++;
            } else {
                filesExist = false;
            }
        }

        if (textFiles.isEmpty()) {
            System.out.println(folderName + " did not have text art files");
            System.exit(0);
        }

        // Enable ANSI support for Windows
        try {
            new ProcessBuilder("cmd", "/c", "color").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Ignore if it fails
        }

        try {
            clearScreen();
            playAnimation(textFiles, loops);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void playAnimation(List<String> textFiles, int loops) throws InterruptedException {
        int i = 0;
        boolean first = true;
        // Calculate number of lines to move up based on first frame
        String backspaceAdjust = "\033[A".repeat(textFiles.get(0).split("\n").length + 1);

        while (i < loops || loops == -1) {
            for (String frame : textFiles) {
                if (!first) {
                    System.out.print(backspaceAdjust);
                }

                // Add random color to each frame
                String color = COLORS[random.nextInt(COLORS.length)];
                System.out.print(color + frame + RESET + "\n");
                
                first = false;
                Thread.sleep(50); // 0.05 seconds
            }
            i++;
        }
    }
}