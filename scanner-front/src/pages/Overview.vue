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
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-info btn-fill float-left"  @click="showModal">
                  Search
                </button>
                <main-modal name="Custom-modal"
                :width="350"
                :height="150"
                :adaptive="true"
                styles ="background-color:grey">
                <div class="text-center modal-style">
                  <h4 slot="header" class="card-title">{{toggleValue}}</h4>
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
        input: {
          text: '',
        },
        role: '',
        username: '',
        toggleValue: 'License is not blocked',
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
      localStorage.removeItem('username');
      this.$router.push({name: 'Login'});
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
    },
    showModal() {
      if (this.input.text === '') {
        this.$notify({type:'warning',text: 'Input field is empty'});
      } 
      else {
        this.$http.post('api/parse', this.input, this.header).then(response => {
          if (response) {
            response.data = this.toggleValue;
            this.$modal.show('Custom-modal');
          }
          }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      }
    }
  },
  computed: {
    getUsername() {
      this.username = localStorage.getItem('username')
      return username
    },
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
