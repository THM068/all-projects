package patterns.command

class OnCommand(val light: Light): Command {

    override fun execute() {
        this.light.on()
    }
}
