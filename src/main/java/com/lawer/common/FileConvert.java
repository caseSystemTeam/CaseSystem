package com.lawer.common;

import java.io.*;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

public class FileConvert {

    /*
    word.doc格式转html
    返回值是新生成文件的html格式的存储路径
    @param filepath是查看文件所在的根目录
    @parm filename 是文件真实的名字 可能含有中文
    @parm targetPath 是生成html要存的地方
    @parm fileid，是要做为html文件的名字，因为url访问带中文会报错，固以文件id作为新文件名字
     */
    public String WordTOHtml(String rootpath,String filepath,String targetPath,String fileid) {
        //String filepath = "D:\\";
        //filename = "22.doc";
        try {

            InputStream input = new FileInputStream(filepath); //将文件真实地址传入，然后以流的形式读取
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument());
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content, PictureType pictureType,
                                          String suggestedName, float widthInches, float heightInches) {
                    return suggestedName;
                }
            });
            wordToHtmlConverter.processDocument(wordDocument);
            List pics = wordDocument.getPicturesTable().getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        pic.writeImageContent(new FileOutputStream(rootpath
                                + pic.suggestFullFileName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            outStream.close();
            String content = new String(outStream.toByteArray());

            FileUtils.writeStringToFile(new File(targetPath, fileid+".html"), content, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/upload/"+fileid+".html";
    }

    /*
    word.docx版本转为html文件

     */
    public String WordTOHtmlDocx(String filepath,String targetPath,String fileid) {
        //String filepath = "D:\\";
        String imagePath = targetPath ;
        String targetFileName = targetPath + fileid+".html";

        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(filepath));
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePath)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver(imagePath));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);

            outputStreamWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/upload/"+fileid+".html";
    }




    /*
    excel转html的方法

     */
    public String excelToHtml(String path,String file){

        HSSFWorkbook excelBook= null;
        try {
            InputStream input=new FileInputStream(path+file);
            excelBook = new HSSFWorkbook(input);
            ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter (DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() );
            excelToHtmlConverter.processWorkbook(excelBook);
            List pics = excelBook.getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get (i);
                    try {
                        pic.writeImageContent (new FileOutputStream (path + pic.suggestFullFileName() ) );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            Document htmlDocument =excelToHtmlConverter.getDocument();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource (htmlDocument);
            StreamResult streamResult = new StreamResult (outStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty (OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty (OutputKeys.INDENT, "yes");
            serializer.setOutputProperty (OutputKeys.METHOD, "html");
            serializer.transform (domSource, streamResult);
            outStream.close();

            String content = new String (outStream.toByteArray() );

            FileUtils.writeStringToFile(new File (path, "exportExcel.html"), content, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}
