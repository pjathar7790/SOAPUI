<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Define template for the root element -->
    <xsl:template match="/records">
        <html>
            <head>
                <title>JUnit Report</title>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h1>JUnit Report</h1>
                <table>
                    <tr>
                        <th>Test Suite</th>
                        <th>Test Case</th>
                        <th>Step Name</th>
                        <th>Step Type</th>
                        <th>Step Status</th>
                        <th>Result Message</th>
                        <th>Execution Date</th>
                        <th>Response Time (s)</th>
                        <th>Execution Time (s)</th>
                    </tr>
                    <xsl:for-each select="record">
                        <xsl:for-each select="Test_Suite">
                            <xsl:for-each select="Test_Case">
                                <xsl:for-each select="Test_Step">
                                    <tr>
                                        <td><xsl:value-of select="../@name" /></td>
                                        <td><xsl:value-of select="../@name" /></td>
                                        <td><xsl:value-of select="@name" /></td>
                                        <td><xsl:value-of select="@Step_Type" /></td>
                                        <td><xsl:value-of select="@Step_Status" /></td>
                                        <td><xsl:value-of select="@Result_message" /></td>
                                        <td><xsl:value-of select="@Execution_Date" /></td>
                                        <td><xsl:value-of select="@Response_Time" /></td>
                                        <td><xsl:value-of select="@Execution_Time" /></td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:for-each>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
