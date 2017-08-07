def location = "/Users/yogeshkumararora/IdeaProjects/jenkins-plugins-wrkspc/groovy-play-area/src/com/home/groovy/csv/parser"
HOSTNAME = 'http://localhost:8080'

M5_HOSTNAME = 'http://localhost:8080'
M6_HOSTNAME = 'http://localhost:8080'

def getAllCsvFiles(def location) {

    def dir = new File(location)
    def allCsvFiles = []
    def index = 0
    dir.eachFileMatch(~/.*.csv/) { file ->
        allCsvFiles[index] = file.getName()
        index++
    }
    println("allCsvFiles: " + allCsvFiles)
    return allCsvFiles.toArray()
}

def getAllFiles(def location, def pattern) {

    def dir = new File(location)
    def allFiles = []
    def index = 0
    dir.eachFileMatch("~/./" + pattern + "/") { file ->
        allFiles[index] = file.getName()
        index++
    }
    println("allFiles: " + allFiles)
    return allFiles.toArray()
}

def getBuildLogUrls(def allCsvFiles) {

    def buildLogUrls = []
    def HOSTNAME = ""
    def jobIndex = 0

    allCsvFiles.each { csvFile ->

        def file = new File(csvFile)
        HOSTNAME = getHostName(csvFile)

        println("---- Now reading ---- " + csvFile)
        file.withReader { Reader reader ->
            //read the first line and ignore it (i.e. header)
            def header = reader.readLine()

            //Read 2nd line onwards
            def lines = reader.readLines()
            def job
            def jobFullName = []
            // file1.readLines()

            lines.each { line ->
                //if line is not blank
                if (line.trim()) {
                    job = line.tokenize(",")
                    jobFullName = job[0]
                    jobBuildNumber = job[1]

                    println("jobFullName: " + jobFullName)
                    println("jobBuildNumber: " + jobBuildNumber)

                    def jobNameToken = []
                    def jobBaseUrl = ""
                    if (jobFullName.contains("/")) {
                        println("jobFullName:  " + jobFullName)
                        jobNameToken = jobFullName.tokenize("/")

                        jobNameToken.each { String token ->
                            jobBaseUrl += "job/" + token + "/"
                            println("jobBaseUrl: " + jobBaseUrl)
                        }
                    } else {
                        jobBaseUrl = "job/" + jobFullName
                    }
                    //  jobNameToken = jobNameToken.toString()
                    // println("jobNameToken.toString() : " + jobNameToken.toString())

                    buildLogUrls[jobIndex] = "$HOSTNAME/$jobBaseUrl/$jobBuildNumber/logText/progressiveText?start=0"
                    jobIndex++
                }
            }
        }

    }
    return buildLogUrls
}

def getHostName(def val) {
    def result

    switch (val) {

        case "M5.csv":
            result = M5_HOSTNAME
            println("M5.csv:" + val)
            break

        case "M6.csv":
            result = M6_HOSTNAME
            println("M6.csv:" + val)
            break
        default:
            result = HOSTNAME
            println("Default csv:" + val)
            break
    }
    result
}

def saveLogFiles(jobUrls) {
    def index = 1
    def logFiles = []
    jobUrls.each { jobUrl ->
        def logText = jobUrl.toURL().text
        println(logText)

        def directory = '/Users/yogeshkumararora/IdeaProjects/jenkins-plugins-wrkspc/groovy-play-area/logs/'

        def file = new File(directory + "jenkins_logs_" + index + ".txt")
        file.text = logText
        logFiles.add(file)
        index++
    }
    println("logFiles: " + logFiles)
    return logFiles
}

def csvFiles = getAllCsvFiles(location)
println("csvFiles: " + csvFiles)
def jobUrls = getBuildLogUrls(csvFiles)
println("jobUrls:" + jobUrls)

saveLogFiles(jobUrls)
/*
int wordCount = 0
file1.eachLine { String line ->
    line.tokenize(",").each { String word ->
        wordCount++
        println word
    }
}
println "Number of words: $wordCount"*/
