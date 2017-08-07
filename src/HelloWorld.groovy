import groovy.json.*
def HOSTNAME = 'http://localhost:8080'
def JOBNAME = 'SonarQube_Scanner_FreeStyle'
def JOB_URL = "$HOSTNAME/job/$JOBNAME/lastSuccessfulBuild"
def text = "$JOB_URL/api/json".toURL().text
println JsonOutput.prettyPrint(text)
def json = new JsonSlurper().parseText(text)
json.artifacts.each{
    println it
}