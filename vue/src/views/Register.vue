<template>
<body id="registration-body">
    <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <div>
          <label for="first-name">First Name</label>
        </div>
        <input type="text" id="first-name" v-model="user.firstName" required autofocus />
      </div>
      <div class="form-input-group">
        <div>
          <label for="last-name">Last Name</label>
        </div>
        <input type="text" id="last-name" v-model="user.lastName" required autofocus />
      </div>
      <div class="form-input-group">
        <div>
          <label for="username">Username</label>
        </div>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <div>
          <label for="email">E-mail</label>
        </div>
        <input type="text" id="email" v-model="user.email" required />
      </div>
      <div class="form-input-group">
        <div>
          <label for="password">Password</label>
        </div>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <div>
          <label for="confirmPassword">Confirm Password</label>
        </div>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</body>

</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
#registration-body {
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
  background-image: linear-gradient(to bottom, #ebfffd, #ebfffd, #b7f7f7);
  width: 33%;
  text-align: center;
  border-radius: 4%;
  height: 70vh;
  /* border-style: solid;
  border-color:  #b7f7f7;
  border-width: 3px; */
  opacity: 0.87;

  margin:100px ;
}
label {
  margin-right: 0.5rem;
}
#register {
   display: flex;
  justify-content: center;
  width: 100%;
  min-height: 100%;
}
</style>
