<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
      <router-link :to="{path:'/scanner/overview'}" class="navbar-brand">Dashboard</router-link>
      <!-- <button type="button"
              class="navbar-toggler navbar-toggler-right"
              :class="{toggled: $sidebar.showSidebar}"
              aria-controls="navigation-index"
              aria-expanded="false"
              aria-label="Toggle navigation"
              @click="toggleSidebar">toggle
        <span class="navbar-toggler-bar burger-lines"></span>
        <span class="navbar-toggler-bar burger-lines"></span>
        <span class="navbar-toggler-bar burger-lines"></span>
      </button> -->
      <div class="collapse navbar-collapse justify-content-end input-city">
        <div class="form-outline">
          <input type="search" class="form-control" v-model="searchCriteria" placeholder="Search" />
          <div class="cities-list" v-if="user">
             <span
              v-for="user in user"
              :key="user.licenseId"
              @click="searchUser(user)"
              class="city-item form-control"> {{user.fullName}}
             </span>
          </div>
        </div>
        <!-- <button type="button" class="btn btn-primary" @click="searchUser">
          <i class="fa fa-search"></i>
        </button> -->
      </div>

      <div class="collapse navbar-collapse justify-content-end">
        <ul class="nav navbar-nav mr-auto">
        </ul>
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a @click="logout" class="nav-link">
              Log out
            </a>
          </li>
        </ul>
      </div>
      
    </div>
  </nav>
</template>
<script>
import AuthHeader from 'src/auth-header.js';

  export default {
    computed: {
      routeName () {
        const {name} = this.$route
        return this.capitalizeFirstLetter(name)
      }
    },
    data () {
      return {
        activeNotifications: false,
        isLoading: true,
        searchCriteria: '',
        isOpen: false,
        results: [],
        search: "",
        arrowCounter: 0,
        user: [],
        header: {
          headers: AuthHeader()
        },
      }
    },
    methods: {
      searchUser(user) {
        localStorage.setItem('blocked-user', JSON.stringify(user));
        this.user = [];
        this.$router.push({name: 'BlockLicense'}).catch((error) => {
          console.log('error --->', error);
          this.$router.go();
        });
      },
      logout(){
        this.$router.push({name: 'Login'});
      },
      capitalizeFirstLetter (string) {
        return string.charAt(0).toUpperCase() + string.slice(1)
      },
      toggleNotificationDropDown () {
        this.activeNotifications = !this.activeNotifications
      },
      closeDropDown () {
        this.activeNotifications = false
      },
      toggleSidebar () {
        this.$sidebar.displaySidebar(!this.$sidebar.showSidebar)
      },
      hideSidebar () {
        this.$sidebar.displaySidebar(false)
      },
    },
    watch: {
      searchCriteria(newValue, oldValue) {
        let queryLength = newValue.length;
        if (queryLength > 3) {
          this.$http.get('api/search?searchQuery='+newValue, this.header).then(response => {
            this.user = response.data;
            this.searchCriteria = '';
          }).catch(() => {
            this.$notify({type:'error',text: 'Session expired login again'});
          });
        }
      }
    }
  }

</script>
<style scoped>
  .search-result {
    position: absolute;
    z-index: 1;
  }
  .input-city .cities-list {
    position: absolute;
    z-index: 1;
  }
  .city-item {
    border: 1px solid #E3E3E3;
    width: 200px;
    height: auto;
  }
</style>
