<template>
<div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-12">
          <card style="height: 500px;">
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
              <!-- custom modal for popup -->
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
          this.enableUnblock = false;
          this.enableBlock = true;
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
