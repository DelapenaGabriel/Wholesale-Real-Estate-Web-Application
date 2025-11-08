<template>
  <div class="navbar-container">
    <div class="logo">
      <router-link :to="{ name: 'home' }"><img src="@/assets/Guegue_logo.png" /></router-link>
    </div>
    <div class="nav-links">
      <ul>
        <li>Home</li>
        <li>Investment Deals</li>
        <li>Quick Sale</li>
        <li>Properties</li>
        <li id="phone-number">✆ (702) 596-3245</li>
        <li v-if="false" id="get-offer-button">Get Cash Offer</li>
        <div v-if="true" class="admin-nav">
          <div class="admin-box">
            <li id="admin"><i class="fa fa-user"></i>Admin</li>
          </div>
          <button id="logout"><i class="fa fa-sign-out"></i> Logout</button>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style>
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
  cursor: default;
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
.navbar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #151c24;
  border-bottom: #474747 1px solid;
  height: 90px;
}
.logo {
  margin-left: 20px;
}
img {
  width: 85px;
  height: auto;
}
ul {
  display: flex;
  justify-content: space-between;
  align-items: center;
  list-style: none;
}
li {
  margin: 0 20px;
  cursor: pointer;
  color: white;
}
#phone-number {
  color: #41ab95;
}

#get-offer-button {
  background: #2a9b84;
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
}
</style>
