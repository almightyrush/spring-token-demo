<template>
<div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-10">
          <card>
            <h4 slot="header" class="card-title">Scan License</h4>
            <form>
              <div class="row">
                <div class="col-md-4">
                  <label>Search </label>
                  <base-input type="text"
                            placeholder="Search" v-model="input.text">
                  </base-input>
                </div>
                <div class="col-md-4 d-flex justify-content-left align-items-center" style="margin-top: 10px;">
                  <button type="submit" class=" btn btn-info btn-fill float-left"  @click="showModal">
                  Search
                </button>
                </div>
              </div>
              <!-- custom modal for popup -->
              <div class="text-center">
                <main-modal name="Custom-modal"
                :width="350"
                :height="150"
                :adaptive="true"
                styles ="background-color:grey">
                <div class="text-center modal-style">
                  <h4 slot="header" class="card-title">{{toggleValue}}</h4>
                </div>
                </main-modal>
              </div><hr/> 

              <div class="row">
                <div class="col-md-9">
                  <base-input type="text"
                            label="Name"
                            placeholder="Name" v-model="user.fullName"
                            disabled>
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6">
                  <base-input type="text"
                            label="License"
                            placeholder="License" v-model="user.licenseNumber"
                            disabled>
                  </base-input>
                </div>
                <div class="col-md-3">
                  <base-input type="text"
                            label="City"
                            placeholder="City" v-model="user.city"
                            disabled>
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-9">
                  <base-input type="text"
                            label="Reason"
                            placeholder="Reason" v-model="user.reason">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2">
                  <button type="submit" class="btn btn-danger btn-fill float-left"  @click="blockUser" :disabled="enableBlock">
                  Block
              </button>
                </div>
                <div class="col-md-2">
                  <button type="submit" class="btn btn-success btn-fill float-left"  @click="unblockUser" :disabled="enableUnblock">
                  UnBlock
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
        input: {
          text: '',
        },
        user: {
          fullName: '',
          licenseNumber: '',
          city: '',
          isBlocked: false,
          licenseId: '',
          reason: ''
        },
        blockTheUser: {
            reason:'',
            user:{
              id: ''
            },
            license: '',
            fullName: '',
            city: ''
        },
        enableUnblock: true,
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
    showModal() {
      if (this.input.text === '') {
        this.$notify({type:'warning',text: 'Input field is empty'});
      } 
      else {
        this.$http.post('api/parse', this.input, this.header).then(response => {
          if (response.data.isBlocked) {
            const data = response.data;
            this.user.city= data.city
            this.user.fullName= data.fullName
            this.user.isBlocked= data.isBlocked
            this.user.licenseId= data.licenseId
            this.user.licenseNumber= data.licenseNumber
            this.user.reason = data.reason
            this.toggleValue = 'License is blocked';
            this.$modal.show('Custom-modal');
            this.enableUnblock = false;
            this.enableBlock = true;
          }
          else {
            const data = response.data;
            this.user.city= data.city
            this.user.fullName= data.fullName
            this.user.isBlocked= data.isBlocked
            this.user.licenseId= data.licenseId
            this.user.licenseNumber= data.licenseNumber
            this.user.reason= data.reason
            this.toggleValue = 'License is not blocked';
            this.$modal.show('Custom-modal');
            this.enableBlock = false;
            this.enableUnblock = true;
          }
          }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      }
    },
    blockUser() {
      const userid = JSON.parse(localStorage.getItem('token'));
      if (this.user.reason === '') {
        this.$notify({type:'warning',text: 'Input field is empty'});
      } 
      else {
        this.blockTheUser.city = this.user.city
        this.blockTheUser.license = this.user.licenseNumber
        this.blockTheUser.fullName = this.user.fullName
        this.blockTheUser.reason = this.user.reason
        this.blockTheUser.user.id = userid.id;
        this.$http.post('api/blockLicense', this.blockTheUser, this.header).then(response => {
          if (response) {
            this.$notify({type:'success',text: 'License is blocked'});
            this.user = {}
          }
          }).catch(() => {
          this.$notify({type:'error',text: 'License is already blocked'});
        });
      }
    },
    unblockUser() {
        this.$http.delete('api/blockLicense'+ '?licenseId=' + this.user.licenseId, this.header).then(response => {
          if (response) {
            this.$notify({type:'success',text: 'License is unblocked'});
            this.user = {}
          }
          }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
    }
  },
  computed: {
    getUser() {
      this.logInUser = localStorage.getItem('token')
      return this.logInUser.accessToken
    }
  },
  created() {
    this.isLogin();
  } 
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
</style>>

</style>
