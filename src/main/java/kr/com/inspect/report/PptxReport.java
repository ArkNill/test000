package kr.com.inspect.report;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Metadata;

@Service
@PropertySource(value = "classpath:properties/report.properties") 
public class PptxReport {
	@Value("${table.column0}") 
	private String column0;
	
	@Value("${table.column1}") 
	private String column1;
	
	@Value("${table.column2}") 
	private String column2;
	
	@Value("${table.column3}") 
	private String column3;
	
	@Value("${table.column4}") 
	private String column4;
	
	/* pptx 보고서 작성 */
	public void writePptx(String path, List<Metadata> list) {
		String pptxFileName = 
				new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) 
				+ "_log.pptx";
		
		XMLSlideShow ppt = new XMLSlideShow();
		for(int i=0; i<list.size(); i++) {
			XSLFSlide slide = ppt.createSlide(); //슬라이드 생성
			XSLFTextBox textBox= slide.createTextBox(); //텍스트 박스 생성 
			XSLFTextParagraph textParagraph = textBox.addNewTextParagraph(); //텍스트 단락 생성 
			XSLFTextRun textRun = textParagraph.addNewTextRun(); // 텍스트 생성
			textBox.setAnchor(new Rectangle(0, 0, 700, 32)); // 텍스트 박스의 좌표 및 크기 설정
			textRun.setText(list.get(i).toString());
			textRun.setFontColor(Color.black); // 텍스트 컬러 설정
			textRun.setFontSize((double) 10); // 텍스트 사이즈 설정 
			textRun.setBold(true); // 텍스트 볼드체 설정
		}
		
		File file = new File(path + pptxFileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			ppt.write(fos);
			System.out.println(path + pptxFileName);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if(ppt!=null) ppt.close();
				if(fos!=null) fos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		
	}
}
