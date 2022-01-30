<template>
    <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <card>
            <template slot="header">
              <h4 class="card-title margin-it">Hotel List 
              <!-- <button type="submit" class="btn btn-info btn-fill float-right"  @click="searhBlocked"> Search </button> -->
              </h4> 
            </template>
             <div class="col-12">
            <v-client-table :columns="columns" :data="tableData" :options="options" >
              <span slot="action" slot-scope="row">
                <button v-if="isActive(row)" type="button" class="btn btn-danger btn-sm btn-fill float-left"  @click.prevent="unblockRestaurant(row)">
                  Deactivate
                </button>
                <button v-else type="submit" class="btn btn-success btn-sm btn-fill float-left"  @click.prevent="unblockRestaurant(row)">
                  Activate
                </button>
              </span>
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
      unblockRestaurant(data) {
        this.$http.get('api/restaurant/deActive?restaurantId=' + data.row.id, this.header).then(response => {
          this.$notify({type:'success',text: response.data.message});
          this.searchRestaurant();
        }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
        
      },
      isActive(data) {
        return data.row.active;
      },
      searchRestaurant() {
        this.$http.get('api/restaurant', this.header).then(response => {
          this.tableData = response.data;
        }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      },
      // blockRestaurant(data) {
      //   this.$modal.show('Custom-modal');
      //   this.userDetails = data;
      // },
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
            if (response.data.isSuccess) {
              this.$notify({type:'success',text: 'License is unblocked'});
              this.$modal.hide('Custom-modal');
              this.searhBlocked();
            } else {
              this.$notify({type:'error',text: response.data.message});
            }
            }).catch((error) => {
            this.$notify({type:'error',text: error});
          });
        }
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
    },
    mounted() {
      this.searchRestaurant();
    }
  }
</script>
