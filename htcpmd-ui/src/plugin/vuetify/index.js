import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify, {
  theme: {
    primary: '#232937',
    secondary: '#282e3c',
    accent: '#303747',
    info: '#323642',
    error: '#ff643d',
  },
});

const opts = {}

export default new Vuetify(opts)
