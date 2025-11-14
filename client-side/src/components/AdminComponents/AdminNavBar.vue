<template>
  <nav class="admin-nav">
    <!-- Logo + Title -->
    <div class="logo-container">
      <router-link :to="{ name: 'home' }"><img src="@/assets/Guegue_logo.png" /></router-link>
      <p>Admin Dashboard</p>
    </div>

    <!-- Hamburger Icon (mobile only) -->
    <div class="hamburger" @click="toggleMenu">
      <i :class="menuOpen ? 'fa-solid fa-times' : 'fa-solid fa-bars'"></i>
    </div>

    <!-- Right side content -->
    <div class="right-side" :class="{ open: menuOpen }">
      <div class="user-info">
        <p id="admin-title">Admin User</p>
        <p>{{ user.username }}</p>
      </div>

      <div class="nav-buttons">
        <div class="close-dashboard-bttn" @click="goHome">
          <i class="fa-solid fa-xmark"></i>
          <p>Close Dashboard</p>
        </div>

        <router-link :to="{ name: 'logout' }">
          <div class="logout">
            <i class="fa-solid fa-right-from-bracket"></i>
            <p id="logout-text">Logout</p>
          </div>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
import AuthService from '@/services/AuthService'

export default {
  data() {
    return {
      user: {},
      menuOpen: false,
    }
  },
  methods: {
    getUser() {
      AuthService.getUser()
        .then((response) => {
          this.user = response.data
        })
        .catch((error) => {
          console.error(error)
        })
    },
    goHome() {
      this.$router.push({ name: 'home' })
    },
    toggleMenu() {
      this.menuOpen = !this.menuOpen
    },
  },
  mounted() {
    this.getUser()
  },
}
</script>

<style scoped>
/* --- Base Layout --- */
.admin-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 60px;
  background-color: #151c24;
  border-bottom: 1px solid #30303054;
  height: 90px;
  position: relative;
  color: white;
}

.logo-container {
  display: flex;
  align-items: center;
  gap: 5px;
  color: rgb(199, 197, 197);
  font-weight: 550;
}

.logo-container a{
  width: 85px;
}

.logo-container img {
  width: 85px;
  height: auto;
}

/* --- Hamburger (mobile) --- */
.hamburger {
  display: none;
  font-size: 24px;
  cursor: pointer;
  color: white;
}

/* --- Right Side Menu --- */
.right-side {
  display: flex;
  align-items: center;
  gap: 20px;
  color: white;
  transition: all 0.3s ease;
}

.user-info {
  display: flex;
  flex-direction: column;
  text-align: right;
  gap: 3px;
  font-size: 13px;
}

#admin-title {
  font-weight: 600;
  font-size: 15px;
}

.nav-buttons {
  display: flex;
  align-items: center;
  gap: 15px;
}

/* 🔧 Unified button base styling */
.close-dashboard-bttn,
.logout {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border-radius: 10px;
  padding: 10px 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  height: 42px; /* ✅ Equal height */
  line-height: 1; /* Prevent font scaling mismatch */
  box-sizing: border-box;
}

/* Individual styles */
.close-dashboard-bttn {
  border: 1px solid #383737;
  background: #3838386c;
  color: white;
}

.close-dashboard-bttn:hover {
  background-color: #858484;
  color: #000;
}

.logout {
  background-color: #512121;
  color: rgb(221, 121, 121);
  border: 1px solid #ab4141;
}

.logout:hover {
  background-color: #ab4141;
  color: white;
}

a {
  width: 100%;
  text-decoration: none;
  color: inherit;
}

/* ---------------- RESPONSIVE ---------------- */

/* Tablets and smaller */
@media (max-width: 1024px) {
  .admin-nav {
    padding: 12px 30px;
  }

}

/* Tablets & Phones */
@media (max-width: 768px) {
  .hamburger {
    display: block;
  }

  .right-side {
    position: absolute;
    top: 90px;
    left: 0;
    width: 100%;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #1b242f;
    border-top: 1px solid #30303054;
    padding: 20px 0;
    transform: translateY(-200%);
    opacity: 0;
    pointer-events: none;
  }

  .right-side.open {
    transform: translateY(0);
    opacity: 1;
    pointer-events: auto;
  }

  .user-info {
    text-align: center;
    font-size: 13px;
    margin-bottom: 15px;
  }

  .nav-buttons {
    width: 100%;
    flex-direction: column;
    gap: 10px;
  }

  .close-dashboard-bttn,
  .logout {
    width: 100%;
    justify-content: center;
  }

  .logo-container img {
    width: 70px;
  }

  .logo-container p {
    font-size: 14px;
  }
}

/* Phones (under 480px) */
@media (max-width: 480px) {
  .admin-nav {
    flex-direction: row;
    justify-content: space-between;
    padding: 10px 20px;
  }

  .logo-container img {
    width: 60px;
  }

  .logo-container p {
    font-size: 13px;
  }

  .hamburger {
    font-size: 22px;
  }

  .close-dashboard-bttn,
  .logout {
    font-size: 13px;
    padding: 8px 10px;
    height: 40px; /* ✅ Same height for smaller screens too */
  }
}
</style>

