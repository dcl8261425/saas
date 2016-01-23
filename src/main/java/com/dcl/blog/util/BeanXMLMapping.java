package com.dcl.blog.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.dcl.blog.model.dto.MessageXML;
import com.dcl.blog.model.dto.MessageXMLItem;
import com.wutka.jox.JOXBeanInputStream;
import com.wutka.jox.JOXBeanOutputStream;

public class BeanXMLMapping {

  /**
   *  Retrieves a bean object for the
   *  received XML and matching bean class
   */
  public static Object fromXML(String xml, Class className) {
    ByteArrayInputStream xmlData = new ByteArrayInputStream
    (xml.getBytes());
    JOXBeanInputStream joxIn = new JOXBeanInputStream(xmlData);
    try {
      return (Object) joxIn.readObject(className);
    } catch (IOException exc) {
      exc.printStackTrace();
      return null;
    } finally {
      try {
        xmlData.close();
        joxIn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   *  Returns an XML document.nbspString for the received bean
   */
  public static String toXML(Object bean) {
    ByteArrayOutputStream xmlData = new ByteArrayOutputStream();
    JOXBeanOutputStream joxOut = new JOXBeanOutputStream(xmlData);
    try {
      joxOut.writeObject(beanName(bean), bean);
      return xmlData.toString();
    } catch (IOException exc) {
      exc.printStackTrace();
      return null;
    } finally {
      try {
        xmlData.close();
        joxOut.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   *  Find out the bean class name
   */
  private static String beanName(Object bean) {
    String fullClassName = bean.getClass().getName();
    String classNameTemp = fullClassName.substring(
        fullClassName.lastIndexOf(".") + 1,
        fullClassName.length()
        );
    return classNameTemp.substring(0, 1)
         + classNameTemp.substring(1);
  }
  /**
   * 获取微信格式的xml
   */
  public static String getWeiXinXml(String xml){
		xml=xml.substring(xml.indexOf(">")+1);
		xml=xml.substring(xml.indexOf(">")+1);
		xml=xml.substring(0,xml.lastIndexOf("<"));
		xml=replaceBlank(xml);
		xml=xml.replace("<![CDATA[<![CDATA[", "<![CDATA[");
		xml=xml.replace("]]>]]>", "]]>");
		System.out.print(xml.indexOf("picUrl"));
		if(xml.indexOf("toUserName")!=-1){
			xml=xml.replace("toUserName", "ToUserName");
		}
		if(xml.indexOf("fromUserName")!=-1){
			xml=xml.replace("fromUserName", "FromUserName");
		}
		if(xml.indexOf("createTime")!=-1){
			xml=xml.replace("createTime", "CreateTime");
		}
		if(xml.indexOf("msgType")!=-1){
			xml=xml.replace("msgType", "MsgType");
		}
		if(xml.indexOf("content")!=-1){
			xml=xml.replace("content", "Content");
		}
		if(xml.indexOf("mediaId")!=-1){
			xml=xml.replace("mediaId", "MediaId");
		}
		if(xml.indexOf("title")!=-1){
			xml=xml.replace("title", "Title");
		}
		if(xml.indexOf("description")!=-1){
			xml=xml.replace("description", "Description");
		}
		if(xml.indexOf("musicURL")!=-1){
			xml=xml.replace("musicURL", "MusicURL");
		}
		if(xml.indexOf("hQMusicUrl")!=-1){
			xml=xml.replace("hQMusicUrl", "HQMusicUrl");
		}
		if(xml.indexOf("thumbMediaId")!=-1){
			xml=xml.replace("thumbMediaId", "ThumbMediaId");
		}
		if(xml.indexOf("articles")!=-1){
			xml=xml.replace("articles", "Articles");
		}
		
		if(xml.indexOf("picUrl")!=-1){
			xml=xml.replace("picUrl", "PicUrl");
		}
		if(xml.indexOf("url")!=-1){
			xml=xml.replace("url", "Url");
		}
		if(xml.indexOf("articleCount")!=-1){
			xml=xml.replace("articleCount", "ArticleCount");
		}
		//xml=xml.substring(xml.indexOf("<msgType>"))+"<![CDATA["+xml.substring(xml.indexOf("<msgType>"),xml.indexOf("</msgType>")-10)+xml.substring(xml.indexOf("</msgType>"),xml.length());
		return "<xml>"+xml+"</xml>";
  }
  public static String replaceBlank(String str) {

	          String dest = "";

	          if (str!=null) {

	              Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	              Matcher m = p.matcher(str);

	              dest = m.replaceAll("");

	          }

	          return dest;

	      }
  public static MessageXML getXMLToMessageXML(String xml) throws JDOMException, IOException{
	  SAXBuilder saxReader = new SAXBuilder();
	  StringReader reader = new StringReader(xml); 
	  InputSource source = new InputSource(reader);  
	  Document document = (Document) saxReader.build(source);
	  Element rootElement = document.getRootElement();

	  //System.out.println("节点个数:"+rootElement.getChildren("user").size());
	  MessageXML xml2=new MessageXML();
	 
	  String name=rootElement.getAttributeValue("name");
	  String result=rootElement.getAttributeValue("result");
	  xml2.setName(name);
	  xml2.setResult(result);
	  List<MessageXMLItem> items=new ArrayList<MessageXMLItem>();
	  for (int i = 0; i < rootElement.getChildren("Item").size(); i++) 
	  {
		  Element item = (Element) rootElement.getChildren("Item").get(i);
		  String cid=item.getAttributeValue("cid");
		  String sid=item.getAttributeValue("sid");
		  String msgid=item.getAttributeValue("msgid");	
		  String total=item.getAttributeValue("total");	
		  String price=item.getAttributeValue("price");	
		  String remain=item.getAttributeValue("remain");	
		  MessageXMLItem item2=new MessageXMLItem();
		  item2.setCid(cid);
		  item2.setMsgid(msgid);
		  item2.setPrice(price);
		  item2.setRemain(remain);
		  item2.setSid(sid);
		  item2.setTotal(total);
		  items.add(item2);
	  }
	  xml2.setItems(items);
	  return xml2;
}

}
