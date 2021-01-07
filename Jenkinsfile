library identifier: 'shared-library@1.0.0', 
    retriever: modernSCM([
        $class: 'GitSCMSource',
        remote: 'https://github.com/ZachDZimmerman/shared-library.git'
        // remote: 'git@github.com:ZachDZimmerman/shared-library.git',
        // credentialsId: 'git-key'
    ])

pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                exampleHelloWorld()
            }
        }
        stage('Args') {
            steps {
                exampleArgs(namedArg1: 'foo', namedArg2: 'bar') {
                    echo 'block steps'
                }
            }
        }
        stage('Resource Script') {
            steps {
                exampleResourceScript()
            }
        }
    }
}
