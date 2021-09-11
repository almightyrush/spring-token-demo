<template>
  <card>
    <h4 slot="header" class="card-title">Add Restaurant</h4>
    <form>
      <div class="row">
        <div class="col-md-12">
          <base-input type="text"
                    label="Name"
                    placeholder="Name"
                    v-model="user.name">
          </base-input>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6">
          <base-input type="text"
                    label="Address 1"
                    placeholder="Address 1"
                    v-model="user.address1">
          </base-input>
        </div>
        <div class="col-md-6">
          <base-input type="text"
                    label="Address 2"
                    placeholder="Address 2"
                    v-model="user.address2">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-4">
          <base-input type="text"
                    label="City"
                    placeholder="City"
                    v-model="user.city">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input type="text"
                    label="Country"
                    placeholder="Country"
                    v-model="user.country">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input type="number"
                    label="Postal Code"
                    placeholder="ZIP Code"
                    v-model="user.zipCode">
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
          name: '',
          address1: '',
          address2:'',
          country: '',
          city: '',
          zipCode: ''
        },
        header: {
          headers: AuthHeader()
        }
      }
    },
    methods:{ 
      checkValidation() {
        if (this.user.name === '' || this.user.address1 === '' || this.user.address2 === ''
        || this.user.country === '' || this.user.city === '' || this.user.zipcode === '') {
          this.$notify({type:'warning',text: 'Fields are empty'});
        }
        else {
          this.addRestaurant();
        }
      },
      addRestaurant() {
        this.$http.post('api/restaurant', this.input, this.header).then(response => {
          if (response) {
            this.$notify({type:'success',text: 'Restaurant added successfully'});
          }
        }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      },
      isLogin() {
        this.$http.get('api/test/user', this.header).then(response => {
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
      // this.isLogin();
    }
  }

</script>
<style>

</style>
