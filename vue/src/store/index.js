import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'


Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const currentInitials = localStorage.getItem('initials');

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}
//added two things here (:
export default new Vuex.Store({
  state: {
    activeItem: {
      id: null,
      name: '',
      quantity: null,
      listId: null
    },
    token: currentToken || '',
    user: currentUser || {},
    initials: currentInitials || "",
    lists: [],
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SAVE_LIST(state, lists) {
      state.lists = lists;
    },
    ADD_NEW_LIST(state, list) {
      state.lists.push(list)
    },
    TOGGLE_COMPLETED(state, item) {
      state.activeItem.completed = item.completed;
    },

    ADD_TO_LIST(state, item) {
      for (let i = 0; i < state.lists.length; i++) {
        if (state.lists[i].id === item.listId) {
          state.lists[i].itemsInList.push(item)
        }
      }
    },
    DELETE_FROM_LIST(state, item) {


      for (let i = 0; i < state.lists.length; i++) {
        let currentList = state.lists[i];

        for (let j = 0; j < currentList.itemsInList.length; j++) {
          let currentItem = currentList.itemsInList[j]

          if (currentItem.id === item.id) {
            currentList.itemsInList.splice(j, 1);

          }
        }

        //splice index of

      }
    },

    UPDATE_ITEM(state, item) {
      for (let i = 0; i < state.lists.length; i++) {
        let currentList = state.lists[i];

        for (let j = 0; j < currentList.itemsInList.length; j++) {
          let currentItem = currentList.itemsInList[j]

          if (currentItem.id === item.id) {
            currentItem = item;

          }
        }

      }
    },

    SET_USER(state, user) {
      state.user = user;

      localStorage.setItem('user', JSON.stringify(user));
      state.initials = user.firstName.substring(0, 1) + user.lastName.substring(0, 1);
      localStorage.setItem('initials', state.initials);
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('initials');
      state.token = '';
      state.user = {};
      state.initials = '',
        axios.defaults.headers.common = {};
    },
    SET_ACTIVE_ITEM(state, data) {
      state.activeItem = data;
    }
  }
})
