def readValuesFromFile(filePath) {
    def values = []
    new File(filePath).eachLine { line ->
        values << line.trim()
    }
    return values
}

def generateJenkinsPipeline(values) {
    def stageSections = values.collect { value ->
        return """
        stage('${value}') {
            steps {
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "\${env.WORKSPACE}" + '''\\\\Scripts\\\\BusinessUsecases\\\\${value}'''
            }
        }"""
    }.join('\n')

    def pipelineTemplate = """pipeline {
    agent { label "\${params.Execute_On}" }
    environment {
        GBL_ENV = "\${params.ExecutionEnvironment}"
        GBL_VM = "\${params.Execute_On}"
        GBL_BuildNumber = "BU-" + "\${currentBuild.number}"
    }    
    stages {
        stage('Clone Repository') {
            steps {
                cleanWs()
                git branch: 'main', credentialsId: 'z004wwpp', url: 'git@code.siemens.com:si-tc/tasks/myTcTestAutomation.git'
                echo (WORKSPACE)
                script {
                    def stageName = "#\${currentBuild.number} - \${GBL_VM} - \${GBL_ENV}"
                    currentBuild.displayName = stageName	               
                    bat '''
                        setx ExecutionEnvironment "%ExecutionEnvironment%"
                    '''.replace('%ExecutionEnvironment%', "\${params.ExecutionEnvironment}")                    
                    bat '''
                        setx TeamcenterServerID "%TeamcenterServerID%"
                    '''.replace('%TeamcenterServerID%', "\${params.TeamcenterServerID}")                    
                    bat '''
                        setx ExecutionReport_To "%ExecutionReport_To%"
                    '''.replace('%ExecutionReport_To%', "\${params.ExecutionReport_To}")                    
                    bat '''
                        setx AutomationDirPath ""\${WORKSPACE}""
                    '''.replace('"\${WORKSPACE}"', "\${WORKSPACE}")                    
                    bat '''
                        setx AutomationBuildNumber %GBL_BuildNumber%
                    '''                    
                    bat '''
                        setx AutomationUseCaseType "Business Usecases"
                    '''                    
                    bat '''
                        setx AutomationResultInDB "Yes"
                    '''
                }
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "\${env.WORKSPACE}" + '''\\\\Scripts\\\\jenkinsTestcase\\\\ApplyUFTSettiing'''
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "\${env.WORKSPACE}" + '''\\\\Scripts\\\\jenkinsTestcase\\\\Update_AutomationSetupDetails'''
            } 
        }                
        ${stageSections}

    }
}"""

    return pipelineTemplate
}

def outputPipelineToFile(pipeline, outputFilePath) {
    new File(outputFilePath).withWriter { writer ->
        writer << pipeline
    }
}

// Define file paths
def inputFilePath = "C:\\Users\\z004xtmc\\Prathamesh\\FailedTestCases.txt"
def outputFilePath = "C:\\Users\\z004xtmc\\Prathamesh\\generated_pipeline.groovy"

// Read values from the text file
def values = readValuesFromFile(inputFilePath)

// Generate Jenkins pipeline script
def pipelineScript = generateJenkinsPipeline(values)

// Output the pipeline script to another Groovy script file
outputPipelineToFile(pipelineScript, outputFilePath)

println "Pipeline script generated and saved to ${outputFilePath}"
