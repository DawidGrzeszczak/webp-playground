package io.github.akwiatek.webpencoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

public class Main {

  public static void main(String... args) throws IOException {
    new Main().runViaJni();
  }

  private void runViaJni() throws IOException {
    byte[] pngBytes;
    try (var is = getClass().getResourceAsStream("screenshot.png")) {
      pngBytes = IOUtils.toByteArray(is);
    }
    System.out.println("pngBytes: " + pngBytes.length);

    byte[] webpBytes = pngToWebp(pngBytes);
    try (var os = new FileOutputStream("/tmp/screenshot.webp")) {
      os.write(webpBytes);
    }
    System.out.println("webpBytes: " + webpBytes.length);
  }

  public byte[] pngToWebp(byte[] pngBytes) throws IOException {
    try (
        var bais = new ByteArrayInputStream(pngBytes);
        var baos = new ByteArrayOutputStream();
        var ios = ImageIO.createImageOutputStream(baos);
    ) {
      // ImageIO closes this stream automatically
      var iis = ImageIO.createImageInputStream(bais);
      boolean encoded = ImageIO.write(ImageIO.read(iis), "webp", ios);
      if (!encoded) {
        throw new IIOException("Could not encode as WebP");
      }
      return baos.toByteArray();
    }
  }
}
