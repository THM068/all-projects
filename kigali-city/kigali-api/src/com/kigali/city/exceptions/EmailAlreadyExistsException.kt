package com.kigali.city.com.kigali.city.exceptions

import com.kigali.city.com.kigali.city.controller.dto.ErrorMessage

class EmailAlreadyExistsException(message: String = "Email already exists"): BusinessException(message) {
}