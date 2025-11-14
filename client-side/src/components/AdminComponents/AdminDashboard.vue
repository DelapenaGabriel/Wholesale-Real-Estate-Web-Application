<template>
  <div class="admin-dashboard">
    <div class="title-add">
      <div class="admin-dash">
        <div class="icon-title">
          <i class="fa-solid fa-building-columns"></i>
          <p>Admin Dashboard</p>
        </div>
        <p>Manage properties and customer inquiries</p>
      </div>
      <div class="add-property-bttn" @click="openForm()">
        <i class="fa-solid fa-plus"></i>
        <p>Add New Property</p>
      </div>
    </div>
    <div class="properties-count">
      <div class="total">
        <p>Total Properties</p>
        <p>{{ properties.length }}</p>
      </div>
      <div class="total">
        <p>Available Properties</p>
        <p id="available">{{ properties.filter((p) => p.status === 'Available').length }}</p>
      </div>
      <div class="total">
        <p>Pending Properties</p>
        <p id="pending">{{ properties.filter((p) => p.status === 'Pending').length }}</p>
      </div>
      <div class="total">
        <p>Sold Properties</p>
        <p id="sold">{{ properties.filter((p) => p.status === 'Sold').length }}</p>
      </div>
      <div class="total">
        <p>New Inquiries</p>
        <p id="inquiries">{{ inquiries.filter((i) => i.status === 'New').length }}</p>
      </div>
    </div>
    <div class="properties-inquiries">
      <!-- Properties Button -->
      <div
        class="pi-bttn"
        :class="{ active: activeTab === 'properties' }"
        @click="activeTab = 'properties'"
      >
        <i class="fa-solid fa-house"></i>
        <p>Properties</p>
      </div>

      <!-- Inquiries Button -->
      <div
        class="pi-bttn"
        :class="{ active: activeTab === 'inquiries' }"
        @click="activeTab = 'inquiries'"
      >
        <i class="fa-solid fa-envelope"></i>
        <p>Inquiries</p>
        <span v-if="newInquiryCount > 0" class="badge">{{ newInquiryCount }}</span>
      </div>
    </div>

    <div v-if="activeTab === 'properties'" class="filter-properties">
      <div class="filter-property-title">
        <i class="fa-solid fa-filter"></i>
        <p>Filter Properties</p>
      </div>
      <div class="status">
        <div class="status-text">
          <p>Property Status</p>
        </div>
        <div class="all-status">
          <div
            v-for="status in propertyFilters"
            :key="status"
            class="status-bttns"
            :class="{ active: selectedPropertyFilter === status }"
            @click="selectedPropertyFilter = status"
          >
            <i v-if="status === 'Available'" class="fa-solid fa-circle-check"></i>
            <i v-else-if="status === 'Pending'" class="fa-regular fa-clock"></i>
            <i v-else-if="status === 'Sold'" class="fa-solid fa-handshake"></i>
            <p>{{ status }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Inquiries Total Counts -->
    <div v-else class="properties-count">
      <div class="total">
        <p>Total Inquiries</p>
        <p>{{ inquiries.length }}</p>
      </div>
      <div class="total">
        <p>Buy Inquiries</p>
        <p id="available">{{ inquiries.filter((i) => i.role === 'Buyer').length }}</p>
      </div>
      <div class="total">
        <p>Sell Inquiries</p>
        <p id="sell-inq">{{ inquiries.filter((i) => i.role === 'Seller').length }}</p>
      </div>
      <div class="total">
        <p>New</p>
        <p id="new-inq">{{ inquiries.filter((i) => i.status === 'New').length }}</p>
      </div>
      <div class="total">
        <p>Contacted</p>
        <p id="contacted-inq">{{ inquiries.filter((i) => i.status === 'Contacted').length }}</p>
      </div>
    </div>

    <!-- Filter Inquiries  -->
    <div v-if="activeTab === 'inquiries'" class="filter-properties">
      <div class="filter-property-title">
        <i class="fa-solid fa-filter"></i>
        <p>Filter Inquiries</p>
      </div>
      <div class="status">
        <div class="status-text">
          <p>Inquiries Type</p>
        </div>
        <div class="all-status">
          <div
            v-for="role in inquiriesTypes"
            :key="role"
            class="status-bttns"
            :class="{ active: selectedInquiriesType === role }"
            @click="selectedInquiriesType = role"
          >
            <i v-if="role === 'Buyer'" class="fa-solid fa-cart-shopping"></i>
            <i v-else-if="role === 'Seller'" class="fa-solid fa-house"></i>
            <p>{{ role }}</p>
          </div>
        </div>

        <div class="status-text">
          <p>Status</p>
        </div>
        <div class="all-status">
          <div
            v-for="status in inquiriesFilters"
            :key="status"
            class="status-bttns"
            :class="{ active: selectedInquiriesFilter === status }"
            @click="selectedInquiriesFilter = status"
          >
            <p>{{ status }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Property Modal -->
    <div v-if="showForm" class="modal-overlay" @click.self="closeForm()">
      <div class="modal">
        <div class="modal-header">
          <h3>Add New Property</h3>
          <button class="close" @click="closeForm()">×</button>
        </div>

        <form class="form" @submit.prevent="createProperty">
          <!-- Property Information -->
          <h4>Property Information</h4>
          <div class="row">
            <input v-model.trim="form.title" placeholder="Property Title *" required />
            <select v-model="form.type" required>
              <option disabled value="">Select type</option>
              <option>Single Family</option>
              <option>Condo</option>
              <option>Townhouse</option>
              <option>Multi-Family</option>
              <option>Land</option>
            </select>
            <select v-model="form.status" required>
              <option disabled value="">Select status</option>
              <option>Available</option>
              <option>Pending</option>
              <option>Sold</option>
            </select>
          </div>

          <!-- Location -->
          <h4>Location</h4>
          <div class="row">
            <input v-model.trim="form.address" placeholder="Street Address *" required />
          </div>
          <div class="row">
            <input v-model.trim="form.city" placeholder="City *" required />
            <input v-model.trim="form.state" placeholder="State *" maxlength="2" required />
            <input v-model.trim="form.zipCode" placeholder="Zip Code *" required />
          </div>

          <!-- Property Details -->
          <h4>Property Details</h4>
          <div class="row">
            <input
              v-model.number="form.bedroom"
              type="number"
              min="0"
              step="1"
              placeholder="Bedrooms *"
              required
            />
            <input
              v-model.number="form.bathroom"
              type="number"
              min="0"
              step="1"
              placeholder="Bathrooms *"
              required
            />
            <input
              v-model.number="form.sqft"
              type="number"
              min="0"
              step="1"
              placeholder="Square Feet *"
              required
            />
          </div>

          <!-- Financial Details -->
          <h4>Financial Details</h4>
          <div class="row">
            <input
              v-model.number="form.price"
              type="number"
              min="0"
              step="1"
              placeholder="Wholesale Price *"
              required
            />
            <input
              v-model.number="form.arv"
              type="number"
              min="0"
              step="1"
              placeholder="ARV (After Repair Value) *"
              required
            />
          </div>

          <div class="file-img">
            <h4>Upload Image</h4>
            <input type="file" @change="imageToURL($event)" />
          </div>

          <div class="actions">
            <button v-if="!form.id" type="submit" class="primary">Add Property</button>
            <button v-else type="button" class="primary" @click="updateProperty">
              Update Property
            </button>
            <button type="button" class="ghost" @click="closeForm()">Cancel</button>
          </div>

          <p v-if="successMsg" class="success">{{ successMsg }}</p>
          <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
        </form>
      </div>
    </div>

    <!-- Properties Container -->
    <div v-if="activeTab === 'properties'" class="property-container">
      <div v-for="(property, index) in filterProperties" :key="index" class="property-card">
        <div class="property-image">
          <img :src="property.imageUrl" alt="" />
        </div>
        <div class="property-details">
          <div class="first-row">
            <div class="title-status">
              <p class="title">{{ property.title }}</p>
              <p
                class="status-detail"
                :class="{
                  available: property.status === 'Available',
                  pending: property.status === 'Pending',
                  sold: property.status === 'Sold',
                }"
              >
                {{ property.status }}
              </p>
            </div>
            <div class="edit-delete">
              <i
                id="edit-icon"
                class="fa-solid fa-pen-to-square"
                @click="editProperty(property)"
              ></i>
              <i
                id="delete-icon"
                class="fa-solid fa-trash"
                @click="deleteProperty(property.id)"
              ></i>
            </div>
          </div>
          <div class="prop-address">
            <p>
              {{ property.address }}, {{ property.city }}, {{ property.state }},
              {{ property.zipCode }}
            </p>
          </div>
          <div class="prop-details">
            <div class="prop-type">
              <p id="prop-key">Type</p>
              <p>{{ property.type }}</p>
            </div>
            <div class="prop-beds-baths">
              <p id="prop-key">Beds/Baths</p>
              <p>{{ property.bedroom }}/{{ property.bathroom }}</p>
            </div>
            <div class="prop-sqft">
              <p id="prop-key">Square Feet</p>
              <p>{{ property.sqft }}</p>
            </div>
            <div class="prop-days">
              <p id="prop-key">Days Listed</p>
              <p>{{ property.daysOnMarket }}</p>
            </div>
          </div>
          <div class="prop-details" id="second-prop-details">
            <div class="prop-price">
              <p id="prop-key">Wholesale Price</p>
              <p id="colored-prop-price">${{ formatNumber(property.price) }}</p>
            </div>
            <div class="prop-arv">
              <p id="prop-key">ARV</p>
              <p id="colored-prop-arv">${{ formatNumber(property.arv) }}</p>
            </div>
            <div class="prop-profit">
              <p id="prop-key">Est. Profit</p>
              <p>${{ formatNumber(estimatedProfit(property)) }}</p>
            </div>
            <div class="prop-margin">
              <p id="prop-key">Margin</p>
              <p>{{ margin(property).toFixed(1) }}%</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <div v-for="inquiry in filterInquiries" :key="inquiry.id" class="inquiries-container">
        <div class="inq-title-edit">
          <div class="inq-name-role-status">
            <div class="inq-name">
              <p>{{ inquiry.fullName }}</p>
            </div>
            <div
              :class="{
                buyer: inquiry.role === 'Buyer',
                seller: inquiry.role === 'Seller',
              }"
              class="inq-role"
            >
              <i v-if="inquiry.role === 'Buyer'" class="fa-solid fa-cart-shopping"></i>
              <i v-else-if="inquiry.role === 'Seller'" class="fa-solid fa-house"></i>
              <p>
                {{ inquiry.role }}
              </p>
            </div>
            <div
              :class="{
                new: inquiry.status === 'New',
                contacted: inquiry.status === 'Contacted',
                closed: inquiry.status === 'Closed',
              }"
              class="inq-status"
            >
              <p>{{ inquiry.status }}</p>
            </div>
          </div>
          <div class="inq-edit-delete">
            <select
              id="inq-status-delete"
              v-model="inquiry.status"
              @change="updateInquiryStatus(inquiry, inquiry.status)"
            >
              <option value="New">New</option>
              <option value="Contacted">Contacted</option>
              <option value="Closed">Closed</option>
            </select>
            <div class="inq-delete" @click="deleteInquiry(inquiry.id)">
              <i class="fa-solid fa-trash"></i>
            </div>
          </div>
        </div>
        <div class="inq-interest-address">
          <div class="inq-interest">
            <p v-if="inquiry.role === 'Buyer'">Interested in:</p>
            <p v-else-if="inquiry.role === 'Seller'">Looking to sell property</p>
            <p v-if="inquiry.role === 'Buyer'" id="buying-inquiry">{{ inquiry.propertyType }}</p>
          </div>
          <div class="inq-address">
            <i class="fa-solid fa-location-dot"></i>
            <p>
              {{ inquiry.address }}, {{ inquiry.city }}, {{ inquiry.state }}, {{ inquiry.zipCode }}
            </p>
          </div>
        </div>
        <div class="inq-email-phone">
          <div class="inq-email">
            <div class="inq-email-logo">
              <i id="email-logo-inq" class="fa-regular fa-envelope"></i>
              <p>Email</p>
            </div>
            <p>{{ inquiry.email }}</p>
          </div>
          <div class="inq-phone">
            <div class="inq-email-logo">
              <i id="phone-logo-inq" class="fa-solid fa-phone"></i>
              <p>Phone</p>
            </div>
            <p>{{ inquiry.phone }}</p>
          </div>
        </div>
        <div v-if="inquiry.role === 'Seller'" class="inq-property-details">
          <div class="inq-condition">
            <p id="inq-title-cond">Condition:</p>
            <p>{{ inquiry.condition }}</p>
          </div>
          <div class="inq-prop-det">
            <p id="inq-prop-title">Property Details:</p>
            <div class="inq-facts">
              <div class="inq-logo-text">
                <i class="fa-solid fa-house"></i>
                <p>{{ inquiry.propertyType }}</p>
              </div>
              <div class="inq-logo-text">
                <i class="fa-solid fa-bed"></i>
                <p>{{ inquiry.bedrooms }} beds</p>
              </div>
              <div class="inq-logo-text">
                <i class="fa-solid fa-bath"></i>
                <p>{{ inquiry.bathrooms }} baths</p>
              </div>
              <div class="inq-logo-text">
                <i class="fa-solid fa-ruler-combined"></i>
                <p>{{ inquiry.sqft }} sqft</p>
              </div>
              <div class="inq-logo-text">
                <i class="fa-regular fa-clock"></i>
                <p>Timeline:</p>
                <p>{{ inquiry.timeline }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="inq-message">
          <p>Message:</p>
          <p>{{ inquiry.additionalDetails }}</p>
        </div>
        <div class="inq-created-at">
          <i class="fa-regular fa-calendar"></i>
          <p>Submitted on</p>
          <p>{{ formatDate(inquiry.createdAt) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PropertyService from '@/services/PropertyService'
import InquiriesService from '@/services/InquiriesService'
import axios from 'axios'

export default {
  name: 'PropertiesInquiriesTabs',
  data() {
    return {
      showForm: false,
      errorMsg: '',
      successMsg: '', // ✅ new message state
      form: {
        title: '',
        type: '',
        status: '',
        address: '',
        city: '',
        state: '',
        zipCode: '',
        bedroom: '',
        bathroom: '',
        sqft: '',
        price: '',
        arv: '',
        imageUrl: 'https://homeye.sdsu.edu/static/house_explore/virltor/houses/house0.jpg',
        createdAt: '',
      },
      activeTab: 'properties',
      properties: [],
      propertyFilters: ['All', 'Available', 'Pending', 'Sold'],
      selectedPropertyFilter: 'All',
      selectedInquiriesFilter: 'All',
      selectedInquiriesType: 'All',
      inquiriesTypes: ['All', 'Buyer', 'Seller'],
      inquiriesFilters: ['All', 'New', 'Contacted', 'Closed'],
      inquiries: [],
      filteredInquiries: [],
    }
  },
  methods: {
    formatDate(dateString) {
      return new Date(dateString).toLocaleString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: '2-digit',
      })
    },
    updateInquiryStatus(inquiry, newStatus) {
      const updated = { ...inquiry, status: newStatus }

      InquiriesService.updateInquiry(inquiry.id, updated)
        .then(() => {
          inquiry.status = newStatus // update UI instantly
          this.successMsg = 'Inquiry status updated!'
          setTimeout(() => (this.successMsg = ''), 1500)
        })
        .catch((err) => {
          console.error('Error updating inquiry:', err)
          this.errorMsg = 'Failed to update inquiry.'
        })
    },
    deleteInquiry(id) {
      if (!confirm('Delete this inquiry?')) return

      InquiriesService.deleteInquiry(id)
        .then(() => {
          this.inquiries = this.inquiries.filter((i) => i.id !== id)
          this.successMsg = 'Inquiry deleted!'
          setTimeout(() => (this.successMsg = ''), 1500)
        })
        .catch((err) => {
          console.error('Error deleting inquiry:', err)
          this.errorMsg = 'Failed to delete inquiry.'
        })
    },

    openForm() {
      this.errorMsg = ''
      this.showForm = true
    },
    closeForm() {
      this.showForm = false
      this.resetForm()
    },
    getProperties() {
      PropertyService.getProperties()
        .then((response) => {
          this.properties = response.data
        })
        .catch((error) => console.error('Error fetching properties:', error))
    },
    createProperty() {
      PropertyService.createProperty(this.form)
        .then((response) => {
          this.successMsg = '✅ Property created successfully!'
          this.getProperties()
          setTimeout(() => (this.successMsg = ''), 2000)
        })
        .catch((error) => {
          this.errorMsg = '❌ Failed to create property.'
          console.error('Error creating property:', error)
          console.log(this.form)
        }).finally(() => {
          this.closeForm()
          this.resetForm()
        })
    },
    updateProperty() {
      PropertyService.updateProperty(this.form.id, this.form)
        .then(() => {
          this.successMsg = '✅ Property updated successfully!'
          this.getProperties()
          setTimeout(() => (this.successMsg = ''), 2000)
        })
        .catch((error) => {
          console.error('❌ Error updating property:', error)
          this.errorMsg = 'Failed to update property.'
        }).finally(() => {
          this.closeForm()
        })
    },

    editProperty(property) {
      this.form = { ...property } // Copy property into form
      this.showForm = true
    },
    deleteProperty(id) {
      if (confirm('Are you sure you want to delete this property?')) {
        PropertyService.deleteProperty(id)
          .then(() => {
            this.properties = this.properties.filter((p) => p.id !== id)
            this.successMsg = '🗑️ Property deleted successfully!'
            setTimeout(() => (this.successMsg = ''), 2000)
          })
          .catch((error) => {
            console.error('❌ Error deleting property:', error)
            this.errorMsg = 'Failed to delete property.'
          })
      }
    },
    resetForm() {
      this.form = {
        title: '',
        type: '',
        status: '',
        address: '',
        city: '',
        state: '',
        zipCode: '',
        bedroom: '',
        bathroom: '',
        sqft: '',
        price: '',
        arv: '',
        imageUrl: 'https://homeye.sdsu.edu/static/house_explore/virltor/houses/house0.jpg',
        createdAt: '',
      }
    },
    getInquiries() {
      InquiriesService.getInquiries()
        .then((response) => {
          this.inquiries = response.data
        })
        .catch((error) => console.error('Error fetching inquiries:', error))
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
    imageToURL(event) {
      const file = event.target.files[0]
      if (!file) {
        console.log('No file selected')
        return
      }

      // Upload the file to Cloudinary
      console.log('Uploading file:', file.name)

      const formData = new FormData()
      formData.append('file', file)
      formData.append('upload_preset', 'wholesale-images') // your unsigned preset

      // ✅ Create a new Axios instance that ignores global defaults
      const cloudAxios = axios.create({
        headers: { 'Content-Type': 'multipart/form-data' },
      })

      // Explicitly remove any inherited Authorization header
      delete cloudAxios.defaults.headers.common['Authorization']

      cloudAxios
        .post('https://api.cloudinary.com/v1_1/di0metsks/image/upload', formData)
        .then((response) => {
          console.log('✅ Cloudinary response:', response.data)
          this.form.imageUrl = response.data.secure_url
        })
        .catch((error) => {
          console.error(
            '❌ Error uploading image to Cloudinary:',
            error.response?.data || error.message,
          )
        })
    },
  },
  computed: {
    newInquiryCount() {
      return this.inquiries.filter((i) => i.status === 'New').length
    },
    buyInquiries() {
      return this.inquiries.filter((i) => i.role === 'Buyer').length
    },
    sellInquiries() {
      return this.inquiries.filter((i) => i.role === 'Seller').length
    },
    contactedInquiries() {
      return this.inquiries.filter((i) => i.status === 'Contacted').length
    },
    filterProperties() {
      if (this.selectedPropertyFilter === 'All') {
        return this.properties
      } else {
        return this.properties.filter((property) => property.status === this.selectedPropertyFilter)
      }
    },
    filterInquiries() {
      return this.inquiries.filter((inquiry) => {
        const matchesStatus =
          this.selectedInquiriesFilter === 'All' || inquiry.status === this.selectedInquiriesFilter

        const matchesType =
          this.selectedInquiriesType === 'All' || inquiry.role === this.selectedInquiriesType

        return matchesStatus && matchesType
      })
    },
  },
  mounted() {
    this.getProperties()
    this.getInquiries()
  },
}
</script>

<style scoped>
.admin-dashboard {
  padding: 30px 100px;
  color: white;
  background: linear-gradient(to right, #030712 0%, #030712 100%);
  min-height: 100vh;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* --- TITLE + ADD PROPERTY --- */
.title-add {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 25px;
}

.admin-dash {
  display: flex;
  flex-direction: column;
  gap: 8px;
  font-size: 14px;
}

.icon-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 20px;
  font-weight: 600;
}

.icon-title i {
  color: white;
  border-radius: 15px;
  padding: 10px;
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
}

/* --- ADD PROPERTY BUTTON --- */
.add-property-bttn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 18px;
  font-size: 14px;
  font-weight: 500;
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  color: white;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 3px 10px rgba(0, 166, 255, 0.2);
}
.add-property-bttn i {
  font-size: 0.9rem;
}
.add-property-bttn:hover {
  background: linear-gradient(90deg, #00d9a6d4, #00a6ffc8);
  transform: translateY(-1px);
}

/* --- PROPERTY STATS GRID --- */
.properties-count {
  margin-top: 15px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 15px;
  margin-bottom: 25px;
}

.total {
  border: 1px solid rgba(192, 192, 192, 0.3);
  background-color: #151c24;
  border-radius: 10px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  font-size: 14px;
}
.total p:first-child {
  color: #cfcfcf;
  font-weight: 500;
}
.total p:last-child {
  font-size: 20px;
}

/* --- COLOR TAGS --- */
#available {
  color: #00d9a6;
}
#pending {
  color: #ff9900;
}
#sold {
  color: #b73333;
}
#inquiries {
  color: #00ccff;
}

/* --- PROPERTIES / INQUIRIES TABS --- */
.properties-inquiries {
  display: flex;
  align-items: center;
  background: #1a1f28;
  padding: 6px;
  border-radius: 25px;
  width: fit-content;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.3);
  gap: 5px;
  margin-bottom: 20px;
}

.pi-bttn {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #9da6b4;
  background: transparent;
  border-radius: 20px;
  padding: 6px 14px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: 0.25s ease;
  position: relative;
}

.pi-bttn:hover {
  background: #2b3240;
  color: #e0e6ef;
}

.pi-bttn.active {
  background: #2d3543;
  color: #fff;
  font-weight: 600;
}

.pi-bttn i {
  font-size: 0.9rem;
}

.badge {
  background: #007bff;
  color: white;
  border-radius: 50%;
  font-size: 0.75rem;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: -4px;
  right: -4px;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.6);
}

/* --- FILTER PROPERTIES BOX --- */
.filter-properties {
  background: #141a22;
  border: 1px solid #252c36;
  border-radius: 10px;
  padding: 1.2rem 1.5rem;
  color: #cdd4df;
  margin-bottom: 1.5rem;
}

.filter-property-title {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #4bfed2;
  font-weight: 600;
  margin-bottom: 1rem;
}

.status {
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.status-text p {
  color: #9da6b4;
  font-size: 0.9rem;
  margin: 0;
}

.all-status {
  display: flex;
  gap: 10px;
}

.status-bttns {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: #202731;
  color: #ccc;
  border-radius: 8px;
  padding: 8px 14px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: all 0.3s ease;
}
.status-bttns:hover {
  background: #00bfa6;
  color: white;
}

/* --- PROPERTY TABLE --- */
.property-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* Card layout */
.property-card {
  display: flex;
  flex-direction: row;
  border: 1px solid #252c36;
  background-color: #0e141b;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 255, 163, 0.05);
  transition: all 0.3s ease;
  height: 100%;
}
.property-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 0 20px rgba(0, 255, 163, 0.1);
}

/* Left image */
.property-image {
  width: 25%;
  overflow: hidden;
}

.property-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.property-image img:hover {
  transform: scale(1.05);
}

/* Right side content */
.property-details {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 20px;
  padding: 20px;
}
.first-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}
.title-status {
  display: flex;
  align-items: center;
  gap: 10px;
}
.title {
  font-size: 18px;
  font-weight: 600;
}
.status-detail {
  font-weight: 600;
  text-transform: capitalize;
  padding: 4px 10px;
  border-radius: 6px;
  font-size: 0.85rem;
}

/* Dynamic colors */
.status-detail.available {
  color: #00d9a6;
  background: rgba(0, 217, 166, 0.1);
}

.status-detail.pending {
  color: #ff9900;
  background: rgba(255, 153, 0, 0.1);
}

.status-detail.sold {
  color: #b73333;
  background: rgba(183, 51, 51, 0.1);
}

.edit-delete {
  display: flex;
  gap: 10px;
  align-items: center;
}

.edit-delete i {
  font-size: 1.1rem;
  cursor: pointer;
}
#edit-icon {
  color: #cacccb;
  padding: 5px;
  border-radius: 10px;
  border: 1px solid #cacccb;
  background-color: #2d3543;
}
#edit-icon:hover {
  background-color: #757b85;
}
#delete-icon {
  color: #b73333;
  border: 1px solid #b73333;
  padding: 5px;
  border-radius: 10px;
  background-color: #3c1f1fd7;
}
#delete-icon:hover {
  background-color: #854242d7;
}
.prop-address p {
  color: rgb(148, 148, 148);
}

.prop-details {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

#second-prop-details {
  border-top: 1px solid gray;
  padding-top: 25px;
}

.prop-type,
.prop-beds-baths,
.prop-sqft,
.prop-days,
.prop-price,
.prop-arv,
.prop-profit,
.prop-margin {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

#prop-key {
  color: #9da6b4;
  font-size: 0.9rem;
  margin: 0;
}
#colored-prop-price {
  color: #04cd9a;
}
#colored-prop-arv {
  color: #0bdaff;
}

/* --- MEDIA QUERIES --- */
@media (max-width: 1024px) {
  .admin-dashboard {
    padding: 25px 60px;
  }
  .add-property-bttn {
    padding: 9px 15px;
    font-size: 13px;
  }
}
@media (max-width: 900px) {
  .property-container {
    grid-template-columns: 1fr;
  }

  .property-card {
    flex-direction: column;
  }

  .property-image {
    width: 100%;
    height: 250px;
  }
}
@media (max-width: 768px) {
  .admin-dashboard {
    padding: 20px 30px;
  }

  .title-add {
    flex-direction: column;
    align-items: flex-start;
  }

  .add-property-bttn {
    align-self: stretch;
    justify-content: center;
    font-size: 13px;
    padding: 10px;
    width: 100%;
  }

  .properties-inquiries {
    margin-top: 15px;
    width: 100%;
    justify-content: space-around;
  }

  .pi-bttn {
    padding: 8px 12px;
    font-size: 0.85rem;
  }

  .filter-properties {
    padding: 1rem;
  }

  .status-bttns {
    flex: 1 1 45%;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .admin-dashboard {
    padding: 15px;
  }

  .icon-title {
    font-size: 18px;
  }

  .add-property-bttn {
    font-size: 12px;
    padding: 8px 12px;
  }

  .properties-count {
    grid-template-columns: 1fr 1fr;
    gap: 10px;
  }

  .pi-bttn {
    padding: 6px 10px;
    font-size: 0.8rem;
  }

  .status-bttns {
    flex: 1 1 100%;
  }
}

@media (max-width: 420px) {
  .all-status {
    flex-wrap: wrap;
  }
}
.status-bttns.active {
  background: linear-gradient(90deg, #00d9a6c8, #00a6ffb1);
  color: white;
  box-shadow: 0 0 10px rgba(0, 217, 166, 0.3);
  transform: scale(1.03);
  transition: all 0.25s ease;
}

/* --- Modal Form -- */
/* modal */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: grid;
  place-items: center;
  z-index: 1000;
}
.modal {
  width: min(980px, 92vw);
  background: #0e141d;
  border: 1px solid #243042;
  border-radius: 14px;
  padding: 18px;
}
.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}
.close {
  background: transparent;
  color: #9fb3c8;
  border: none;
  font-size: 22px;
  cursor: pointer;
}
.form h4 {
  margin: 14px 0 8px;
  color: #dde0e4;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 5px;
}
.row {
  display: flex;
  gap: 10px;
}
.row input,
.row select {
  width: 100%;
  background: #0b1119;
  border: 1px solid #223044;
  border-radius: 8px;
  padding: 10px 12px;
  color: #e6efff;
}
.actions {
  margin-top: 16px;
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}
.primary {
  background: linear-gradient(90deg, #00d9a6, #00a6ff);
  color: white;
  border: none;
  border-radius: 10px;
  padding: 10px 16px;
  cursor: pointer;
  font-weight: 600;
}
.primary:hover {
  background: linear-gradient(90deg, #00d9a6c4, #00a6ffb7);
}
.ghost {
  background: transparent;
  color: #c7d4e5;
  border: 1px solid #2a3a50;
  border-radius: 10px;
  padding: 10px 16px;
  cursor: pointer;
}
.ghost:hover {
  color: #6e7b89;
}
.success {
  color: #00d084;
  margin-top: 8px;
}
.error {
  color: #ff6b6b;
  margin-top: 8px;
}

@media (max-width: 860px) {
  .row {
    grid-template-columns: 1fr;
  }
}
#sell-inq {
  color: rgb(218, 102, 218);
}
#new-inq {
  color: #00ccff;
}
#contacted-inq {
  color: #f09b02;
}

.inquiries-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
  border: 1px solid #252c36;
  padding: 20px;
  border-radius: 12px;
  background: #131a22;
}

.inq-title-edit {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}

.inq-name-role-status {
  display: flex;
  align-items: center;
  gap: 10px;
}

.inq-role {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
  font-weight: 500;
}
.inq-status {
  font-size: 12px;
  font-weight: 500;
  text-align: center;
}
.inq-role.buyer {
  background: #179033cf;
  border-radius: 15px;
  padding: 5px 10px;
}
.inq-role.seller {
  background: #9610f4;
  border-radius: 15px;
  padding: 5px 10px;
}
.inq-status.new {
  background: #155dfc;
  border-radius: 15px;
  padding: 5px 10px;

  color: white;
}
.inq-status.contacted {
  background: #f09b02;
  border-radius: 15px;
  padding: 5px 10px;
  color: white;
}
.inq-status.closed {
  background: #485160;
  border-radius: 15px;
  padding: 5px 10px;
  color: white;
}
.inq-edit-delete {
  display: flex;
  gap: 10px;
  align-items: center;
}
.inq-edit-delete select {
  background: #242d39;
  border: 1px solid #3e454d;
  border-radius: 8px;
  padding: 7px 12px;
  color: #e6efff;
}
.inq-delete {
  color: #b53333;
  border: 1px solid #b73333;
  padding: 7px;
  border-radius: 10px;
  background-color: #3c1f1fd7;
  cursor: pointer;
}
.inq-delete:hover {
  background-color: #854242d7;
}
.inq-interest-address {
  display: flex;
  flex-direction: column;
  gap: 15px;
  font-size: 15px;
}
.inq-interest {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 15px;
  color: rgb(158, 157, 157);
}
#buying-inquiry {
  color: #00d9a6;
}
.inq-address {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 15px;
  color: rgb(158, 157, 157);
}
.inq-email-phone {
  margin-top: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}
.inq-email,
.inq-phone {
  border: 1px solid #252c36;
  border-radius: 12px;
  padding: 15px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.inq-email-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 15px;
  color: rgb(158, 157, 157);
}
#email-logo-inq {
  color: #00d9a6;
}
#phone-logo-inq {
  color: #09e6ea;
}
.inq-message {
  border: 1px solid #252c36;
  border-radius: 12px;
  padding: 15px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.inq-message p:first-child {
  color: rgb(158, 157, 157);
}
.inq-created-at {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 5px;
  font-size: 13px;
  margin-top: 20px;
  color: rgb(158, 157, 157);
}
.inq-property-details {
  border: 1px solid #252c36;
  border-radius: 12px;
  padding: 15px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.inq-condition {
  display: flex;
  gap: 5px;
  align-items: center;
}
#inq-title-cond {
  color: rgb(158, 157, 157);
}
.inq-prop-det {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.inq-facts {
  display: flex;
  gap: 5px;
}
.inq-logo-text {
  display: flex;
  width: 100%;
  gap: 10px;
  align-items: center;
}
.inq-logo-text i {
  color: rgb(203, 58, 239);
}
#inq-title-fact {
  color: rgb(158, 157, 157);
}
/* --- RESPONSIVE INQUIRIES --- */

/* --- FIXED + MODERNIZED SELLER PROPERTY DETAILS --- */

.inq-property-details {
  border: 1px solid #2d3645;
  border-radius: 12px;
  padding: 18px;
  display: flex;
  flex-direction: column;
  gap: 18px;
  background: #131a22;
  box-shadow: inset 0 0 10px rgba(0, 255, 163, 0.04);
}

/* Condition row */
.inq-condition {
  display: flex;
  gap: 6px;
  align-items: center;
}

/* Section title */
#inq-title-cond,
#inq-title-fact, #inq-prop-title {
  color: #9aa5b5;
}

/* Facts Section */
.inq-prop-det {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* MAIN FIX — Responsive Layout */
.inq-facts {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 12px;
}

/* Fact row */
.inq-logo-text {
  display: flex;
  align-items: center;
  gap: 10px;
  background: #161e28;
  border: 1px solid #2d3645;
  padding: 10px;
  border-radius: 10px;
}

.inq-logo-text i {
  color: #00d9a6;
  font-size: 1rem;
}

/* --- RESPONSIVE FIXES --- */
@media (max-width: 768px) {
  .inq-property-details {
    padding: 15px;
  }

  .inq-facts {
    grid-template-columns: 1fr 1fr; /* 2 columns tablet */
  }
}

@media (max-width: 480px) {
  .inq-facts {
    grid-template-columns: 1fr; /* 1 full-width column phone */
    gap: 10px;
  }

  .inq-logo-text {
    padding: 12px;
  }
}
@media (max-width: 768px) {
  .inquiries-container {
    padding: 15px;
  }

  .inq-title-edit {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }

  .inq-name-role-status {
    align-items: flex-start;
    gap: 10px;
  }

  .inq-edit-delete {
    width: 100%;
    justify-content: space-between;
  }

  .inq-email-phone {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }

  .inq-email,
  .inq-phone {
    width: 100%;
  }

  .inq-interest-address {
    font-size: 14px;
  }

  .inq-address {
    font-size: 14px;
    gap: 5px;
    align-items: flex-start;
  }

  .inq-message {
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .inquiries-container {
    padding: 12px;
  }

  .inq-role,
  .inq-status {
    font-size: 11px;
    padding: 4px 8px;
  }

  .inq-email-logo,
  .inq-phone-logo {
    font-size: 13px;
  }

  .inq-created-at {
    font-size: 12px;
  }
}
</style>
