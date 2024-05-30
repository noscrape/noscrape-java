import org.junit.jupiter.api.Test;
import org.noscrape.Noscrape;

import java.io.IOException;

public class NoscrapeTest {

    @Test
    void testNoscrape() throws IOException, InterruptedException {
        String fontPath = System.getenv().get("FONT_PATH");

        Noscrape noscrape = new Noscrape(fontPath);

        String obfuscatedString = noscrape.obfuscate("test");

        String rendered = noscrape.render();

        assert(!obfuscatedString.isBlank());
        assert(!rendered.isBlank());
    }

}
