package patterns.chainOfResponsibility

import patterns.chainOfResponsibility.RequestType.PURCHASE

class VP : Handler() {

    override fun handleRequest(request: Request) {
        if(request.requestType == PURCHASE) {
            if(request.amount < 1500) {
                println("VP's can approve purchases below 1500")
            }
            else {
                successor?.handleRequest(request)
            }
        }
    }

}
