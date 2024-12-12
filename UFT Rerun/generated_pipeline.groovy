pipeline {
    agent { label "${params.Execute_On}" }
    environment {
        GBL_ENV = "${params.ExecutionEnvironment}"
        GBL_VM = "${params.Execute_On}"
        GBL_BuildNumber = "BU-" + "${currentBuild.number}"
    }    
    stages {
        stage('Clone Repository') {
            steps {
                cleanWs()
                git branch: 'main', credentialsId: 'z004wwpp', url: 'git@code.siemens.com:si-tc/tasks/myTcTestAutomation.git'
                echo (WORKSPACE)
                script {
                    def stageName = "#${currentBuild.number} - ${GBL_VM} - ${GBL_ENV}"
                    currentBuild.displayName = stageName	               
                    bat '''
                        setx ExecutionEnvironment "%ExecutionEnvironment%"
                    '''.replace('%ExecutionEnvironment%', "${params.ExecutionEnvironment}")                    
                    bat '''
                        setx TeamcenterServerID "%TeamcenterServerID%"
                    '''.replace('%TeamcenterServerID%', "${params.TeamcenterServerID}")                    
                    bat '''
                        setx ExecutionReport_To "%ExecutionReport_To%"
                    '''.replace('%ExecutionReport_To%', "${params.ExecutionReport_To}")                    
                    bat '''
                        setx AutomationDirPath ""${WORKSPACE}""
                    '''.replace('"${WORKSPACE}"', "${WORKSPACE}")                    
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
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "${env.WORKSPACE}" + '''\\Scripts\\jenkinsTestcase\\ApplyUFTSettiing'''
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "${env.WORKSPACE}" + '''\\Scripts\\jenkinsTestcase\\Update_AutomationSetupDetails'''
            } 
        }                
        
        stage('Teamcenter_Login') {
            steps {
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "${env.WORKSPACE}" + '''\\Scripts\\BusinessUsecases\\Teamcenter_Login'''
            }
        }

        stage('Organization_Volume_FSC_report') {
            steps {
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "${env.WORKSPACE}" + '''\\Scripts\\BusinessUsecases\\Organization_Volume_FSC_report'''
            }
        }

        stage('TCRA report generation from TC') {
            steps {
                uftScenarioLoad archiveTestResultsMode: 'ONLY_ARCHIVE_FAILED_TESTS_REPORT', testPaths: "${env.WORKSPACE}" + '''\\Scripts\\BusinessUsecases\\TCRA report generation from TC'''
            }
        }

    }
}