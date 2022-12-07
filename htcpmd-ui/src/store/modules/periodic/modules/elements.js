import * as mutationTypes from '../mutation-types'
const elementsData = require('@/assets/data/periodic.json')
let group = order =>
  (order < 3 ? 'first' :
    (order < 19 ? 'second' :
      (order < 91 ? 'body' : 'bottom')))
export default {
  state: {
    darkMode: mutationTypes.darkMode,
    lang: mutationTypes.lang || 'en',
    Elements: {
      first: [],
      second: [],
      body: [],
      bottom: []
    }
  },
  mutations: {
    SET_ELEMENTS: (state, data) => {
      data.forEach(el =>
        state.Elements[group(el.el_order)]
        .push(el))
    },
    TOGGLE_DARK_MODE: (state) => {
      state.darkMode = mutationTypes != state.darkMode
    },
    LANG_SWITCH(state, data) {
      state.lang = this._vm.i18n.locale = data ? data : (state.lang == 'tr' ? 'en' : 'tr')
      mutationTypes.lang = state.lang
      document.title = this._vm.i18n.messages[state.lang].UI.name
    }
  },
  getters: {
    Elements: state => state.Elements,
    darkMode: state => state.darkMode,
    lang: state => state.lang,
  },
  actions: {
    getElements: async ({
      state,
      commit
    }) => {
      if (state.Elements.first.length == 0)
        commit('SET_ELEMENTS', elementsData)
    }
  }
}
