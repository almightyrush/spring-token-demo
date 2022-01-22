<template>
<div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-12">
          <!-- v-show=false -->
          <card v-show="false" style="height: 500px;">
            <!-- <input type="text"  v-model="inputText"> -->
            <h4 slot="header" class="card-title">Scan License</h4>
            <form>
              <div>
                <div class="col-md-12">
                  <input type="text" ref="search" class="selector" v-model="inputText">
                  
                </div>
                <div class="col-md-4 d-flex justify-content-left align-items-center" style="margin-top: 10px;">
                  <button type="submit" class=" btn btn-info btn-fill float-left left-padding"  @click="showModal">
                  Search
                </button>
                </div>
              </div>
              <!-- custom modal for popup
              <div class="text-center">
                <main-modal name="Custom-modal"
                :width="500"
                :height="250"
                :adaptive="true"
                styles ="background-color:indianred">
                <div class="text-center modal-style">
                  <h3 slot="header" class="card-title warning-style">{{toggleValue}}</h3>
                </div>
                </main-modal>
                </div> -->
            </form>
          </card>

         
          <!-- scanner type 1 -->
           <card v-if="scannerType()">
            <h4 slot="header" class="card-title">Scan License</h4>
            <form>
            <hr/> 
              <div class="row">
                <div class="col-md-10">
                  <base-input tabindex="1" type="text"
                            label="Name"
                            placeholder="FullName" v-model="user.fullName">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-4">
                  <label id="address" for="address">Address</label> <br/>
                  <textarea name="address" 
                    rows="3" 
                    cols="35" 
                    tabindex="2"
                    style="resize: none;"
                    class="form-control"
                    placeholder="Address">
                  </textarea>
                </div>
                <div class="col-md-3">
                  <base-input type="text"
                            label="City"
                            placeholder="City" tabindex="3" v-model="user.city">
                  </base-input>
                </div>
                <div class="col-md-3">
                  <base-input type="text"
                            label="State"
                            placeholder="State" tabindex="4" v-model="user.state">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2">
                  <base-input type="text"
                            label="Zip Code"
                            placeholder="Zip Code" tabindex="5" v-model="user.postalCode">
                  </base-input>
                  <base-input  tabindex="6" type="text">
                  </base-input>
                  <base-input :disabled="true" tabindex="7" type="text">
                  </base-input>
                </div>
                <div class="col-md-8">
                  <base-input type="text"
                              label="License"
                              placeholder="License" tabindex="10" v-model="user.licenseNumber">
                  </base-input>
                </div>  
              </div>
              <div class="row" v-if="hideUnblock">
                <div class="col-md-8">
                  <base-input type="text"
                            label="Reason"
                            placeholder="Reason" v-model="user.reason">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2" v-if="hideBlock">
                  <button type="button" class="btn btn-danger btn-fill float-left" @click="blockUser">
                  Block
              </button>
                </div>
                <div class="col-md-2"  v-if="hideUnblock">
                  <button type="button" class="btn btn-success btn-fill float-left"  @click="unblockUser">
                  Unblock
              </button>
                </div>
                <div class="col-md-2" v-if="hideSearch">
                  <button type="button" class="btn btn-primary btn-fill float-left" @click="checkValidation">
                  Search
                </button>
                </div>
                <div class="col-md-2" v-if="!hideSearch">
                  <button type="reset" class="btn btn-default btn-fill float-left" @click="clearForm">
                  Clear
                  </button>
                </div>
              </div>
            </form>
          </card>
           <!-- Scanner type 2 -->
          <card v-else>
            <h4 slot="header" class="card-title">Scan License</h4>
            <form>
              <hr/>
              <div class="row">
                <div class="col-md-9">
                  <base-input type="text"
                            label="Name"
                            placeholder="FullName" v-model="user.fullName">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6">
                  <base-input type="text"
                              label="License"
                              placeholder="License" v-model="user.licenseNumber">
                  </base-input>
                </div>
                <div class="col-md-3">
                  <base-input type="text"
                            label="City"
                            placeholder="City" v-model="user.city">
                  </base-input>
                </div>
              </div>
              <div class="row" v-if="hideUnblock">
                <div class="col-md-7">
                  <base-input type="text"
                            label="Reason"
                            placeholder="Reason" v-model="user.reason">
                  </base-input>
                </div>
              </div>
               <div class="row">
                <div class="col-md-2" v-if="hideBlock">
                  <button type="button" class="btn btn-danger btn-fill float-left" @click="blockUser">
                  Block
                  </button>
                </div>
                <div class="col-md-2"  v-if="hideUnblock">
                  <button type="button" class="btn btn-success btn-fill float-left"  @click="unblockUser">
                  Unblock
                  </button>
                </div>
                <div class="col-md-2" v-if="hideSearch">
                  <button type="button" class="btn btn-primary btn-fill float-left" @click="checkValidation">
                  Search
                  </button>
                </div>
                <div class="col-md-2" v-if="!hideSearch">
                  <button type="reset" class="btn btn-default btn-fill float-left" @click="clearForm">
                  Clear
                </button>
                </div>
              </div>
            </form>
          </card>
          <div>
            <main-modal name="Custom-modal"
            :width="520"
            :height="170"
            :adaptive="true">
              <div class="modal-dialog modal-content" style="background-color: beige;">
                  <div class="modal-body">
                    <label> <h3 slot="header" class="card-title">{{toggleValue}}</h3></label>
                  </div>
              </div>
            </main-modal>
          </div>
           
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
        hideSearch: true,
        hideBlock: false,
        hideUnblock: false,
        inputText: '',
        inputUser: {
          licenseNo : '',
          fullName: '',
          city: '',
          country:'',
          postalCode: ''
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
        role: '',
        logInUser: '',
        toggleValue: '',
        header: { headers: AuthHeader() },
      }
    },
  methods: {
    scannerType() {
      this.role = JSON.parse(localStorage.getItem('token'));
      console.log(this.role.restaurant.scannerType);
      if(this.role.restaurant.scannerType === 'scanner_1'){
        return true;
      }
      return false;
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
      let input = {
        text: this.inputText
      }
      this.$http.post('api/parse', input, this.header).then(response => {
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
          this.inputText = '';
        }
        else {
          this.$notify({type:'success',text: 'License is not blocked'});
          this.inputText = '';
        }
        }).catch((error) => {
        this.$notify({type:'error',text: error});
      });
    },
    parseText(text) {
      let start = '%';
      text = start.concat(text);
      let end = '?';
      text = text.concat(end);
      return text;
    },
    checkValidation() {
        if (this.user.fullName === '' || this.user.licenseNo === '' || this.user.city === '' ) {
          this.$notify({type:'warning',text: 'Fields are empty'});
        }
        else {
          this.searchUser();
        }
      },
    searchUser() {
      this.inputUser.fullName = this.user.fullName;
      this.inputUser.licenseNo = this.user.licenseNumber;
      this.inputUser.city = this.user.city;
      this.inputUser.country = this.user.country;
      this.inputUser.postalCode = this.user.postalCode;
      this.$http.post('api/license/check', this.inputUser, this.header).then(response => {
        if (response.data.isBlocked) {
          const data = response.data;
          this.user.reason = data.reason
          this.toggleValue = 'License is blocked';
          this.$modal.show('Custom-modal');
          this.hideSearch = false;
          this.hideUnblock = true;
        }
        else {
          this.$notify({type:'success',text: 'License is not blocked'});
          this.hideSearch = false;
          this.hideBlock = true;
        }
        }).catch((error) => {
        this.$notify({type:'error',text: error});
      });
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
    },
    clearForm() {
      this.hideSearch = true;
      this.hideBlock =  false;
      this.hideUnblock = false;
      this.user = {
          fullName: '',
          licenseNumber: '',
          city: '',
          isBlocked: false,
          licenseId: '',
          reason: ''
        };
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
  },
  watch: {
    // inputText(newValue, oldValue) {
    //   if (newValue !== oldValue && newValue !== '') {
    //     this.showModal();
    //     this.$nextTick(() => {
    //       this.inputText = ''
    //     });;
    //   }
    // }
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
    background-color: transparent;
    border: none;
  }
  .warning-style {
    color: red;
    font-size: bold;
  }
  .left-padding {
    margin-left: 7px;
  }
  .selector { 
    height: 300px; 
    width: 700px;
    border: 1px solid lightgray;
  }
</style>
