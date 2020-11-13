package kr.com.inspect.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Metadata;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

@Service
@PropertySource(value = "classpath:properties/report.properties") 
public class HwpReport {
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
	
	/* hwp 보고서 작성 */
	public void writeHwp(String path, List<Metadata> list) {
		String hwpFileName = 
				new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) 
				+ "_log.hwp";
		
		try {
			HWPFile hwpFile = BlankFileMaker.make();
			Section s = hwpFile.getBodyText().getSectionList().get(0);
			Paragraph p = s.getParagraph(0);
			p.createText();
			ParaText pt = p.getText();
			for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
				pt.addString(list.get(rowIdx).toString());
				pt.addString("\n\n");
			}
			HWPWriter.toFile(hwpFile, path+hwpFileName);
		}catch(Exception e) {
			//e.printStackTrace();
		}
	}
}
