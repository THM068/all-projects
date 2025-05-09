package com.bill.tracker.views
import zio.http.template.Element.PartialElement
import zio.http.template._
object MainLayout {
  def layout(pageTitle: String, content: Html) = html(
    // Support for child nodes
    head(
      title(pageTitle),
      script(srcAttr := "https://unpkg.com/htmx.org@1.9.9"),
      link(relAttr := "stylesheet", href := "https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css")
    ),
    body(
      div(css := "container" :: Nil, content, footer),
      )
  )

  def footer = div(
    css := "footer" :: Nil, "Footer"
  )
}
