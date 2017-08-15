import org.codehaus.groovy.runtime.ProcessGroovyMethods

def invokeJenkinsCli(def jenkinsUrls, def operation, def job, def buildParam) {

    jenkinsUrls.each { k, v ->

        println "${k}:${v}"

        def command = createCommandToExecute("${v}", operation, job , buildParam)

        println("command being executed: " + command)

        //Sample: //"java -jar /Users/yogeshkumararora/Downloads/jenkins-cli.jar -s http://localhost:8080/ build DevOps_Tooling/my_first_pipeline/ -v -s"
        def Process process = executeCommand(command)
        def error = process.err.text
        def consoleLog = process.getText()

        println(error)
        //println(consoleLog)

        File file = new File("${k}" + ".csv")
        file.text = consoleLog
    }
}

/**
 * To create a command to execute via Jenkins CLI.
 * @param jenkinsUrl
 * @param operation
 * @param job
 */
def createCommandToExecute(def jenkinsUrl, def operation, def job, def buildParam) {

    def command = "java -jar /Users/yogeshkumararora/Downloads/jenkins-cli.jar -s "
    command = command + jenkinsUrl + " " + operation + " " + job

    if(buildParam.trim()) {
        command = command + " -p param=" + buildParam + " -v -s"
    } else {
        command = command + " -v -s"
    }
}

def executeCommand(def command) {

    ProcessGroovyMethods proc = new ProcessGroovyMethods()
    Process process = proc.execute(command)
    process.waitFor()
    return process
}
def jenkinsUrls = [M5: 'http://localhost:8080/', M6: 'http://localhost:8080/', M7: 'http://localhost:8080/']
invokeJenkinsCli(jenkinsUrls,  "build", "DevOps_Tooling/HelloWorld/", "10")


