pipeline {
  agent any
  stages {
    stage('Source') {
      steps {
        git(url: 'https://github.com/stay7/hamoney-server.git', branch: 'develop')
      }
    }

    stage('Build') {
      steps {
        sh './gradlew BootJar'
      }
    }

    stage('Deploy') {
      steps {
        sshPublisher()
      }
    }

  }
}