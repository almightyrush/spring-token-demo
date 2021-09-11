<template>
<div class="content">
    <div class="container-fluid center-element">
      <div class="row">
        <div class="col-md-3">
          <div class="container vertical-center">
            <card>
            <h4 slot="header" class="card-title float-center">Login</h4>
            <form>
              <div class="row">
                <div class="col-md-12">
                  <base-input type="text"
                            label="Username"
                            placeholder="Username"
                            v-model="input.username">
                  </base-input>
                </div>
              </div>

              <div class="row">
                <div class="col-md-12">
                  <base-input type="password"
                            label="Password"
                            placeholder="Password"
                            v-model="input.password">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="text-center col-md-12">
                <button type="submit" class="btn btn-info btn-fill" style="width: -webkit-fill-available;" @click.prevent="login">
                  Login
                </button>
              </div>
              </div>
            </form>
          </card>
          </div>
        </div>
      </div>
    </div>
</div>
</template>
<script>
  import Card from 'src/components/Cards/Card.vue'
  export default {
    name: 'Login',
    components: {
      Card
    },
    data() {
      return {
        input: {
          username: "",
          password: ""
        }
      }
    },
    methods: {
      login() {
        const data =  {username: this.input.username, password: this.input.password }
        this.$http.post('api/auth/signin', data).then(response => {
          if (response.data.accessToken) {
            localStorage.setItem('UserRole', JSON.stringify(response.data.roles[0]))
            localStorage.setItem('token', JSON.stringify(response.data));
            this.$notify({type:'success',text: 'Login successful'});
            localStorage.setItem('username', this.input.username);
            this.$router.push({name: 'Overview'});
          } else {
            this.$notify({type:'error',text: 'Incorrect username or password'});
          }
        }).catch(() => {
        this.$notify({type:'error',text: 'Incorrect username or password'});
      });;        
      },
    }
  }
</script>
<style scoped>
.vertical-center {
    margin-top: 10% auto;
    /* width: 30% !important; */
    /* border: 1px solid rgb(128, 128, 128); */
    padding: 20px;    
}
.center-element {
    position: relative;
    top: 50%;
    left: 35%;
    margin: 30px;
    padding: 30px;
}
</style>
