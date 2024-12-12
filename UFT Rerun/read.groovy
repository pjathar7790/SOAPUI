@Grab(group='org.apache.poi', module='poi', version='4.1.2')
@Grab(group='org.apache.poi', module='poi-ooxml', version='4.1.2')
@Grab(group='org.apache.commons', module='commons-collections4', version='4.4')
@Grab(group='org.apache.xmlbeans', module='xmlbeans', version='3.1.0')
@Grab(group='org.apache.poi', module='poi-ooxml-schemas', version='4.1.2')

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.*

def readExcelFile(filePath) {
    FileInputStream fis = new FileInputStream(new File(filePath))
    XSSFWorkbook workbook = new XSSFWorkbook(fis)
    
    Sheet sheet = workbook.getSheetAt(0)
    
    sheet.each { Row row ->
        def rowData = []
        row.each { Cell cell ->
            switch (cell.cellType) {
                case CellType.STRING:
                    rowData << cell.stringCellValue
                    break
                case CellType.NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        rowData << cell.dateCellValue
                    } else {
                        rowData << cell.numericCellValue
                    }
                    break
                case CellType.BOOLEAN:
                    rowData << cell.booleanCellValue
                    break
                default:
                    rowData << "Unknown Value"
            }
        }
        println rowData.join(", ")
    }
    
    workbook.close()
    fis.close()
}

def filePath = "C:\\Users\\z004xtmc\\Prathamesh\\ExecutionReport.xlsx"
readExcelFile(filePath)
