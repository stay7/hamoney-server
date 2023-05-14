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
                sshPublisher(
                        publishers: [
                                sshPublisherDesc(configName: 'hamoney-dev',
                                        transfers: [
                                                sshTransfer(
                                                        cleanRemote: false,
                                                        excludes: '',
                                                        flatten: false,
                                                        makeEmptyDirs: false,
                                                        noDefaultExcludes: false,
                                                        removePrefix: 'build/libs',
                                                        sourceFiles: 'build/libs/*.jar')
                                        ],
                                        usePromotionTimestamp: false,
                                        useWorkspaceInPromotion: false,
                                        verbose: false)
                        ])
                sh 'echo "deploy"'
            }
        }
    }
}