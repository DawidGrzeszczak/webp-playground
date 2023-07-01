package io.github.akwiatek.webpencoder;

import static java.util.Arrays.copyOfRange;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class MainTest {

  private static final byte[] PNG_SIGNATURE = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A,
      0x0A};
  private static final byte[] WEBP_SIGNATURE = {0x52, 0x49, 0x46, 0x46};

  private final Main main = new Main();

  @Test
  void findsInputPng() throws IOException {
    byte[] pngBytes = loadFile("screenshot.png");

    assertNotNull(pngBytes);
    assertTrue(isPng(pngBytes), "Expected PNG signature");
  }

  @Test
  void itEncodesPngToWepb() throws IOException {
    byte[] pngBytes = loadFile("screenshot.png");
    byte[] webpBytes = main.pngToWebp(pngBytes);

    assertNotNull(webpBytes);
    assertTrue(isWebp(webpBytes), "Expected WebP signature");
  }

  private byte[] loadFile(String filename) throws IOException {
    byte[] pngBytes;
    try (var is = getClass().getResourceAsStream(filename)) {
      pngBytes = IOUtils.toByteArray(is);
    }
    return pngBytes;
  }

  private static boolean isPng(byte[] bytes) {
    return Arrays.equals(copyOfRange(bytes, 0, 8), PNG_SIGNATURE);
  }

  private static boolean isWebp(byte[] bytes) {
    return Arrays.equals(copyOfRange(bytes, 0, 4), WEBP_SIGNATURE);
  }
}