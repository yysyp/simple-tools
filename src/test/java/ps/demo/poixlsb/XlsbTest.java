package ps.demo.poixlsb;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.binary.XSSFBSharedStringsTable;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.binary.XSSFBStylesTable;
import org.apache.poi.xssf.eventusermodel.XSSFBReader;
import ps.demo.common.StringXTool;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class XlsbTest {

    public static void main(String [] args) throws Exception {
        File file = new File("C:\\Users\\yysyp\\Desktop\\tmp\\mytest.xlsb");
        OPCPackage opcPackage = OPCPackage.open(file);
        XSSFBReader xssfbReader = new XSSFBReader(opcPackage);
        XSSFBSharedStringsTable xssfbSharedStringsTable = new XSSFBSharedStringsTable(opcPackage);
        XSSFBStylesTable xssfbStylesTable = xssfbReader.getXSSFBStylesTable();
        XSSFBReader.SheetIterator it = (XSSFBReader.SheetIterator)xssfbReader.getSheetsData();

        MyXSSFSheetContentHandler<XlsbModelA> handler = new MyXSSFSheetContentHandler<XlsbModelA>(XlsbModelA.class, 1);
        InputStream is = it.next();
        String sheetName = it.getSheetName();
        XSSFBSheetHandler sheetHandler = new XSSFBSheetHandler(is, xssfbStylesTable, it.getXSSFBSheetComments(), xssfbSharedStringsTable
        , handler, new DataFormatter(), false);
        sheetHandler.parse();

        List<XlsbModelA> data = handler.getList();

        //log.info("data={}", data);
        StringXTool.printOut(data);
    }

}
