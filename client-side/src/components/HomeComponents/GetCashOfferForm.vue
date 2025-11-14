<template>
  <div class="quick-sale-section">
    <div class="quick-sale-bubble">
      <i class="fa-solid fa-bolt"></i>
      <p>Quick Sale Program</p>
    </div>

    <h2 class="cash-offer-title">Get Your Cash Offer in 24 Hours</h2>
    <p class="cash-offer-subtext">
      No obligations, no pressure. Just a fair, competitive cash offer for your property.
    </p>

    <div class="form-card">
      <form @submit.prevent="createSellerInquiry">
        <!-- Contact Info -->
        <div class="form-section">
          <div class="section-header">
            <span class="section-number">1</span>
            <p>Contact Information</p>
          </div>
          <div class="form-row">
            <input type="text" placeholder="Full Name *" v-model="form.name" />
            <input type="email" placeholder="Email *" v-model="form.email" />
            <input type="tel" placeholder="Phone Number *" v-model="form.phone" />
          </div>
        </div>

        <!-- Property Location -->
        <div class="form-section">
          <div class="section-header">
            <span class="section-number">2</span>
            <p>Property Location</p>
          </div>
          <div class="form-row">
            <input type="text" placeholder="Street Address *" v-model="form.address" />
          </div>
          <div class="form-row">
            <input type="text" placeholder="City *" v-model="form.city" />
            <input type="text" placeholder="State *" v-model="form.state" />
            <input type="text" placeholder="ZIP Code *" v-model="form.zip" />
          </div>
        </div>

        <!-- Property Details -->
        <div class="form-section">
          <div class="section-header">
            <span class="section-number">3</span>
            <p>Property Details</p>
          </div>
          <div class="form-row">
            <select v-model="form.propertyType">
              <option disabled value="">Property Type *</option>
              <option>Single Family</option>
              <option>Multi-Family</option>
              <option>Condo/Townhome</option>
              <option>Land</option>
              <option>Commercial</option>
            </select>
            <select v-model="form.condition">
              <option disabled value="">Condition *</option>
              <option>Move-in Ready</option>
              <option>Good</option>
              <option>Needs Cosmetic Work</option>
              <option>Needs Major Repairs</option>
              <option>Distressed</option>
            </select>
          </div>
          <div class="form-row">
            <input type="number" placeholder="Bedrooms" v-model="form.bedrooms" />
            <input type="number" placeholder="Bathrooms" v-model="form.bathrooms" />
          </div>
          <div class="form-row">
            <input type="number" placeholder="Square Feet" v-model="form.sqft" />
            <select v-model="form.timeline">
              <option disabled value="">Timeline to Sell *</option>
              <option>ASAP (1-2 days)</option>
              <option>Soon (1-2 weeks)</option>
              <option>Within a month</option>
              <option>Flexible</option>
            </select>
          </div>
          <textarea placeholder="Additional Details (optional)" v-model="form.details"></textarea>
        </div>

        <!-- Info + Submit -->
        <div class="info-and-button">
          <ul class="info-list">
            <li><i class="fa-solid fa-circle-check"></i> No obligations or commitments</li>
            <li><i class="fa-solid fa-clock"></i> Response within 24 hours</li>
            <li><i class="fa-solid fa-bolt"></i> Close in as fast as 7 days</li>
          </ul>
          <button type="submit" class="submit-btn" :disabled="loading">
            {{ loading ? 'Sending...' : 'Get My Cash Offer' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import emailjs from '@emailjs/browser'
import InquiriesService from '@/services/InquiriesService'
export default {
  data() {
    return {
      loading: false,
      form: {
        name: '',
        email: '',
        phone: '',
        address: '',
        city: '',
        state: '',
        zip: '',
        propertyType: '',
        condition: '',
        bedrooms: '',
        bathrooms: '',
        sqft: '',
        timeline: '',
        details: '',
      },
      submitting: false,
      error: '',
      success: false,
    }
  },
  methods: {
    createSellerInquiry() {
      this.submitting = true
      this.error = ''
      this.success = false

      InquiriesService.createInquiry({
        fullName: this.form.name,
        email: this.form.email,
        phone: this.form.phone,
        address: this.form.address,
        city: this.form.city,
        state: this.form.state,
        zipCode: this.form.zip,
        propertyType: this.form.propertyType,
        bedrooms: this.form.bedrooms,
        bathrooms: this.form.bathrooms,
        sqft: this.form.sqft,
        condition: this.form.condition,
        timeline: this.form.timeline,
        additionalDetails: this.form.details,
        status: 'New',
        role: 'Seller',
      })
        .then(() => {
          this.success = true
          return this.sendEmail()
        })
        .then(() => {
          setTimeout(() => this.closeInquiry(), 2500)
          this.resetForm()
        })
        .catch(() => {
          this.error = '❌ Failed to submit. Please try again.'
        })
        .finally(() => {
          this.submitting = false
        })
    },
    sendEmail() {
      this.loading = true
      return emailjs
        .send('service_y5ti5lk', 'template_zlclujo', this.form, 'rabtsnlxlQr7aY-3R')
        .then(() => {
          console.log('Email sent!')
        })
        .catch((err) => {
          console.error('Email failed:', err)
        })
        .finally(() => {
          this.loading = false
        })
    },
    resetForm() {
      this.form = {
        name: '',
        email: '',
        phone: '',
        address: '',
        city: '',
        state: '',
        zip: '',
        propertyType: '',
        condition: '',
        bedrooms: '',
        bathrooms: '',
        sqft: '',
        timeline: '',
        details: '',
      }
    },
  },
}
</script>

<style scoped>
.quick-sale-section {
  text-align: center;
  color: #fff;
  padding: 4rem 2rem;
  background: #0b1016;
}

.quick-sale-bubble {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: rgba(20, 40, 30, 0.4);
  border: 1px solid #1f8a70;
  color: #45e3b5;
  padding: 8px 16px;
  border-radius: 999px;
  font-size: 14px;
}

.cash-offer-title {
  margin-top: 1.5rem;
  font-size: 1.3rem;
  font-weight: 600;
}

.cash-offer-subtext {
  font-size: 0.9rem;
  color: #b7b7b7;
  margin-bottom: 2rem;
}

.form-card {
  max-width: 800px;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 15px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 2rem;
  backdrop-filter: blur(10px);
  text-align: left;
}

.section-header {
  display: flex;
  align-items: center;
  margin-bottom: 0.8rem;
  gap: 0.5rem;
}

.section-number {
  background: linear-gradient(135deg, #00ffa3, #009e85);
  color: #000;
  font-weight: bold;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-row {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

input,
select,
textarea {
  flex: 1;
  background: #0e141b;
  border: 1px solid #2b2f34;
  border-radius: 6px;
  padding: 10px;
  color: #ddd;
  font-size: 14px;
}

textarea {
  width: 100%;
  min-height: 80px;
  resize: none;
}

.info-and-button {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.info-list {
  margin-top: 1.5rem;
  list-style: none;
  color: #b7b7b7;
  font-size: 0.9rem;
}

.info-list li {
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  gap: 8px;
}

.info-list i {
  color: #27e2b0;
}

.submit-btn {
  margin-top: 1.5rem;
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  border: none;
  color: white;
  font-weight: 600;
  padding: 12px 20px;
  border-radius: 8px;
  cursor: pointer;
  float: right;
  transition: all 0.3s ease;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 255, 187, 0.4);
}

/* 🧭 RESPONSIVE DESIGN */

/* Tablets */
@media screen and (max-width: 1024px) {
  .form-row {
    flex-wrap: wrap;
  }
  input,
  select {
    flex: 1 1 45%;
  }
}

/* Mobile */
@media screen and (max-width: 768px) {
  .info-and-button {
    flex-direction: column;
  }
  .form-card {
    padding: 1.5rem;
  }
  .form-row {
    flex-direction: column;
    gap: 0.8rem;
  }
  .submit-btn {
    float: none;
  }
  .info-list {
    display: flex;
    flex-wrap: wrap;
  }
}
</style>
