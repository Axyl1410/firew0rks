import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class fireworks {
  private static final String OS = System.getProperty("os.name").toLowerCase();

  public static void main(String[] args) {
    // Show help if requested
    if (args.length > 0 && args[0].equals("--help")) {
      showHelp();
      System.exit(0);
    }

    // Use default values
    String folderName = args.length > 0 ? args[0] : "fireworks";
    int loops = args.length > 1 ? Integer.parseInt(args[1]) : 20;

    // Resolve folder path relative to package location
    Path folderPath = Paths.get(folderName);

    if (!Files.exists(folderPath)) {
      System.out.println(folderName + " could not be found");
      System.exit(0);
    }

    List<String> textFrames = loadTextFrames(folderPath);

    if (textFrames.isEmpty()) {
      System.out.println(folderName + " did not have text art files");
      System.exit(0);
    }

    try {
      playAnimation(textFrames, loops);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Animation interrupted");
    }
  }

  private static void showHelp() {
    System.out.println();
    System.out.println("Play text art animations in the terminal\n");
    System.out.println("Usage: java TextArtAnimator [folder] [loops]");
    System.out.println("\t[folder]\tFolder containing text art frames (default: fireworks)");
    System.out.println("\t[loops]\t\tNumber of times to loop the animation or use -1 to loop until the user terminates the program (default: 20)");
    System.out.println();
  }

  private static List<String> loadTextFrames(Path folderPath) {
    List<String> textFrames = new ArrayList<>();
    int frameIndex = 0;

    while (true) {
      Path filePath = folderPath.resolve(frameIndex + ".txt");

      if (Files.exists(filePath)) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
          StringBuilder content = new StringBuilder();
          String line;
          while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
          }
          textFrames.add(content.toString());
        } catch (IOException e) {
          throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
        frameIndex++;
      } else {
        break;
      }
    }

    return textFrames;
  }

  private static void playAnimation(List<String> textFrames, int loops) throws InterruptedException {
    int loopCount = 0;

    while (loops == -1 || loopCount < loops) {
      for (String frame : textFrames) {
        clearConsole();
        System.out.print(frame + System.lineSeparator());
        Thread.sleep(50); // 0.05 seconds
      }
      loopCount++;
    }
  }

  private static void clearConsole() {
    try {
      if (OS.contains("win")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (IOException | InterruptedException ex) {
      throw new RuntimeException("Error clearing console", ex);
    }
  }
}
