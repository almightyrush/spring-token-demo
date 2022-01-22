<template>
    <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-10">
          <card>
            <h4 slot="header" class="card-title">Change Pin</h4>
            <form>
            <hr/> 
              <div class="row">
                <div class="col-md-4">
                  <base-input type="password"
                            label="Current Pin"
                            placeholder="Current Pin" v-model="user.pin">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-4">
                  <base-input type="password"
                            label="New Pin"
                            placeholder="New Pin" v-model="user.newPin">
                  </base-input>
                </div>
                 <div class="col-md-4">
                  <base-input type="password"
                            label="Re-enter New Pin"
                            placeholder="Re-enter New Pin" v-model="newPinAgain">
                  </base-input>
                </div>
              </div>
              <div class="row">
                <div class="col-md-2">
                  <button type="submit" class="btn btn-primary btn-fill float-left"  @click="checkPin">
                  Update
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
import Card from 'src/components/Cards/Card.vue';
import AuthHeader from 'src/auth-header.js';

export default {
    name: 'Pin-Change',
    components: {
      Card
    },
    data() {
      return {
        user: {
            newPin: '',
            pin: '',
            restaurantId: '',
            userId: ''
        },
        newPinAgain: '',
        currentUser: '',
        header: { headers: AuthHeader() },
      }
    },
    methods: {
        updatePin() {
            this.currentUser = JSON.parse(localStorage.getItem('token'));
            this.user.restaurantId = this.currentUser.restaurant.id;
            this.user.userId = this.currentUser.id;
            this.$http.post('api/restaurant/pinChange', this.user, this.header).then(response => {
            if (response) {
                this.$notify({type:'success',text: 'Pin changed'});
                this.user = {}
            }
            }).catch((error) => {
                this.$notify({type:'error',text: error});
            });
        },
        checkPin() {
             if (this.user.newPin === this.newPinAgain) {
                this.updatePin();
                return false;
            } else {
                this.$notify({type:'warning',text: 'Pin does not match'});
                return true;
            }
        },
    },
    computed: {
        
    }
}
</script>
