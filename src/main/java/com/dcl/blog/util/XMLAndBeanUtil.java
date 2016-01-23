package com.dcl.blog.util;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

public class XMLAndBeanUtil {
    /**

     * 将xml字符串转化为对象

     *

     * @param xmlString

     *            xml字符串

     * @param className

     *            类得全称（包名+类名）字符串

@param  cl

     *            对象的class名称



     * @return 转化成的对象

     */

     public Object xmlString2Object(String xmlString ,String className,Class cl) {

        // 创建一个读取xml文件的流

        StringReader xmlReader = new StringReader(xmlString);

        // 创建一个BeanReader实例，相当于转化器

        BeanReader beanReader = new BeanReader();

        //配置BeanReader实例 

   beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false); 

   beanReader.getBindingConfiguration().setMapIDs(false); //不自动生成ID 



   //注册要转换对象的类，并指定根节点名称 

   try {

               //beanReader.registerBeanClass("SelectUserIDListBean", SelectUserIDListBean.class);

               beanReader.registerBeanClass(className,cl);

     } catch (IntrospectionException e1) {

               // TODO Auto-generated catch block

               e1.printStackTrace();

     } 

        // 将XML解析Java Object

        Object obj = null;

        try {

         obj = beanReader.parse(xmlReader);

        } catch (IOException e) {

         e.printStackTrace();



        } catch (SAXException e) {

         e.printStackTrace();

        }

        return obj;

     }



     /**

     * 将对象转换为xml字符串

     */

     public String Object2XmlString(Object object) {

        String xmlString = null;

        // 创建一个输出流，将用来输出Java转换的XML文件

        StringWriter outputWriter = new StringWriter();

        // 输出XML的文件头

        outputWriter.write("<?xml version='1.0' ?>\n");

        // 创建一个BeanWriter实例，并将BeanWriter的输出重定向到指定的输出流

        BeanWriter beanWriter = new BeanWriter(outputWriter);

        // 配置BeanWriter对象

        beanWriter.getXMLIntrospector().getConfiguration()

          .setAttributesForPrimitives(false);

        beanWriter.getBindingConfiguration().setMapIDs(false);

        beanWriter.setWriteEmptyElements(true);

        try {

         beanWriter.write(object);

        } catch (Exception e) {

         e.printStackTrace();

        }

        xmlString = outputWriter.toString();

        // 关闭输出流

        try {

         outputWriter.close();

        } catch (IOException e) {

         e.printStackTrace();

        }

        return xmlString;

     }
}
