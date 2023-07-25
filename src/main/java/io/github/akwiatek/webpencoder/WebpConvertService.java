package io.github.akwiatek.webpencoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

public class WebpConvertService {

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
