package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel导入导出通用工具类
 * @author Administrator
 *
 */
public class ExcelUtils {

	/**
	 * Excel 表格导出通用方法
	 * 
	 * @param titleRows     文件标题行数	
	 * @param templetpath   模板文件地址
	 * @param datas         数据
	 * @param outPath       输出临时文件地址
	 * @return              OutputStream
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static OutputStream exportExcel(int titleRows, File templetFile, List<String[]> datas, ServletOutputStream os,int maxRow) throws InvalidFormatException, IOException {

		InputStream is = new FileInputStream(templetFile);
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
														// 2003/2007/2010
														// 都是可以处理的
		
		// 写入数据
		Sheet sheet = workbook.getSheetAt(0);// 得到第一个工作簿
		int countRow = sheet.getLastRowNum();
		//int countRow = sheet.getPhysicalNumberOfRows();
		System.out.println("countRow=="+countRow);
		//清除第一个工作簿中除标题以外的数据
		/*for (int i = titleRows; i <= countRow; i++) {
			Row row = sheet.getRow(i);
			if(row!=null){
				sheet.removeRow(row);
			}
		}
		workbook.write(os);*/
		
		//写入数据
		for (int i = 0; i < datas.size(); i++) {
			String[] rowDatas = datas.get(i);
			Row row = sheet.createRow(i + titleRows);
			for (int j = 0; j < rowDatas.length; j++) {
				System.out.println(rowDatas[j]);
				row.createCell(j).setCellValue(rowDatas[j]);
			}
		}
		workbook.write(os);
		is.close();
		return os;
	}

	/**
	 * 导入Excel表通用方法 读取Excel文件内容 将数据封装为List<String[]>
	 * @param is        Excel文件输入流
	 * @param endCol    Excel文件列数
	 * @param startRow  文件标题行数
	 * @return          List<String[]>
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<String[]> importExcel(InputStream is, int endCol, int startRow)
			throws InvalidFormatException, IOException {
		List<String[]> list = new ArrayList<String[]>();
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
														// 2003/2007/2010
														// 都是可以处理的
		Sheet sheet = workbook.getSheetAt(0);// 得到第一个工作簿
		System.out.println(sheet.getLastRowNum() + "====总行数");
		for (int i = 0; i <= sheet.getLastRowNum() - startRow; i++) {
			// 根据单元格创建数组用户存储每行数据
			StringBuffer sb = new StringBuffer();
			String[] contents = new String[endCol];
			Row row = sheet.getRow(startRow);
			for (int j = 0; j < endCol; j++) {
				Cell cell = row.getCell(j);
				contents[j] = cell.getStringCellValue();
				sb.append(cell.getStringCellValue());
				// System.out.print(cell.getStringCellValue());
			}
			if (sb.length() > 0) {
				System.out.println(sb);
				list.add(contents);
			}
		}
		is.close();
		//System.out.println(list.size());
		return list;
	}

	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// Excel 表格导出测试
			List<String[]> datas = new ArrayList<String[]>();
			String[] s = new String[127];
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < s.length; i++) {
					s[i] = i + "数据";
				}
				datas.add(s);
			}
			// 2007
			//OutputStream out1 = ExcelUtils.exportExcel(1, "D:\\jy1.xlsx", datas, "D:\\jy1_copy.xlsx", 1000);
			// 2003
			//OutputStream out = ExcelUtils.exportExcel(4, "D:\\jy2.xls", datas, "D:\\jy2_cppy.xls", 1000);

			// Excel 表格导入测试
			InputStream is = new FileInputStream(new File("D:\\jy1_copy.xlsx"));//2007
			//InputStream is = new FileInputStream(new File("D:\\123_cppy.xls"));// 2003
			
			List<String[]> listString = ExcelUtils.importExcel(is, 127, 1);
			for (String[] strings : listString) {
				String s1 = "";
				for (int i = 0; i < strings.length; i++) {
					s1 += strings[i];
				}
				System.out.println(s1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}