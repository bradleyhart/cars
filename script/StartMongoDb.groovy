def databaseLocation = "/home/brad"
def databaseName = "cars"
def mongodLocation = "/home/brad/software/mongodb/mongodb-linux-x86_64-2.6.4/bin/mongod"

run "rm -rf $databaseLocation/$databaseName"
run "mkdir -p $databaseLocation/$databaseName"

run "$mongodLocation --dbpath $databaseLocation/$databaseName"

def run(command){
    println "Running $command"
    def process = command.execute()
    println process.text
    if (process.exitValue() != 0) throw new RuntimeException("$command failed")
}