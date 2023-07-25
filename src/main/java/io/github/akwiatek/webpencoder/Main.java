package io.github.akwiatek.webpencoder;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class Main {

  public static void main(String... args) throws IOException {
    new Main().runViaJni(new WebpConvertService());
  }

  private void runViaJni(WebpConvertService service) throws IOException {
    byte[] pngBytes;
    try (var is = getClass().getResourceAsStream("screenshot.png")) {
      pngBytes = IOUtils.toByteArray(is);
    }
    System.out.println("pngBytes: " + pngBytes.length);

    byte[] webpBytes = service.pngToWebp(pngBytes);
    try (var os = new FileOutputStream("/tmp/screenshot.webp")) {
      os.write(webpBytes);
    }
    System.out.println("webpBytes: " + webpBytes.length);
  }

}
