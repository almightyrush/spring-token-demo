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
        const userobj = JSON.parse(localStorage.getItem('token'));
        this.user.restaurant = userobj.restaurant;
        this.$http.post('api/auth/signup', this.user, this.header).then(response => {
          const data = response.data;
          if (data) {
            this.user = {}; 
            this.$notify({type:'success',text: data.message});
          }
          else {
            this.$notify({type:'error',text: data.message});
          }
        }).catch((error) => {
          this.$notify({type:'error',text: "Username or Email already exists."});
        });
      },
      isLogin() {
        const header = {
          headers: AuthHeader()
        }
        this.$http.get('api/test/user', header).then(response => {
          if (response.data) {
            //do nothing
            // console.log(response.data);
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
      this.isLogin();
    }
  }

</script>
<style>

</style>
