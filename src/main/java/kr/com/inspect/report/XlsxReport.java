package kr.com.inspect.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@PropertySource(value = "classpath:properties/report.properties")
public class XlsxReport {
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

	@Value("${table.column5}")
	private String column5;

	@Value("${tabl.e.column6}")
	private String column6;

	@Value("${table.column7}")
	private String column7;

	@Value("${table.column8}")
	private String column8;
	
	/* xlsx 한국어 강의 목록 리스트 작성 */
	public void writeXlsxMetadata(HttpServletResponse response, String path, List<Metadata> list) {
		String xlsxFileName =
				"LectureList_"+
				new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".xlsx"; //파일명
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		XSSFWorkbook workbook = new XSSFWorkbook(); //워크북
		XSSFSheet sheet = workbook.createSheet(); //워크시트
		XSSFRow row = sheet.createRow(0); //행
		XSSFCell cell; //셀
		cell = row.createCell(0);
		cell.setCellValue("날짜 : " + day);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("한국어 강의 목록 리스트");
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));


		row = sheet.createRow(4);
		/* 헤더 정보 구성 */
		cell = row.createCell(0);
		cell.setCellValue(column0);
		cell = row.createCell(1);
		cell.setCellValue("title");
		cell = row.createCell(2);
		cell.setCellValue("subtitle");
		cell = row.createCell(3);
		cell.setCellValue(column1);
		cell = row.createCell(4);
		cell.setCellValue("file_num");
		cell = row.createCell(5);
		cell.setCellValue("running_time");
		cell = row.createCell(6);
		cell.setCellValue("문장수");
		cell = row.createCell(7);
		cell.setCellValue("어절수");

		// 리스트의 size 만큼 row를 생성
		Metadata metadata;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			metadata = list.get(rowIdx);
			row = sheet.createRow(rowIdx+5); //행 생성
			cell = row.createCell(0);
			cell.setCellValue(metadata.getId());
			cell = row.createCell(1);
			cell.setCellValue(metadata.getProgram().getTitle());
			cell = row.createCell(2);
			cell.setCellValue(metadata.getProgram().getSubtitle());
			cell = row.createCell(3);
			cell.setCellValue(metadata.getCreator());
			cell = row.createCell(4);
			cell.setCellValue(metadata.getTitle());
			cell = row.createCell(5);
			cell.setCellValue(metadata.getProgram().getRunning_time());
			cell = row.createCell(6);
			cell.setCellValue(Integer.toString(metadata.getSentence_count()));
			cell = row.createCell(7);
			cell.setCellValue(Integer.toString(metadata.getEojeol_total()));
		}

		// 입력된 내용 파일로 쓰기
		String fullPath = path + xlsxFileName;
		File file = new File(fullPath);
		FileOutputStream fos = null;


		try {
			fos = new FileOutputStream(fullPath);
			workbook.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			response.setHeader("Content-Disposition", "attachment;filename="+xlsxFileName);
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentType("application/vnd.ms-excel");
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* xlsx utterance 리스트 작성 */
	public void writeXlsxUtterance(HttpServletResponse response, String path, List<Utterance> list, Metadata metadata) {
		String xlsxFileName =
				metadata.getTitle()+ "_" +
						new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".xlsx"; //파일명
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		XSSFWorkbook workbook = new XSSFWorkbook(); //워크북
		XSSFSheet sheet = workbook.createSheet(); //워크시트
		XSSFRow row = sheet.createRow(0); //행
		XSSFCell cell; //셀
		cell = row.createCell(0);
		cell.setCellValue("날짜 : " + day);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

		// 강의명과 부제 출력
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(metadata.getProgram().getTitle()+"  "+metadata.getProgram().getSubtitle());
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,3));

		// running time
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue("running time: " + metadata.getProgram().getRunning_time());
		sheet.addMergedRegion(new CellRangeAddress(3,3,0,1));

		// creator
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("creator: " + metadata.getCreator());
		sheet.addMergedRegion(new CellRangeAddress(4,4,0,1));

		row = sheet.createRow(6);
		/* 헤더 정보 구성 */
		cell = row.createCell(0);
		cell.setCellValue(column0);
		cell = row.createCell(1);
		cell.setCellValue("title");
		cell = row.createCell(2);
		cell.setCellValue("start");
		cell = row.createCell(3);
		cell.setCellValue("end");
		cell = row.createCell(4);
		cell.setCellValue("어절수");

		// 리스트의 size 만큼 row를 생성
		Utterance utterance;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			utterance = list.get(rowIdx);
			row = sheet.createRow(rowIdx+7); //행 생성
			cell = row.createCell(0);
			cell.setCellValue(Integer.toString(rowIdx+1));
			cell = row.createCell(1);
			cell.setCellValue(utterance.getForm());
			cell = row.createCell(2);
			cell.setCellValue(utterance.getStart());
			cell = row.createCell(3);
			cell.setCellValue(utterance.getFinish());
			cell = row.createCell(4);
			cell.setCellValue(utterance.getEojeol_count());
		}

		// 입력된 내용 파일로 쓰기
		String fullPath = path + xlsxFileName;
		File file = new File(fullPath);
		FileOutputStream fos = null;


		try {
			fos = new FileOutputStream(fullPath);
			workbook.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			response.setHeader("Content-Disposition", "attachment;filename="+xlsxFileName);
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentType("application/vnd.ms-excel");
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
