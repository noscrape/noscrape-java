package org.noscrape;

public class OSDetector {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isLinux() {
        return (OS.contains("nux"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    public static String get() {
        if (isWindows()) {
            return "windows";
        } else if (isMac()) {
            return "darwin";
        } else if (isLinux()) {
            return "linux";
        } else if (isUnix()) {
            return "unix";
        } else if (isSolaris()) {
            return "solaris";
        }

        throw new RuntimeException("Unsupported OS: " + OS);
    }
}