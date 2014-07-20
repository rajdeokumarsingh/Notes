package com.techbydesign.magnolia.module.jcaptcha;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public final class CaptchaService {

	private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();

	private CaptchaService() {}
	
	public static ImageCaptchaService getInstance() { return instance; }
}