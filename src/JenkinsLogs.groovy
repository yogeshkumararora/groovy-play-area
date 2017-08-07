import org.codehaus.groovy.runtime.ProcessGroovyMethods

def html = "http://localhost:8080/job/DevOps_Tooling/job/first_free_style//15/logText/progressiveText?start=0".toURL().text

println(html)

def directory = '/Users/yogeshkumararora/IdeaProjects/jenkins-plugins-wrkspc/groovy-play-area/logs/'

def file = new File(directory + "jenkins_logs.txt")
file.text = html


def commandStr = ["grep", "BUILD FAILURE",  "/Users/yogeshkumararora/IdeaProjects/jenkins-plugins-wrkspc/groovy-play-area/logs/jenkins_logs.txt"];
ProcessGroovyMethods proc = new ProcessGroovyMethods();
Process process = proc.execute(commandStr);
process.waitFor();


def error = process.err.text
def textFound = process.getText()
def matchFound = false
if(textFound.trim()) {
   matchFound = true
    println "TEXT: <" + error  + ">:<"+ textFound.trim() +"> " + matchFound
} else {
    println "match not found"
}
