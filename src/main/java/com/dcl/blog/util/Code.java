package com.dcl.blog.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
/**
 * 生成验证码
 * @author Administrator
 *
 */
public class Code {
	private int width = 70;
	private int height = 30;
	public static final String CODE="code";
	public static final String IMG="img";
	public Map<String, Object> getImage() {
		Map<String,Object> map=new HashMap<String, Object>();
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 设定图像背景色(因为是做背景，所以偏淡)
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
		g.setFont(font);
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int length = 4;
		// 设置备选验证码:包括"a-z"和数字"0-9" 68
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int size = base.length();
		for (int i = 0; i < length; i++) {
			int start = random.nextInt(size);
			String strRand = base.substring(start, start + 1);
			// 用随机产生的颜色将验证码绘制到图像中。
			// 生成随机颜色(因为是做前景，所以偏深)
			// g.setColor(getRandColor(1, 100));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(strRand, 15 * i + 6, 24);
			randomCode.append(strRand);
		}
		map.put(Code.CODE, randomCode.toString().toLowerCase());
		g.dispose();
		map.put(Code.IMG, buffImg);
		 // 将图像输出到Servlet输出流中。
		return map;
	}

	private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	public static boolean testCode(HttpServletRequest req){
		String code=(String) req.getSession().getAttribute(SessionString.SEESION_CODE);
		String code2=req.getParameter("testcode");
		boolean b=false;
		if(code2!=null&&code!=null){
			b=code.equals(code2.toLowerCase());
			req.getSession().removeValue(SessionString.SEESION_CODE);
		}
		return b;
	}
}
