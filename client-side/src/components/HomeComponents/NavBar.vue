<template>
  <div class="navbar-container">
    <div class="logo">
      <router-link :to="{ name: 'home' }"><img src="@/assets/Guegue_logo.png" /></router-link>
    </div>

    <!-- Hamburger Icon (visible on small screens) -->
    <div class="hamburger" @click="toggleMenu">
      <i :class="menuOpen ? 'fa fa-times' : 'fa fa-bars'"></i>
    </div>

    <div class="nav-links" :class="{ open: menuOpen }">
      <ul>
        <li><a href="#home" @click.prevent="scrollToSection('home')">Home</a></li>
        <li><a href="#why-us" @click.prevent="scrollToSection('why-us')">Investment Deals</a></li>
        <li><a href="#sell" @click.prevent="scrollToSection('sell')">Quick Sale</a></li>
        <li><a href="#buy" @click.prevent="scrollToSection('buy')">Properties</a></li>
        <li id="phone-number">✆ (702) 596-3245</li>
        <li v-if="!isAdmin" id="get-offer-button">
          <a href="#sell" @click.prevent="scrollToSection('sell')">Get Cash Offer</a>
        </li>
        <li v-else class="admin-nav">
          <div class="admin-box">
            <i class="fa-regular fa-user"></i>
            <p>Admin</p>
          </div>
          <div class="logout">
            <i class="fa-solid fa-right-from-bracket"></i>
            <router-link :to="{ name: 'logout' }"><p id="logout-text">Logout</p></router-link>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import AuthService from '@/services/AuthService'

export default {
  data() {
    return {
      menuOpen: false,
    }
  },
  computed: {
    // always render from Vuex (reactive)
    user() {
      return this.$store.state.user || {}
    },
    // robust role check
    isAdmin() {
      const role = this.user.role || (Array.isArray(this.user.authorities) && this.user.authorities[0]) || ''
      return ['ROLE_ADMIN', 'ADMIN', 'admin', 'ROLE_SUPER_ADMIN'].includes(String(role))
    },
  },
  mounted() {
    // hydrate on hard refresh (if token exists but store empty)
    const hasRole = !!(this.$store.state.user && this.$store.state.user.role)
    const token = this.$store.state.token || localStorage.getItem('token')
    if (!hasRole && token) {
      AuthService.getUser()
        .then((res) => { if (res && res.data) this.$store.commit('SET_USER', res.data) })
        .catch(() => {})
    }
  },
  methods: {
    toggleMenu() { this.menuOpen = !this.menuOpen },
    scrollToSection(id) {
      const el = document.getElementById(id)
      if (el) el.scrollIntoView({ behavior: 'smooth', block: 'start' })
      this.menuOpen = false
    },
  },
}
</script>


<style scoped>
i {
  margin-right: 10px;
}
.admin-nav {
  display: flex;
  gap: 10px;
  align-items: center;
}
.admin-box,
.logout {
  display: flex;
  gap: 5px;
  align-items: center;
  border: 1px solid #383737;
  padding: 7px 15px;
  border-radius: 15px;
}
.admin-box {
  color: rgb(238, 118, 238);
  background-color: rgba(101, 56, 101, 0.523);
  border: 1px solid rgb(141, 60, 141);
}
.logout {
  color: rgb(213, 100, 100);
  background-color: rgba(101, 56, 56, 0.416);
  border: 1px solid rgb(163, 80, 78);
  cursor: pointer;
}
#logout-text {
  color: rgb(213, 100, 100);
}
.logout:hover, .logout:hover #logout-text {
  background-color: rgb(163, 80, 78);
  color: white;
}
#admin {
  padding: 7px 15px;
  border-radius: 5px;
  background-color: #1f2834;
  color: white;
  border: 1px solid #383737;
}
#logout {
  padding: 7px 15px;
  border-radius: 5px;
  background-color: #512121;
  color: rgb(221, 121, 121);
  border: 1px solid #ab4141;
  margin-left: 10px;
  cursor: pointer;
}
#logout:hover {
  background-color: #ab4141;
  color: white;
}
.navbar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #151c24;
  border-bottom: #30303054 1px solid;
  height: 90px;
  position: relative;
}
.logo img {
  width: 85px;
  height: auto;
}
ul {
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style: none;
  padding: 0;
  margin: 0;
}
li {
  margin: 0 20px;
  color: white;
  cursor: default;
}
ul li:nth-child(-n + 4):hover {
  font-weight: 550;
}

ul li a {
  text-decoration: none;
  color: white;
}
#phone-number {
  color: #55dbc1;
}
#get-offer-button {
  background: linear-gradient(
    90deg,
    rgba(42, 155, 132, 1) 0%,
    rgba(87, 199, 133, 1) 26%,
    rgb(197, 185, 71) 100%
  );
  font-weight: bolder;
  padding: 10px;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
#get-offer-button:hover {
  background: linear-gradient(
    90deg,
    rgba(42, 155, 132, 0.756) 0%,
    rgba(87, 199, 134, 0.658) 26%,
    rgba(197, 184, 71, 0.769) 100%
  );
}

/* Hamburger Icon (hidden on desktop) */
.hamburger {
  display: none;
  font-size: 28px;
  color: white;
  cursor: pointer;
}

/* Tablet & Mobile */
@media screen and (max-width: 1024px) {
  .hamburger {
    display: block;
  }

  .nav-links {
    position: absolute;
    top: 90px;
    right: 0;
    background-color: #151c24;
    width: 100%;
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.4s ease-out;
  }

  .nav-links.open {
    max-height: 400px; /* enough for full list */
    transition: max-height 0.4s ease-in;
  }

  ul {
    flex-direction: column;
    align-items: start;
    padding: 10px 20px;
  }

  li {
    margin: 10px 0;
  }
  #get-offer-button {
    width: 100%;
    text-align: center;
  }
}
</style>
