<template>
  <div class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <card class="strpied-tabled-with-hover"
                body-classes="table-full-width table-responsive">
            <template slot="header">
              <h4 class="card-title">Restaurant</h4>
            </template>
            <!-- <l-table class="table-hover table-striped"
                     :columns="table1.columns"
                     :data="table1.data">
            </l-table> -->
          </card>
        </div>
        <div class="col-12">
          <card class="card-plain">
            <template slot="header">
              <h4 class="card-title">Users</h4>
            </template>
            <div class="table-responsive">
              <!-- <l-table class="table-hover"
                       :columns="table2.columns"
                       :data="table2.data">
              </l-table> -->
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

  const tableColumns = ['Id', 'Name', 'Country', 'City']
  const tableData = [{
    id: 1,
    name: 'Dakota Rice',
    salary: '$36.738',
    country: 'Niger',
    city: 'Oud-Turnhout'
  },
  {
    id: 2,
    name: 'Minerva Hooper',
    salary: '$23,789',
    country: 'Curaçao',
    city: 'Sinaai-Waas'
  },
  {
    id: 3,
    name: 'Sage Rodriguez',
    salary: '$56,142',
    country: 'Netherlands',
    city: 'Baileux'
  },
  {
    id: 4,
    name: 'Philip Chaney',
    salary: '$38,735',
    country: 'Korea, South',
    city: 'Overland Park'
  },
  {
    id: 5,
    name: 'Doris Greene',
    salary: '$63,542',
    country: 'Malawi',
    city: 'Feldkirchen in Kärnten'
  }]
  export default {
    components: {
      LTable,
      Card
    },
    data () {
      return {
        header: {
          headers: AuthHeader()
        },
        table1: {
          columns: [...tableColumns],
          data: [...tableData]
        },
        table2: {
          columns: [...tableColumns],
          data: [...tableData]
        }
      }
    },
    methods: {
      getAllRestaurant() {
        this.$http.get('api/restaurant', this.header).then(response => {
          // this.allRestaurant = response.data;
          debugger;
          console.log('data ', response.data);
        }).catch(() => {
          this.$notify({type:'error',text: 'Session expired login again'});
          // this.$router.push({name: 'Login'});
        });
      },
      created() {
        debugger;
        this.getAllRestaurant();
      }
    }
  }
</script>
<style>
</style>
