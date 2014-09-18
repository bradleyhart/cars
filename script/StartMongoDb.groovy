def databaseLocation = "/home/brad"
def databaseName = "cars"
def mongodLocation = "/home/brad/software/mongodb/mongodb-linux-x86_64-2.6.4/bin/mongod"

if (!isRunningAlready(databaseLocation, databaseName)) {
    run "rm -rf $databaseLocation/$databaseName"
    run "mkdir -p $databaseLocation/$databaseName"
    run "$mongodLocation --dbpath $databaseLocation/$databaseName"
}

private boolean isRunningAlready(String databaseLocation, String databaseName) {
    run("ps -aux").contains("$databaseLocation/$databaseName")
}

def run(String command) {
    println "Running $command"
    def process = command.execute()
    process.waitFor()
    def text = process.text
//    println text
    if (process.exitValue() != 0) throw new RuntimeException("$command failed")
    text
}