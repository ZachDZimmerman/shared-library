def call() {
    def script = libraryResource('com/example/scripts/example.sh')
    sh "$script"
}
