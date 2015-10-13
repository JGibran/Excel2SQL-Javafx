/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel2sql.util;

/**
 *
 * @author Gibran Polonsky
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
public class ExcelReader{
	private String path;
	
	
	public ExcelReader(String path){
		this.path = path;
	}
	
	public ArrayList<String> read(int pageIndex){
		ArrayList<String> queryes = new ArrayList<String>();
		
		boolean first = true;
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		
		try{
			FileInputStream file = new FileInputStream(new File(path));
		
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			XSSFSheet sheet = workbook.getSheetAt(pageIndex);

			Iterator<Row> rowIterator = sheet.iterator();
			
			while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					switch (cell.getCellType()){
						case Cell.CELL_TYPE_NUMERIC:
								if(first){
									params.add(String.valueOf((int) Math.floor(cell.getNumericCellValue())));
								}else{
									values.add(String.valueOf((int) Math.floor(cell.getNumericCellValue())));
								}
								
						break;
						case Cell.CELL_TYPE_STRING:
								if(first){
									params.add(cell.getStringCellValue());
								}else{
									values.add(cell.getStringCellValue());
								}
						break;
					}
				}
			
				String[] stockParams = new String[params.size()];
				stockParams = params.toArray(stockParams);
				
				String[] stockValues = new String[values.size()];
				stockValues = values.toArray(stockValues);
				
				values.clear();
				if(!first){
					queryes.add(QueryBuilder.getQuery(sheet.getSheetName(), stockParams, stockValues));
				}
				first = false;
			}
			file.close();
			return queryes;
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
			return null;
	}
}