package com.bill.tracker.views.htmx

import zio.http.template.Attributes.PartialAttribute

object HtmxAttributes {

  final def hxGet: PartialAttribute[String] = PartialAttribute("hx-get")
  final def hxTrigger: PartialAttribute[String] = PartialAttribute("hx-trigger")
  final def hxTarget: PartialAttribute[String] = PartialAttribute("hx-target")

}
