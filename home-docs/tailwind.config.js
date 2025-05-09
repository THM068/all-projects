/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./templates/*.html"],
  theme: {
    container: {
        center: true,
        maxWidth: {
            'sm': '640px',
            'md': '768px',
            'lg': '1024px',
            'xl': '1280px',
        },
    },
    extend: {

    },
  },
  plugins: [require('@tailwindcss/forms'),],
}
