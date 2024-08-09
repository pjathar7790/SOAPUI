<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:param name="suiteName"/>
    
    <xsl:template match="/records">
        <html>
            <head>
                <title>JUnit Report - <xsl:value-of select="record[Test_Suite/@name=$suiteName]/@name" /></title>
                <style>
                    body { font-family: Arial, sans-serif; }
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h1>JUnit Report - <xsl:value-of select="record[Test_Suite/@name=$suiteName]/@name" /></h1>
                <table>
                    <tr>
                        <th>Test Case</th>
                    </tr>
                    <xsl:for-each select="record[Test_Suite/@name=$suiteName]/Test_Suite/Test_Case">
                        <tr>
                            <td>
                                <a href="{concat(@name, '.html')}"><xsl:value-of select="@name" /></a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
