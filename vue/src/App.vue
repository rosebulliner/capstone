<template>
  <div id="app">
    <div id="nav">
      <router-link :to="{ name: 'home' }"><img id="logo" src="../images/logo2.png"/></router-link>
      <!-- <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp; -->

      <div v-on:click="dropdown()" class='circle'>
        <p class= 'circle-inner'>{{ $store.state.initials }}</p>
        <div v-if="active">
          <router-link class="logout" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
        </div>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>

export default {
  data(){
    return {
      active: false
    }
  },
  methods: {
    dropdown() {
      this.active = !this.active;
    }
  }
}
</script>


<style scoped>
*{
 font-family: 'Happy Monkey', cursive;
}
button{
  font-family: 'Happy Monkey', cursive;
}

#logo {
  width: 75%;
}
#nav {
  position: fixed; top:0; left: 0;; z-index: 9999;
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: space-between;
  text-align: center;
  background-color: #ebfffd;
  opacity: 0.9;
  /* border-bottom-style: solid;
  border-bottom-color: #b7f7f7;
  border-bottom-width: 3px; */
}

.circle{
  cursor: pointer;
  display: inline-block;
  background-color: #fde896;
  margin: 10px;
  border-radius: 50%;
  height: 50px;
  width: 50px;
  align-self: center;
  border: 2px solid black;
  
}

.circle-inner{
  text-transform: uppercase;
  font-weight: bold;
}
#profile {
   margin-left: auto;
   align-self: center;
}

.logout{
  color: #fde896;
  text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
  margin-bottom: 10px;
  font-weight: bold;
  text-decoration: none;
}
</style>
