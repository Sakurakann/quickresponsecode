package com.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.deploy.net.HttpResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Project: quickresponsecode.
 * Package: com.qrcode.
 * User: Administrator.
 * Date: 2017-09-28 10:20.
 * Author: Haiyangp.
 */
public class QuickResCode {

  private void qrCodeToFile(String contents, String charSet, int width, int height, String format,
      String file)
      throws WriterException, IOException {
    Map<EncodeHintType, String> encodeHintMap = new HashMap<EncodeHintType, String>();
    encodeHintMap.put(EncodeHintType.CHARACTER_SET, charSet);

    BitMatrix bitMatrix = new MultiFormatWriter()
        .encode(contents, BarcodeFormat.QR_CODE, width, height, encodeHintMap);
    Path filePath = Paths.get(file);
    MatrixToImageWriter.writeToPath(bitMatrix, format, filePath);
  }

  private void qrCodeToStream(String contents, String charSet, int width, int height , String format,
      OutputStream outputStream) throws WriterException, IOException {
    Map<EncodeHintType, String> encodeHintMap = new HashMap<EncodeHintType, String>();
    encodeHintMap.put(EncodeHintType.CHARACTER_SET, charSet);

    BitMatrix bitMatrix = new MultiFormatWriter()
        .encode(contents, BarcodeFormat.QR_CODE, width, height, encodeHintMap);
    MatrixToImageWriter.writeToStream(bitMatrix,format,outputStream);
  }

  private void qrCodeToStream(String contents, String charSet, int width, int height ,String format,
      HttpServletResponse response) throws WriterException, IOException {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setContentType("image/" + format);

    Map<EncodeHintType, String> encodeHintMap = new HashMap<EncodeHintType, String>();
    encodeHintMap.put(EncodeHintType.CHARACTER_SET, charSet);

    BitMatrix bitMatrix = new MultiFormatWriter()
        .encode(contents, BarcodeFormat.QR_CODE, width, height, encodeHintMap);
    MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
  }

  public static void main(String[] args) {
    QuickResCode quickResCode = new QuickResCode();
    // png格式图片占用空间最小,
    try {
      quickResCode.qrCodeToFile("你知道我是谁嘛", "utf-8", 300,
          300, "png", "d:" + File.separator + "a.png");
      System.out.println("qr success");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (WriterException e) {
      e.printStackTrace();
    }
  }
}

