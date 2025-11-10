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
        <li>Home</li>
        <li>Investment Deals</li>
        <li>Quick Sale</li>
        <li>Properties</li>
        <li id="phone-number">✆ (702) 596-3245</li>
        <li v-if="user.role !== 'ROLE_ADMIN'" id="get-offer-button">Get Cash Offer</li>
        <div v-else class="admin-nav">
          <div class="admin-box">
            <li id="admin"><i class="fa fa-user"></i>Admin</li>
          </div>
          <router-link :to="{ name: 'logout' }"
            ><button id="logout"><i class="fa fa-sign-out"></i> Logout</button></router-link
          >
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import authService from '@/services/AuthService'
export default {
  data() {
    return {
      user: {
        id: '',
        username: '',
        role: '',
      },
      menuOpen: false,
    }
  },
  methods: {
    toggleMenu() {
      this.menuOpen = !this.menuOpen
    },
    getUser() {
      authService
        .getUser()
        .then((response) => {
          this.user.id = response.data.id
          this.user.username = response.data.username
          this.user.role = response.data.role
        })
        .catch((error) => {
          console.log('Error Occurred retrieving user', error)
        })
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
  align-items: center;
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
  font-weight: bold;
  padding: 10px;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}
#get-offer-button:hover {
  color: #000000;
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
