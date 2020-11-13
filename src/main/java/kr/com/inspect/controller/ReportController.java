package kr.com.inspect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.com.inspect.dto.Utterance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.report.DocxReport;
import kr.com.inspect.report.HwpReport;
import kr.com.inspect.report.PptxReport;
import kr.com.inspect.report.XlsxReport;
import kr.com.inspect.service.PostgreService;

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class ReportController {
	/* PostgreSQL */
	@Autowired 
	private PostgreService postgreService;
	private List<Metadata>  metadata;
	private List<Utterance>  utterances;
	private Metadata meta;

	/* 파일 생성 */
	@Autowired
	private HwpReport hwpReport;
	
	@Autowired
	private DocxReport docxReport;
	
	@Autowired
	private XlsxReport xlsxReport;
	
	@Autowired
	private PptxReport pptxReport;
	
	@Value("${report.docx.directory}")
	private String docxPath;
	
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/* 한국어 강의 목록 리스트 파일로 출력 */
	@GetMapping("/metadata/{format}")
	public void writeMetadata(HttpServletRequest request,
							  HttpServletResponse response,
										Model model,
										@PathVariable String format) {
		metadata = postgreService.getMetadataAndProgram();
		String url = "";
		
		switch(format) {
			case ("hwp"): //한글 파일
				// hwpReport.writeHwp(hwpPath, list);
				url = "report/hwpReport";
				break;
			case ("docx"): //docx 파일
				docxReport.writeDocxMetadata(response, docxPath, metadata);
				url = "report/docxReport";
				break;
			case ("xlsx"): //xlsx 파일
				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata);
				url = "report/xlsxReport";
				break;
			case ("pptx"): //pptx 파일 
				// pptxReport.writePptx(pptxPath, list);
				url = "report/pptxReport";
				break;
			default:
				break;
		}
	}

	/* utterance 리스트 docx 파일로 출력 */
	@GetMapping("/utterance/docx/{format}")
	public void writeUtteranceDocx(HttpServletRequest request,
							  HttpServletResponse response,
							  Model model,
							  @PathVariable Integer format) {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);
		String url = "";

		docxReport.writeDocxUtterance(response, docxPath, utterances, meta);
	}

	/* utterance 리스트 xlsx 파일로 출력 */
	@GetMapping("/utterance/xlsx/{format}")
	public void writeUtteranceXlsx(HttpServletRequest request,
							   HttpServletResponse response,
							   Model model,
							   @PathVariable Integer format) {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);
		String url = "";

		xlsxReport.writeXlsxUtterance(response, xlsxPath, utterances, meta);
	}
}
