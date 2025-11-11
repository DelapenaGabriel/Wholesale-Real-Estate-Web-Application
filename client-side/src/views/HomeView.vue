<template>
    <div class="home">
        <nav-bar/>
        <section id="home"><first-section/></section>
        <section id="why-us"><why-choose-us/></section>
        <section id="sell"><get-cash-offer-form/></section>
        <section id="buy"><property-section/></section>
        <footer-section/>
    </div>
</template>

<script>
import NavBar from '@/components/HomeComponents/NavBar.vue';
import FirstSection from '@/components/HomeComponents/FirstSection.vue';
import WhyChooseUs from '@/components/HomeComponents/WhyChooseUs.vue';
import GetCashOfferForm from '@/components/HomeComponents/GetCashOfferForm.vue';
import PropertySection from '@/components/HomeComponents/PropertySection.vue';
import FooterSection from '@/components/HomeComponents/FooterSection.vue';
export default {
    components: {
        NavBar,
        FirstSection,
        WhyChooseUs,
        GetCashOfferForm,
        PropertySection,
        FooterSection
    }
}
</script>

<style>

</style>