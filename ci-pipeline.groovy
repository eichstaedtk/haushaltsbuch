pipeline {

    environment {
        branch = 'master'
        scmUrl = 'https://github.com/eichstaedtk/haushaltsbuch'
    }
    stages {
        stage('Git checkout') {
            steps {
                git branch: branch, credentialsId: 'eichstaedtk', url: scmUrl
            }
        }

        stage('Gradle build') {
            steps {
                sh 'gradle clean build'

                jacoco(
                        execPattern: 'build/jacoco/*.exec',
                        classPattern: 'build/classes',
                        sourcePattern: 'src/main/java',
                        exclusionPattern: 'src/test*')
            }
        }

    }
    post {
        failure {
            mail to: 'konrad.eichstaedt@gmx.de', subject: 'Haushaltsbuch Pipeline failed', body: "${env.BUILD_URL}"
        }
    }
}