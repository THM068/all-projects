package patterns.command
//invoker
class Switch {

    fun storeAndExecute(command: Command) {
        command.execute()
    }

}
