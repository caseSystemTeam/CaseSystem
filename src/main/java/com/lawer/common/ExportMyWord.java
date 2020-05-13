package com.lawer.common;

import freemarker.template.*;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * className: ExportMyWord
 * description: TODO
 *
 * @author hasee
 * @version 1.0
 * @date 2019/7/15 14:54
 */
public class ExportMyWord {
    private Configuration config = null;

    public ExportMyWord() {
        config = new Configuration();
        config.setDefaultEncoding("utf-8");
    }

    /**
     * FreeMarker生成Word
     *
     * @param dataMap      数据
     * @param templateName 目标名
     * @param saveFilePath 保存文件路径的全路径名（路径+文件名）
     * @Author Huang Xiaocong 2018年12月15日 下午10:19:03
     */
    public void createWord(Map<String, Object> dataMap, String templateName, String saveFilePath,ServletContext context) {
        //加载模板(路径)数据
        //config.setClassForTemplateLoading(this.getClass(), "/templates");  //根据当前所在路径查找文件
        //配置到webapp下才能访问，放到/resources/freemarker下边访问不了
        config.setServletContextForTemplateLoading(context,"/freemarker");  //根据servlet路径找文件
        //设置异常处理器 这样的话 即使没有属性也不会出错 如：${list.name}...不会报错
        config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        Template template = null;
        if (templateName.endsWith(".ftl")) {
            templateName = templateName.substring(0, templateName.indexOf(".ftl"));
        }
        try {
            template = config.getTemplate(templateName + ".ftl");
        } catch (TemplateNotFoundException e) {
            //log.info("模板文件未找到");
            System.out.println("模板文件未找到");
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            //log.info("模板类型不正确");
            System.out.println("模板类型不正确");
            e.printStackTrace();
        } catch (IOException e) {
            //log.info("IO读取失败");
            System.out.println("IO读取失败");
            e.printStackTrace();
        }
        File outFile = new File(saveFilePath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            //log.info("输出文件时未找到文件");
            System.out.println("输出文件时未找到文件");
            e.printStackTrace();
        }
        out = new BufferedWriter(new OutputStreamWriter(fos));
        //将模板中的预先的代码替换为数据
        try {
            template.process(dataMap, out);
        } catch (TemplateException e) {
            //log.info("填充模板时异常");
            System.out.println("填充模板时异常");
            e.printStackTrace();
        } catch (IOException e) {
            //log.info("IO读取时异常");
            System.out.println("IO读取时异常");
            e.printStackTrace();
        }
        System.out.println("由模板文件：" + templateName + ".ftl" + " 生成文件 ：" + saveFilePath + " 成功！！");
        try {
            out.close();//web项目不可关闭
        } catch (IOException e) {
            //log.info("关闭Write对象出错");
            System.out.println("关闭write对象出错");
            e.printStackTrace();
        }
    }

    /**
     * 获得图片的Base64编码
     *
     * @param //imgFile
     * @return
     * @Author Huang Xiaocong 2018年12月15日 下午10:15:10
     */
//    public String getImageStr(String imgFile) {
//        InputStream in = null;
//        byte[] data = null;
//        try {
//            in = new FileInputStream(imgFile);
//        } catch (FileNotFoundException e) {
//            log.info("加载图片未找到");
//            e.printStackTrace();
//        }
//        try {
//            data = new byte[in.available()];
//            //注：FileInputStream.available()方法可以从输入流中阻断由下一个方法调用这个输入流中读取的剩余字节数
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            log.info("IO操作图片错误");
//            e.printStackTrace();
//        }
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data);
//
//    }

//    public static void main(String[] args) {
//        ExportMyWord emw = new ExportMyWord();
//        Map<String, Object> dataMap = new HashMap<String, Object>();
//        dataMap.put("name", "傅泽鹏");
//        dataMap.put("age", 20);
//        dataMap.put("sex", "男");
//       // dataMap.put("imgheader", emw.getImageStr("D:\\图片\\130.png"));
//        dataMap.put("phoneNum", "135880***0");
//        dataMap.put("project", "计算机");
//        dataMap.put("address", "杭州");
//        dataMap.put("natureWork", "全职");
//        dataMap.put("industry", "IT");
//        dataMap.put("application", "Java开发");
//        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        Map<String, Object> map2 = new HashMap<String, Object>();
//        Map<String, Object> map3 = new HashMap<String, Object>();
//        map.put("time", "2017年-2021年");
//        map.put("schoolname", "浙江科技学院");
//        map.put("education", "本科");
//        map.put("glory", "荣誉");
//        map2.put("time", "2014年-2017年");
//        map2.put("schoolname", "瑞安四中");
//        map2.put("education", "高中");
//        map2.put("glory", "荣誉");
//        map3.put("time", "2011年-2014年");
//        map3.put("schoolname", "莘塍一中");
//        map3.put("education", "初中");
//        map3.put("glory", "荣誉");
//        newsList.add(map);
//        newsList.add(map2);
//        newsList.add(map3);
//        //注意list 的名字
//        dataMap.put("list", newsList);
//        dataMap.put("projectExperience", "辩论小程序开发");
//        dataMap.put("introduce", "喜欢二次元，编程的大学生");
//        emw.createWord(dataMap, "information_list_double.ftl", "D:\\测试.docx");
//    }
}
