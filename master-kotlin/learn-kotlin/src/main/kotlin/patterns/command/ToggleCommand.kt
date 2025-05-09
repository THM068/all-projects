package patterns.command

class ToggleCommand(val light: Light) : Command{

    override fun execute() {
        this.light.toggle()
    }
}