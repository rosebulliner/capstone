<template>
<body id="login-body">
  <div id="login">
    <form class="form" @submit.prevent="login">
      <h1 >Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <div>
          <label for="username">Username</label>
          </div> 
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <div>  
          <label for="password">Password</label>
        </div>
      
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form>
  </div>
  </body>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/home");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
  /* width: 50%;
  background-color: #b7f7f7; */
}
#login-body {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-image:
            linear-gradient(to bottom,
              rgba(255,255,255, 0.2),
              rgba(255,255,255, 0.2),
              rgba(255,247,198, 0.5)), url('../../images/background4.png');
  /* background-image: linear-gradient(to bottom, #ebfffd, #ebfffd, #b7f7f7), url('../../images/background2.png'); */
  background-repeat: no-repeat;
  background-size: cover;
  margin: 0px;

  

  }

form {
  background-image: linear-gradient(to bottom, #ebfffd, #ebfffd);
  width: 33%;
  text-align: center;
  margin:100px ;
  border-radius: 4%;
  height: 50vh;
  opacity: 0.87;
  /* border-style: solid;
  border-color:  #b7f7f7;
  border-width: 3px; */
}
label {
  margin-right: 0.5rem;
}

#login {
  display: flex;
  justify-content: center;
  width: 100%;
  min-height: 100%;
}
</style>