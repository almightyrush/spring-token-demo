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
          <div class="cities-list" v-if="false">
             <span
              v-for="city in city"
              :key="city.id"
              @click="searchUser(city)"
              class="city-item form-control"
            >
            {{city.name}} hello <br>
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
        city: [
          {
            id: 1,
            name: 'india india india india'
          },
          {
            id: 2,
            name: 'USA'
          },
          {
            id: 3,
            name: 'AFG'
          }
        ]
      }
    },
    methods: {
      searchUser(user) {
        console.log(user);
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
