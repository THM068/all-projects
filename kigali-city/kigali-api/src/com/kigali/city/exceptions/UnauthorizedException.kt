package com.kigali.city.com.kigali.city.exceptions

class UnauthorizedException(message: String ="email or password invalid."): BusinessException(message)