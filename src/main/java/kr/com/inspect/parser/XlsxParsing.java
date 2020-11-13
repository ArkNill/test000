package kr.com.inspect.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.com.inspect.dto.Program;

public class XlsxParsing {
	
	/* Excel 파일을 읽어서 List<Program>에 파싱 */
	public List<Program> setProgramList(String fullPath) {
		// 반환할 객체를 생성
		List<Program> list = new ArrayList<Program>();
		
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		
		try {
			
			fis= new FileInputStream(fullPath);
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new XSSFWorkbook(fis);
			
			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow   curRow;
			XSSFCell  curCell;
			Program program;
			
			curSheet = workbook.getSheetAt(0);
			// row 탐색 for문
				for(int rowIndex=0; rowIndex < curSheet.getPhysicalNumberOfRows()-1; rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if(rowIndex != 0) {
						// 현재 row 반환
						curRow = curSheet.getRow(rowIndex);
						program = new Program();
						String value;
						
						// row의 두번째 cell값이 비어있지 않은 경우 만 cell탐색
						if(curRow.getCell(1).getStringCellValue() != null) {							
							for(int cellIndex=0;cellIndex<curRow.getPhysicalNumberOfCells(); cellIndex++) {
								curCell = curRow.getCell(cellIndex);
								
								switch (cellIndex) {
									case 0: // 아이디
										program.setId((int)curCell.getNumericCellValue());
										break;
										
									case 1: // 파일번호
										program.setFile_num(curCell.getStringCellValue());
										break;
										
									case 2: // 프로그램명
										program.setTitle(curCell.getStringCellValue());
										break;
										
									case 3: // 부제
										program.setSubtitle(curCell.getStringCellValue());
										break;
										
									case 4: // 방송시간
										Date time = curCell.getDateCellValue();
										String timeString = new SimpleDateFormat("mm:ss").format(time);
										program.setRunning_time("0:"+timeString);
										break;
		
									default:
										break;
								}
							}
							// cell 탐색 이후 vo 추가
							list.add(program);
						}
					}
				}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				// 사용한 자원은 finally에서 해제
				if(workbook!= null) workbook.close();
				if(fis!= null) fis.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
