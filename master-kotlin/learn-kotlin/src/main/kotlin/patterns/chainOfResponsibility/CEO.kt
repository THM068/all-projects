package patterns.chainOfResponsibility

class CEO : Handler() {
    override fun handleRequest(request: Request) {
        println("CEO's can approve anything they want.")
    }

}
