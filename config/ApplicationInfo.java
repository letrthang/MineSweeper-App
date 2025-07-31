package config;

/**
 * Application configuration and metadata
 */
public class ApplicationInfo {
    public static final String APP_NAME = "Minesweeper app";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR = "Thang Le";
    public static final String JAVA_VERSION = "17";

    /**
     * Display application information
     */
    public static void displayInfo() {
        System.out.println("=================================");
        System.out.println("Application: " + APP_NAME);
        System.out.println("Version: " + VERSION);
        System.out.println("Author: " + AUTHOR);
        System.out.println("Java Version: " + JAVA_VERSION);
        System.out.println("=================================");
    }

    private ApplicationInfo() {

    }
}
