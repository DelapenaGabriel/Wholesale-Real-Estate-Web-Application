<template>
  <div class="first-section">
    <div class="landing-container">
      <div class="wholesale-specialist">
        <i class="fa-regular fa-gem"></i>
        <p>Wholesale Real Estate Specialist</p>
      </div>

      <div class="text-maximize">
        <p>Close Deals Faster.</p>
        <p id="maximize-returns">Maximize Returns.</p>
      </div>

      <div class="text-connect">
        <p>
          Connect with motivated sellers and exclusive wholesale opportunities. We facilitate
          lightning-fast cash transactions with zero hassle, zero commissions, and maximum ROI.
        </p>
      </div>

      <div class="sell-and-deal-buttons">
        <a href="#sell" @click.prevent="scrollToSection('sell')"
          ><div class="sell-button">
            <p>Sell Property Fast</p>
            <i class="fa-solid fa-arrow-right-long"></i></div
        ></a>
        <a href="#buy" @click.prevent="scrollToSection('buy')"
          ><div class="wholesale-deals-button">
            <p>View Wholesale Deals</p>
          </div></a
        >
      </div>

      <div class="time-fees-deals-cards">
        <div class="time-card">
          <div class="time-logo"><i class="fa-regular fa-clock"></i></div>
          <div class="time-text">
            <p id="time-emphasize">7 Days</p>
            <p id="time-description">Average Closing Time</p>
          </div>
        </div>

        <div class="fees-card">
          <div class="fees-logo"><i class="fa-regular fa-money-bill-1"></i></div>
          <div class="fees-text">
            <p id="fees-emphasize">0%</p>
            <p id="fees-description">Fees & Commissions</p>
          </div>
        </div>

        <div class="deals-card">
          <div class="deals-logo"><i class="fa-regular fa-handshake"></i></div>
          <div class="deals-text">
            <p id="deals-emphasize">300+</p>
            <p id="deals-description">Deals Closed</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    scrollToSection(id) {
      const el = document.getElementById(id)
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' })
      }
    },
  },
}
</script>

<style scoped>
.first-section {
  background: linear-gradient(
    90deg,
    rgba(0, 0, 0, 1) 0%,
    rgba(17, 43, 64, 1) 88%,
    rgba(1, 1, 5, 1) 100%
  );
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.landing-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 65%;
  max-width: 1000px;
}

/* --- Main Text --- */
.wholesale-specialist {
  display: flex;
  gap: 10px;
  align-items: center;
  border: 1px solid #55dbc1;
  width: fit-content;
  padding: 10px 16px;
  border-radius: 25px;
  background-color: #4ebea83f;
}
.wholesale-specialist p,
.wholesale-specialist i {
  color: #55dbc1;
  font-size: 14px;
}

.text-maximize {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  font-size: 48px;
  font-weight: 700;
}
#maximize-returns {
  color: #20e8ca;
}

.text-connect {
  margin-top: 20px;
  font-size: 22px;
  line-height: 1.5;
}

/* --- Buttons --- */
.sell-and-deal-buttons {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-top: 30px;
  font-weight: 550;
  flex-wrap: wrap;
}
a {
  text-decoration: none;
}
.sell-button,
.wholesale-deals-button {
  border-radius: 10px;
  padding: 15px 20px;
  display: flex;
  gap: 10px;
  align-items: center;
  cursor: pointer;
}
.sell-button {
  border: 1px solid #121212;
  color: white;
  background: linear-gradient(
    90deg,
    rgba(42, 123, 155, 1) 0%,
    rgba(87, 199, 133, 1) 50%,
    rgba(237, 221, 83, 1) 100%
  );
}
.sell-button:hover {
  opacity: 0.8;
}
.wholesale-deals-button {
  border: 1px solid #525252;
  background-color: #242726c9;
}
.wholesale-deals-button:hover {
  background-color: #4c4c4c;
}

/* --- Cards --- */
.time-fees-deals-cards {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  margin-top: 45px;
  flex-wrap: wrap;
}
.time-card,
.fees-card,
.deals-card {
  display: flex;
  align-items: center;
  border: 1px solid #263047;
  background-color: #0d1321;
  border-radius: 18px;
  padding: 25px 20px;
  flex: 1;
  gap: 20px;
  justify-content: center;
  min-width: 250px;
}

.time-logo,
.fees-logo,
.deals-logo {
  border-radius: 16px;
  padding: 8px;
}
.time-logo {
  background-color: #1a652e64;
}
.fees-logo {
  background-color: #2b498564;
}
.deals-logo {
  background-color: #8832ad64;
}
.time-logo i {
  color: #84f7a2;
  font-size: 28px;
}
.fees-logo i {
  color: #48deeb;
  font-size: 28px;
}
.deals-logo i {
  color: #e49bfc;
  font-size: 28px;
}

#time-emphasize,
#fees-emphasize,
#deals-emphasize {
  font-size: 36px;
  font-weight: 700;
}

p {
  color: white;
}

/* --- Tablet (≤1024px) --- */
@media screen and (max-width: 1024px) {
  .landing-container {
    width: 85%;
  }
  .text-maximize {
    font-size: 40px;
  }
  .text-connect {
    font-size: 20px;
  }
}

/* --- Mobile (≤768px) --- */
@media screen and (max-width: 768px) {
  .first-section {
    height: auto;
    padding: 60px 0;
    text-align: center;
  }

  .landing-container {
    width: 90%;
    align-items: center;
  }

  .wholesale-specialist {
    font-size: 12px;
  }

  .text-maximize {
    font-size: 32px;
    justify-content: center;
  }

  .text-connect {
    font-size: 18px;
    margin-top: 10px;
  }

  .sell-and-deal-buttons {
    flex-direction: column;
    width: 100%;
  }

  .sell-button,
  .wholesale-deals-button {
    width: 100%;
    justify-content: center;
  }

  .time-fees-deals-cards {
    flex-direction: column;
    align-items: center;
    gap: px;
    width: 100%;
  }

  .time-card,
  .fees-card,
  .deals-card {
    justify-content: start;
    gap: 35px;
    width: 100%;
  }

  .time-text,
  .fees-text,
  .deals-text {
    text-align: left;
  }
}
</style>
