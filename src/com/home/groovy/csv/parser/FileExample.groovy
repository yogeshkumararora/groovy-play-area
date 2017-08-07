package com.home.groovy.csv.parser

/**
 * Created by yogeshkumararora on 06/08/17.
 */
class FileExample {
    static void main(String[] args) {
        new File("/Users/yogeshkumararora/IdeaProjects/jenkins-plugins-wrkspc/groovy-play-area/src/com/home/groovy/csv/parser").eachFileMatch(~/.*.csv/) { file ->
           // println file.getName()
        }
    }
}
