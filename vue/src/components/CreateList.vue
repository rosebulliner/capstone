<template>
<form v-on:sumbit.prevent>
  <div class="field">
      <label for="title">Title</label>
      <input type="text" v-model="list.title">
  </div>
  <div class="actions">
      <button type="submit" v-on:click="saveList()">Add List</button>
  </div>
  </form>
</template>

<script>
// import List from "./List.vue"
import ListServices from "../services/ListServices.js"
export default {
    name: "create-list",
    data(){
        return {
            list: {
                id: Math.floor(Math.random() * (1000 - 100) + 100),
                title: ""
            }
        };
    },
    methods: {
    saveList() {
      ListServices.addList(this.list).then( (response) => {
        if(response.status === 201){
          this.$router.push( { name: 'home' } )
        }
      });
    }
  }
}
</script>

<style>
button{
  font-family: 'Happy Monkey', cursive;
}

form {
  padding: 20px;
  font-size: 16px;
}
form * {
  box-sizing: border-box;
  line-height: 1.5;
}
.field {
  display: flex;
  flex-direction: column;
}
.field label {
  margin: 4px 0;
  font-weight: bold;
}
.field input,
.field textarea {
  padding: 8px;
  font-size: 18px;
}
.field textarea {
  height: 300px;
}
.actions {
  text-align: right;
  padding: 10px 0;
}
</style>