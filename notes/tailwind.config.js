/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./web/templates/**/*.html",
          "./web/templates/*.html","./static/**/*.js"],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms')
  ],
}

