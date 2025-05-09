/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./assets/views/*.html", "./assets/views/**/*.html"],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms')
  ],
}

