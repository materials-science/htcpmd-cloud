<template>
  <div class="page-login" :loading="loading">
    <page-background>
      <account-form>
        <!-- logo -->
        <img class="page-login--logo" src="@/assets/images/all.png" />
        <!-- form -->
        <div class="page-login--form" style="transform: scale(1.1)">
          <el-card shadow="never">
            <el-form
              ref="loginForm"
              label-position="top"
              :rules="rules"
              :model="loginForm"
              size="default"
            >
              <el-form-item prop="username">
                <el-input
                  type="text"
                  v-model="loginForm.username"
                  placeholder="username"
                >
                  <i slot="prepend" class="fa fa-user-o"></i>
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  type="password"
                  v-model="loginForm.password"
                  placeholder="password"
                >
                  <i slot="prepend" class="fa fa-keyboard-o"></i>
                </el-input>
              </el-form-item>
              <el-form-item prop="code" v-if="captchaEnabled">
                <el-input
                  type="text"
                  v-model="loginForm.code"
                  placeholder="validate code"
                  auto-complete="off"
                >
                  <template slot="append">
                    <img
                      class="login-code"
                      alt="validate code"
                      :src="codeUrl"
                      @click="getCode"
                    />
                  </template>
                </el-input>
              </el-form-item>
              <el-button
                size="default"
                @click="submit"
                type="primary"
                class="button-login"
              >
                Sign in
              </el-button>
            </el-form>
          </el-card>
          <p class="page-login--options" flex="main:justify cross:center">
            <span><d2-icon name="question-circle" /> Forget</span>
            <span v-if="register" @click="redirect_to_signup()">Sign up</span>
          </p>
        </div>
      </account-form>
    </page-background>
  </div>
</template>

<script>
import PageBackground from "../components/page-background";
import AccountForm from "../components/account-form";
import { mapActions } from "vuex";
import localeMixin from "@/locales/mixin.js";
import api from "@/api";
import util from "@/libs/util.js";
import { encrypt, decrypt } from "@/libs/jsencrypt";
export default {
  components: { PageBackground, AccountForm },
  mixins: [localeMixin],
  data() {
    return {
      // 表单
      loginForm: {
        username: "guest",
        password: "guest@ias",
        code: "v9am",
        uuid: "",
        rememberMe: false
      },
      // 表单校验
      rules: {
        username: [
          {
            required: true,
            message: "Please input username",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "Please input password",
            trigger: "blur"
          }
        ],
        code: [
          {
            required: true,
            message: "Please input code",
            trigger: "blur"
          }
        ]
      },
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      loading: false,
      codeUrl: ""
    };
  },
  methods: {
    ...mapActions("d2admin/account", ["login"]),
    getCode() {
      api.AUTH_CODE_IMG().then(res => {
        this.captchaEnabled =
          res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = util.cookies.get("username");
      const password = util.cookies.get("password");
      const rememberMe = util.cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    /**
     * @description 接收选择一个用户快速登录的事件
     * @param {Object} user 用户信息
     */
    handleUserBtnClick(user) {
      this.loginForm.username = user.username;
      this.loginForm.password = user.password;
      this.submit();
    },
    redirect_to_signup() {
      this.$router.replace("/signup");
    },
    /**
     * @description 提交表单
     */
    // 提交登录信息
    submit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          const Cookies = util.cookies;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, {
              expires: 30
            });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          // 登录
          this.login({
            username: this.loginForm.username,
            password: this.loginForm.password,
            code: this.loginForm.code,
            uuid: this.loginForm.uuid
          })
            .then(() => {
              this.loading = false;
              // 重定向对象不存在则返回顶层路径
              this.$router.replace(this.$route.query.redirect || "/");
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        } else {
          // 登录表单校验失败
          this.$message.error("Validated form failed, check it.");
        }
      });
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  }
};
</script>

<style lang="scss">
// 快速选择用户面板
.page-login--quick-user {
  @extend %flex-col;
  justify-content: center;
  align-items: center;
  padding: 10px 0px;
  border-radius: 4px;
  &:hover {
    background-color: $color-bg;
    i {
      color: $color-text-normal;
    }
    span {
      color: $color-text-normal;
    }
  }
  i {
    font-size: 36px;
    color: $color-text-sub;
  }
  span {
    font-size: 12px;
    margin-top: 10px;
    color: $color-text-sub;
  }
}
</style>
