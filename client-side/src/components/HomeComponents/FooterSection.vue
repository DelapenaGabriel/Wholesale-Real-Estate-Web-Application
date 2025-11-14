<template>
  <footer class="footer">
    <div class="footer-container">
      <!-- Company Info -->
      <div class="footer-column company">
        <div class="logo">
          <img src="../../assets/Guegue_logo.png" alt="Logo" />
          <div>
            <h3>GUEGUE</h3>
            <p>REAL ESTATE GROUP</p>
          </div>
        </div>
        <p class="description">
          Pioneering wholesale real estate solutions. We connect motivated sellers with qualified
          cash buyers, facilitating fast, profitable transactions nationwide.
        </p>
        <div class="social-media">
          <a href="https://www.instagram.com/gueguerealty" target="_blank" rel="noopener noreferrer"
            ><i class="fa-brands fa-instagram"></i
          ></a>
        </div>
      </div>

      <!-- Quick Links -->
      <div class="footer-column links">
        <h4>Quick Links</h4>
        <ul>
          <li><a href="#home" @click.prevent="scrollToSection('home')">Home</a></li>
          <li><a href="#why-us" @click.prevent="scrollToSection('why-us')">Why Us</a></li>
          <li><a href="#sell" @click.prevent="scrollToSection('sell')">Sell Quick</a></li>
          <li><a href="#buy" @click.prevent="scrollToSection('buy')">Buy Wholesale</a></li>
          <li><a href="#home" @click.prevent="scrollToSection('home')">About</a></li>
        </ul>
      </div>

      <!-- Contact -->
      <div class="footer-column contact">
        <h4>Contact</h4>
        <div class="contact-item">
          <i class="fa-solid fa-phone"></i>
          <div>
            <span>Call Us</span>
            <p>(702) 596-3245</p>
          </div>
        </div>
        <div class="contact-item">
          <i class="fa-solid fa-envelope"></i>
          <div>
            <span>Email</span>
            <p>joemelandre@guegueinvest.org</p>
          </div>
        </div>
      </div>
    </div>

    <div class="footer-bottom">
      <p>© 2025 Guegue Real Estate Group. All rights reserved.</p>
      <div class="bottom-links">
        <router-link to="/privacy-policy">Privacy Policy</router-link>
        <router-link to="/terms-of-service">Terms of Service</router-link>
        <router-link to="/compliance">Compliance</router-link>
      </div>
    </div>

    <!-- Fixed Admin Login Button -->
    <div v-if="currentUser.role !== 'ROLE_ADMIN'" class="admin-login-bttn">
      <button class="admin-login" @click="openLogin">
        <i class="fa-solid fa-right-to-bracket"></i>
        Admin Login
      </button>
    </div>

    <div v-else class="admin-login-bttn">
      <button class="admin-login" @click="openDashboard">
        <i class="fa-solid fa-right-to-bracket"></i>
        <p>Open Dashboard</p>
      </button>
    </div>
    

    <!-- Admin Login Modal -->
    <transition name="fade">
      <div v-if="showLogin" class="al-overlay" @click.self="closeLogin">
        <div class="al-card" role="dialog" aria-modal="true" aria-labelledby="al-title">
          <button class="al-close" aria-label="Close" @click="closeLogin">×</button>

          <h3 id="al-title">Welcome Back</h3>
          <p class="al-sub">Sign in to access your admin dashboard.</p>

          <form class="al-form" @submit.prevent="login">
            <label class="al-label">Email</label>
            <div class="al-input">
              <i class="fa-regular fa-envelope"></i>
              <input
                ref="emailEl"
                type="email"
                v-model="user.username"
                placeholder="your@email.com"
                required
              />
            </div>

            <label class="al-label">Password</label>
            <div class="al-input">
              <i class="fa-solid fa-lock"></i>
              <input
                :type="showPw ? 'text' : 'password'"
                v-model="user.password"
                placeholder="••••••••"
                required
                minlength="6"
              />
              <button class="al-eye" type="button" @click="showPw = !showPw">
                <i :class="showPw ? 'fa-regular fa-eye-slash' : 'fa-regular fa-eye'"></i>
              </button>
            </div>

            <button class="al-submit" type="submit" :disabled="loading">
              <span v-if="!loading">Sign In</span>
              <span v-else>Signing in…</span>
            </button>

            <p v-if="error" class="al-error">{{ error }}</p>
          </form>
        </div>
      </div>
    </transition>
  </footer>
</template>

<script>
import AuthService from '@/services/AuthService' // <-- use your real service
export default {
  name: 'FooterSection',
  data() {
    return {
      showLogin: false,
      showPw: false,
      loading: false,
      error: '',
      user : {
        username: '',
        password: '',
      },
      currentUser: {
      }
    };
  },
  methods: {
    getUser(){
      AuthService.getUser().then((response) => {
        this.currentUser = response.data;
      }).catch((error) => {console.log(error)});
    },
    openLogin() {
      this.showLogin = true;
      this.$nextTick(() => this.$refs.emailEl && this.$refs.emailEl.focus());
      // ESC to close
      this._esc = (e) => e.key === 'Escape' && this.closeLogin();
      window.addEventListener('keydown', this._esc);
    },
    closeLogin() {
      this.showLogin = false;
      this.error = '';
      window.removeEventListener('keydown', this._esc);
    },
    openDashboard() {
      this.$router.push('/admin');
    },
    login() {
      AuthService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            console.log(response.data);
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.getUser();
            this.closeLogin();
          }
        })
        .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else if (response.status === 401) {
            alert("Invalid username and password!");
          } else {
            alert(response.message);
          }
        }).finally(() => { this.loading = false; });
    },
    scrollToSection(id) {
      const el = document.getElementById(id);
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'start' });
    },

    // Kept for compatibility if you need it elsewhere
    goToAdmin() {
      this.openLogin(); // now opens modal instead of routing
    },
  },
  mounted() {
    this.getUser();
  },
};
</script>

<style scoped>
/* --- existing footer styles (unchanged) --- */
.footer { background: #0b0d11; color: #ccc; padding-top: 3rem; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; position: relative; }
.footer-container { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 2.5rem; padding: 0 3rem; }
.footer-column h4 { color: #fff; margin-bottom: 1rem; font-size: 1rem; }
.footer-column.company .logo { display: flex; align-items: center; gap: 0.8rem; }
.logo img { width: 40px; height: 40px; border-radius: 12px; }
.logo h3 { color: #fff; margin: 0; font-weight: 600; }
.logo p { margin: 0; font-size: 0.75rem; color: #999; letter-spacing: 0.5px; }
.description { margin: 1rem 0; font-size: 0.9rem; line-height: 1.5; }
.links ul { list-style: none; padding: 0; }
.links ul li { margin-bottom: 0.6rem; }
.links ul li a { text-decoration: none; color: #ccc; transition: color 0.3s; }
.links ul li a:hover { color: #00c897; }
.contact-item { display: flex; align-items: flex-start; gap: 0.8rem; margin-bottom: 1rem; }
.contact-item i { color: #00c897; font-size: 1rem; margin-top: 2px; }
.contact-item span { font-size: 0.8rem; color: #999; }
.contact-item p { margin: 0; color: #ccc; font-size: 0.9rem; }
.footer-bottom { border-top: 1px solid #1a1d22; margin-top: 3rem; padding: 1rem 3rem; display: flex; flex-direction: column; align-items: center; gap: 1rem; }
.footer-bottom p { margin: 0; color: #888; font-size: 0.85rem; }
.bottom-links { display: flex; flex-wrap: wrap; align-items: center; gap: 1rem; }
.bottom-links a { color: #aaa; text-decoration: none; font-size: 0.85rem; transition: color 0.3s; }
.bottom-links a:hover { color: #00c897; }
.admin-login { position: fixed; right: 20px; bottom: 20px; background: linear-gradient(90deg, #a100ff, #ff0077); color: #fff; border: none; padding: 0.6rem 1.2rem; border-radius: 25px; font-size: 0.9rem; display: flex; align-items: center; gap: 0.5rem; cursor: pointer; box-shadow: 0 4px 10px rgba(0,0,0,0.4); z-index: 1000; transition: transform .2s ease, opacity .2s ease; }
.admin-login:hover { transform: scale(1.05); opacity: 0.9; }
@media (min-width: 768px) { .footer-bottom { flex-direction: row; justify-content: space-between; } }

/* --- IG logo --- */
.social-media a{
text-decoration: none;
color: #fff;
font-size: 1.4rem;
transition: color 0.3s;
}
.social-media a:hover{
color: #bdbdbd;
}


/* --- Modal styles --- */
.al-overlay {
  position: fixed; inset: 0; z-index: 2000;
  background: rgba(11, 16, 23, 0.65);
  backdrop-filter: blur(4px);
  display: grid; place-items: center;
}
.fade-enter-active, .fade-leave-active { transition: opacity .2s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

.al-card {
  width: min(520px, 92vw);
  background: #0f1621;
  border: 1px solid #223041;
  border-radius: 14px;
  box-shadow: 0 20px 60px rgba(0,0,0,.45);
  padding: 22px 22px 26px;
  color: #e8f3ff;
  position: relative;
  animation: pop .2s ease;
}
@keyframes pop { from { transform: translateY(-8px); opacity: 0; } to { transform: none; opacity: 1; } }

.al-close {
  position: absolute; top: 10px; right: 12px;
  width: 34px; height: 34px; border-radius: 8px;
  background: #14202e; color: #9fb5c9; border: 1px solid #223041;
  cursor: pointer;
}
.al-close:hover { color: #fff; }

h3#al-title { margin: 4px 0 6px; font-size: 22px; color: #ffffff; }
.al-sub { margin: 0 0 14px; color: #98a8bc; font-size: 0.95rem; }

.al-form { display: grid; gap: 12px; }
.al-label { font-size: 0.85rem; color: #a8b6c8; }

.al-input {
  display: flex; align-items: center; gap: 10px;
  background: #0e2233; border: 1px solid #233447;
  padding: 10px 12px; border-radius: 10px;
}
.al-input i { color: #7fa3c1; }
.al-input input {
  flex: 1; background: transparent; border: 0; outline: 0; color: #e8f3ff;
  font-size: 0.95rem;
}
.al-eye { border: 0; background: transparent; color: #9bb3c8; cursor: pointer; }

.al-submit {
  margin-top: 6px;
  width: 100%;
  padding: 12px 16px;
  border: 0; border-radius: 10px;
  color: #fff; font-weight: 600; cursor: pointer;
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  transition: transform .15s ease, opacity .15s ease;
}
.al-submit:hover { transform: translateY(-1px); opacity: .95; }
.al-submit:disabled { opacity: .6; cursor: not-allowed; }

.al-error { color: #ff7b7b; margin: 6px 0 0; font-size: 0.9rem; }
</style>
