<template>
<admin-nav-bar/>
<admin-dashboard/>
</template>

<script>
import AdminNavBar from '@/components/AdminComponents/AdminNavBar.vue';
import AdminDashboard from '@/components/AdminComponents/AdminDashboard.vue';
export default {
    components: { 
        AdminNavBar,
        AdminDashboard
    }
}
</script>