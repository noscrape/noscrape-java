package org.noscrape;

public class ArchitectureDetector {

    private static final String ARCHITECTURE = System.getProperty("os.arch").toLowerCase();

    private static boolean isArm64() {
        return (ARCHITECTURE.contains("aarch64") || ARCHITECTURE.contains("arm64"));
    }

    private static boolean isX86_64() {
        return (ARCHITECTURE.contains("amd64") || ARCHITECTURE.contains("x86_64"));
    }

    private static boolean isX86() {
        return (ARCHITECTURE.contains("x86") || ARCHITECTURE.contains("i386") || ARCHITECTURE.contains("i486") || ARCHITECTURE.contains("i586") || ARCHITECTURE.contains("i686"));
    }

    private static boolean isArm() {
        return (ARCHITECTURE.contains("arm"));
    }

    public static String get() {
        if (isArm64()) {
            return "arm64";
        } else if (isX86_64()) {
            return "x86_64";
        } else if (isX86()) {
            return "x86";
        } else if (isArm()) {
            return "arm";
        }

        throw new RuntimeException("Unsupported architecture: " + ARCHITECTURE);
    }
}