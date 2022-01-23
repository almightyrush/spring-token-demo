<template>
    <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <card>
            <template slot="header">
              <h4 class="card-title margin-it">User List 
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
        columns: ['firstName', 'lastName' , 'userName', 'restaurant.name'],
        tableData: [],
        options: {
          filterable: false,
          perPage: 10,
          perPageValues: [],
          pagination: {
            dropdown: false,
            show: true,
            },
            headings: {
                'restaurant.name': 'Restaurant'
            },
        userDetails: {},
        errorResponse: '',
        },
        pin:'',
        header: { headers: AuthHeader() },
      }
    },
    methods: {
      searchUsers() {
        const userobj = JSON.parse(localStorage.getItem('token'));
        this.$http.get('api/restaurant/users?userId='+userobj.id, this.header).then(response => {
            this.tableData = response.data;
        }).catch((error) => {
          this.$notify({type:'error',text: error});
        });
      },
    },
    mounted() {
      this.searchUsers();
    }
  }
</script>
