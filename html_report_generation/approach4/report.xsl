<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Define the template for the root element -->
    <xsl:template match="/records">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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

                <!-- Loop over each TestSuite -->
                <xsl:for-each select="record/Test_Suite">
                    <h2>Test Suite: <xsl:value-of select="@name"/></h2>
                    <p>Total Tests: <!-- Logic to count total tests --></p>
                    <p>Failures: <!-- Logic to count failures --></p>
                    <p>Errors: <!-- Logic to count errors --></p>
                    <p>Total Time: <!-- Logic to calculate total time --></p>

                    <!-- Table for Test Cases -->
                    <table>
                        <tr>
                            <th>Test Case</th>
                            <th>Total Time (s)</th>
                            <th>Test Steps</th>
                        </tr>

                        <!-- Loop over each TestCase in the TestSuite -->
                        <xsl:for-each select="Test_Case">
                            <tr>
                                <td><xsl:value-of select="@name"/></td>
                                <td><xsl:value-of select="Test_Step/@Execution_Time"/></td>
                                <td>
                                    <table>
                                        <tr>
                                            <th>Step Name</th>
                                            <th>Time (s)</th>
                                            <th>Status</th>
                                        </tr>

                                        <!-- Loop over each TestStep in the TestCase -->
                                        <xsl:for-each select="Test_Step">
                                            <tr>
                                                <td><xsl:value-of select="@name"/></td>
                                                <td><xsl:value-of select="@Response_Time"/></td>
                                                <td>
                                                    <xsl:choose>
                                                        <xsl:when test="@Step_Status='PASS'">
                                                            <span class="teststep-pass">PASS</span>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <span class="teststep-fail">FAIL</span>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
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

    <!-- Utility templates for calculating totals and counts -->
    <xsl:template name="count-tests">
        <!-- Add logic to count total tests -->
    </xsl:template>

    <xsl:template name="count-failures">
        <!-- Add logic to count failures -->
    </xsl:template>

    <xsl:template name="calculate-total-time">
        <!-- Add logic to calculate total time -->
    </xsl:template>

</xsl:stylesheet>
