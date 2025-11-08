<template>
    <div class="home">
        <nav-bar/>
    </div>
</template>

<script>
import NavBar from '@/components/HomeComponents/NavBar.vue';

export default {
    components: {
        NavBar
    }
}
</script>

<style>
.home{
    background-color: #0b0d1c;
    height: 100vh;
}
</style>