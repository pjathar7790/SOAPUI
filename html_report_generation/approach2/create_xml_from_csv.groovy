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
        record {
            header.eachWithIndex { h, i ->
                "${h.replaceAll(' ', '_')}"(columns[i])
            }
        }
    }
}

// Write the XML content to the file
xmlFile.text = writer.toString()

// Print the generated XML content for debugging
println "XML file content:\n${writer.toString()}"
println "XML file created successfully."
