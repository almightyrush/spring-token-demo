<template>
  <div class="wrapper">
    <side-bar>
      <sidebar-link to="/scanner/overview">
        <i class="nc-icon nc-badge"></i>
        <p>Dashboard</p>
      </sidebar-link>
      <sidebar-link v-if="isAdmin() || isModerator()" to="/scanner/user">
        <i class="nc-icon nc-circle-09"></i>
        <p>Add User</p>
      </sidebar-link>
      <sidebar-link v-if="isAdmin()" to="/scanner/hotel">
        <i class="nc-icon nc-bank"></i>
        <p>Add Hotel</p>
      </sidebar-link>
     <sidebar-link to="/scanner/table-list">
        <i class="nc-icon nc-notes"></i>
        <p>Blocked License List</p>
      </sidebar-link>
      <sidebar-link to="/scanner/block-license">
        <i class="nc-icon nc-single-02"></i>
        <p>Block License</p>
      </sidebar-link>
    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>

      <dashboard-content @click="toggleSidebar">

      </dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss">

</style>
<script>
  import TopNavbar from './TopNavbar.vue'
  import ContentFooter from './ContentFooter.vue'
  import DashboardContent from './Content.vue'
  import MobileMenu from './MobileMenu.vue'
  export default {
    components: {
      TopNavbar,
      ContentFooter,
      DashboardContent,
      MobileMenu
    },
    methods: {
      toggleSidebar () {
        if (this.$sidebar.showSidebar) {
          this.$sidebar.displaySidebar(false)
        }
      },
      isAdmin() {
        const role = localStorage.getItem('UserRole');
        return role.includes('ROLE_ADMIN');
      },
      isModerator() {
        const role = localStorage.getItem('UserRole');
        return role.includes('ROLE_MODERATOR');
      },
    }
  }

</script>
