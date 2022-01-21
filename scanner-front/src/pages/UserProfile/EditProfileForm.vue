<template>
  <card>
    <h4 slot="header" class="card-title">Add User</h4>
    <form>
      <div class="row">
        <div class="col-md-3">
          <base-input type="text"
                    label="Username"
                    placeholder="Username"
                    v-model="user.username">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input type="password"
                    label="Password"
                    placeholder="Password"
                    v-model="user.password">
          </base-input>
        </div>
        <div class="col-md-6">
          <base-input type="email"
                    label="Email"
                    placeholder="Email"
                    v-model="user.email">
          </base-input>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6">
          <base-input type="text"
                    label="First Name"
                    placeholder="First Name"
                    v-model="user.firstName">
          </base-input>
        </div>
        <div class="col-md-6">
          <base-input type="text"
                    label="Last Name"
                    placeholder="Last Name"
                    v-model="user.lastName">
          </base-input>
        </div>
      </div>
      <!-- <div class="row">
        <div class="col-md-4">
          <label>Role</label>
          <select class="form-control form-control-sm" v-model="selectedRole">
              <option value="User">User</option>
              <option value="Admin">Admin</option>
            </select>
        </div> -->
        <!-- <div class="col-md-4">
          <label>Restaurant</label>
            <select class="form-control form-control-sm" v-model="user.restaurant">
            <option 
            v-for="(restaurant) in allRestaurant"  :value="restaurant"
            :key="restaurant.name">{{restaurant.name}}</option>
          </select>
        </div> -->
      </div>
      <div class="text-center">
        <button type="submit" class="btn btn-info btn-fill float-right" @click.prevent="checkValidation">
          Add User
        </button>
      </div>
    </form>
  </card>
</template>
<script>
  import Card from 'src/components/Cards/Card.vue'
  import AuthHeader from 'src/auth-header.js'

  export default {
    components: {
      Card
    },
    data () {
      return {
        user: {
          username: '',
          email: '',
          firstName: '',
          lastName: '',
          password: '',
          role: [],
          restaurant: []
        },
        selectedRole:'',
        header: {
          headers: AuthHeader()
        },
        allRestaurant: '',
      }
    },
    methods: {
      checkValidation() {
        if (this.user.username === '' || this.user.email === '' || this.user.firstName === ''
        || this.user.lastName === '' || this.user.password === '' ) {
          this.$notify({type:'warning',text: 'Fields are empty'});
        }
        else {
          this.onSubmit();
        }
      },
      onSubmit() {
        this.user.role.push(this.selectedRole);
        this.$http.post('api/auth/signup', this.user, this.header).then(response => {
          console.log('resposne is ',  response);
          if (response) {
            // this.$router.push({name: 'Search-Customer'});
            this.$notify({type:'success',text: 'User added successfully'});
          }
          else {
            this.$notify({type:'erroe',text: response});
          }
        }).catch((error) => {
          tthis.$notify({type:'error',text: error});
        });
      },
      getAllRestaurant() {
        this.$http.get('api/restaurant', this.header).then(response => {
          this.allRestaurant = response.data;
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
          this.$router.push({name: 'Login'});
        });
      },
      isLogin() {
        const header = {
          headers: AuthHeader()
        }
        this.$http.get('api/test/user', header).then(response => {
          if (response.data) {
            console.log(response.data);
          }
          else {
            this.$notify({type:'error',text: 'Session expired login again'});
          }        
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
          this.$router.push({name: 'Login'});
        });
      }
    },
    created() {
      this.getAllRestaurant();
      this.isLogin();
    }
  }

</script>
<style>

</style>
