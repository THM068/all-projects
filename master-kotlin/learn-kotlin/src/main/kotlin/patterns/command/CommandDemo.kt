package patterns.command

fun main() {
    val bedroomLight = Light()
    val kitchenLight = Light()
    val light = Light()

    val lightSwitch = Switch()
    val toggleCommand = ToggleCommand(light)
    lightSwitch.storeAndExecute(toggleCommand)
    lightSwitch.storeAndExecute(ToggleCommand(kitchenLight))
    lightSwitch.storeAndExecute(ToggleCommand(bedroomLight))

    val lights = listOf<Light>(bedroomLight, kitchenLight, light)
    val allLightsCommand = AllLightsCommand(lights)

    lightSwitch.storeAndExecute(allLightsCommand)


}