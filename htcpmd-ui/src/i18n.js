import Vue from "vue";
import VueI18n from "vue-i18n";
import util from "@/libs/util";
import setting from "./setting";

Vue.use(VueI18n);

function loadLocaleMessages() {
  const locales = require.context(
    "./locales",
    true,
    /[A-Za-z0-9-_,\s]+\.json$/i
  );
  const messages = {};
  for (const key of locales.keys()) {
    const matched = key.match(/([A-Za-z0-9-_]+)\./i);
    if (matched && matched.length > 1) {
      const locale = matched[1];
      const localeElementUI = require(`element-ui/lib/locale/lang/${
        locales(key)._element
      }`);
      messages[locale] = {
        ...locales(key),
        ...(localeElementUI ? localeElementUI.default : {})
      };
    }
  }
  return messages;
}

const messages = loadLocaleMessages();

Vue.prototype.$languages = Object.keys(messages).map(language => ({
  label: messages[language]._name,
  value: language
}));

const i18n = new VueI18n({
  locale: util.cookies.get("lang") || process.env.VUE_APP_I18N_LOCALE,
  fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE,
  messages,
  silentTranslationWarn: true
});

Vue.mixin({
  methods: {
    $_t: (key, split = false, ignoreCase = true, pre = "dict.", ...args) => {
      let old = key;
      if (setting.i18n.ignoreCase && ignoreCase) {
        key = key.toLocaleLowerCase();
      }
      if (setting.i18n.split && split && i18n.locale.includes("zh-")) {
        let oldSp = old.split(/\W+/);
        if (oldSp.length > 1) {
          return key
            .split(/\W+/)
            .map((str, idx) => {
              let res = i18n.t(pre + str, args);
              return res === pre + str ? oldSp[idx] : res;
            })
            .join(" ")
            .replace(/(?<=[\u4E00-\u9FA5])\s+(?=[\u4E00-\u9FA5])/g, "");
        }
      }
      let res = i18n.t(pre + key, args);
      if (res === pre + key) {
        res = i18n.t(pre + old, args);
        return res === pre + old ? old : res;
      }
      return res === pre + key ? i18n.t(old, args) : res;
    }
  }
});

export default i18n;
