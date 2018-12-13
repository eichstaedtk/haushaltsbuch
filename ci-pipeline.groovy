pipeline {
    agent { node { label 'my1stSlave' } }
    tools {
        maven 'maven3.6'
        jdk 'OpenJDK 11'
    }
    environment {
        branch = 'master'
        scmUrl = 'http://gitlab.sbb.spk-berlin.de/StaatsbibliothekAPI/sruclient.git'
    }
    stages {
        stage('Git checkout') {
            steps {
                git branch: branch, credentialsId: 'Jenkins-Gitlab', url: scmUrl
            }
        }

        stage('Maven build') {
            steps {
                sh 'mvn clean package'

                jacoco(
                        execPattern: 'target/*.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        exclusionPattern: 'src/test*')
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    // Optionally use a Maven environment you've configured already

                    sh 'mvn clean package sonar:sonar'

                }
            }
        }

        stage('Dependency Check') {

            steps {

                dependencyCheckAnalyzer datadir: 'dependency-check-data', includeVulnReports: true, hintsFile: '', includeCsvReports: false, includeHtmlReports: true, includeJsonReports: false, isAutoupdateDisabled: false, outdir: '', scanpath: '', skipOnScmChange: false, skipOnUpstreamChange: false, suppressionFile: '', zipExtensions: ''

                dependencyCheckPublisher canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''

                archiveArtifacts allowEmptyArchive: true, artifacts: '**/dependency-check-report.html', onlyIfSuccessful: true
            }
        }

        stage('Maven Deploy') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                sh 'mvn clean deploy'
            }
        }

    }
    post {
        failure {
            mail to: 'konrad.eichstaedt@sbb.spk-berlin.de', subject: 'SRU-Client Pipeline failed', body: "${env.BUILD_URL}"
        }
    }
}