<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <html>
      <head>
        <title>Test Report</title>
        <style>
          body {
            font-family: Arial, sans-serif;
          }
          table {
            width: 100%;
            border-collapse: collapse;
          }
          th, td {
            border: 1px solid #ddd;
            padding: 8px;
          }
          th {
            background-color: #f2f2f2;
            text-align: left;
          }
          .teststep-pass {
            color: green;
          }
          .teststep-fail {
            color: red;
          }
        </style>
      </head>
      <body>
        <h1>Test Report</h1>
        <xsl:for-each select="testsuites/testsuite">
          <h2>Test Suite: <xsl:value-of select="@name" /></h2>
          <p>Total Tests: <xsl:value-of select="@tests" /></p>
          <p>Failures: <xsl:value-of select="@failures" /></p>
          <p>Errors: <xsl:value-of select="@errors" /></p>
          <p>Total Time: <xsl:value-of select="@time" /> seconds</p>

          <table>
            <tr>
              <th>Test Case</th>
              <th>Total Time (s)</th>
              <th>Test Steps</th>
            </tr>
            <xsl:for-each select="testcase">
              <tr>
                <td><xsl:value-of select="@name" /></td>
                <td><xsl:value-of select="@time" /></td>
                <td>
                  <table>
                    <tr>
                      <th>Step Name</th>
                      <th>Time (s)</th>
                      <th>Status</th>
                    </tr>
                    <xsl:for-each select="teststep">
                      <tr>
                        <td><xsl:value-of select="@name" /></td>
                        <td><xsl:value-of select="@time" /></td>
                        <td>
                          <xsl:attribute name="class">
                            <xsl:choose>
                              <xsl:when test="@status='PASS'">teststep-pass</xsl:when>
                              <xsl:otherwise>teststep-fail</xsl:otherwise>
                            </xsl:choose>
                          </xsl:attribute>
                          <xsl:value-of select="@status" />
                        </td>
                      </tr>
                    </xsl:for-each>
                  </table>
                </td>
              </tr>
            </xsl:for-each>
          </table>
        </xsl:for-each>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
