<template>
  <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-10">
          <card>
            <template slot="header">
              <h4 class="card-title margin-it">Blocked License 
              <button type="submit" class="btn btn-info btn-fill float-right"  @click="searhBlocked"> Search </button></h4> 
            </template>
             <div class="col-12">
            <v-client-table :columns="columns" :data="tableData" :options="options" >
              <span slot="action" slot-scope="row">
                <button type="submit" class="btn btn-success btn-sm btn-fill float-left"  @click.prevent="blockUser(row)">
                  Unblock
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
  import LTable from 'src/components/Table.vue';
  import Card from 'src/components/Cards/Card.vue';
  import AuthHeader from 'src/auth-header.js';

  export default {
    components: {
      LTable,
      Card
    },
    data () {
      return {
        columns: ['fullName', 'city' , 'license', 'reason','action'],
        tableData: [],
        options: {
            filterable: false,
            perPage: 10,
            perPageValues: [],
            pagination: {
              dropdown: false,
              show: true,
            },
            columnsClasses: {
              'fullName': 'fullNameClass',
              'city': 'fullNameClass',
              'license': 'fullNameClass',
              'reason': 'fullNameClass',
              'action': 'fullNameClass'
            }
        },
        header: { headers: AuthHeader() },
      }
    },
    methods: {
      searhBlocked() {
        this.$http.get('api/blockLicense', this.header).then(response => {
          this.tableData = response.data;
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
        });
      },
      blockUser(data) {
         this.$http.delete('api/blockLicense'+ '?licenseId=' + data.row.id, this.header).then(response => {
          if (response) {
            this.$notify({type:'success',text: 'License is unblocked'});
            this.searhBlocked();
          }
          }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      },
    },
    created() {
      this.searchBlocked();
    }
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
</style>
