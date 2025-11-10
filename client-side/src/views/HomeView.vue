<template>
    <div class="home">
        <nav-bar/>
        <first-section/>
        <why-choose-us/>
        <get-cash-offer-form/>
    </div>
</template>

<script>
import NavBar from '@/components/HomeComponents/NavBar.vue';
import FirstSection from '@/components/HomeComponents/FirstSection.vue';
import WhyChooseUs from '@/components/HomeComponents/WhyChooseUs.vue';
import GetCashOfferForm from '@/components/HomeComponents/GetCashOfferForm.vue';
export default {
    components: {
        NavBar,
        FirstSection,
        WhyChooseUs,
        GetCashOfferForm
    }
}
</script>

<style>

</style>