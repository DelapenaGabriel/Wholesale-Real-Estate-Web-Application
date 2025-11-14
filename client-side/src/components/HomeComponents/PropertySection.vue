<template>
  <div class="investment-page">
    <div class="header">
      <span>
        <i class="fa-regular fa-building"></i>
        <p class="exclusive-btn" id="title-deal">Exclusive Wholesale Deals</p>
      </span>
      <h2>Current Investment Opportunities</h2>
      <p>
        Hand-selected wholesale properties with verified profit potential. For qualified cash buyers
        only.
      </p>
    </div>

    <div class="property-grid">
      <div v-for="(property, index) in visibleProperties" :key="index" class="property-card">
        <div class="property-image">
          <img :src="property.imageUrl" alt="Property Image" />
          <!-- Gradient overlay -->
          <div class="image-overlay"></div>
          <div class="badge available" v-if="property.status === 'Available'">Available Now</div>
          <div class="badge listed">{{ property.daysOnMarket }} days listed</div>
        </div>

        <div class="property-info">
          <div class="price-row">
            <div class="price-box" id="wholesale-price">
              <p class="label" id="wholesale-p">Wholesale Price</p>
              <p class="value">${{ formatNumber(property.price) }}</p>
            </div>
            <div class="price-box">
              <p class="label">ARV</p>
              <p class="value">${{ formatNumber(property.arv) }}</p>
            </div>
          </div>

          <h3 class="title">{{ property.title }}</h3>
          <div class="property-address">
            <i class="fa-solid fa-location-dot"></i>
            <p class="address">{{ property.address }}</p>
          </div>

          <div class="details">
            <span><i class="fa-solid fa-bed"></i> Beds: {{ property.bedroom }}</span>
            <span><i class="fa-solid fa-bath"></i> Baths: {{ property.bathroom }}</span>
            <span><i class="fa-solid fa-ruler-combined"></i> Sqft: {{ property.sqft }}</span>
          </div>

          <div class="profit-row">
            <div class="profit-box" id="profit-border">
              <span>
                <i class="fa-solid fa-arrow-trend-up"></i>
                <p class="label" id="profit-text">Est. Profit</p>
              </span>
              <p class="value">${{ formatNumber(estimatedProfit(property)) }}</p>
            </div>
            <div class="profit-box" id="margin-border">
              <span>
                <i class="fa-solid fa-percent"></i>
                <p class="label" id="margin-text">Margin</p>
              </span>
              <p class="value">{{ margin(property).toFixed(1) }}%</p>
            </div>
          </div>

          <button class="request-btn" @click="openInquiry(property)">Request Deal Analysis</button>
        </div>
      </div>
    </div>

    <div class="show-more">
      <button
        v-if="visibleCount < properties.filter((p) => p.status === 'Available').length"
        @click="showMore"
      >
        Show More
      </button>
    </div>

    <!-- Deal Inquiry Modal -->
    <transition name="fade">
      <div v-if="showInquiry" class="inq-overlay" @click.self="closeInquiry">
        <div class="inq-card" role="dialog" aria-modal="true" aria-labelledby="inq-title">
          <button class="inq-close" aria-label="Close" @click="closeInquiry">×</button>

          <h3 id="inq-title">Request Deal Analysis</h3>
          <p class="inq-sub">Get detailed analysis and schedule a property viewing</p>

          <!-- Summary -->
          <div class="inq-summary">
            <div class="inq-summary-header">
              <div class="inq-summary-title">
                <h4>{{ selected?.title }}</h4>
                <p class="inq-addr">
                  <i class="fa-solid fa-location-dot"></i> {{ selected?.address }}
                </p>
              </div>
              <span v-if="selected?.status === 'Available'" class="status-pill">Available</span>
            </div>

            <div class="inq-chips">
              <div class="chip">
                <span class="chip-label"
                  ><i class="fa-solid fa-dollar-sign" id="dollar-icon"></i> Wholesale Price</span
                >
                <span class="chip-value">${{ formatNumber(selected?.price || 0) }}</span>
              </div>
              <div class="chip">
                <span class="chip-label"
                  ><i class="fa-solid fa-arrow-trend-up" id="profit-icon"></i> Est. Profit</span
                >
                <span class="chip-value"
                  >${{ formatNumber(estimatedProfit(selected || { arv: 0, price: 0 })) }}</span
                >
              </div>
              <div class="chip">
                <span class="chip-label"
                  ><i class="fa-solid fa-arrow-trend-up" id="roi-icon"></i> ROI</span
                >
                <span class="chip-value"
                  >{{ margin(selected || { price: 1, arv: 0 }).toFixed(1) }}%</span
                >
              </div>
            </div>
          </div>

          <!-- Form -->
          <form class="inq-form" @submit.prevent="submitBuyerInquiry">
            <div class="row">
              <div class="name-container">
                <label class="f-label">Full Name *</label>
                <div class="f-input">
                  <input
                    ref="nameEl"
                    v-model.trim="form.name"
                    placeholder="John Investor"
                    required
                  />
                </div>
              </div>

              <div class="name-container">
                <label class="f-label">Phone Number *</label>
                <div class="f-input">
                  <input v-model.trim="form.phone" placeholder="(555) 123-4567" required />
                </div>
              </div>
            </div>

            <div class="name-container">
              <label class="f-label">Email Address *</label>
              <div class="f-input">
                <input
                  type="email"
                  v-model.trim="form.email"
                  placeholder="investor@example.com"
                  required
                />
              </div>
            </div>

            <div class="name-container">
              <label class="f-label">Message / Questions</label>
              <div class="f-input">
                <textarea
                  v-model.trim="form.message"
                  rows="3"
                  placeholder="I’m interested in learning more about this property."
                ></textarea>
              </div>
            </div>

            <div class="help-box">
              <p class="help-title">What happens next?</p>
              <ul>
                <li>- We’ll review your inquiry within 24 hours</li>
                <li>- You’ll receive a detailed deal analysis report</li>
                <li>- Schedule a property viewing or virtual tour</li>
              </ul>
            </div>

            <div class="actions">
              <button type="button" class="btn-cancel" @click="closeInquiry">Cancel</button>
              <button type="submit" class="btn-submit" :disabled="submitting">
                <span v-if="!submitting">Submit Inquiry</span>
                <span v-else>Submitting…</span>
              </button>
            </div>

            <p v-if="error" class="inq-error">{{ error }}</p>
            <p v-if="success" class="inq-success">Thanks! We’ll be in touch shortly.</p>
          </form>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import PropertyService from '@/services/PropertyService'
import InquiriesService from '@/services/InquiriesService' // when ready
import { faCity } from '@fortawesome/free-solid-svg-icons'
import emailjs from '@emailjs/browser'
export default {
  data() {
    return {
      visibleCount: 4,
      properties: [],
      showInquiry: false,
      selected: null,
      // use the same name as the template bindings:
      form: {
        name: '',
        email: '',
        phone: '',
        message: '',
      },
      submitting: false,
      error: '',
      success: false,
    }
  },
  computed: {
    visibleProperties() {
      return this.properties
        .filter((property) => property.status === 'Available')
        .slice(0, this.visibleCount)
    },
  },
  methods: {
    showMore() {
      this.visibleCount += 2
    },
    estimatedProfit(property) {
      return property.arv - property.price
    },
    margin(property) {
      return (this.estimatedProfit(property) / property.price) * 100
    },
    formatNumber(num) {
      return num.toLocaleString()
    },

    getProperties() {
      PropertyService.getProperties()
        .then((response) => {
          this.properties = response.data
        })
        .catch(() => {})
    },

    openInquiry(property) {
      this.selected = property
      this.form = { name: '', phone: '', email: '', message: '' }
      this.error = ''
      this.success = false
      this.showInquiry = true
      this.$nextTick(() => this.$refs.nameEl && this.$refs.nameEl.focus())
      this._esc = (e) => e.key === 'Escape' && this.closeInquiry()
      window.addEventListener('keydown', this._esc)
    },
    closeInquiry() {
      this.showInquiry = false
      window.removeEventListener('keydown', this._esc)
    },

    submitBuyerInquiry() {
      this.submitting = true
      this.error = ''
      this.success = false

      // ✅ Use the correct service and include property details
      InquiriesService.createInquiry({
        fullName: this.form.name,
        email: this.form.email,
        phone: this.form.phone,
        address: this.selected.address,
        city: this.selected.city,
        state: this.selected.state,
        zipCode: this.selected.zipCode,
        propertyType: this.selected.type,
        bedrooms: this.selected.bedroom,
        bathrooms: this.selected.bathroom,
        sqft: this.selected.sqft,
        condition: null,
        timeline: this.selected.daysOnMarket,
        additionalDetails: this.form.message,
        status: 'New',
        role: 'Buyer',
      })
        .then(() => {
          return this.sendEmail()
        })
        .then(() => {
          this.success = true
          setTimeout(() => this.closeInquiry(), 2500)
        })
        .catch(() => {
          this.error = '❌ Failed to submit. Please try again.'
        })
        .finally(() => {
          this.submitting = false
        })
    },
    sendEmail() {
      emailjs
        .send(
          'service_y5ti5lk', // ← Replace with your EmailJS Service ID
          'template_ef6wzqa', // ← Replace with your EmailJS Template ID
          this.form,
          'rabtsnlxlQr7aY-3R', // ← Replace with your EmailJS Public Key
        )
        .then(() => {
          console.log('Email sent!')
        })
        .catch((err) => {
          console.error('Email failed:', err)
        })
    },
  },
  // ✅ mounted must be at the root of the component, not inside methods
  mounted() {
    this.getProperties()
  },
}
</script>

<style scoped>
/* Overall Page */
.investment-page {
  background-color: #0f1621;
  color: #ffffff;
  padding: 2rem 1rem;
  text-align: center;
  min-height: 100vh;
}

/* Header */
.header h2 {
  font-size: 1.5rem;
  margin: 0.5rem 0;
}
.header p {
  color: #a1a1a1;
  font-size: 0.95rem;
  line-height: 1.4;
}
.header span {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: #0c674d4c;
  border: 1px solid #1f8a70;
  color: #45e3b5;
  padding: 8px 16px;
  border-radius: 999px;
  font-size: 14px;
}

#title-deal {
  color: #45e3b5;
}

/* Grid */
.property-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
  max-width: 1100px;
  margin: 2rem auto;
}

@media (min-width: 768px) {
  .property-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* Property Card */
.property-card {
  background-color: #1a2433;
  border-radius: 16px;
  overflow: hidden;
  text-align: left;
  display: flex;
  flex-direction: column;
}

.property-card:hover {
  transform: scale(1.05);
  transition: transform 0.3s ease-in-out;
  transform: translateY(-5px);
  border-color: #2c3f55;
  box-shadow: 0 6px 20px rgba(0, 255, 200, 0.1);
}

/* Image Section */
.property-image {
  position: relative;
  height: 0;
  padding-bottom: 56.25%; /* 16:9 ratio */
}
.property-image img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Gradient overlay */
.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /* here’s the linear gradient from the screenshot style */
  background: linear-gradient(180deg, rgba(15, 22, 33, 0.078) 0%, rgba(15, 22, 33, 0.132) 100%);
}

/* Badges */
.badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: #00bfa5;
  padding: 4px 10px;
  font-size: 0.75rem;
  border-radius: 5px;
  font-weight: 500;
}
.badge.listed {
  left: auto;
  right: 12px;
  background: #2f3a4a;
}

/* Info Section */
.property-info {
  padding: 1.25rem;
}
.price-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  gap: 10px;
}
.price-box {
  border: 1px solid #2c3f55;
  border-radius: 10px;
  width: 100%;
  padding: 15px;
}
#wholesale-price {
  background: linear-gradient(90deg, #00d9a694, #00a6ff91);
}
#wholesale-p {
  color: white;
}
.price-box .label {
  font-size: 0.8rem;
  color: #aaa;
  margin: 0;
}
.price-box .value {
  font-weight: 600;
  margin: 0.2rem 0;
}

.title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 0.3rem;
}

.property-address {
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.property-address i {
  color: #aaa;
}
.address {
  color: #aaa;
  font-size: 0.9rem;
}
.details {
  display: flex;
  justify-content: space-between;
  color: #aaa;
  font-size: 0.85rem;
  margin-top: 25px;
  margin-bottom: 1rem;
  border-bottom: 1px solid #2c3f55;
}

.details > span {
  margin-bottom: 25px;
}

.profit-row {
  display: flex;
  margin-bottom: 1.25rem;
  gap: 10px; /* ✅ add this */
}

.profit-box {
  border-radius: 10px;
  width: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column; /* helps layout labels/values vertically */
  justify-content: space-between;
}

.profit-box span {
  display: flex;
  align-items: center;
  gap: 8px;
}
#profit-border {
  background: #039a774c;
  border: 1px solid #00d9a6a8;
}

#profit-border span i,
#profit-border span p {
  color: #00d9a6;
}
#margin-border {
  background: #06677b38;
  border: 1px solid #02d5ffda;
}
#margin-border span i,
#margin-border span p {
  color: #02d5ffda;
}
.profit-box .label {
  font-size: 0.8rem;
  color: #aaa;
  margin: 0;
}
.profit-box .value {
  font-weight: 600;
  margin: 0.5rem 0;
}

/* Buttons */
.request-btn {
  width: 100%;
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 0.75rem;
  font-weight: 500;
  cursor: pointer;
}
.request-btn:hover {
  background: linear-gradient(90deg, #08b48c94, #0877b2d2);
}
.show-more {
  text-align: center;
  margin-top: 1.5rem;
}
.show-more button {
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 0.75rem 1.5rem;
  cursor: pointer;
  font-size: 1rem;
}
.show-more button:hover {
  background: linear-gradient(90deg, #08b48c94, #0877b2d2);
}

/* Modal base */
.inq-overlay {
  position: fixed;
  inset: 0;
  z-index: 2500;
  background: rgba(10, 14, 20, 0.6);
  backdrop-filter: blur(3px);
  display: grid;
  place-items: center;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.inq-card {
  width: min(760px, 92vw);
  background: #101826;
  border: 1px solid #28374b;
  color: #e9f3ff;
  border-radius: 14px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.45);
  padding: 20px 20px 18px;
  position: relative;
  animation: pop 0.2s ease;
  max-height: 90vh; /* ✅ */
  overflow: auto; /* ✅ scroll if content taller than viewport */
  -webkit-overflow-scrolling: touch;
}
@keyframes pop {
  from {
    transform: translateY(-8px);
    opacity: 0;
  }
  to {
    transform: none;
    opacity: 1;
  }
}

.inq-close {
  position: absolute;
  top: 10px;
  right: 12px;
  width: 34px;
  height: 34px;
  border-radius: 8px;
  background: #14202e;
  color: #9fb5c9;
  border: 1px solid #223041;
  cursor: pointer;
}
.inq-close:hover {
  color: #fff;
}

#inq-title {
  margin: 4px 0 2px;
  font-size: 22px;
  color: #fff;
}
.inq-sub {
  margin: 0 0 16px;
  color: #a9b7c7;
  font-size: 0.95rem;
}

/* Summary */
.inq-summary {
  background: #0f2233;
  border: 1px solid #233447;
  border-radius: 10px;
  padding: 14px;
  margin-bottom: 16px;
}
.inq-summary-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}
.inq-summary-title {
  display: flex;
  flex-direction: column;
  align-items: start;
  text-align: start;
  gap: 10px;
}
.inq-summary h4 {
  margin: 0;
  color: #eaf2ff;
}
.inq-addr {
  margin: 4px 0 0;
  color: #93a6bb;
}

#dollar-icon {
  color: rgb(60, 255, 0);
}

#profit-icon {
  color: rgb(0, 255, 251);
}

#roi-icon {
  color: rgb(144, 0, 255);
}
.status-pill {
  background: #0aa378;
  color: #eafff9;
  border: 1px solid #1dcaa0;
  padding: 6px 10px;
  border-radius: 999px;
  font-size: 0.85rem;
}
.inq-chips {
  margin-top: 12px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
}
@media (max-width: 900px) {
  .inq-chips {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (max-width: 520px) {
  .inq-chips {
    grid-template-columns: 1fr;
  }
}
.chip {
  background: #0f1e2b;
  border: 1px solid #2a3a51;
  border-radius: 10px;
  padding: 15px;
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 4px;
}
.chip-label {
  color: #9ab0c7;
  font-size: 0.8rem;
}
.chip-value {
  color: #eaf6ff;
  font-weight: 600;
}

/* Form */
.inq-form {
  display: grid;
  gap: 12px;
}

.row {
  display: flex;
  gap: 12px;
}

.name-container {
  flex: 1 1 0; /* stretch evenly */
  min-width: 0; /* allow inputs to shrink instead of overflowing */
  width: 100%;
}

.f-input {
  width: 100%;
  margin-top: 5px;
}
.f-input input,
.f-input textarea {
  width: 100%;
}

@media (max-width: 640px) {
  .row {
    flex-direction: column;
  } /* single column on mobile */
}

.f-label {
  font-size: 0.85rem;
  color: #a8b6c8;
  margin-bottom: 4px;
}
.f-input {
  background: #0e2233;
  border: 1px solid #233447;
  border-radius: 10px;
  padding: 10px 12px;
}
.f-input input,
.f-input textarea {
  background: transparent;
  border: 0;
  outline: 0;
  width: 100%;
  color: #e9f3ff;
  font-size: 0.95rem;
}

/* Help box */
.help-box {
  background: #0d3b33;
  border: 1px solid #1e806e;
  color: #dff9f1;
  border-radius: 10px;
  padding: 12px;
  display: flex;
  flex-direction: column;
  align-items: start;
  text-align: start;
}
.help-title {
  margin: 0 0 6px;
  font-weight: 600;
  color: #00d9a6;
}
.help-box ul {
  list-style: none;
}

/* Actions */
.actions {
  display: flex;
  gap: 10px;
  justify-content: center;
  align-items: center;
}
.btn-cancel {
  background: #1e2733;
  color: #d1d9e6;
  border: 1px solid #334255;
  padding: 10px 16px;
  border-radius: 10px;
  cursor: pointer;
}
.btn-cancel:hover {
  background: #2a3647;
}
.btn-submit {
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  color: #fff;
  border: 0;
  border-radius: 10px;
  padding: 10px 16px;
  font-weight: 600;
  cursor: pointer;
  transition:
    transform 0.15s ease,
    opacity 0.15s ease;
}
.btn-submit:hover {
  transform: translateY(-1px);
  opacity: 0.95;
}
.btn-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.inq-error {
  color: #ff7b7b;
}
.inq-success {
  color: #75f1c8;
}
</style>
