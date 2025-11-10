<template>
  <div class="why-choose-us">
    <div class="choose-us-bubble">
      <i class="fa-solid fa-arrow-trend-up"></i>
      <p>Why Choose Us</p>
    </div>

    <div class="built-speed">
      <p>Built for Speed & Profit</p>
    </div>

    <div class="we-streamline">
      <p>
        We streamline every aspect of wholesale real estate to deliver unmatched value to both
        sellers and investors.
      </p>
    </div>

    <div class="cards">
      <div class="card instant-cash-card">
        <div class="icon-wrapper green">
          <i class="fa-solid fa-dollar-sign"></i>
        </div>
        <p class="title">Instant Cash Offers</p>
        <p class="description">
          Get competitive cash offers within 24 hours. No waiting, no uncertainty.
        </p>
      </div>

      <div class="card fast-closing-card">
        <div class="icon-wrapper blue">
          <i class="fa-solid fa-bolt"></i>
        </div>
        <p class="title">Fast Closings</p>
        <p class="description">
          Close in as little as 7 days. We move at your speed with zero delays.
        </p>
      </div>

      <div class="card maximum-roi-card">
        <div class="icon-wrapper purple">
          <i class="fa-solid fa-arrow-trend-up"></i>
        </div>
        <p class="title">Maximum ROI</p>
        <p class="description">
          Access exclusive wholesale deals with proven profit potential for investors.
        </p>
      </div>

      <div class="card expert-card">
        <div class="icon-wrapper orange">
          <i class="fa-solid fa-users"></i>
        </div>
        <p class="title">Expert Network</p>
        <p class="description">
          Connect with our network of qualified cash buyers and motivated sellers.
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.why-choose-us {
  background-color: #0b121b;
  padding: 100px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 25px;
  position: relative;
  overflow: hidden;
}

/* Tag bubble */
.choose-us-bubble {
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(21, 52, 45, 0.3);
  border: 1px solid rgba(72, 199, 156, 0.4);
  border-radius: 30px;
  padding: 10px 20px;
  color: #20e8ca;
  font-weight: 600;
  font-size: 14px;
}
.choose-us-bubble i {
  color: #20e8ca;
}

/* Titles */
.built-speed p {
  color: white;
  font-size: 26px;
  font-weight: 600;
}
.we-streamline p {
  color: #a9b1c1;
  max-width: 800px;
  line-height: 1.6;
  font-size: 17px;
}

/* Cards Layout */
.cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 25px;
  margin-top: 50px;
  width: 85%;
  max-width: 1200px;
}
.card {
  background-color: #101822;
  border: 1px solid #1e2a3b;
  border-radius: 12px;
  padding: 30px;
  text-align: left;
  display: flex;
  flex-direction: column;
  gap: 30px;
  transition: all 0.3s ease;
}
.card:hover {
  transform: translateY(-5px);
  border-color: #2c3f55;
  box-shadow: 0 6px 20px rgba(0, 255, 200, 0.1);
}

/* Icon styles */
.icon-wrapper {
  width: 100%;
  padding: 10px 16px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: start;
}
.icon-wrapper i {
  color: white;
  font-size: 20px;
}
.icon-wrapper.green {
  background: linear-gradient(90deg, #00ff84, #00c673);
}
.icon-wrapper.blue {
  background: linear-gradient(90deg, #1fa2ff, #12d8fa);
}
.icon-wrapper.purple {
  background: linear-gradient(90deg, #a445b2, #d41872);
}
.icon-wrapper.orange {
  background: linear-gradient(90deg, #ff512f, #f09819);
}

/* Text inside cards */
.title {
  color: white;
  font-weight: 600;
  font-size: 18px;
}
.description {
  color: #a9b1c1;
  font-size: 15px;
  line-height: 1.6;
}

/* Tablet (≤1024px) */
@media screen and (max-width: 1024px) {
  .cards {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    width: 90%;
  }
  .we-streamline p {
    font-size: 16px;
    width: 90%;
  }
}

/* Mobile (≤768px) */
@media screen and (max-width: 768px) {
  .why-choose-us {
    padding: 60px 0;
  }
  .choose-us-bubble {
    font-size: 13px;
    padding: 8px 16px;
  }
  .built-speed p {
    font-size: 22px;
  }
  .we-streamline p {
    font-size: 15px;
  }
  .cards {
    grid-template-columns: 1fr;
    width: 85%;
  }
  .card {
    padding: 25px;
    text-align: center;
    align-items: center;
  }
}
</style>
