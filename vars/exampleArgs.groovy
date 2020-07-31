def call(Map argsMap = [:], Object block) {
    
    // Handle defaults in arguments
    def defaultMap = [ hasDefaultArg: 'baz' ]
    def args = defaultMap << argsMap

    // Required arguments
    assert args.requiredArg: "Missing required arugment."

    println "Arguments: " + args.toMapString()
    println "Block:" + block
}

