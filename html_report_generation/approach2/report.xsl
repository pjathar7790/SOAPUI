<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/records">
        <html>
            <head>
                <title>Test Report</title>
                <style>
                    table { width: 100%; border-collapse: collapse; }
                    th, td { border: 1px solid black; padding: 5px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h1>Test Report</h1>
                <table>
                    <tr>
                        <th>Test Suite</th>
                        <th>Test Case</th>
                        <th>Test Step</th>
                        <th>Step Type</th>
                        <th>Step Status</th>
                        <th>Result Message</th>
                        <th>Execution Date</th>
                        <th>Response Time</th>
                        <th>Execution Time</th>
                    </tr>
                    <xsl:for-each select="record">
                        <tr>
                            <td><xsl:value-of select="Test_Suite"/></td>
                            <td><xsl:value-of select="Test_Case"/></td>
                            <td><xsl:value-of select="Test_Step"/></td>
                            <td><xsl:value-of select="Step_Type"/></td>
                            <td><xsl:value-of select="Step_Status"/></td>
                            <td><xsl:value-of select="Result_message"/></td>
                            <td><xsl:value-of select="Execution_Date"/></td>
                            <td><xsl:value-of select="Response_Time"/></td>
                            <td><xsl:value-of select="Execution_Time"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
