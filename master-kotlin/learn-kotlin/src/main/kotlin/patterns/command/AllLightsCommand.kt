package patterns.command

class AllLightsCommand(val lights: List<Light>) : Command {

    override fun execute() {
        this.lights.forEach {
            if(it.isOn) {
                it.toggle()
            }
        }
    }

}
