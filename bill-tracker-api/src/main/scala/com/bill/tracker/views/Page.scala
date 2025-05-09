package com.bill.tracker.views
import com.bill.tracker.views.MainLayout.layout
import com.bill.tracker.views.htmx.HtmxAttributes.{hxGet, hxTarget, hxTrigger}
import zio.http.template._
//    <link rel="stylesheet" th:href="@{/static/application.css}" type="text/css">
object Page {
  def main = layout("Hello world",
    div(
      div(id := "bunny"),
      h1("Hello world"),
      div(css := "container":: Nil, div(css := "notification" :: "is-primary" :: Nil,
        s"This container is centered on desktop and larger viewports."
        )
      ),
      button(hxGet := "/bunny", hxTarget := "#bunny", hxTrigger := "click", "click me")
    )
  )
}
