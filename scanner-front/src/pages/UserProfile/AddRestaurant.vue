<template>
  <card>
    <h4 slot="header" class="card-title">Add Hotel</h4>
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
         <div class="col-md-3">
          <base-input type="password"
                    label="pin"
                    placeholder="PIN"
                    v-model="user.pin">
          </base-input>
        </div>
        <div class="col-md-4">
          <label>Scanner Type</label> 
            <select class="form-control form-control-sm" v-model="user.scannerType">
             <option class="form-control" value="scanner_1" selected>scanner 1 </option>
              <option class="form-control" value="scanner_2">scanner 2</option>
          </select>
        </div>
      </div>
      <div class="text-center">
        <button type="submit" class="btn btn-info btn-fill float-right" @click.prevent="checkValidation">
          Add Hotel
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
          zipCode: '',
          email: '',
          username: '',
          password: '',
          pin: '',
          scannerType: 'scanner_1'
        },
        header: {
          headers: AuthHeader()
        }
      }
    },
    methods:{
      checkValidation() {
        const lengthRegex = /^([0-9]{6})$/;
        if (this.user.name === '' || this.user.address1 === '' || this.user.username === '' || this.user.email === ''
        || this.user.country === '' || this.user.city === '' || this.user.password === '' || this.user.pin === '') {
          this.$notify({type:'warning',text: 'Fields are empty'});
        } else if (!lengthRegex.test(this.user.pin)) {
          this.$notify({type:'warning',text: 'Pin should be numeric of length 6'});
          return true;
        }
        else {
          this.addRestaurant();
        }
      },
      addRestaurant() {
        this.$http.post('api/restaurant', this.user, this.header).then(response => {
          const data = response.data;
          if (data.success) {
            this.$notify({type:'success',text: 'Restaurant added successfully'});
            this.user = {};
          }
          else { 
            this.$notify({type:'error',text: data.errorMsg});
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
      this.isLogin();
    }
  }

</script>
<style>

</style>
