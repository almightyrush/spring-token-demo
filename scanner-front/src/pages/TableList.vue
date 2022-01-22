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
              <span slot="action" slot-scope="row">
                <button :disabled="isAdmin(row)" type="submit" class="btn btn-success btn-sm btn-fill float-left"  @click.prevent="blockUser(row)">
                  Unblock
                </button>
              </span>
            </v-client-table>
            <div>
                <main-modal name="Custom-modal"
                :width="520"
                :height="210"
                :adaptive="true">
                  <div class="modal-dialog modal-content">
                      <div class="modal-body">
                        <label>Enter pin to unblock</label>
                        <base-input type="password"
                            placeholder="Pin"
                            v-model="pin">
                        </base-input>
                        <button type="button" class="btn btn-success btn-fill"  @click="unblockUser">Unblock</button>
                      </div>
                  </div>
                </main-modal>
            </div>
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
        columns: ['fullName', 'license','city' , 'country', 'address1', 'address2','postalCode' ,'reason','action'],
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
      isAdmin(data) {
        console.log(data);
        const role = localStorage.getItem('UserRole');
        const restaurant = data.row.user.restaurant.id;
        const userid = JSON.parse(localStorage.getItem('token'));
        if(role.includes('ROLE_ADMIN') || userid.restaurant.id === restaurant) {
          return true;
        }
        return false;
      },
      searhBlocked() {
        this.$http.get('api/blockLicense', this.header).then(response => {
          this.tableData = response.data;
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
        });
      },
      blockUser(data) {
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
      this.searhBlocked();
    }
    // created() {
    //   this.searchBlocked();
    // }
  }
</script>
<style>
.VuePagination {
  text-align: center;
  -webkit-box-align: center !important;
  align-items: center !important;
  -webkit-box-pack: center !important;
  justify-content: center !important;
  display: flex !important;
}

.vue-title {
  text-align: center;
  font-size: 200 !important;
  margin-bottom: 10px;
}
.margin-it {
  margin-left: 20px !important;
  margin-right: 20px !important;
}
.fullNameClass{
  width: 20%;
}
.border-style {
  border: 5px solid grey;
}
</style>
