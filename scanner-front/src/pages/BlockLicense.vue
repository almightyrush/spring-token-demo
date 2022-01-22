<template>
<div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-10">
          <card>
            <h4 slot="header" class="card-title">Block License</h4>
            <form>
            <hr/> 
              <div class="row">
                <div class="col-md-10">
                  <base-input type="text"
                    label="Name"
                    placeholder="Name" v-model="user.fullName">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-4">
                  <base-input type="text"
                    label="License"
                    placeholder="License" v-model="user.licenseNumber">
                  </base-input>
                </div>
                 <div class="col-md-3">
                  <base-input type="text"
                    label="Coutry"
                    placeholder="Country" v-model="user.country">
                  </base-input>
                </div>
                <div class="col-md-3">
                  <base-input type="text"
                    label="City"
                    placeholder="City" v-model="user.city">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-5">
                  <base-input type="text"
                    label="Address Line 1"
                    placeholder="Address Line 1" v-model="user.address1">
                  </base-input>
                </div>
                <div class="col-md-5">
                  <base-input type="text"
                    label="Address Line 2"
                    placeholder="Address Line 2" v-model="user.address2">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2">
                  <base-input type="text"
                    label="Zip Code"
                    placeholder="Zip Code" v-model="user.postalCode">
                  </base-input>
                </div>
                <div class="col-md-8">
                  <base-input type="text"
                    label="Reason"
                    placeholder="Reason" v-model="user.reason">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2" v-if="enableBlock">
                  <button type="submit" class="btn btn-danger btn-fill float-left"  @click="blockUser">
                  Block
                  </button>
                </div>
                <!-- <div class="col-md-2" v-if="enableUnblock">
                  <button type="submit" class="btn btn-success btn-fill float-left" @click="unblockUser">
                  UnBlock
                  </button>
                </div> -->
                <div class="col-md-2" v-if="enableUnblock">
                  <button type="submit" class="btn btn-primary btn-fill float-left" @click="blockUser">
                  Update
                  </button>
                </div>
                <div class="col-md-2">
                  <button type="reset" class="btn btn-default btn-fill float-left" @click="resetUser">
                  Reset
                  </button>
                </div>
              </div>
            </form>
          </card>
        </div>
      </div>
    </div>
</div>
</template>
<script>
import Card from 'src/components/Cards/Card.vue'
import AuthHeader from 'src/auth-header.js'
export default {
  components: {
    Card,
  },
  data() {
      return {
        inputText: '',
        user: {
          fullName: '',
          licenseNumber: '',
          city: '',
          isBlocked: false,
          licenseId: '',
          reason: '',
          country: '',
          address1: '',
          address2: '',
          postalCode: ''
        },
        blockTheUser: {
            licenseId:'',
            reason:'',
            user:{
              id: ''
            },
            license: '',
            fullName: '',
            city: '',
            country: '',
            address1: '',
            address2: '',
            postalCode: ''
        },
        enableUnblock: false,
        enableBlock: true,
        role: '',
        logInUser: '',
        toggleValue: '',
        header: { headers: AuthHeader() },
      }
    },
  methods: {
    isAdmin() {
      this.role = localStorage.getItem('UserRole')
      return this.role === 'ROLE_ADMIN'? true : false;
    },
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('UserRole');
      this.$router.push({name: 'Login'});
    },
    isLogin() {
      this.$http.get('api/test/user', this.header).then(response => {
        if (response.data) {
          // do nothing
        }
        else {
          this.$notify({type:'error',text: 'Session expired login again'});
        }        
      }).catch(() => {
        this.$notify({type:'error',text: 'Session expired login again'});
        this.$router.push({name: 'Login'});
      });
    },
    blockUser() {
      const userid = JSON.parse(localStorage.getItem('token'));
      if (this.user.reason === '' || this.user.fullName === '' || this.user.license === '' || this.user.city === ''
      ||this.user.country === '' || this.user.postalCode === '' || this.user.address1 === '') {
        this.$notify({type:'warning',text: 'Input field is empty'});
      } 
      else {
        this.blockTheUser.city = this.user.city
        this.blockTheUser.license = this.user.licenseNumber
        this.blockTheUser.fullName = this.user.fullName
        this.blockTheUser.reason = this.user.reason
        this.blockTheUser.user.id = userid.id;
        this.blockTheUser.address1 = this.user.address1;
        this.blockTheUser.address2 = this.user.address2;
        this.blockTheUser.postalCode = this.user.postalCode;
        this.blockTheUser.country = this.user.country;
        if (this.user.licenseId !== null) {
          this.blockTheUser.licenseId = this.user.licenseId;
        }
        console.log('blockuser', this.blockTheUser);
        this.$http.post('api/blockLicense', this.blockTheUser, this.header).then(response => {
          if (response) {
            this.$notify({type:'success',text: 'License is blocked'});
            this.user = {}
            localStorage.removeItem('blocked-user');
          }
          }).catch(() => {
          this.$notify({type:'error',text: 'License is already blocked'});
        });
      }
    },
    resetUser() {
      this.enableUnblock = false;
      this.enableBlock = true;
      this.isAdmin();
    },
    isAdmin() {
        const role = localStorage.getItem('UserRole');
        if(role.includes('ROLE_ADMIN')) {
          this.enableBlock = false;
        }
    },
  },
  computed: {
    getUser() {
      this.logInUser = localStorage.getItem('token')
      return this.logInUser.accessToken
    },
   
  },
  mounted() {
      const blockedUser = JSON.parse(localStorage.getItem('blocked-user'));
      if(blockedUser != null) {
        this.user = blockedUser;
        this.enableUnblock = true;
        this.enableBlock = false;
      }
  },
  beforeDestroy() {
    localStorage.removeItem('blocked-user');
  },
  created() {
    this.isLogin();
    this.isAdmin();
  },
}
</script>
<style scoped>
  .min-height {
    min-height: 550px;  
  }
  .modal-style {
    margin-top: 50px;
    background-color: darkgrey;
  }
  .modal-bg {
    background-color: grey;
  }
  .search-box {
    height: 100%;
    width: 100%;
    position: fixed;
    /* color: transparent; */
    background-color: transparent;
    border: none;
  }
  .warning-style {
    color: red;
    font-size: bold;
  }
</style>
