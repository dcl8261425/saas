package com.dcl.blog.util;

public class EmailUtil {
	public static String rigisterEmail(String url){
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
"<head>"+
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"+
"<title>"+"Demystifying Email Design</title>"+
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>"+
"</head>"+
"<body style=\"margin: 0; padding: 0;\">"+
	"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+	
		"<tr>"+
			"<td style=\"padding: 10px 0 30px 0;\">"+
				"<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">"+
					"<tr>"+
						"<td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">"+
							"<img src=\"http://www.haida360.com/img/h1.gif\" alt=\"Creating Email Magic\" width=\"300\" height=\"230\" style=\"display: block;\" />"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
						
								"<tr>"+
									"<td>"+
										"请点击链接创建管理员账号:<br/>"+
										url+
									"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#eeac50\" style=\"padding: 30px 30px 30px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
								"<tr>"+
										"<td style=\" text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"40%\">"+
												"<img style=\"width: 140px; height: 140px;\" class=\"img-polaroid\" src=\"http://www.haida360.com/fileSrc/getOrgImg?filename=getOrgImg.jpg\">"+
										"</td>"+
									"<td align=\"right\" style=\"text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"60%\">"+

												"扫描微信二维码，快速浏览最新信息<br/>"+
													"【二手 租房 低价超市 招聘 求职】<br/>+【邢台新闻 搞笑漫画 搞笑视频】"
												
									+"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
								"<tr>"+
									"<td style=\" color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"60%\">"+
										"&reg; 海达微信息 www.haida360.com<br/>"+
										"&reg; 免费生成微信微网站 weixin.haida360.com<br/>"+
										"&reg; 免费教育网站 jiaoyu.haida360.com<br/>"+
									"</td>"+
									"<td align=\"right\" style=\" text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"40%\">"+
											
												"制作人电话:18617409699 董成龙 "+
									"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
				"</table>"+
			"</td>"+
		"</tr>"+
	"</table>"+
"</body>"+
"</html>";
		return html;
	}
	public static String EmailContent(String content){
		String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
"<head>"+
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"+
"<title>"+"Demystifying Email Design</title>"+
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>"+
"</head>"+
"<body style=\"margin: 0; padding: 0;\">"+
	"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+	
		"<tr>"+
			"<td style=\"padding: 10px 0 30px 0;\">"+
				"<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">"+
					"<tr>"+
						"<td align=\"center\" bgcolor=\"#70bbd9\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">"+
							"<img src=\"http://www.haida360.com/img/h1.gif\" alt=\"Creating Email Magic\" width=\"300\" height=\"230\" style=\"display: block;\" />"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
						
								"<tr>"+
									"<td>"+
									content+
									"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#eeac50\" style=\"padding: 30px 30px 30px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
								"<tr>"+
										"<td style=\" text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"40%\">"+
												"<img style=\"width: 140px; height: 140px;\" class=\"img-polaroid\" src=\"http://www.haida360.com/fileSrc/getOrgImg?filename=getOrgImg.jpg\">"+
										"</td>"+
									"<td align=\"right\" style=\"text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"60%\">"+

												"扫描微信二维码，快速浏览最新信息<br/>"+
													"【二手 租房 低价超市 招聘 求职】<br/>+【邢台新闻 搞笑漫画 搞笑视频】"
												
									+"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
					"<tr>"+
						"<td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">"+
							"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"+
								"<tr>"+
									"<td style=\" color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"60%\">"+
										"&reg; 海达微信息 www.haida360.com<br/>"+
										"&reg; 免费生成微信微网站 weixin.haida360.com<br/>"+
										"&reg; 免费教育网站 jiaoyu.haida360.com<br/>"+
									"</td>"+
									"<td align=\"right\" style=\" text-align: center; color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"40%\">"+
											
												"制作人电话:18617409699 董成龙 "+
									"</td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
					"</tr>"+
				"</table>"+
			"</td>"+
		"</tr>"+
	"</table>"+
"</body>"+
"</html>";
		return html;
	}
}
