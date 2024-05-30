package org.noscrape;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LibLoader {

    public static String loadLibrary() {
        String os = OSDetector.get();
        String arch = ArchitectureDetector.get();

        String binaryPathString =  String.format("/bin/noscrape_%s_%s", os, arch);

        URL binaryResource  = LibLoader.class.getResource(binaryPathString);

        if (binaryResource == null) {
            throw new RuntimeException("OS/Architecture combination is not supported: " + os + "/" + arch);
        }

        Path binaryPath = Paths.get(binaryResource.getPath());

        return binaryPath.toAbsolutePath().toString();
    }
}
