/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', 'ui-sans-serif', 'system-ui', '-apple-system', 'sans-serif'],
      },
      colors: {
        dev: {
          bg: '#0B0D0F',
          card: '#111418',
          border: '#252A30',
          text: '#F2F4F7',
          secondary: '#9AA1AA',
          muted: '#6B7280',
          accent: '#22D3EE',
          accentSecondary: '#3B82F6',
          success: '#22C55E',
        },
      },
    },
  },
  plugins: [],
};
