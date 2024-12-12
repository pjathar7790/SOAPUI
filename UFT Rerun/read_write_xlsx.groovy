@Grab(group='org.apache.poi', module='poi', version='4.1.2')
@Grab(group='org.apache.poi', module='poi-ooxml', version='4.1.2')
@Grab(group='org.apache.commons', module='commons-collections4', version='4.4')
@Grab(group='org.apache.xmlbeans', module='xmlbeans', version='3.1.0')
@Grab(group='org.apache.poi', module='poi-ooxml-schemas', version='4.1.2')

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.*

def readExcelAndWriteFailuresToFile(filePath, outputFilePath) {
    FileInputStream fis = new FileInputStream(new File(filePath))
    XSSFWorkbook workbook = new XSSFWorkbook(fis)
    Sheet sheet = workbook.getSheetAt(0)

    int testCaseNameCol = -1
    int automationResultCol = -1
    int applicationEnvCol = -1

    // Assume the first row is the header row
    Row headerRow = sheet.getRow(0)
    headerRow.eachWithIndex { Cell cell, int index ->
        if (cell.stringCellValue == "Test Case Name") {
            testCaseNameCol = index
        } else if (cell.stringCellValue == "Automation Result") {
            automationResultCol = index
        } else if (cell.stringCellValue == "Application Env.") {
            applicationEnvCol = index
        }
    }

    if (testCaseNameCol == -1 || automationResultCol == -1 || applicationEnvCol == -1) {
        println "Error: Could not find 'Test Case Name', 'Automation Result', or 'Application Env.' columns."
        return
    }

    File outputFile = new File(outputFilePath)
    outputFile.withWriter('UTF-8') { writer ->
        sheet.eachWithIndex { Row row, int rowIndex ->
            if (rowIndex > 0) { // Skip header row
                String testCaseName = row.getCell(testCaseNameCol)?.stringCellValue
                String automationResult = row.getCell(automationResultCol)?.stringCellValue
                String applicationEnv = row.getCell(applicationEnvCol)?.stringCellValue

                if (testCaseName && automationResult?.toUpperCase() == "FAIL") {
                    String trimmedTestCaseName = testCaseName.replace("_${applicationEnv}", "").replaceAll(/_\d{8}_\d{6}$/, "")
                    writer.writeLine(trimmedTestCaseName)
                }
            }
        }
    }

    workbook.close()
    fis.close()
    println "Finished writing failed test cases to ${outputFilePath}"
}

def filePath = "C:\\Users\\z004xtmc\\Prathamesh\\ExecutionReport.xlsx"
def outputFilePath = "C:\\Users\\z004xtmc\\Prathamesh\\FailedTestCases.txt"
readExcelAndWriteFailuresToFile(filePath, outputFilePath)
