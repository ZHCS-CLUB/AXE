package com.chinare.axe.captcha;

import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.web.servlet.View;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class CaptchaView implements View {

    ImageVerification imageVerification;

    public static final String CAPTCHA_SESSION_KEY = "CAPTCHA-SESSION-KEY-R-K-D";

    Log logger = Logs.get();

    public CaptchaView() {
        super();
        this.imageVerification = new ImageVerification(4, new DefaultCaptchaGener());
    }

    public CaptchaView(CaptchaGener captchaGener, int length) {
        super();
        this.imageVerification = new ImageVerification(length, captchaGener);
    }

    public CaptchaView(CaptchaGener captchaGener) {
        super();
        this.imageVerification = new ImageVerification(4, captchaGener);
    }

    public CaptchaView(int length) {
        super();
        this.imageVerification = new ImageVerification(length, new DefaultCaptchaGener());
    }

    public CaptchaView(ImageVerification imageVerification) {
        super();
        this.imageVerification = imageVerification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.View#getContentType()
     */
    @Override
    public String getContentType() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.View#render(java.util.Map,
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletresponseonse)
     */
    @Override
    public void render(Map<String, ?> model,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws Exception {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream out = response.getOutputStream();
        if (ImageIO.write(imageVerification.creatImage(), "JPEG", out)) {
            logger.debug("写入输出流成功:" + imageVerification.getVerifyCode() + ".");
        } else {
            logger.debug("写入输出流失败:" + imageVerification.getVerifyCode() + ".");
        }
        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_SESSION_KEY, imageVerification.getVerifyCode());
        out.flush();
        out.close();
    }

}
