<template>
    <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <card>
            <template slot="header">
              <h4 class="card-title margin-it">Blocked License 
              <!-- <button type="submit" class="btn btn-info btn-fill float-right"  @click="searhBlocked"> Search </button> -->
              </h4> 
            </template>
             <div class="col-12">
            <v-client-table :columns="columns" :data="tableData" :options="options" >
            </v-client-table>
            </div>
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
    components: {
      Card
    },
    data () {
      return {
        columns: ['name', 'city' , 'country', 'address1', 'address2','scannerType','action'],
        tableData: [],
        options: {
          filterable: false,
          perPage: 10,
          perPageValues: [],
          pagination: {
            dropdown: false,
            show: true,
        },
        pin: '',
        userDetails: {},
        errorResponse: '',
        },
        header: { headers: AuthHeader() },
      }
    },
    methods: {
      searchUsers() {
        this.$http.get('api/user', this.header).then(response => {
            console.log(user);
          this.tableData = response.data;
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
        });
      },
      blockRestaurant(data) {
        this.$modal.show('Custom-modal');
        this.userDetails = data;
      },
      unblockUser() {
        const userobj = JSON.parse(localStorage.getItem('token'));
        if (this.pin === undefined) {
          this.$notify({type:'error',text: 'Enter Pin to unblock'});
        }
        else {
          const userParams= {
            restaurantId: userobj.restaurant.id,
            userId: userobj.id,
            licenceId: this.userDetails.row.id,
            pin: this.pin
          }
          this.$http.post('api/blockLicense/unblock', userParams, this.header).then(response => {
            console.log('resposne is ',  response);
            if (response.data.isSuccess) {
              this.$notify({type:'success',text: 'License is unblocked'});
              this.$modal.hide('Custom-modal');
              this.searhBlocked();
            } else {
              this.$notify({type:'error',text: response.data.message});
            }
            }).catch((error) => {
            console.log(error);
            this.$notify({type:'error',text: error});
          });
        }
      }
    },
    mounted() {
    //   this.searchUsers();
    }
  }
</script>
