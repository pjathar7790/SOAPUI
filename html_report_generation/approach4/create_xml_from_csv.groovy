import groovy.xml.MarkupBuilder

// Read the CSV file
def csvFile = new File('ExecutionReport.csv')
def csvLines = csvFile.readLines()

// Handle CSV parsing correctly
def parseCsvLine(line) {
    def values = line.split(/,(?=(?:[^"]*"[^"]*")*[^"]*$)/).collect { it.replaceAll(/^"|"$/, '') }
    return values
}

// Extract header and data
def header = parseCsvLine(csvLines[0])
def dataLines = csvLines.drop(1).collect { parseCsvLine(it) }

// Create an XML file
def xmlFile = new File('output.xml')
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

// Build the XML structure
xml.records {
    dataLines.each { columns ->
        def suiteName = columns[0]
        def caseName = columns[1]
        def stepName = columns[2]
        def stepType = columns[3]
        def stepStatus = columns[4]
        def resultMessage = columns[5]
        def executionDate = columns[6]
        def responseTime = columns[7]
        def executionTime = columns[8]

        // Create XML structure with nested elements
        record {
            "${header[0].replaceAll(' ', '_')}"(name: suiteName) {
                "${header[1].replaceAll(' ', '_')}"(name: caseName) {
                    "${header[2].replaceAll(' ', '_')}"(
                        name: stepName,
                        Step_Type: stepType,
                        Step_Status: stepStatus,
                        Result_message: resultMessage,
                        Execution_Date: executionDate,
                        Response_Time: responseTime,
                        Execution_Time: executionTime
                    )
                }
            }
        }
    }
}

// Write the XML content to the file
xmlFile.text = writer.toString()

// Print the generated XML content for debugging
println "XML file content:\n${writer.toString()}"
println "XML file created successfully."
