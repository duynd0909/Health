package HealthDeclaration.controller;

import HealthDeclaration.qrcode.ZXingHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/qrcode")
@Log4j2
@CrossOrigin(origins = "http://localhost:8181")
public class QRCodeController {
    @RequestMapping(value = "/link", method = RequestMethod.GET)
    public void qrcode(@RequestParam String text, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingHelper.getQRCodeImage(text, 200, 200));
        outputStream.flush();
        outputStream.close();
    }
}
