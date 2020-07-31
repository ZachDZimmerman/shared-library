
# Introduction

[Jenkins shared libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/) allow code reuse between pipeline projects.

Custom [steps](https://jenkins.io/doc/pipeline/steps/) can be created for use in pipelines.  Shell scripts and other supporting files can be included and used by custom steps or directly by pipelines.

When authoring pipelines and shared libraries, be aware of certain limitations and recommended practices.
* [Scripted versus Declarative Pipeline syntax](https://jenkins.io/doc/book/pipeline/#declarative-versus-scripted-pipeline-syntax)
* Use the [Snippet Generator](https://jenkins.io/doc/book/pipeline/getting-started/#snippet-generator) and the [Declarative Directive Generator](https://jenkins.io/doc/book/pipeline/getting-started/#directive-generator)
* [Pipeline: Best Practices](https://support.cloudbees.com/hc/en-us/articles/230922208-Pipeline-Best-Practices)


## Usage

Use the [`library` step](https://jenkins.io/doc/pipeline/steps/workflow-cps-global-lib/#-library-%20load%20a%20shared%20library%20on%20the%20fly) to [dynamically include a shared library in your pipeline](https://jenkins.io/doc/book/pipeline/shared-libraries/#dynamic-retrieval) as this keeps the most information in the Jenkinsfile under source control rather than in Jenkins configuration.

A basic `Jenkinsfile` example:

```groovy
library identifier: 'jenkins-shared-library@v1.0.0', 
    retriever: modernSCM([
        $class: 'GitSCMSource',
        remote: 'https://github.com/mhicks-cloudbees/jenkins-shared-library.git'
        // remote: 'git@github.com:mhicks-cloudbees/jenkins-shared-library.git',
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
    }
}
```

> **Note**: This method of referencing a shared library requires using a specific library version (as with `@v1.0.0` in the `library` step above). We recommend against using `@master`. This prevents your pipeline from using changes in newer versions of the shared library that could cause errors or unexpected behavior. New versions of shared libraries can then be tested separately from standard builds. See [releases](https://github.com/mhicks-cloudbees/jenkins-shared-library/releases).

## Steps

This shared library defines the following custom [steps](#steps):

* [exampleArgs](#exampleArgs)
* [exampleHelloWorld](#exampleHelloWorld)
* [exampleResourceScript](#exampleResourceScript)

### `exampleArgs`
Use named parameters with defaults and a block section. See [vars/exampleArgs.groovy](vars/exampleArgs.groovy).

```groovy
steps {
    exampleArgs(requiredArg: 'foo', optionalArg: 'bar') {
        echo 'block steps'
    }
}
```

Example build log output:
```
[Pipeline] echo
Arguments: [requiredArg:foo, optionalArg:bar, hasDefaultArg:baz]
[Pipeline] echo
Block:org.jenkinsci.plugins.workflow.cps.CpsClosure
```

**Arguments**

| Argument name  | Type       | Purpose                                    | Default   | Required? |
|----------------|------------|--------------------------------------------|-----------|-----------|
| requiredArg    | String     | A required named argument.                 |           | ✔         |
| optionalArg    | String     | Another example named argument.            |           |           |
| hasDefaultArg  | String     | Yet another example named argument.        | baz       |           |

### `exampleHelloWorld`
A trivial step example. See [`vars/exampleHelloWorld.groovy`](vars/exampleHelloWorld.groovy).

```groovy
steps {
    exampleHelloWorld()
}
```

Example build log output:
```
[Pipeline] echo
Hello, World!
```

### `exampleResourceScript`
Run a script from the shared library resources as a build step. See [vars/exampleResourceScript.groovy](vars/exampleResourceScript.groovy) and [resources/com/example/scripts/example.sh](resources/com/example/scripts/example.sh).

```
steps {
    exampleResourceScript()
}
```

Example build log output:
```
[Pipeline] libraryResource
[Pipeline] sh
+ env
+ cut -d = -f 1
+ grep JENKINS
JENKINS_NODE_COOKIE
JENKINS_HOME
JENKINS_URL
JENKINS_SERVER_COOKIE
```

## Contributing

Pull requests are welcome! When adding steps, please add appropriate entries to this document in the [Steps](#steps) section and links in the [Introduction](#introduction) list.  For major changes, please open an issue to discuss what you'd like to change.
